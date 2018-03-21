package BillScreen;

import Restaurant.Bill;
import Restaurant.Dish;
import Restaurant.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class BillScreenController implements Initializable {


    public ComboBox scrollMenu;
    public Button payButton;
    public Button receiptButton;
    public Table table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    /**
     *
     *
     */
    public void createReceiptWindow() {
        String customer = (String) scrollMenu.getValue();
        if (!(customer == null)) {
            if (customer.equals("All")) {
                System.out.println(customer + "haha");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Bill.outputBill(table.getTableOrder()),
                        ButtonType.OK, ButtonType.CANCEL);
                alert.setTitle("Is this all correct?");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            } else {
                int customerNum = Integer.parseInt(customer.substring(customer.length() - 1, customer.length()));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Bill.outputBill(table.getCustomerOrder(customerNum)),
                        ButtonType.OK, ButtonType.CANCEL);
                alert.setTitle("Is this all correct?");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }

        } else {
            Alert alert2 = new Alert(Alert.AlertType.WARNING, "Select a Customer",
                    ButtonType.OK);
            alert2.showAndWait();
        }


    }

    public void createRestaurantReceiptWindow(ArrayList<Dish> dishes) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Bill.outputBill(dishes),
                ButtonType.OK, ButtonType.CANCEL);
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

    public double getTip() {
        ButtonType percent = new ButtonType("%");
        ButtonType dollarAmount = new ButtonType("$");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Pick tip type",
                percent, dollarAmount, ButtonType.CANCEL);


        Dialog dialog = new TextInputDialog();
        dialog.setTitle("Tip Dialog");
        dialog.setHeaderText("Enter your tip");
        Optional<String> result = dialog.showAndWait();
        String entered = "none.";

        if (result.isPresent()) {

            entered = result.get();
        }

        return 0;
    }

    /**
     *
     *
     */
    public void generateCustomerReceiptTxt() {
        Date date = new Date();

        String fileName = "receipt2.txt.txt";
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {

        } catch (Exception e) {
        }
    }

    /**
     *
     */
    public void generateRestaurantReceiptTxt() {
        Date date = new Date();

        String fileName = "";
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {

        } catch (Exception e) {
        }
    }


}
