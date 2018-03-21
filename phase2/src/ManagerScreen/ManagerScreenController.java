package ManagerScreen;

import Restaurant.Cook;
import Restaurant.IWorker;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
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

  private final String  REQUESTFILE = "request.txt";


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

    //getColumnName().setCellValueFactory(new PropertyValueFactory<IWorker, String>("tableID"));

    updateRequestText();

  }

  private ObservableList<InventoryIngredient> getInventoryTableData() {
    ObservableList<InventoryIngredient> ingredients = FXCollections.observableArrayList();

    return ingredients;
  }

  private ObservableList<IWorker> getWorkerData() {
    ObservableList<IWorker> workers = FXCollections.observableArrayList();
    Server server = new Server("John");
    Cook cook = new Cook("Tim");
    Manager man = new Manager("Ling");
    workers.addAll(server, cook, man);
    return workers;
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

  public void test() throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
        "../TablesScreen/TablesScreen.fxml"));
    Parent root1 = fxmlLoader.load();
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    //stage.initStyle(StageStyle.UNDECORATED);
    stage.setTitle("ABC");
    stage.setScene(new Scene(root1));
    stage.show();
  }

  public void buttonAddEdit(){

    if( getTableInventory().getSelectionModel().getSelectedItem() != null){



    } else {


    }


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
}
