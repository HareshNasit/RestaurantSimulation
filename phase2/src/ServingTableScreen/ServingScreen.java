package ServingTableScreen;

import Restaurant.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Restaurant.Dish;
import Restaurant.Table;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServingScreen implements Initializable {


    public TableView cookTable;
    public TableColumn TableCol;
    public TableColumn DishCol1;
    private ObservableList<Dish> dishes;

    public void setCookTable(Restaurant restaurant) {
        this.DishCol1.getTableView().setItems(getDishes(restaurant));
    }
    private ObservableList<Dish> getDishes(Restaurant restaurant) {
        this.dishes = FXCollections.observableArrayList();
        dishes.addAll(restaurant.getServingTable().getDishesToBeCooked());
        return dishes;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        //System.out.println(getCookTable());
    }

    public <T, S> TableColumn<S, T> getTableIDColumn() {
        return TableCol;
    }

    public <T, S> TableColumn<S, T> getDishCol() {
        return DishCol1;
    }

    public TableView getCookTable() {
        return cookTable;
    }
}
