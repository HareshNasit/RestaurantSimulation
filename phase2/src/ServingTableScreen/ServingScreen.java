package ServingTableScreen;

import Restaurant.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Restaurant.Dish;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServingScreen implements Initializable {


    public TableView cookTable;
    public TableColumn TableCol;
    public TableColumn DishCol;
    private ObservableList<Dish> dishes;

    public void setCookTable(ArrayList<Dish> Dishes) {
        this.DishCol.getTableView().setItems(getDishes(Dishes));
    }
    private ObservableList<Dish> getDishes(ArrayList<Dish> Dishes) {
        this.dishes = FXCollections.observableArrayList();
        dishes.addAll(Dishes);
        return dishes;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        DishCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
    }

    public <T, S> TableColumn<S, T> getTableIDColumn() {
        return TableCol;
    }

    public <T, S> TableColumn<S, T> getDishCol() {
        return DishCol;
    }

    public TableView getCookTable() {
        return cookTable;
    }
}
