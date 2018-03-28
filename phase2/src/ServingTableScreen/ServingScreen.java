package ServingTableScreen;

import Restaurant.Restaurant;
import Restaurant.Server;
import Restaurant.ServingTable;

import Restaurant.ServingTableListener;
import java.io.IOException;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Restaurant.Dish;
import Restaurant.Cook;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import Restaurant.ModelControllerInterface;
import Restaurant.IWorker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import notification.Notification;

public class ServingScreen extends VBox implements ModelControllerInterface {


    @FXML private TableView tab1Table;
    @FXML private TableColumn tab1TableId;
    @FXML private TableColumn tab1Dish;
    @FXML private Button accept;
    @FXML private Button reject;
    @FXML private TableView tab2Table;
    @FXML private TableColumn tab2TableId;
    @FXML private TableColumn tab2Dish;
    @FXML private TableView tab3Table;
    @FXML private TableColumn tab3TableId;
    @FXML private TableColumn tab3Dish;
    @FXML private AnchorPane tab1;
    @FXML private Button DishReadyButton;
    @FXML private TableColumn tab1Comment;
    @FXML private TableColumn tab2Comment;
    @FXML private TableColumn tab3Comment;
    @FXML private Label checkLabel;
    @FXML private Button tab1Refresh;
    @FXML private ImageView tick;
    @FXML private ImageView cross;
    @FXML private AnchorPane tab2;
    @FXML private ImageView tick1;
    @FXML private Pane notificationArea;
    private Notification notification;


    private Restaurant restaurant;
    private ServingTable servingTable;
    private IWorker cook;

    public ServingScreen(Restaurant restaurant, ServingTable servingTable, IWorker worker){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ServingTableScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.restaurant = restaurant;
            this.servingTable = servingTable;
            setCook(worker);
            initialize();
            updateScreen();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


    }

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

    public void initialize() {

        tab1TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab1Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        tab1Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));

        tab2TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab2Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        tab2Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));

        tab3TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
        tab3Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        tab3Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));

        notification = new Notification();
        notificationArea.getChildren().setAll(notification);


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
        Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();
        try {
            if (((Cook) getCook()).canBePrepared(dish, getRestaurant().getInventory())) {
                checkLabel.setText("Can be Prepared");
                checkLabel.setTextFill(Paint.valueOf("Green"));
            } else {
                // checkLabel.setText("OOPS NOT ENOUGH INGREDIENTS");
                checkLabel.setText(getRestaurant().getInventory().getLowIngredientStrings(dish));
                checkLabel.setTextFill(Paint.valueOf("Red"));
            }
        }
        catch(NullPointerException e){
            System.out.println("No row selected");
        }
    }

    public void acceptDish(ActionEvent actionEvent) {

        try {
            Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to accept?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if(dish.getComment().equals("Cook")) {
                    ((Cook) getCook()).acceptCook(dish, getRestaurant().getServingTable(), getRestaurant()
                        .getInventory());
                    setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
                    setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
                    setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
                }
                else{
                    ((Cook) getCook()).acceptNoCook(dish, getRestaurant().getServingTable());
                }
            }
        }
        catch(NullPointerException e){
            System.out.println("No row selected");
        }
        checkLabel.setText("");
        this.updateScreen();
    }

    public void rejectDish(ActionEvent actionEvent) {
        try {
            Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reject?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert1.showAndWait();
            if (alert1.getResult() == ButtonType.YES) {
                ((Cook) getCook()).rejectDish(dish, getRestaurant().getServingTable());
                setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
            }
        }
        catch(NullPointerException e){
            System.out.println("No row selected");
            }
        checkLabel.setText("");

    }

    public void dishReadyToBeServed() {
        try {
            Dish dish = (Dish) tab2Table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                ((Cook) getCook()).serveDish(dish, getRestaurant().getServingTable());
                setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
                setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
                setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
            }
        }
        catch (NullPointerException e){
            System.out.println("No row selected");
        }
    }

    public void refreshScreen(ActionEvent actionEvent) {
        setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
        setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
        setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
    }

    public void refreshScreen1(ActionEvent actionEvent) {
        setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
        setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
        setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
    }
    public void updateScreen(){
        setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
        setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
        setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
    }

    @Override
    public void openNotification(String message) {
        notification.pushNotification(message);
    }

    @Override
    public void openReceiverFunction() {
        notification.openScanner();

    }

    public ServingTable getServingTable() {
        return servingTable;
    }

    public void setServingTable(ServingTable servingTable) {
        this.servingTable = servingTable;
    }

    public IWorker getCook() {
        return cook;
    }

    public void setCook(IWorker cook) {
        this.cook = cook;

        if(cook.getType().equals("Cook")){
            servingTable.addCook((ServingTableListener) cook);
            ((Cook) cook).setScreen(this);
        }
        else if(cook.getType().equals("Server")){
            tab1.getChildren().remove(accept);
            tab1.getChildren().remove(checkLabel);
            tab1.getChildren().remove(reject);
            tab1.getChildren().remove(tick);
            tab1.getChildren().remove(cross);
            tab2.getChildren().remove(tick1);
            tab2.getChildren().remove(DishReadyButton);
            Button back = new Button();
            back.setLayoutX(400);
            back.setLayoutY(3);
            tab1.getChildren().add(back);
            servingTable.addServer((ServingTableListener) cook);
            ((Server) cook).setScreen(this);
        }


    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
