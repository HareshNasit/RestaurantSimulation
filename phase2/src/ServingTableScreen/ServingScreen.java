package ServingTableScreen;

import Restaurant.Restaurant;
import Restaurant.ServingTable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Restaurant.Dish;
import Restaurant.Cook;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import Restaurant.ModelControllerInterface;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ServingScreen implements Initializable, ModelControllerInterface {


    public TableView tab1Table;
    public TableColumn tab1TableId;
    public TableColumn tab1Dish;
    public Button accept;
    public Button reject;
    public TableView tab2Table;
    public TableColumn tab2TableId;
    public TableColumn tab2Dish;
    public TableView tab3Table;
    public TableColumn tab3TableId;
    public TableColumn tab3Dish;
    public AnchorPane tab1;
    public Button DishReadyButton;
    public TableColumn tab1Comment;
    public TableColumn tab2Comment;
    public TableColumn tab3Comment;
    public Label checkLabel;
    public Button tab1Refresh;

    private Dish dishSelectedTab1;
    private Dish dishSelectedTab2;
    public Restaurant restaurant;
    private ServingTable servingTable;
    public Cook cook;

    public void setCookTable(ArrayList<Dish> Dishes) {
        ObservableList<Dish> dishesToBeCooked = FXCollections.observableArrayList();
        dishesToBeCooked.addAll(Dishes);
        this.tab1Dish.getTableView().setItems(dishesToBeCooked);
    }
    public void setBeingCookedTable(ArrayList<Dish> Dishes){
            ObservableList<Dish> dishesBeingCooked = FXCollections.observableArrayList();
            dishesBeingCooked.addAll(Dishes);
            this.tab2Dish.getTableView().setItems(dishesBeingCooked);
    }
    public void setReadyTable(ArrayList<Dish> Dishes){
        ObservableList<Dish> dishesToBeServed = FXCollections.observableArrayList();
        dishesToBeServed.addAll(Dishes);
        this.tab3Dish.getTableView().setItems(dishesToBeServed);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tab1TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab1Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        tab1Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));

        tab2TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab2Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        tab2Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));

        tab3TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab3Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        tab3Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));
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
        if(cook.canBePrepared(dishSelectedTab1,restaurant.getInventory())){
            checkLabel.setText("Can be Prepared");
            checkLabel.setTextFill(Paint.valueOf("Green"));
        }
        else{
            // checkLabel.setText("OOPS NOT ENOUGH INGREDIENTS");
            checkLabel.setText(restaurant.getInventory().getLowIngredientStrings(dishSelectedTab1));
            checkLabel.setTextFill(Paint.valueOf("Red"));
        }
        System.out.println(dishSelectedTab1.getTableName());
    }

    public void acceptDish(ActionEvent actionEvent) {
        // Manually removing it.
//        cookTable.getItems().remove(dishSelected);
//        dishesBeingCooked.add(dishSelected);
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to accept?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                cook.acceptCook(dishSelectedTab1, restaurant.getServingTable(), restaurant.getInventory());
                setCookTable(restaurant.getServingTable().getDishesToBeCooked());
                setBeingCookedTable(restaurant.getServingTable().getDishesBeingCooked());
                setReadyTable(restaurant.getServingTable().getDishesToBeServed());
            }
        }
        catch(NullPointerException e){
            System.out.println("No row selected");
        }
        this.dishSelectedTab1 = null;
    }

    public void rejectDish(ActionEvent actionEvent) {
        cook.rejectDish(dishSelectedTab1,restaurant.getServingTable());
        setCookTable(restaurant.getServingTable().getDishesToBeCooked());
    }
    public void rowSelectedTab2(MouseEvent mouseEvent){
        this.dishSelectedTab2 = (Dish) tab2Table.getSelectionModel().getSelectedItem();
        System.out.println(dishSelectedTab2.getTableName());


    }
    public void dishReadyToBeServed() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                cook.serveDish(dishSelectedTab2, restaurant.getServingTable());
                setCookTable(restaurant.getServingTable().getDishesToBeCooked());
                setBeingCookedTable(restaurant.getServingTable().getDishesBeingCooked());
                setReadyTable(restaurant.getServingTable().getDishesToBeServed());
            }
        }
        catch (NullPointerException e){
            System.out.println("No row selected");
        }
        dishSelectedTab2 = null;
    }

    public void refreshScreen(ActionEvent actionEvent) {
        setCookTable(restaurant.getServingTable().getDishesToBeCooked());
        setBeingCookedTable(restaurant.getServingTable().getDishesBeingCooked());
        setReadyTable(restaurant.getServingTable().getDishesToBeServed());
    }

    public void refreshScreen1(ActionEvent actionEvent) {
        setCookTable(restaurant.getServingTable().getDishesToBeCooked());
        setBeingCookedTable(restaurant.getServingTable().getDishesBeingCooked());
        setReadyTable(restaurant.getServingTable().getDishesToBeServed());
    }
    public void updateScreen(){
        setCookTable(restaurant.getServingTable().getDishesToBeCooked());
        setBeingCookedTable(restaurant.getServingTable().getDishesBeingCooked());
        setReadyTable(restaurant.getServingTable().getDishesToBeServed());
    }

    public ServingTable getServingTable() {
        return servingTable;
    }

    public void setServingTable(ServingTable servingTable) {
        this.servingTable = servingTable;
    }
}
