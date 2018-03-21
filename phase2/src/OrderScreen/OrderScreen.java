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
    public TableView menuTableView;
    public TableView orderTableView;
    public TableColumn priceColumn;

    public TableColumn menuIdColumn;
    public TableColumn menuDishColumn;
    public TableColumn menuPriceColumn;
    public TableColumn menuIngredientsColumn;
    public Button printTableBill;
    public Button printIndividualBillButton;

    public TableColumn getMenuIdColumn() {
        return menuIdColumn;
    }

    public void setMenuIdColumn(TableColumn menuIdColumn) {
        this.menuIdColumn = menuIdColumn;
    }

    public TableColumn getMenuDishColumn() {
        return menuDishColumn;
    }

    public void setMenuDishColumn(TableColumn menuDishColumn) {
        this.menuDishColumn = menuDishColumn;
    }

    public TableColumn getMenuPriceColumn() {
        return menuPriceColumn;
    }

    public void setMenuPriceColumn(TableColumn menuPriceColumn) {
        this.menuPriceColumn = menuPriceColumn;
    }

    public TableColumn getMenuIngredientsColumn() {
        return menuIngredientsColumn;
    }

    public void setMenuIngredientsColumn(TableColumn menuIngredientsColumn) {
        this.menuIngredientsColumn = menuIngredientsColumn;
    }

    public TableColumn getPriceColumn() {
        return priceColumn;
    }

    public void setPriceColumn(TableColumn priceColumn) {
        this.priceColumn = priceColumn;
    }

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

    /**
     * Gives the data of the selected cell which is the price of the dish in this case
     * @param actionEvent
     */
    public void getSelectedCellPrice(ActionEvent actionEvent){
        TablePosition pos = (TablePosition) orderTableView.getSelectionModel().getSelectedCells().get(3);
        int row = pos.getRow();

// Item here is the table view type:
        Object item = orderTableView.getItems().get(row);

        TableColumn col = pos.getTableColumn();

// this gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        System.out.println(data);
    }

    /**
     * Prints the bill for the table.
     * @param actionEvent
     */
    public void printTableBill(ActionEvent actionEvent){

    }

}

