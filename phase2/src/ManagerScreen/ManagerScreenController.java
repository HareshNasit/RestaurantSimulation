package ManagerScreen;

import Restaurant.IWorker;
import Restaurant.Inventory;
import Restaurant.InventoryIngredient;
import Restaurant.Manager;
import Restaurant.Restaurant;
import Restaurant.ModelControllerInterface;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sun.tools.jconsole.Worker;

public class ManagerScreenController extends TabPane implements ModelControllerInterface{

  private Manager manager;
  private Restaurant restaurant;
  private final String  REQUESTFILE = "request.txt";

  @FXML private TableColumn columnIngredient;
  @FXML private TableColumn columnAmount;
  @FXML private TableColumn columnRestock;
  @FXML private TableColumn columnThreshold;
  @FXML private Button buttonAddEdit;
  @FXML private TextField textFieldThreshold;
  @FXML private TextField textFieldAmount;
  @FXML private TextField textFieldIngredient;
  @FXML private TextField textFieldRestock;
  @FXML private TableView tableViewWorkers;
  @FXML private TableColumn columnType;
  @FXML private TableColumn columnName;
  @FXML private TableColumn columnStatus;
  @FXML private TextArea textFieldRequest;
  @FXML private Tab tabInventory;
  @FXML private TableView tableInventory;

  public ManagerScreenController(Manager manager, Restaurant restaurant) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManagerScreen.fxml"));
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


  public void initialize() {
    //Sets the columns of each table to their respective types
    columnType.setCellValueFactory(new PropertyValueFactory<IWorker, String>("type"));
    columnName.setCellValueFactory(new PropertyValueFactory<IWorker, String>("name"));

    columnIngredient.setCellValueFactory(new PropertyValueFactory<InventoryIngredient, String>("name"));
    columnAmount.setCellValueFactory(new PropertyValueFactory<InventoryIngredient, Integer>("currentQuantity"));
    columnRestock.setCellValueFactory(new PropertyValueFactory<InventoryIngredient, Integer>("restockQuantity"));
    columnThreshold.setCellValueFactory(new PropertyValueFactory<InventoryIngredient, Integer>("lowerThreshold"));

    this.setIngredientTableRowAction();

    //getColumnName().setCellValueFactory(new PropertyValueFactory<IWorker, String>("tableID"));

    updateRequestText();


  }

  /**
   * Generates an ObservableList of IWorkers
   * @return ObservableList of IWorkers
   */
  private ObservableList<IWorker> getWorkerData() {
    ObservableList<IWorker> workers = FXCollections.observableArrayList();
    workers.addAll(getManager().getWorkers());
    return workers;
  }

  /**
   * Generates an ObservableList of InventoryIngredients
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
  private void updateRequestText(){
    try (BufferedReader fileReader = new BufferedReader(new FileReader(REQUESTFILE))) {
      String line = fileReader.readLine();
      while (line != null) {
          textFieldRequest.appendText(line + System.lineSeparator());
          line = fileReader.readLine();
      }
    } catch (java.io.IOException e) {
    }
  }

  public void updateScreen(){
    tableViewWorkers.setItems(getWorkerData());
    tableInventory.setItems(getIngredients(restaurant.getInventory()));

  }

  @Override
  public void openNotification(String message) {

  }

  @Override
  public void openReceiverFunction() {

  }

  public void buttonStockAction(){
    IWorker worker = (IWorker) tableViewWorkers.getSelectionModel().getSelectedItem();
    worker.scanStock(restaurant.getInventory(), "meems", 123);

  }


  /**
   * When the add button is clicked, a new ingredient is created as specified by the respective
   * text fields
   */
  public void buttonAddAction(){

      String name = textFieldIngredient.getText().trim();
      int amount = Integer.valueOf(textFieldAmount.getText().trim());
      int restockQuantity = Integer.valueOf(textFieldRestock.getText().trim());
      int lowerThreshold = Integer.valueOf(textFieldThreshold.getText().trim());
      InventoryIngredient ingredient = new InventoryIngredient(name, amount, restockQuantity, lowerThreshold);
      restaurant.getInventory().addNewIngredient(ingredient);
      //There should be a better way to set this
      tableInventory.setItems(getIngredients(restaurant.getInventory()));
      clearTextFields();

  }

  /**
   * Edits a selected ingredients based on the text fields and updates accordingly.
   */
  public void buttonEditAction(){
    InventoryIngredient ingredient = (InventoryIngredient)
        tableInventory.getSelectionModel().getSelectedItem();

    if (ingredient != null){
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
  public void buttonDeleteAction(){
    InventoryIngredient ingredient = (InventoryIngredient)
        tableInventory.getSelectionModel().getSelectedItem();
    restaurant.getInventory().removeIngredient(ingredient.getName());
    tableInventory.setItems(getIngredients(restaurant.getInventory()));
    clearTextFields();
  }

  /**
   * Clears all the text field of input
   */
  private void clearTextFields(){
    textFieldThreshold.clear();
    textFieldIngredient.clear();
    textFieldAmount.clear();
    textFieldRestock.clear();

  }

  /**
   * Populate the text fields with the respective data of ingredient
   * @param ingredient Ingredient that is going to populate the fields
   */
  private void populateTextFields(InventoryIngredient ingredient){
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


  public void callWorker(){
    IWorker worker = (IWorker) tableViewWorkers.getSelectionModel().getSelectedItem();
    worker.sendNotification("Come to my office");
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
    tableViewWorkers.setItems(getWorkerData());
    manager.setScreen(this);
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

}
