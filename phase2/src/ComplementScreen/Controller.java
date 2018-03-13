package ComplementScreen;

import Restaurant.Dish;
import Restaurant.DishIngredient;
import Restaurant.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements EventHandler<ActionEvent> {

    public Button cancel;
    public Button addition;
    public Button accept;
    public Button subtract;
    public Dish dish;
    public ScrollPane scrollWindow;
    private HashMap<String, DishIngredient> ingredients;
    FXMLLoader loader;


    public Controller() {
        // this.dish = dish;
        //this.ingredients = dish.getIngredients();
        try {
            loader = new FXMLLoader(getClass().getResource("complements.fxml"));
            addition.setOnAction(this::handle);
            subtract.setOnAction(this);
        } catch (Exception e) {

        }
    }

    @Override
    public void handle(ActionEvent event) {
        if ((event.getSource()) == addition) {
            System.out.println("hey");
        } else if (((Button) event.getSource()).getId().equals("subtract")) {
            System.out.println("hey");
        } else if (((Button) event.getSource()).getText().equals("accept")) {
            System.out.println("hey");

        } else if (((Button) event.getSource()).getText().equals("addition")) {
            System.out.println("hey");

        }
    }

    public void displayScreen() throws Exception {
        Stage window = new Stage();

        //This means that you can't change any other windows besides this one.
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Table Screen");
        Parent root = loader.load();
        window.setScene(new Scene(root));
        window.show();

    }


}
