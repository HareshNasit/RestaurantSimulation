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

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements EventHandler<ActionEvent>, Initializable {

    public Button cancel;
    public Button addition;
    public Button accept;
    public Button subtract;
    public Dish dish;
    public TableView tableView;
    public TableColumn ingredientColumn;
    public TableColumn amountColumn;
    public TableColumn amountLeftColumn;

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

        this.tableView.refresh();
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
        this.ingredients = dish.getIngredients();
    }

    public void setSelectedIngredient(String ingredient) {
        this.selectedIngredient = ingredient;
    }



    /**
     * After the constructor is called, this is called.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getIngredientColumn().setCellValueFactory(new PropertyValueFactory<DishIngredient, String>("name"));
        getAmountColumn().setCellValueFactory(new PropertyValueFactory<DishIngredient, Integer>("amount"));
        getAmountLeftColumn().setCellValueFactory(new PropertyValueFactory<Table, Integer>(""));

    }

    /**
     * Sets the UI tables to show the Restaurant list of tables
     *
     * @param
     */
    public void setIngredients() {
        getTableView().setItems(getDishIngredient());
    }

    /**
     * Returns an ObservableList of the Restaurant's table list
     *
     * @return ObservableList of Tables
     */
    private ObservableList<DishIngredient> getDishIngredient() {
        ObservableList<DishIngredient> dishIngredients = FXCollections.observableArrayList();
        dishIngredients.addAll(getDishIngredients());
        return dishIngredients;
    }

    private ArrayList<DishIngredient> getDishIngredients(){
        ArrayList<DishIngredient> ingredients = new ArrayList<>();
        for(String key: this.ingredients.keySet()){
            ingredients.add(this.ingredients.get(key));
        }
        return ingredients;
    }


    public TableColumn getAmountColumn() {
        return amountColumn;
    }

    public TableColumn getAmountLeftColumn() {
        return amountLeftColumn;
    }

    public TableColumn getIngredientColumn() {
        return ingredientColumn;
    }

    public TableView getTableView() {
        return tableView;
    }
}
