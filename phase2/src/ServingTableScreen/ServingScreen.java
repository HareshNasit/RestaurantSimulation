package ServingTableScreen;

import complementScreen.ComplementScreenController;
import complementScreen.ComplementScreenCookExtra;
import Restaurant.Inventory;
import Restaurant.Restaurant;
import Restaurant.Server;
import Restaurant.ServingTable;
import Restaurant.DishStatus;
import Restaurant.DishIngredient;
import Restaurant.Notifiable;
import Restaurant.WorkerType;
import TablesScreen.TablesScreen;
import java.io.IOException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import MenuDishes.Dish;
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
import java.util.ArrayList;
import javafx.stage.Stage;
import notification.Notification;

public class ServingScreen extends VBox implements ModelControllerInterface {


  @FXML
  private TableView tab1Table; // The 1st tab table of this screen which displays the dishes to be cooked.
  @FXML
  private TableColumn tab1TableId; // The table column for the table numbers in the 1st tab.
  @FXML
  private TableColumn tab1Dish; // The table column for the dishes in the 1st tab.
  @FXML
  private Button accept;
  @FXML
  private Button reject;
  @FXML
  private TableView tab2Table;  // The table for the dishes being cooked in the 2nd tab.
  @FXML
  private TableColumn tab2TableId; // The table column for the table numbers in the 2nd tab.
  @FXML
  private TableColumn tab2Dish;// The table column for the dishes in the 2nd tab.
  @FXML
  private TableView tab3Table; // The table for the dishes to be served in the 3rd tab.
  @FXML
  private TableColumn tab3TableId; // The table column for the table numbers in the 3rd tab.
  @FXML
  private TableColumn tab3Dish; // The table column for the dishes in the 3rd tab.
  @FXML
  private AnchorPane tab1; // The tab1 on the screen.
  @FXML
  private Button DishReadyButton; // Button to check if the dish is ready on the 2nd tab.
  @FXML
  private TableColumn tab1Comment; // The table column for the comments of the dishes in the tab1.
  @FXML
  private TableColumn tab2Comment;// The table column for the comments of the dishes in the tab2.
  @FXML
  private TableColumn tab3Comment; // The table column for the comments of the dishes in the tab3.
  @FXML
  private Label checkLabel; // Label which displays if the dish can be prepared to the cook.
  @FXML
  private ImageView tick; // Tick image.
  @FXML
  private ImageView cross; // X image/
  @FXML
  private AnchorPane tab2; // The 2nd tab on this screen.
  @FXML
  private ImageView tick1; // Tick image.
  @FXML
  private Pane notificationArea; // Area for displaying notifications to the cook.
  @FXML
  private VBox vBox; // The background screen for this screen.
  @FXML
  private AnchorPane tab3; // The 3rd tab on this screen.
  @FXML
  private TabPane tabsPane; // The tab pane.
  @FXML
  private TableColumn columnDishStatus;
  @FXML
  private Button addExtra; // Button for the cook to add extra ingredients.
  private Notification notification; // The notification.


  private Restaurant restaurant; // The restaurant.
  private ServingTable servingTable; // The serving table.
  private IWorker cook; // The cook who is going to see this screen.

    /** Initializing the screen.
     * @param restaurant the restaurant.
     * @param servingTable  the serving table.
     * @param worker the worker.
     * */
  public ServingScreen(Restaurant restaurant, ServingTable servingTable, IWorker worker) {
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
    /**Set the Dishes to be cooked table on the first tab
     * @param Dishes Array list of the dishes.
     */
  public void setCookTable(ArrayList<Dish> Dishes) {
    ObservableList<Dish> dishesToBeCooked = FXCollections.observableArrayList();
    dishesToBeCooked.addAll(Dishes);
    this.tab1Dish.getTableView().setItems(dishesToBeCooked);
  }
    /**Set the Dishes being cooked table on the second tab
     * @param Dishes Array list of the dishes.
     */
  public void setBeingCookedTable(ArrayList<Dish> Dishes) {
    ObservableList<Dish> dishesBeingCooked = FXCollections.observableArrayList();
    dishesBeingCooked.addAll(Dishes);
    this.tab2Dish.getTableView().setItems(dishesBeingCooked);
  }
    /**Set the Dishes to be served table on the third tab
     * @param Dishes Array list of the dishes.
     */
  public void setReadyTable(ArrayList<Dish> Dishes) {
    ObservableList<Dish> dishesToBeServed = FXCollections.observableArrayList();
    dishesToBeServed.addAll(Dishes);
    this.tab3Dish.getTableView().setItems(dishesToBeServed);
  }
    /* Initialize the tables when the screen is constructed.
    * */
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

    columnDishStatus.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishStatus"));

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
    /** The method is called when the user selects a row on the table on the first tab*/
  public void rowSelected(MouseEvent mouseEvent) {
    Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();
    try {
      if (((Cook) getCook()).canBePrepared(dish, getRestaurant().getInventory())) {
        checkLabel.setText("Can be Prepared");
        checkLabel.setTextFill(Paint.valueOf("Green"));
        accept.setDisable(false);
      } else {
        checkLabel.setText(getRestaurant().getInventory().getLowIngredientStrings(dish));
        checkLabel.setTextFill(Paint.valueOf("Red"));
        accept.setDisable(true);
      }
      if(dish.getDishStatus().equals(DishStatus.RETURNED)){
        addExtra.setDisable(false);
        }
        else if(dish.getDishStatus().equals(DishStatus.SENT)){
          addExtra.setDisable(true);
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
  }
    /** The method is called when the cook presses the accept button and calls methods from the cook class
     * accordingly.
     */
  public void acceptDish(ActionEvent actionEvent) {
    try {
      Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();

      String message = getCookMessage(servingTable.checkPriority(dish),((Cook) cook).hasDishes());

      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES,
          ButtonType.CANCEL);
      alert.showAndWait();
      if (alert.getResult() == ButtonType.YES) {
        if (dish.getDishStatus().equals(DishStatus.SENT)) {
          ((Cook) getCook()).acceptCook(dish, getRestaurant().getServingTable(), getRestaurant()
              .getInventory());
          setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
          setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
          setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
        } else if(dish.getDishStatus().equals(DishStatus.RETURNED)){
          ((Cook) getCook()).acceptNoCook(dish, getRestaurant().getServingTable());
        }
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
    checkLabel.setText("");
    this.updateScreen();
  }

  /**
   *
   * @param priority The boolean
   * @param hasDishes The boolean if there are dishes.
   * @return String the message.
   */
  private String getCookMessage(boolean priority,boolean hasDishes){
    StringBuilder message = new StringBuilder();
    if (priority){
      message.append("There are dishes that have been ordered first but not cooked" + System.lineSeparator());
    }
    if (hasDishes){
      message.append("You have dishes that are currently cooking" + System.lineSeparator());
    }

    message.append("Are you sure you want to accept?");
    return message.toString();
  }
  /** The method is called when the reject dish is pressed which rejects the dish accordingly.
  */
  public void rejectDish(ActionEvent actionEvent) {
    try {
      Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();
      Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reject?",
          ButtonType.YES, ButtonType.CANCEL);
      alert1.showAndWait();
      if (alert1.getResult() == ButtonType.YES) {
        ((Cook) getCook()).rejectDish(dish, getRestaurant().getServingTable());
        setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
        restaurant.restaurantLogger.logDishRejected(dish);
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
    checkLabel.setText("");

  }
    /** The method is called when the dishReady button is pressed which sets the dish on the serving table.
     */
  public void dishReadyToBeServed() {
    try {
      Dish dish = (Dish) tab2Table.getSelectionModel().getSelectedItem();
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?",
          ButtonType.YES, ButtonType.CANCEL);
      alert.showAndWait();
      if (alert.getResult() == ButtonType.YES) {
        ((Cook) getCook()).serveDish(dish, getRestaurant().getServingTable());
        setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
        setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
        setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
        restaurant.restaurantLogger.logDishPrepared(dish);
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
  }

    /**
     * Update the screen.
     */
  public void updateScreen() {
    setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
    setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
    setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
  }

  @Override
  public void openNotification(String message) {
    notification.pushNotification(message);
  }

  @Override
  public void openReceiverFunction(Inventory inventory, String ingredient, int amount) {
    notification.openScanner();
    notification.getButtonPickUp().setOnAction(event -> inventory.addStock(ingredient, amount));

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

    /**
     * Set the worker depending on the type of the worker.
     * @param cook The Worker
     */
  public void setCook(IWorker cook) {
    this.cook = cook;

    if (cook.getType().equals(WorkerType.COOK)) {
      servingTable.addCook((Notifiable) cook);
      ((Cook) cook).setScreen(this);
    } else if (cook.getType().equals(WorkerType.SERVER)) {
      tab1.getChildren().remove(accept);
      tab1.getChildren().remove(checkLabel);
      tab1.getChildren().remove(reject);
      tab1.getChildren().remove(tick);
      tab1.getChildren().remove(cross);
      tab2.getChildren().remove(tick1);
      tab2.getChildren().remove(DishReadyButton);
      Button back = new Button();
      back.setOnAction(event -> {
        TablesScreen tablesScreen = new TablesScreen((Server) cook, restaurant);
        vBox.getChildren().setAll(tablesScreen);
      });
      back.setLayoutX(400);
      back.setLayoutY(3);
      tab3.getChildren().add(back);
      Button serveDishBtn = new Button();
      serveDishBtn.setOnAction(event -> {
        try {
          Dish dish = (Dish) tab3Table.getSelectionModel().getSelectedItem();
          ((Server) cook).serveDish(dish, restaurant);
          restaurant.restaurantLogger.logDishDelivered(dish);
        } catch (NullPointerException e) {
          System.out.println("No row selected");
        }
      });
      tabsPane.getSelectionModel().select(2);
      serveDishBtn.setLayoutX(492);
      serveDishBtn.setLayoutY(282);
      serveDishBtn.setText("Serve Dish");
      tab3.getChildren().add(serveDishBtn);
      back.setText("Back");
      ((Server) cook).setScreen(this);
    }


  }

  public void addIngredientsIfEnough() {
    try {
      Dish dish = (Dish) this.tab1Table.getSelectionModel().getSelectedItem();
      if (dish != null) {

        Stage primaryStage = new Stage();

        ComplementScreenCookExtra controller = new ComplementScreenCookExtra(dish,restaurant);

        primaryStage.setTitle("Complements Cook Menu");
        primaryStage.setScene(new Scene(controller));
        primaryStage.setOnCloseRequest(event -> {
          controller.cancelEvent();
        });
        primaryStage.show();

      }

    } catch (NullPointerException e) {
      System.out.println("Choose a dish to add compliments");
    }

  }
  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }
}
