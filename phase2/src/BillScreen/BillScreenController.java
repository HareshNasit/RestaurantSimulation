package BillScreen;

import Restaurant.Bill;
import Restaurant.Dish;
import Restaurant.Restaurant;
import Restaurant.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BillScreenController implements Initializable {

  public ComboBox scrollMenu;
  public Button payButton;
  public Button receiptButton;
  public Table table;
  public Restaurant restaurant;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  /** */
  public void createReceiptWindow() {
    String customer = (String) scrollMenu.getValue();
    if (!(customer == null)) {
      if (customer.equals("All")) {
        System.out.println(customer + "haha");
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

  public void createRestaurantReceiptWindow(Table table) {
    Alert alert =
        new Alert(
            Alert.AlertType.CONFIRMATION, Bill.outputBill(table), ButtonType.OK, ButtonType.CANCEL);
    alert.setTitle("Is this all correct?");
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.showAndWait();
  }

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

  private String getTextTipInput() {

    Dialog dialog = new TextInputDialog();
    dialog.setTitle("Tip Dialog");
    dialog.setHeaderText("Enter your tip");
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

  private boolean validTipEntry(String tip) {
    try {
      double tipAmount = Double.parseDouble(tip);
      boolean fail = (BigDecimal.valueOf(tipAmount).scale() > 2);
      return !fail && tipAmount >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public void createPaymentWindow() {
    String customer = (String) scrollMenu.getValue();
    if (!(customer == null)) {

      if (customer.equals("All")) {
        double tip = getTip(Bill.getSubtotal(table.getTableOrder()));
        if (!(tip == -1)) {
          Alert alert =
              new Alert(
                  Alert.AlertType.CONFIRMATION,
                  Bill.finalPaymentBillTable(table, tip),
                  ButtonType.YES,
                  ButtonType.CANCEL);
          alert.setTitle("Is this all correct?");
          alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
          alert.showAndWait();
          if (alert.getResult() == ButtonType.YES) {
            restaurant.writeToRECEIPTFILE(Bill.finalPaymentBillTable(table, tip));
          }
        }

      } else {
        double tip = getTip(Bill.getSubtotal(table.getTableOrder()));
        if (tip != -1) {
          int customerNum =
              Integer.parseInt(customer.substring(customer.length() - 1, customer.length()));
          Alert alert =
              new Alert(
                  Alert.AlertType.CONFIRMATION,
                  Bill.finalPaymentSinglePerson(table, customerNum, tip),
                  ButtonType.YES,
                  ButtonType.CANCEL);
          alert.setTitle("Is this all correct?");
          alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
          alert.showAndWait();
          if (alert.getResult() == ButtonType.YES) {
            restaurant.writeToRECEIPTFILE(Bill.finalPaymentSinglePerson(table, customerNum, tip));
          }
        }
      }

    } else {
      Alert alert2 = new Alert(Alert.AlertType.WARNING, "Select a Customer", ButtonType.OK);
      alert2.showAndWait();
    }
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }
}
