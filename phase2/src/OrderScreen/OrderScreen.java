package OrderScreen;

import BillScreen.BillScreenController;
import ComplementScreen.ComplementScreenController;
import Restaurant.Dish;
import Restaurant.Restaurant;
import Restaurant.Server;
import Restaurant.Table;
import Restaurant.MenuItem;
import Restaurant.ModelControllerInterface;
import TablesScreen.TablesScreen;
// import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
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


  public OrderScreen(Server server, Table table, Restaurant restaurant) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orders.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      setServer(server);
      this.restaurant = restaurant;
      this.table = table;
      setCustomerLables(table.getTableSize());
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

    menuIdColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("id"));
    menuDishColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("name"));
    menuPriceColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Double>("price"));



    this.setRowAction();
    System.out.println(getOrderTableView());
    notification = new Notification();
    notificationArea.getChildren().setAll(notification);

  }

  public void setRowAction() {
    getOrderTableView().setRowFactory(tv -> {
      TableRow<Dish> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getClickCount() == 2) {
          Dish rowData = row.getItem();
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

  /**
   * Opens the bill screen GUI when the print bill button is clicked
   */
  public void openBillScreen() throws IOException {
    Stage primaryStage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/BillScreen/bill.fxml"));
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
   * Prints the bill for the table.
   */
  public void printTableBill(ActionEvent actionEvent) {
  }

  public void setOrderTable(ArrayList<Dish> orderedDishes) {
    ObservableList<Dish> dishes = FXCollections.observableArrayList();
    dishes.addAll(orderedDishes);
    this.idColumn.getTableView().setItems(dishes);
  }

  public void rowSelectedId(javafx.scene.input.MouseEvent mouseEvent) {
//    this.menuSelectedDish = (Dish) menuTableView.getSelectionModel().getSelectedItem();
//    menuSelectedDishId = menuSelectedDish.getId();
//    menuSelectedDishName = menuSelectedDish.getName();
//    menuSelectedDishCustomerNum = menuSelectedDish.getCustomerNum();
  }

  /**
   * This method assigns a specific comment given by a customer to the selected dish
   *
   * @param comment the comment of the customer
   */
  public void addCommentToDish(String comment) {
    Dish dish = (Dish) orderTableView.getSelectionModel().getSelectedItem();
    dish.setComment(comment);
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
      System.out.println(entered);
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
    Dish dish = (Dish) orderTableView.getSelectionModel().getSelectedItem();
    server.removeDish(table, dish);
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
    restaurant.updateServers(String.format("Table %s has been seated", table.getTableID()));
    setCustomerLables(tableSize);
  }

  private void setCustomerLables(int tableSize){
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
        restaurant.updateServers(String.format("Table %s has been seated", table.getTableID()));
        System.out.println(entered);
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
            getClass().getResource("/ComplementScreen/complements.fxml"));
        Parent root = loader.load();
        ComplementScreen.ComplementScreenController controller = loader.getController();

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
  }

  @Override
  public void openNotification(String message) {
    notification.pushNotification(message);

  }

  @Override
  public void openReceiverFunction() {
    notification.openScanner();
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
    server.passOrder(table, restaurant.getServingTable());
  }

  //-----------------------GETTERS AND SETTERS BELOW---------------------------

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
    server.setScreen(this);
    System.out.println("New Screen has been changed");
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

