package ManagerScreen;

import Restaurant.Cook;
import Restaurant.Dish;
import Restaurant.DishIngredient;
import Restaurant.IWorker;
import Restaurant.Inventory;
import Restaurant.InventoryIngredient;
import Restaurant.Manager;
import Restaurant.Server;
import Restaurant.Table;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerScreenController implements Initializable {

  private Inventory inventory;

  private final String  REQUESTFILE = "request.txt";
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
  private TextArea textFieldRequest;
  @FXML //Allows the variable to be encapsualted
  private Tab tabInventory;
  @FXML
  private TableView tableInventory;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    getColumnType().setCellValueFactory(new PropertyValueFactory<IWorker, String>("type"));
    getColumnName().setCellValueFactory(new PropertyValueFactory<IWorker, String>("name"));
    tableViewWorkers.setItems(getWorkerData());

    getColumnIngredient().setCellValueFactory(new PropertyValueFactory<InventoryIngredient, String>("name"));
    getColumnAmount().setCellValueFactory(new PropertyValueFactory<InventoryIngredient, Integer>("currentQuantity"));
    getColumnRestock().setCellValueFactory(new PropertyValueFactory<InventoryIngredient, Integer>("restockQuantity"));
    getColumnThreshold().setCellValueFactory(new PropertyValueFactory<InventoryIngredient, Integer>("lowerThreshold"));

    inventory = new Inventory();
    inventory.readInventory();

    tableInventory.setItems(getIngredients());
    this.setIngredientTableRowAction();

    //getColumnName().setCellValueFactory(new PropertyValueFactory<IWorker, String>("tableID"));

    updateRequestText();

  }

  private ObservableList<IWorker> getWorkerData() {
    ObservableList<IWorker> workers = FXCollections.observableArrayList();
    Server server = new Server("John");
    Cook cook = new Cook("Tim");
    Manager man = new Manager("Ling");
    workers.addAll(server, cook, man);
    return workers;
  }

  private ObservableList<InventoryIngredient> getIngredients() {
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

  public void buttonAddAction(){

      String name = textFieldIngredient.getText().trim();
      int amount = Integer.valueOf(textFieldAmount.getText().trim());
      int restockQuantity = Integer.valueOf(textFieldRestock.getText().trim());
      int lowerThreshold = Integer.valueOf(textFieldThreshold.getText().trim());
      InventoryIngredient ingredient = new InventoryIngredient(name, amount, restockQuantity, lowerThreshold);
      inventory.addNewIngredient(ingredient);
      //There should be a better way to set this
      tableInventory.setItems(getIngredients());
      clearTextFields();

  }

  private void clearTextFields(){
    textFieldThreshold.clear();
    textFieldIngredient.clear();
    textFieldAmount.clear();
    textFieldRestock.clear();

  }

  public void setIngredientTableRowAction() {
    getTableInventory().setRowFactory(tv -> {
      TableRow<InventoryIngredient> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty()) {
          InventoryIngredient ingredient = row.getItem();
          textFieldIngredient.setText(ingredient.getName());
          textFieldAmount.setText(String.valueOf(ingredient.getCurrentQuantity()));
          textFieldRestock.setText(String.valueOf(ingredient.getRestockQuantity()));
          textFieldThreshold.setText(String.valueOf(ingredient.getLowerThreshold()));

        }
      });
      return row;
    });
  }



  //---------------------- Getters and Setters --------------------

  public Tab getTabInventory() {
    return tabInventory;
  }

  public void setTabInventory(Tab tabInventory) {
    this.tabInventory = tabInventory;
  }

  public TextArea getTextFieldRequest() {
    return textFieldRequest;
  }

  public void setTextFieldRequest(TextArea textFieldRequest) {
    this.textFieldRequest = textFieldRequest;
  }

  public TableColumn getColumnType() {
    return columnType;
  }

  public void setColumnType(TableColumn columnType) {
    this.columnType = columnType;
  }

  public TableColumn getColumnName() {
    return columnName;
  }

  public void setColumnName(TableColumn columnName) {
    this.columnName = columnName;
  }

  public TableColumn getColumnStatus() {
    return columnStatus;
  }

  public void setColumnStatus(TableColumn columnStatus) {
    this.columnStatus = columnStatus;
  }

  public TableView getTableViewWorkers() {
    return tableViewWorkers;
  }

  public void setTableViewWorkers(TableView tableViewWorkers) {
    this.tableViewWorkers = tableViewWorkers;
  }

  public TextField getTextFieldThreshold() {
    return textFieldThreshold;
  }

  public void setTextFieldThreshold(TextField textFieldThreshold) {
    this.textFieldThreshold = textFieldThreshold;
  }

  public TextField getTextFieldAmount() {
    return textFieldAmount;
  }

  public void setTextFieldAmount(TextField textFieldAmount) {
    this.textFieldAmount = textFieldAmount;
  }

  public TextField getTextFieldIngredient() {
    return textFieldIngredient;
  }

  public void setTextFieldIngredient(TextField textFieldIngredient) {
    this.textFieldIngredient = textFieldIngredient;
  }

  public TextField getTextFieldRestock() {
    return textFieldRestock;
  }

  public void setTextFieldRestock(TextField textFieldRestock) {
    this.textFieldRestock = textFieldRestock;
  }

  public TableView getTableInventory() {
    return tableInventory;
  }

  public void setTableInventory(TableView tableInventory) {
    this.tableInventory = tableInventory;
  }

  public TableColumn getColumnIngredient() {
    return columnIngredient;
  }

  public void setColumnIngredient(TableColumn columnIngredient) {
    this.columnIngredient = columnIngredient;
  }

  public TableColumn getColumnAmount() {
    return columnAmount;
  }

  public void setColumnAmount(TableColumn columnAmount) {
    this.columnAmount = columnAmount;
  }

  public TableColumn getColumnRestock() {
    return columnRestock;
  }

  public void setColumnRestock(TableColumn columnRestock) {
    this.columnRestock = columnRestock;
  }

  public TableColumn getColumnThreshold() {
    return columnThreshold;
  }

  public void setColumnThreshold(TableColumn columnThreshold) {
    this.columnThreshold = columnThreshold;
  }

  public Button getButtonAddEdit() {
    return buttonAddEdit;
  }

  public void setButtonAddEdit(Button buttonAddEdit) {
    this.buttonAddEdit = buttonAddEdit;
  }
}
