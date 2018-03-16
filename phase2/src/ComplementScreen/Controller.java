package ComplementScreen;

import Restaurant.Dish;
import Restaurant.DishIngredient;
import Restaurant.Menu;
import Restaurant.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements EventHandler<ActionEvent>, Initializable {

    public Button cancel;
    public Button addition;
    public Button accept;
    public Button subtract;
    public Dish dish;
    public ScrollPane scrollWindow;
    private HashMap<String, DishIngredient> ingredients;
    FXMLLoader loader;
    private String selectedIngredient;

    @Override
    public void handle(ActionEvent event) {

        if ((event.getSource()) == addition) {
            this.dish.getIngredients().containsKey(this.selectedIngredient);
            if (this.dish.getIngredients().get(selectedIngredient).amountCanBeAdded(1)) {
                this.dish.addIngredient(selectedIngredient, 1);
            }
            System.out.println(dish.getIngredients().get(selectedIngredient).getAmount());
        } else if ((event.getSource()) == subtract) {
            if (this.dish.getIngredients().get(selectedIngredient).amountCanBeSubtracted(1)) {
                this.dish.subtractIngredient(selectedIngredient, 1);
            }
            System.out.println(dish.getIngredients().get(selectedIngredient).getAmount());
        } else if ((event.getSource()) == accept) {

            System.out.println(this.selectedIngredient);
        } else if ((event.getSource()) == cancel) {

        }

        if (this.dish.getIngredients().get(selectedIngredient).amountCanBeAdded(1)) {
            addition.setDisable(false);
            System.out.println("enabled bro add");
        } else {
            addition.setDisable(true);
            System.out.println("disabled bro add");
        }
        if (this.dish.getIngredients().get(selectedIngredient).amountCanBeSubtracted(1)) {
            subtract.setDisable(false);
            System.out.println("enabled bro subtract");
        } else {
            System.out.println("disabled bro subtract");
            subtract.setDisable(true);
        }

    }

    public void addUserData(Dish dish) {
        this.dish = dish;
        System.out.println(this.dish);
        this.selectedIngredient = "sausage";
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

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setSelectedIngredient(String ingredient) {
        this.selectedIngredient = ingredient;
    }

    /**
     * After the constructor is called, this is called.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
