package ManagerScreen;

import Restaurant.IWorker;
import Restaurant.Inventory;
import Restaurant.InventoryIngredient;
import Restaurant.Manager;
import Restaurant.Restaurant;
import Restaurant.ModelControllerInterface;
import MenuDishes.Dish;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import notification.Notification;
// import sun.tools.jconsole.Worker;

public class ManagerScreenController extends VBox implements ModelControllerInterface {

  private Manager manager;
  private Restaurant restaurant;
  private final String REQUESTFILE = "request.txt";
  private final String MANAGERSCREEN = "ManagerScreen.fxml";

  @FXML
  private TableColumn columnIngredient;
  @FXML
  private TableColumn columnAmount;
  @FXML
  private TableColumn columnRestock;
  @FXML
  private TableColumn columnThreshold;
  @FXML
  private Button buttonAddEdit;
  @FXML
  private TextField textFieldThreshold;
  @FXML
  private TextField textFieldAmount;
  @FXML
  private TextField textFieldIngredient;
  @FXML
  private TextField textFieldRestock;
  @FXML
  private TableView tableViewWorkers;
  @FXML
  private TableColumn columnType;
  @FXML
  private TableColumn columnName;
  @FXML
  private TableColumn columnStatus;
  @FXML
  private TextArea textAreaRequest;
  @FXML
  private Tab tabInventory;
  @FXML
  private TableView tableInventory;
  @FXML
  private Pane paneNotification;
  @FXML
  private TabPane tabsPane;
  @FXML
  TableColumn columnDishName;
  @FXML
  TableColumn columnTime;
  @FXML
  TableColumn columnDishTable;
  @FXML
  TableColumn columnCustomerNum;
  @FXML
  TableColumn columnDishStatus;
  @FXML
  TableView tableViewDishes;
  @FXML
      private TextArea textAreaLog;
  @FXML
  private Button buttonRefreshLog;

  Notification notification;

  /**
   * Creates a new manager screen with a given manager on the given restaurant
   *
   * @param manager manager that will operate on this screen
   * @param restaurant restaurant that the manager operates on
   */
  public ManagerScreenController(Manager manager, Restaurant restaurant) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MANAGERSCREEN));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      initialize();

      this.manager = manager;
      manager.setScreen(this);
      this.restaurant = restaurant;
      updateScreen();


    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * Initializes GUI components with appropriate settings
   */
  private void initialize() {
    tabsPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    //Sets the columns of each table to their respective types
    columnType.setCellValueFactory(new PropertyValueFactory<IWorker, String>("type"));
    columnName.setCellValueFactory(new PropertyValueFactory<IWorker, String>("name"));

    columnIngredient
        .setCellValueFactory(new PropertyValueFactory<InventoryIngredient, String>("name"));
    columnAmount.setCellValueFactory(
        new PropertyValueFactory<InventoryIngredient, Integer>("currentQuantity"));
    columnRestock.setCellValueFactory(
        new PropertyValueFactory<InventoryIngredient, Integer>("restockQuantity"));
    columnThreshold.setCellValueFactory(
        new PropertyValueFactory<InventoryIngredient, Integer>("lowerThreshold"));

    columnDishName.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
    columnDishStatus.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishStatus"));
    columnDishTable.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
    columnCustomerNum.setCellValueFactory(new PropertyValueFactory<Dish, Integer>("customerNum"));
    //columnTime.setCellValueFactory(new PropertyValueFactory<Dish, Integer>("lowerThreshold"));

    this.setIngredientTableRowAction();
    notification = new Notification();
    paneNotification.getChildren().setAll(notification);
    //getColumnName().setCellValueFactory(new PropertyValueFactory<IWorker, String>("tableID"));

    updateRequestText();

  }

  public void testInventory() {
    System.out.println("CHECKING Inventory");
    restaurant.getInventory().removeStock("chocolate", 2);
    this.updateRequestText();

  }

  /**
   * Generates the list of undelivered dishes (i.e dishes that are still in the serving table)
   */
  public void requestUndelivered() {
    tableViewDishes.setItems(getDishData());
    tableViewDishes.refresh();
  }

  /**
   * Generates an ObservableList of IWorkers
   *
   * @return ObservableList of IWorkers
   */
  private ObservableList<IWorker> getWorkerData() {
    ObservableList<IWorker> workers = FXCollections.observableArrayList();
    workers.addAll(restaurant.getWorkers());
    return workers;
  }

  private ObservableList<Dish> getDishData() {
    ObservableList<Dish> dishes = FXCollections.observableArrayList();
    dishes.addAll(restaurant.getServingTable().getUndeliveredDishes());
    return dishes;

  }

  /**
   * Generates an ObservableList of InventoryIngredients
   *
   * @return ObservableList of Inventory Ingredients
   */
  private ObservableList<InventoryIngredient> getIngredients(Inventory inventory) {
    ObservableList<InventoryIngredient> ingredients = FXCollections.observableArrayList();
    ingredients.setAll(inventory.getInventoryAsCollection());
    return ingredients;
  }

  /**
   * Updates the request tab in the manager screen with the contents of request.txt
   */
  private void updateRequestText() {
    textAreaRequest.clear();
    try (BufferedReader fileReader = new BufferedReader(new FileReader(REQUESTFILE))) {
      String line = fileReader.readLine();
      while (line != null) {
        textAreaRequest.appendText(line + System.lineSeparator());
        line = fileReader.readLine();
      }
    } catch (java.io.IOException e) {
    }
  }

  /**
   * Updates the logger with content
   */
  private void updateLogger(){
    textAreaLog.setText(restaurant.restaurantLogger.returnAllContent());

  }

  /**
   * When the button is clicked, the log text area is refreshed
   */
  public void buttonRefreshAction(){
    updateLogger();
  }

  /**
   * Updates the GUI components when new changes occurs
   */
  public void updateScreen() {
    tableViewWorkers.setItems(getWorkerData());
    tableInventory.setItems(getIngredients(restaurant.getInventory()));
    updateRequestText();

  }

  /**
   * Opens message notification on this screen
   *
   * @param message message to be delivered
   */
  @Override
  public void openNotification(String message) {
    notification.pushNotification(message);
  }

  /**
   * Open the receiver function for the manager
   * @param inventory
   * @param ingredient
   * @param amount
   */
  @Override
  public void openReceiverFunction(Inventory inventory, String ingredient, int amount) {
    //Future implementation
  }

  /**
   * Calls an a worker to receive stock;
   */
  public void buttonStockAction() {
    IWorker worker = (IWorker) tableViewWorkers.getSelectionModel().getSelectedItem();

    TextInputDialog dialog = new TextInputDialog("stock, amount");
    dialog.setTitle("Receiver");
    dialog.setHeaderText("New Stock");
    dialog.setContentText("Enter stock name and amount as followed:");

// Traditional way to get the response value.
    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()){

      String[] outputResult = result.get().split("\\,");
      String ingredient = outputResult[0].trim();
      restaurant.restaurantLogger.logRequest(worker, ingredient);

      try{
      int amount = Integer.valueOf(outputResult[1].trim());
      worker.scanStock(restaurant.getInventory(), ingredient, amount);
      } catch(NumberFormatException e){
        e.printStackTrace();
        buttonStockAction();
      }

    }

  }


  /**
   * When the add button is clicked, a new ingredient is created as specified by the respective text
   * fields
   */
  public void buttonAddAction() {

    String name = textFieldIngredient.getText().trim();
    int amount = Integer.valueOf(textFieldAmount.getText().trim());
    int restockQuantity = Integer.valueOf(textFieldRestock.getText().trim());
    int lowerThreshold = Integer.valueOf(textFieldThreshold.getText().trim());
    InventoryIngredient ingredient = new InventoryIngredient(name, amount, restockQuantity,
        lowerThreshold);
    restaurant.getInventory().addNewIngredient(ingredient);
    //There should be a better way to set this
    restaurant.restaurantLogger.logInventoryChanged(ingredient, amount);
    tableInventory.setItems(getIngredients(restaurant.getInventory()));
    clearTextFields();

  }

  /**
   * Edits a selected ingredients based on the text fields and updates accordingly.
   */
  public void buttonEditAction() {
    InventoryIngredient ingredient = (InventoryIngredient)
        tableInventory.getSelectionModel().getSelectedItem();

    if (ingredient != null) {
      ingredient.setCurrentQuantity(Integer.valueOf(textFieldAmount.getText().trim()));
      ingredient.setRestockQuantity(Integer.valueOf(textFieldRestock.getText().trim()));
      ingredient.setLowerThreshold(Integer.valueOf(textFieldThreshold.getText().trim()));
    }

    //Only works when editing objects directlty
    tableInventory.refresh();
    clearTextFields();

  }

  /**
   * Edits a selected ingredients based on the text fields and updates accordingly.
   */
  public void buttonDeleteAction() {
    InventoryIngredient ingredient = (InventoryIngredient)
        tableInventory.getSelectionModel().getSelectedItem();
    restaurant.getInventory().removeIngredient(ingredient.getName());
    tableInventory.setItems(getIngredients(restaurant.getInventory()));
    clearTextFields();
  }

  /**
   * Clears all the text field of input
   */
  private void clearTextFields() {
    textFieldThreshold.clear();
    textFieldIngredient.clear();
    textFieldAmount.clear();
    textFieldRestock.clear();

  }

  /**
   * Populate the text fields with the respective data of ingredient
   *
   * @param ingredient Ingredient that is going to populate the fields
   */
  private void populateTextFields(InventoryIngredient ingredient) {
    textFieldIngredient.setText(ingredient.getName());
    textFieldAmount.setText(String.valueOf(ingredient.getCurrentQuantity()));
    textFieldRestock.setText(String.valueOf(ingredient.getRestockQuantity()));
    textFieldThreshold.setText(String.valueOf(ingredient.getLowerThreshold()));
  }

  /**
   * Handles the events when a row is selected for the IngredientsTable
   */
  private void setIngredientTableRowAction() {
    tableInventory.setRowFactory(tv -> {
      TableRow<InventoryIngredient> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty()) {

          InventoryIngredient ingredient = row.getItem();
          populateTextFields(ingredient);

        }
      });
      return row;
    });
  }

  /**
   * Starts the main restaurant system
   */
  public void startSystem() {
    manager.startSystem(restaurant);
  }

  /**
   * Closes the main restaurant system
   */
  public void shutDownSystem() {
    manager.shutDownSystem(restaurant);
  }

  /**
   * Calls the desired worker to manager's office
   */
  public void callWorker() {
    IWorker worker = (IWorker) tableViewWorkers.getSelectionModel().getSelectedItem();
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Worker Notification");
    dialog.setContentText("Send Message");

// Traditional way to get the response value.
    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()){
      worker.sendNotification(result.get());
      restaurant.restaurantLogger.logRequest(worker, result.get());
    }


  }

  /**
   * Returns the manager operating this screen
   *
   * @return manager operating this screen
   */
  public Manager getManager() {
    return manager;
  }

  /**
   * Sets the manager of this screen
   */
  public void setManager(Manager manager) {
    this.manager = manager;
    tableViewWorkers.setItems(getWorkerData());
    manager.setScreen(this);
  }

  /**
   * Gets the restaurant of this screen
   *
   * @return restaurant of this screen
   */
  public Restaurant getRestaurant() {
    return restaurant;
  }

  /**
   * Sets the restaurant of this screen.
   *
   * @param restaurant restaurant of this screen
   */
  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

}
