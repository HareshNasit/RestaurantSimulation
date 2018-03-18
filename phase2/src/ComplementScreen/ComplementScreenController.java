package ComplementScreen;

import Restaurant.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.net.URL;
import java.util.*;

public class ComplementScreenController implements EventHandler<ActionEvent>, Initializable {

    public Button cancel;
    public Button addition;
    public Button accept;
    public Button subtract;
    public Dish dish;
    public TableView tableView;
    public TableColumn ingredientColumn;
    public TableColumn amountColumn;
    public Inventory inventory;
    public TableColumn priceColumn;
    public TextArea dishStatusLabel;

    private HashMap<String, DishIngredient> ingredients;
    private String selectedIngredient;

    /**
     * After the constructor is called, this is called.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getIngredientColumn().setCellValueFactory(new PropertyValueFactory<DishIngredient, String>("name"));
        getAmountColumn().setCellValueFactory(new PropertyValueFactory<DishIngredient, Integer>("amount"));
        getPriceColumn().setCellValueFactory(new PropertyValueFactory<DishIngredient, Integer>("price"));
        this.selectedIngredient = "";
        this.updateDishLabel();
        this.setRowAction();
    }


    @Override
    public void handle(ActionEvent event) {
        try {
            this.updateSelectedIngredient();
            this.updateButtonAddSubDisabled();
            this.addAndSubtractEvent(event);
            this.updateButtonAddSubDisabled();
            this.tableView.refresh();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a complement!",
                    ButtonType.OK);
            alert.showAndWait();
        }

    }

    public void acceptEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you are finished?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            this.closeWindow(accept);
        }
    }

    public void cancelEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            this.dish.setToBaseIngredients();
            this.closeWindow(cancel);
            System.out.println(this.dish);
        }
    }

    public void updateDishLabel(){
        dishStatusLabel.setText(this.dish.toString());
    }

    public void addAndSubtractEvent(ActionEvent event) {

        if ((event.getSource()) == addition) {
            if (this.dish.getIngredients().get(selectedIngredient).amountCanBeAdded(1)) {
                this.dish.addIngredient(selectedIngredient, 1);
            }
            System.out.println(dish.getIngredients().get(selectedIngredient).getAmount());
        } else if ((event.getSource()) == subtract) {
            if (this.dish.getIngredients().get(selectedIngredient).amountCanBeSubtracted(1)) {
                this.dish.subtractIngredient(selectedIngredient, 1);
            }
        }
        System.out.println(this.dish);
        this.updateDishLabel();
    }

    public void updateSelectedIngredient() {
        DishIngredient ingredient = (DishIngredient) getTableView().getSelectionModel().getSelectedItem();
        this.selectedIngredient = ingredient.getName();
    }

    public void updateButtonAddSubDisabled() {
        if (!this.selectedIngredient.equals("")) {
            if (this.dish.getIngredients().get(selectedIngredient).amountCanBeAdded(1)) {
                addition.setDisable(false);
            } else {
                addition.setDisable(true);
            }
            if (this.dish.getIngredients().get(selectedIngredient).amountCanBeSubtracted(1)) {
                subtract.setDisable(false);
            } else {
                subtract.setDisable(true);
            }
        }
    }

    public void setRowAction() {
        getTableView().setRowFactory(tv -> {
            TableRow<DishIngredient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    DishIngredient rowData = row.getItem();
                    System.out.println("Double click on: " + rowData.getName());
                    updateSelectedIngredient();
                    updateButtonAddSubDisabled();
                }
            });
            return row;
        });
    }


    public void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }


    public void displayScreen() throws Exception {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("complements.fxml"));
        //This means that you can't change any other windows besides this one.
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Table Screen");
        Parent root = loader.load();
        window.setScene(new Scene(root));
        window.show();

    }


    public ArrayList<InventoryIngredient> getInventoryIngredients() {
        ArrayList<InventoryIngredient> ingredients = new ArrayList<>();
        for (String key : this.ingredients.keySet()) {
            ingredients.add(this.inventory.getInventoryIngredient(key));
        }
        return ingredients;
    }


    public TableColumn getAmountColumn() {
        return amountColumn;
    }


    public TableColumn getIngredientColumn() {
        return ingredientColumn;
    }

    public TableView getTableView() {
        return tableView;
    }


    /**
     * Returns an ObservableList of the Restaurant's table list
     *
     * @return ObservableList of Tables
     */
    private ObservableList<DishIngredient> getDishIngredient() {
        ObservableList<DishIngredient> dishIngredients = FXCollections.observableArrayList();
        ArrayList<DishIngredient> ingredients = new ArrayList<>();
        for (String key : this.ingredients.keySet()) {
            ingredients.add(this.ingredients.get(key));
        }
        dishIngredients.addAll(ingredients);
        return dishIngredients;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
        this.ingredients = dish.getIngredients();
    }

    public void setSelectedIngredient(String ingredient) {
        this.selectedIngredient = ingredient;
    }


    /**
     * Sets the UI tables to show the Restaurant list of tables
     *
     * @param
     */
    public void setIngredients() {
        getTableView().setItems(getDishIngredient());

    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ObservableList<InventoryIngredient> getInventoryIngredient() {
        ObservableList<InventoryIngredient> inventoryIngredients = FXCollections.observableArrayList();
        inventoryIngredients.addAll(getInventoryIngredients());
        return inventoryIngredients;
    }

    public TableColumn getPriceColumn() {
        return priceColumn;
    }
}
