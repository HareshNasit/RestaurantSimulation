package OrderScreen;

import Restaurant.Dish;
import Restaurant.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
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

    public TableColumn menuIdColumn;
    public TableColumn menuDishColumn;
    public TableColumn menuPriceColumn;
    public TableColumn menuIngredientsColumn;
    public Button printTableBill;
    public Button printIndividualBillButton;
    public TableColumn commentColumn;
    public TableColumn customerNumber;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public Button addCommentButton;
    public Pane paneBox;

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

    public TableColumn getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(TableColumn customerNumber) {
        this.customerNumber = customerNumber;
    }

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
        this.setRowAction();
        System.out.println(getOrderTableView());
    }

    public void setRowAction() {
        getOrderTableView().setRowFactory(tv -> {
            TableRow<Dish> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Dish rowData = row.getItem();
                    System.out.println("Click on: " + rowData.getName());
                    String finalString = "The price of this dish is: " + rowData.getPrice() + System.lineSeparator() +
                            "The ingredients of this dish are: " + System.lineSeparator() + rowData.getIngredientString()
                            + "Comment: " + rowData.getComment();
                    Alert alert = new Alert(Alert.AlertType.NONE, finalString,
                             ButtonType.OK);
                    alert.setTitle("Dish details");
                    alert.showAndWait();
                    System.out.println(rowData.getPrice());
                }
            });
            return row;
        });
    }

    /**
     * Opens the bill screen GUI when the print bill button is clicked
     */
    public void openBillScreen() throws IOException {
        System.out.println("Loading Bill Screen.....");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "../BillScreen/bill.fxml"));
        Parent root1 = fxmlLoader.load();
        paneBox.getChildren().setAll(root1);
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

