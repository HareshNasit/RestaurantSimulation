package ServingTableScreen;

import Restaurant.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Restaurant.Dish;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServingScreen implements Initializable {


    public TableView cookTable;
    public TableColumn cookTableId;
    public TableColumn cookDish;
    public Button check;
    public Button accept;
    public Button reject;
    public TableView beingCookedTable;
    public TableColumn CookedTableId;
    public TableColumn CookedDish;
    public TableView readyTable;
    public TableColumn readyTableId;
    public TableColumn DishReady;
    private ObservableList<Dish> dishesToBeCooked;
    private ObservableList<Dish> dishesBeingCooked;
    private ObservableList<Dish> dishesToBeServed;
    private Dish dishSelected;

    public void setCookTable(ArrayList<Dish> Dishes) {
        this.dishesToBeCooked = FXCollections.observableArrayList();
        dishesToBeCooked.addAll(Dishes);
        this.cookDish.getTableView().setItems(dishesToBeCooked);
    }
    public void setBeingCookedTable(ArrayList<Dish> Dishes){
            this.dishesBeingCooked = FXCollections.observableArrayList();
            dishesBeingCooked.addAll(Dishes);
            this.CookedDish.getTableView().setItems(dishesBeingCooked);
    }
    public void setReadyTable(ArrayList<Dish> Dishes){
        this.dishesToBeServed = FXCollections.observableArrayList();
        dishesToBeServed.addAll(Dishes);
        this.DishReady.getTableView().setItems(dishesToBeServed);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cookTableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        cookDish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));

        CookedTableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        CookedDish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));

        readyTableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        DishReady.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
    }

    public <T, S> TableColumn<S, T> getTableIDColumn() {
        return cookTableId;
    }

    public <T, S> TableColumn<S, T> getDishCol() {
        return cookDish;
    }

    public TableView getCookTable() {
        return cookTable;
    }

    public void rowSelected(MouseEvent mouseEvent) {
        this.dishSelected = (Dish) cookTable.getSelectionModel().getSelectedItem();
    }
}
