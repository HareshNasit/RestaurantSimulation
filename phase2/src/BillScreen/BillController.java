package BillScreen;

import Restaurant.Bill;
import Restaurant.Dish;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class BillController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    /**
     *
     * @param dishes Non empty list of the customer's Dishes
     */
    public void createCustomerReceiptWindow(ArrayList<Dish> dishes){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Bill.outputBill(dishes),
                ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Is this all correct?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public void createRestaurantReceiptWindow(ArrayList<Dish> dishes){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, Bill.outputBill(dishes),
                ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Is this all correct?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }


    /**
     *
     * @param dishes
     */
    public void generateCustomerReceiptTxt(ArrayList<Dish> dishes){
        Date date = new Date();

        String fileName = "";
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {

        }catch(Exception e){
        }
    }

    /**
     *
     */
    public void generateRestaurantReceiptTxt(){
        Date date = new Date();

        String fileName = "";
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {

        }catch(Exception e){
        }
    }





}
