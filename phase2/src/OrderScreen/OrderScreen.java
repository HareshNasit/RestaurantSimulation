package OrderScreen;

import billScreen.BillScreenController;
import MenuDishes.Dish;
import Restaurant.Inventory;
import Restaurant.Restaurant;
import Restaurant.Server;
import Restaurant.Table;
import MenuDishes.MenuItem;
import Restaurant.ModelControllerInterface;
import Restaurant.DishStatus;
import Restaurant.WorkerType;
import TablesScreen.TablesScreen;

// import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import notification.Notification;


public class OrderScreen extends VBox implements ModelControllerInterface {

  @FXML
  private Button buttonSend;
  @FXML
  private Button buttonBack;
  @FXML
  private ComboBox customerNumberDropDown;
  @FXML
  private TableColumn customerNumberColumn;
  @FXML
  private Button addComplimentsButton;
  private Server server;
  private Table table;
  private Restaurant restaurant;
  @FXML
  private Label tableOrderTitle;
  @FXML
  private Label menuLabel;
  @FXML
  private Button addDish;
  @FXML
  private Button removeDish;
  @FXML
  private TableView menuTableView;
  @FXML
  private TableView orderTableView;
  @FXML
  private TableColumn menuIdColumn;
  @FXML
  private TableColumn menuDishColumn;
  @FXML
  private TableColumn menuPriceColumn;
  @FXML
  private TableColumn menuIngredientsColumn;
  @FXML
  private Button openBillScreen;

  @FXML
  private TableColumn commentColumn;
  @FXML
  private TableColumn idColumn;
  @FXML
  private TableColumn nameColumn;
  @FXML
  private TableColumn dishStatusColumn;
  @FXML
  private Button addCommentButton;
  @FXML
  private Pane paneBox;

  private double menuSelectedDishId;
  private String menuSelectedDishName;
  private int menuSelectedDishCustomerNum;
  private ArrayList<Dish> dishes;

  private Notification notification;
  @FXML
  private Pane notificationArea;
  @FXML
  private VBox vBox;
  @FXML
  private Button buttonReturn;
  @FXML
  private Button buttonServe;


  public OrderScreen(Server server, Table table, Restaurant restaurant) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orders.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      setServer(server);
      this.restaurant = restaurant;
      this.table = table;
      setCustomerLabels(table.getTableSize());
      initialize();
      updateScreen();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void initialize() {
    idColumn.setCellValueFactory(new PropertyValueFactory<Dish, Double>("id"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
    customerNumberColumn
        .setCellValueFactory(new PropertyValueFactory<Dish, Integer>("customerNum"));
    commentColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));
    dishStatusColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishStatus"));

    menuIdColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("id"));
    menuDishColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("name"));
    menuPriceColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("price"));

    buttonReturn.setVisible(false);
    buttonServe.setVisible(false);

    this.setRowAction();
    System.out.println(getOrderTableView());
    notification = new Notification();
    notificationArea.getChildren().setAll(notification);

  }

  /**
   * Opens a dialog when a row in the menu order table view is clicked. The dialog shows the dish price and the
   * ingredients including the extra added compliments.
   */
  public void setRowAction() {
    getOrderTableView().setRowFactory(tv -> {
      TableRow<Dish> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        Dish rowData = row.getItem();

        if (!row.isEmpty()){
          buttonReturn.setVisible(rowData.getDishStatus() == DishStatus.SERVED);
          buttonServe.setVisible(rowData.getDishStatus() == DishStatus.PICKUP);
        }

        //buttonReturn.setVisible(rowData.getDishStatus() == DishStatus.PICKUP);

        if (!row.isEmpty() && event.getClickCount() == 2) {

          System.out.println("Click on: " + rowData.getName());
          String finalString =
              "The price of this dish is: " + rowData.getPrice() + System.lineSeparator() +
                  "The ingredients of this dish are: " + System.lineSeparator() + rowData
                  .getIngredientString()
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

  public void returnButtonAction(){

    Dish dish = (Dish) orderTableView.getSelectionModel().getSelectedItem();
    if (dish.getComment().equals("")){
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Please put a comment before returning");
      dialog.setContentText("Comment");

      Optional<String> result = dialog.showAndWait();
      if (result.isPresent()){
        dish.setComment(result.get());
        server.returnDish(dish, restaurant.getServingTable());
        restaurant.restaurantLogger.logDishReturned(dish, dish.getComment());
      } else {
        server.sendNotification("Please input a message");
      }

    } else {
      server.returnDish(dish, restaurant.getServingTable());
      restaurant.restaurantLogger.logDishReturned(dish, dish.getComment());

    }

  }

  public void buttonServeAction(){
    Dish dish = (Dish) orderTableView.getSelectionModel().getSelectedItem();
    server.serveDish(dish, restaurant);
    restaurant.restaurantLogger.logDishDelivered(dish);

  }
  /**
   * Opens the bill screen GUI when the print bill button is clicked
   */
  public void openBillScreen() throws IOException {
    Stage primaryStage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/billScreen/bill.fxml"));
    Parent root = loader.load();
    BillScreenController controller = loader.getController();

    controller.setTable(table);
    controller.setRestaurant(restaurant);

    primaryStage.setTitle("Bill Menu");
    primaryStage.setScene(new Scene(root));

    primaryStage.show();


  }

  /**
   * Gives the data of the selected cell which is the price of the dish in this case
   */
  public void getSelectedCellPrice(ActionEvent actionEvent) {
    TablePosition pos = (TablePosition) orderTableView.getSelectionModel().getSelectedCells()
        .get(3);
    int row = pos.getRow();

// Item here is the table view type:
    Object item = orderTableView.getItems().get(row);

    TableColumn col = pos.getTableColumn();

// this gives the value in the selected cell:
    String data = (String) col.getCellObservableValue(item).getValue();
    System.out.println(data);
  }

  /**
   * Makes the order appear on the menu table order view
   * @param orderedDishes the dishes that the table ordered
   */
  public void setOrderTable(ArrayList<Dish> orderedDishes) {
    ObservableList<Dish> dishes = FXCollections.observableArrayList();
    dishes.addAll(orderedDishes);
    this.idColumn.getTableView().setItems(dishes);
  }

  /**
   * This method assigns a specific comment given by a customer to the selected dish
   *
   * @param comment the comment of the customer
   */
  public void addCommentToDish(String comment) {
    Dish dish = (Dish) orderTableView.getSelectionModel().getSelectedItem();
    dish.setComment(comment);
    updateScreen();

  }

  public void openCommentDialog() {
    Dialog dialog = new TextInputDialog();
    dialog.setTitle("Comment Dialog");
    dialog.setHeaderText(
        "Enter your comment for the dish"
            + System.lineSeparator()
            + "Example: Make the falafel crispy on the outside");
    Optional<String> result = dialog.showAndWait();
    String entered = "";

    while (result.isPresent() && ((result.get()).equals("") || !validCommentEntry(result.get()))) {
      result = dialog.showAndWait();
    }
    if (result.isPresent()) {
      entered = result.get();
      addCommentToDish(entered);
    }
  }

  private boolean validCommentEntry(String comment) {
    try {
      return comment instanceof String;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Removes dishes from a specific customer's order
   */
  public void removeDishFromOrder() {
    //TODO: Open A Dialog that asks the server if he wants to remove the dish.

    Dish dish = (Dish) orderTableView.getSelectionModel().getSelectedItem();

    String message = "";

    if (dish.getDishStatus() == DishStatus.REJECTED || dish.getDishStatus() == DishStatus.ORDERED ||
        dish.getDishStatus() == DishStatus.SENT ){
      server.removeDish(table, dish);
    } else {
      message = String.format("Dish is %s. Are you sure you want to remove it?", dish.getDishStatus());
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.CANCEL);
      alert.showAndWait();
      if (alert.getResult() == ButtonType.YES) {
        server.removeDish(table, dish);
      }}

    updateScreen();
  }

  /**
   * Adds dishes to a specific customer's order
   */
  public void addDishToOrder() {
    // TODO: Create a way to give customer number
    // TODO: Add Compliments Somehow
    try {
      MenuItem dish = (MenuItem) menuTableView.getSelectionModel().getSelectedItem();
      String customer = (String) customerNumberDropDown.getValue();
      int customerNumber = (int) customer.charAt(9);
      System.out.println(customer);
      System.out.println(customerNumber);
      server.addOrder(getTable(), customerNumber - 48, dish);
      updateScreen();
    } catch (NullPointerException e) {
      System.out.println("Choose a customer from the drop down menu");
    }

  }

  /**
   * Adds the number of customers at the table to the drop down menu so each customer can have
   * his/her own order.
   *
   * @param table the table at which the customers are seated
   */
  public void addOptionsToComboBox(Table table) {
    this.table = table;
    int tableSize = setTableOccupied();
    restaurant.notifyWorker(WorkerType.SERVER,String.format("Table %s has been seated", table.getTableID()) );
    setCustomerLabels(tableSize);
  }

    /**
     * Produces a list of customers for a table based on the number of people entered
     * @param tableSize the number of people of the table
     */
  private void setCustomerLabels(int tableSize){
    ArrayList<String> customerLabels = new ArrayList<>();
    for (int k = 1; k <= tableSize; k++) {
      customerLabels.add("Customer " + k);
    }
    ObservableList<String> labels = FXCollections.observableArrayList();
    labels.addAll(customerLabels);
    customerNumberDropDown.setItems(labels);
  }

  /**
   * Checks if the number of customers at a table entered is a valid input value
   *
   * @param customerInput the number of customers that have entered
   * @return returns true if it is a valid input else false
   */
  private boolean validCustomerEntry(String customerInput) {
    try {
      int numCustomers = Integer.parseInt(customerInput);
      return numCustomers >= 1 && numCustomers < 50;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public int setTableOccupied() {
    Dialog dialog = new TextInputDialog();
    dialog.setTitle("Table Dialog");
    dialog.setHeaderText(
        "Enter the number of people"
            + System.lineSeparator()
            + "Format: a whole number between 1 and 50"
            + System.lineSeparator()
            + "Example: 8");
    Optional<String> result = dialog.showAndWait();
    String entered = "";
    while (result.isPresent() && ((result.get()).equals("") || !validCustomerEntry(result.get()))) {
      result = dialog.showAndWait();
    }
    try {
      if (result.isPresent()) {
        entered = result.get();
        table.setOccupied(Integer.parseInt(entered));
        restaurant.notifyWorker(WorkerType.SERVER,String.format("Table %s has been seated", table.getTableID()));
      }
    } catch (Exception e) {
      System.out.println("Enter the number of people occupying the table");
      entered = "0";
    }
    try {
      int numberOfPeople = Integer.parseInt(entered);
      return numberOfPeople;
    } catch (NumberFormatException e) {
      return 0;
    }
  }


  public void backButtonAction() {

    TablesScreen tablesScreen = new TablesScreen(server, restaurant);
    vBox.getChildren().setAll(tablesScreen);

  }

  /**
   * Opens the compliment menu with only the ingredients that are allowed to be added or removed to
   * the selected dish
   */
  public void openComplimentMenu() {
    try {
      Dish dish = getOrderTableView().getSelectionModel().getSelectedItem();
      if (dish != null) {

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/complementScreen/complements.fxml"));
        Parent root = loader.load();
        complementScreen.ComplementScreenController controller = loader.getController();

        controller.setDish(dish);
        controller.setIngredients();

        primaryStage.setTitle("Complement Menu");
        primaryStage.setOnCloseRequest(event -> {
          controller.cancelEvent();
        });
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      System.out.println("Choose a dish to add compliments");
    }
  }

  public void rowSelectedCheckIngredients(MouseEvent mouseEvent) {
    MenuItem dish = (MenuItem) menuTableView.getSelectionModel().getSelectedItem();
    try {
      if (!restaurant.getInventory().hasEnoughIngredients(dish.getIngredientAmounts())) {
        addDish.setDisable(true);
      } else {
        addDish.setDisable(false);
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
  }

  public void updateScreen() {
    tableOrderTitle.setText("Table" + table.getTableID() + " Order");
    //TODO: Disable or not show certain MenuItems that have not enough ingredients
    menuTableView.setItems(getMenuItem());
    orderTableView.setItems(getOrderDish());
    orderTableView.refresh();
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

  public ObservableList<MenuItem> getMenuItem() {
    ObservableList<MenuItem> menu = FXCollections.observableArrayList();
    menu.addAll(restaurant.getMenu().getMenuItems());
    return menu;
  }

  public ObservableList<Dish> getOrderDish() {
    ObservableList<Dish> orderedDishes = FXCollections.observableArrayList();
    orderedDishes.addAll(table.getTableOrder());
    return orderedDishes;
  }

  public void sendOrder() {
    ArrayList<Dish> order = server.passOrder(table, restaurant.getServingTable());
    restaurant.restaurantLogger.logOrderMessage(order);
    updateScreen();
  }

  //-----------------------GETTERS AND SETTERS BELOW---------------------------

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
    server.setScreen(this);
  }

  public Table getTable() {
    return table;
  }

  public void setTable(Table table) {
    this.table = table;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  public ArrayList<Dish> getDishes() {
    return dishes;
  }

  public void setDishes(ArrayList<Dish> dishes) {
    this.dishes = dishes;
    getOrderTableView().setItems(getOrderDish());
  }


  public TableView<Dish> getOrderTableView() {
    return orderTableView;
  }

  public TableView<Dish> getMenuTableView() {
    return menuTableView;
  }

}

