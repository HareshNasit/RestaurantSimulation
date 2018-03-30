package billScreen;

import Restaurant.Bill;
import Restaurant.Restaurant;
import Restaurant.Table;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BillScreenController extends GridPane {

  @FXML
  private ComboBox scrollMenu;
  @FXML
  private Button payButton;
  @FXML
  private Button receiptButton;
  private Table table;
  private Restaurant restaurant;

  /**
   * Creates a bill screen window that allows customers to pay their bill.
   *
   * @param restaurant Restaurant the table is at.
   * @param table table which is paying.
   */
  public BillScreenController(Restaurant restaurant, Table table) {

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bill.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
      this.restaurant = restaurant;
      setTable(table);
    } catch (IOException e) {

    }
  }

  /**
   * Creates a receipt window, so the customer can see how much they owe and which dishes they
   * ordered.
   */
  public void createReceiptWindow() {
    String customer = (String) scrollMenu.getValue();
    if (!(customer == null)) {
      if (customer.equals("All")) {
        Alert alert =
            new Alert(
                Alert.AlertType.CONFIRMATION,
                Bill.outputBill(table),
                ButtonType.OK,
                ButtonType.CANCEL);
        alert.setTitle("Is this all correct?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
      } else {
        int customerNum =
            Integer.parseInt(customer.substring(customer.length() - 1, customer.length()));
        Alert alert =
            new Alert(
                Alert.AlertType.CONFIRMATION,
                Bill.outputSingleBill(table, customerNum),
                ButtonType.OK,
                ButtonType.CANCEL);
        alert.setTitle("Is this all correct?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
      }

    } else {
      Alert alert2 = new Alert(Alert.AlertType.WARNING, "Select a Customer", ButtonType.OK);
      alert2.showAndWait();
    }
  }

  /**
   * Creates a receipt window for the whole table.
   */
  public void createRestaurantReceiptWindow(Table table) {
    Alert alert =
        new Alert(
            Alert.AlertType.CONFIRMATION, Bill.outputBill(table), ButtonType.OK, ButtonType.CANCEL);
    alert.setTitle("Is this all correct?");
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.showAndWait();
  }

  /**
   * Sets the table and adds labels to drop down menu.
   */
  public void setTable(Table table) {
    this.table = table;
    ArrayList<String> menuLabels = new ArrayList<String>();

    for (int i = 1; i <= table.getTableSize(); i++) {
      menuLabels.add("Customer " + i);
    }
    menuLabels.add("All");
    ObservableList<String> labels = FXCollections.observableArrayList();
    labels.addAll(menuLabels);
    scrollMenu.setItems(labels);
  }

  /**
   * Gets the tip from the customer.
   *
   * @param subtotal if the customer selects a percent tip, then subtotal is used.
   */
  public double getTip(double subtotal) {
    double tip = -1;
    ButtonType percent = new ButtonType("%");
    ButtonType dollarAmount = new ButtonType("$");
    Alert alert =
        new Alert(
            Alert.AlertType.CONFIRMATION,
            "Pick tip type",
            percent,
            dollarAmount,
            ButtonType.CANCEL);

    alert.showAndWait();
    if (alert.getResult() == percent) {
      String input = getTextTipInput();
      if (!input.equals("")) {
        tip = Bill.getPercentWithTip(subtotal, Double.parseDouble(input));
      }
    } else if (alert.getResult() == dollarAmount) {
      String input = getTextTipInput();
      if (!input.equals("")) {
        tip = Double.parseDouble(input);
      }
    }
    return tip;
  }


  /**
   * Gets a valid tip input.
   *
   * @return String
   */
  private String getTextTipInput() {

    Dialog dialog = new TextInputDialog();
    dialog.setTitle("Tip Dialog");
    dialog.setHeaderText(
        "Enter your tip."
            + System.lineSeparator()
            + "Format: no more than 2 decimal places"
            + System.lineSeparator()
            + "Ex. 2.02 ");
    Optional<String> result = dialog.showAndWait();
    String entered = "";

    while (result.isPresent() && ((result.get()).equals("") || !validTipEntry(result.get()))) {
      result = dialog.showAndWait();
    }
    if (result.isPresent()) {
      entered = result.get();
      System.out.println(entered);
    }
    return entered;
  }

  /**
   * Checks if the String is a number and has no more than 2 decimals.
   *
   * @param tip text passed in from the TextInputDialog.
   */
  private boolean validTipEntry(String tip) {
    try {
      double tipAmount = Double.parseDouble(tip);
      boolean fail = (BigDecimal.valueOf(tipAmount).scale() > 2);
      return !fail && tipAmount >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }


  /**
   * Creates the payment window for the customer.
   */
  public void createPaymentWindow() {
    String customer = (String) scrollMenu.getValue();
    if (!(customer == null)) {

      if (customer.equals("All")) {
        double tip = getTip(Bill.getSubtotal(table.getTableOrder()));
        if (!(tip == -1)) {
          allBillConfirmationHelper(tip);
        }

      } else {
        double tip = getTip(Bill.getSubtotal(table.getTableOrder()));
        if (tip != -1) {
          customerBillConfirmationHelper(customer, tip);
        }
      }

    } else {
      Alert alert2 = new Alert(Alert.AlertType.WARNING, "Select a Customer", ButtonType.OK);
      alert2.showAndWait();
    }
  }

  /**
   * Sets the restaurant.
   *
   * @param restaurant The Restaurant.
   */
  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  /**
   * Helper method for writing receipt to txt (if the customer accepts).
   *
   * @param customer Customer from the drop down menu.
   * @param tip tip that the customer payed.
   */
  private void customerBillConfirmationHelper(String customer, double tip) {
    int customerNum =
        Integer.parseInt(customer.substring(customer.length() - 1, customer.length()));
    if (alertBillHelper(Bill.finalPaymentSinglePerson(table, customerNum, tip), tip)
        == ButtonType.YES) {
      restaurant.writeToRECEIPTFILE("##################################" + System.lineSeparator());
      restaurant.writeToRECEIPTFILE(Bill.finalPaymentSinglePerson(table, customerNum, tip));
    }
  }

  /**
   * Helper method for writing receipt to txt for the whole table (if the person paying accepts).
   *
   * @param tip tip that the customer payed.
   */
  private void allBillConfirmationHelper(double tip) {
    if (alertBillHelper(Bill.finalPaymentBillTable(table, tip), tip) == ButtonType.YES) {
      restaurant.writeToRECEIPTFILE("##################################" + System.lineSeparator());
      restaurant.writeToRECEIPTFILE(Bill.finalPaymentBillTable(table, tip));
    }
  }


  /**
   * Helper method for getting customer input.
   *
   * @param bill The String representation of the bill.
   * @param tip The tip amount.
   */
  private ButtonType alertBillHelper(String bill, double tip) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, bill, ButtonType.YES, ButtonType.CANCEL);
    alert.setTitle("Is this all correct?");
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.showAndWait();
    return alert.getResult();
  }
}
