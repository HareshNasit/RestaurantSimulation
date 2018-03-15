package ManagerScreen;

import Restaurant.Inventory;
import Restaurant.InventoryIngredient;
import Restaurant.Table;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagerScreenController implements Initializable {

  @FXML //Allows the variable to be encapsualted
  private Tab tabInventory;
  @FXML
  private TableColumn columnIsLow;
  @FXML
  private TableColumn columnRestock;
  @FXML
  private TableColumn columnLowThresh;
  @FXML
  private TableColumn columnAmount;
  @FXML
  private TableColumn columnIngredient;
  @FXML
  private TableView tableInventory;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    columnIngredient.setCellValueFactory(new PropertyValueFactory<Table, String>("name"));
    columnAmount.setCellValueFactory(new PropertyValueFactory<Table, Integer>("currentQuantity"));
    columnLowThresh.setCellValueFactory(new PropertyValueFactory<Table, Integer>("lowerThreshold"));
    columnRestock.setCellValueFactory(new PropertyValueFactory<Table, Integer>("restockQuantity"));
    getInventoryTableData();

  }

  private ObservableList<InventoryIngredient> getInventoryTableData() {
    ObservableList<InventoryIngredient> ingredients = FXCollections.observableArrayList();
    InventoryIngredient ip = new InventoryIngredient("memes", 10, 10, 10);
    InventoryIngredient jp = new InventoryIngredient("masd", 10, 10, 10);
    InventoryIngredient yp = new InventoryIngredient("sd", 10, 10, 10);

    ingredients.addAll(ip, jp, yp);
    tableInventory.setItems(ingredients);
    return ingredients;
  }

  //---------------------- Getters and Setters --------------------

  public Tab getTabInventory() {
    return tabInventory;
  }

  public void setTabInventory(Tab tabInventory) {
    this.tabInventory = tabInventory;
  }

  public TableColumn getColumnIsLow() {
    return columnIsLow;
  }

  public void setColumnIsLow(TableColumn columnIsLow) {
    this.columnIsLow = columnIsLow;
  }

  public TableColumn getColumnRestock() {
    return columnRestock;
  }

  public void setColumnRestock(TableColumn columnRestock) {
    this.columnRestock = columnRestock;
  }

  public TableColumn getColumnLowThresh() {
    return columnLowThresh;
  }

  public void setColumnLowThresh(TableColumn columnLowThresh) {
    this.columnLowThresh = columnLowThresh;
  }

  public TableColumn getColumnAmount() {
    return columnAmount;
  }

  public void setColumnAmount(TableColumn columnAmount) {
    this.columnAmount = columnAmount;
  }

  public TableColumn getColumnIngredient() {
    return columnIngredient;
  }

  public void setColumnIngredient(TableColumn columnIngredient) {
    this.columnIngredient = columnIngredient;
  }

  public TableView getTableInventory() {
    return tableInventory;
  }

  public void setTableInventory(TableView tableInventory) {
    this.tableInventory = tableInventory;
  }
}
