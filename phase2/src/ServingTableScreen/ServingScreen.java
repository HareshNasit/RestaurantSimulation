package ServingTableScreen;

import Restaurant.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Restaurant.Dish;
import Restaurant.Cook;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServingScreen implements Initializable {


    public TableView tab1Table;
    public TableColumn tab1TableId;
    public TableColumn tab1Dish;
    public Button check;
    public Button accept;
    public Button reject;
    public TableView tab2Table;
    public TableColumn tab2TableId;
    public TableColumn tab2Dish;
    public TableView tab3Table;
    public TableColumn tab3TableId;
    public TableColumn tab3Dish;
    public AnchorPane tab1;
    public Button DishreadyButton;
    private ObservableList<Dish> dishesToBeCooked;
    private ObservableList<Dish> dishesBeingCooked;
    private ObservableList<Dish> dishesToBeServed;
    private Dish dishSelectedTab1;
    private Dish dishSelectedTab2;
    public Restaurant restaurant;
    public Cook cook;

    public void setCookTable(ArrayList<Dish> Dishes) {
        this.dishesToBeCooked = FXCollections.observableArrayList();
        dishesToBeCooked.addAll(Dishes);
        this.tab1Dish.getTableView().setItems(dishesToBeCooked);
    }
    public void setBeingCookedTable(ArrayList<Dish> Dishes){
            this.dishesBeingCooked = FXCollections.observableArrayList();
            dishesBeingCooked.addAll(Dishes);
            this.tab2Dish.getTableView().setItems(dishesBeingCooked);
    }
    public void setReadyTable(ArrayList<Dish> Dishes){
        this.dishesToBeServed = FXCollections.observableArrayList();
        dishesToBeServed.addAll(Dishes);
        this.tab3Dish.getTableView().setItems(dishesToBeServed);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tab1TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab1Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));

        tab2TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab2Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));

        tab3TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab3Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
    }

    public <T, S> TableColumn<S, T> getTableIDColumn() {
        return tab1TableId;
    }

    public <T, S> TableColumn<S, T> getDishCol() {
        return tab1Dish;
    }

    public TableView getCookTable() {
        return tab1Table;
    }

    public void rowSelected(MouseEvent mouseEvent) {
        this.dishSelectedTab1 = (Dish) tab1Table.getSelectionModel().getSelectedItem();
        System.out.println(dishSelectedTab1.getTableName());
    }

    public void acceptDish(ActionEvent actionEvent) {
        // Manually removing it.
//        cookTable.getItems().remove(dishSelected);
//        dishesBeingCooked.add(dishSelected);
        cook.acceptCook(dishSelectedTab1,restaurant.getServingTable(),restaurant.getInventory());
        setCookTable(restaurant.getServingTable().getDishesToBeCooked());
        setBeingCookedTable(restaurant.getServingTable().getDishesBeingCooked());
        setReadyTable(restaurant.getServingTable().getDishesToBeServed());
    }

    public void rejectDish(ActionEvent actionEvent) {
        cook.rejectDish(dishSelectedTab1,restaurant.getServingTable());
        setCookTable(restaurant.getServingTable().getDishesToBeCooked());
    }

    public void checkDish(ActionEvent actionEvent) {
    }

    public void rowSelectedTab2(MouseEvent mouseEvent){
        this.dishSelectedTab2 = (Dish) tab2Table.getSelectionModel().getSelectedItem();
        System.out.println(dishSelectedTab2.getTableName());


    }
    public void dishReadyToBeServed(ActionEvent actionEvent) {
        cook.serveDish(dishSelectedTab2,restaurant.getServingTable());
        setCookTable(restaurant.getServingTable().getDishesToBeCooked());
        setBeingCookedTable(restaurant.getServingTable().getDishesBeingCooked());
        setReadyTable(restaurant.getServingTable().getDishesToBeServed());
    }
}
