package OrderScreen;

import Restaurant.Dish;
import Restaurant.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class OrderScreen implements EventHandler<ActionEvent>, Initializable{

    public Label tableOrderTitle;
    public Label menuLabel;
    public Button addDish;
    public Button removeDish;
    public Button printBill;
    public TableView menuTableView;
    public TableView orderTableView;
    public TableColumn priceColumn;

    public TableColumn getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(TableColumn customerNumber) {
        this.customerNumber = customerNumber;
    }

    public TableColumn customerNumber;

    public TableColumn getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(TableColumn idColumn) {
        this.idColumn = idColumn;
    }

    public TableColumn getNameColumn() {
        return nameColumn;
    }

    public void setNameColumn(TableColumn nameColumn) {
        this.nameColumn = nameColumn;
    }

    public TableColumn idColumn;
    public TableColumn nameColumn;

    private String dishName;
    private int dishNumber;
    private int dishPrice;

    private ArrayList<Dish> dishes;

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
        getOrderTableView().setItems(getOrderDish());
    }

    /**
     *
     * @return
     */
    public ObservableList<Dish> getOrderDish(){
        ObservableList<Dish> orderedDishes = FXCollections.observableArrayList();
        orderedDishes.addAll(getDishes());
        return orderedDishes;
    }

    public TableView<Dish> getOrderTableView(){
        return orderTableView;
    }

    public TableView<Dish> getMenuTableView(){
        return menuTableView;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(int dishNumber) {
        this.dishNumber = dishNumber;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public void handle(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getIdColumn().setCellValueFactory(new PropertyValueFactory<Dish, Double>("id"));
        getNameColumn().setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        getCustomerNumber().setCellValueFactory(new PropertyValueFactory<Dish, Integer>("customerNum"));
        System.out.println(getOrderTableView());
    }


    public void printBill(ActionEvent actionEvent){
        
    }
}

