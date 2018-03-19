package BillScreen;

import Restaurant.Dish;
import javafx.fxml.Initializable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BillController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void generateReceipt(ArrayList<Dish> dishes){
        String fileName = "";
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {

        }catch(Exception e){
        }
    }


}
