package TablesScreen;

import Restaurant.Table;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;


/**
 * The controller for the TablesScreen. Manages all user interactions regarding this GUI
 */
public class TablesScreen implements EventHandler<ActionEvent>, Initializable {

  @FXML
  private TableView<Table> tableView; //Table of tables
  @FXML
  private Button tableButton; //Button that will open the Table's menu
  @FXML
  private Button servingTableButton; // Button that will open the ServingTableMenu
  @FXML
  private Button managerButton; //Button that will call the manager
  @FXML
  private TableColumn tableStatusColumn;
  @FXML
  private TableColumn occupiedTableColumn;
  @FXML
  private TableColumn tableIDColumn;
  @FXML
  private HBox hBox; //The container that contains all of this
  private ArrayList<Table> tables;

  /**
   * Sets the UI tables to show the Restaurant list of tables
   *
   * @param tables ArrayList of Tables
   */
  public void setTables(ArrayList tables) {
    this.tables = tables;
    getTableView().setItems(getTable());
  }

  /**
   * Opens the selected restaurant table's menu from the TablesScreen tables
   */
  public void openTableMenu(){

    try {
      Table table = getTableView().getSelectionModel().getSelectedItem();
      System.out.println(String.format("Open Table (%s) Menu.......", table.getTableID()));
    } catch (NullPointerException e){
      System.out.println("Select a table bro");

    }
    
  }

  /**
   * Opens the serving table for the server to see
   */
  public void openServingTable(){
    System.out.println("Loading Serving Table.....");
  }

  /**
   * Calls the manager for special events
   */
  public void callManager(){
    System.out.println("Calling Manager......");
  }

  /**
   * After the constructor is called, this is called.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    getTableIDColumn().setCellValueFactory(new PropertyValueFactory<Table, String>("tableID"));
    getOccupiedTableColumn().setCellValueFactory(new PropertyValueFactory<Table, Boolean>("isOccupied"));
    System.out.println(getTableView());
  }

  /**
   * Returns an ObservableList of the Restaurant's table list
   *
   * @return ObservableList of Tables
   */
  private ObservableList<Table> getTable() {
    ObservableList<Table> people = FXCollections.observableArrayList();
    people.addAll(getTables());
    return people;
  }

  @Override
  public void handle(ActionEvent event) {
    if (((Button) event.getSource()).getText().equals("memes")) {
      System.out.println("more memes");
    }
  }

  public TableView<Table> getTableView() {
    return tableView;
  }

  public void setTableView(TableView<Table> tableView) {
    this.tableView = tableView;
  }

  public HBox gethBox() {
    return hBox;
  }

  public void sethBox(HBox hBox) {
    this.hBox = hBox;
  }

  public ArrayList<Table> getTables() {
    return tables;
  }

  public Button getTableButton() {
    return tableButton;
  }

  public void setTableButton(Button tableButton) {
    this.tableButton = tableButton;
  }

  public Button getServingTableButton() {
    return servingTableButton;
  }

  public void setServingTableButton(Button servingTableButton) {
    this.servingTableButton = servingTableButton;
  }

  public Button getManagerButton() {
    return managerButton;
  }

  public void setManagerButton(Button managerButton) {
    this.managerButton = managerButton;
  }

  public TableColumn getTableStatusColumn() {
    return tableStatusColumn;
  }

  public void setTableStatusColumn(TableColumn tableStatusColumn) {
    this.tableStatusColumn = tableStatusColumn;
  }

  public TableColumn getOccupiedTableColumn() {
    return occupiedTableColumn;
  }

  public void setOccupiedTableColumn(TableColumn occupiedTableColumn) {
    this.occupiedTableColumn = occupiedTableColumn;
  }

  public TableColumn getTableIDColumn() {
    return tableIDColumn;
  }

  public void setTableIDColumn(TableColumn tableIDColumn) {
    this.tableIDColumn = tableIDColumn;
  }
}
