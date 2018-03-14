package TablesScreen;

import Restaurant.Table;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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

  public TableView<Table> tableView; //Table of tables
  public Button tableButton; //Button that will open the Table's menu
  public Button servingTableButton; // Button that will open the ServingTableMenu
  public Button managerButton; //Button that will call the manager
  public TableColumn tableStatusColumn;
  public TableColumn occupiedTableColumn;
  public TableColumn tableIDColumn;
  public HBox hBox; //The container that contains all of this
  private ArrayList<Table> tables;

  /**
   * Sets the UI tables to show the Restaurant list of tables
   *
   * @param tables ArrayList of Tables
   */
  public void setTables(ArrayList tables) {
    this.tables = tables;
    tableView.setItems(getTable());
  }

  /**
   * Opens the selected restaurant table's menu from the TablesScreen tables
   */
  public void openTableMenu(){
    Table table = tableView.getSelectionModel().getSelectedItem();
    System.out.println(String.format("Open Table (%s) Menu.......", table.getTableID()));
    //Pass this table to the new window that needs to pop up

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
    tableIDColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("tableID"));
    occupiedTableColumn.setCellValueFactory(new PropertyValueFactory<Table, Boolean>("isOccupied"));
    System.out.println(tableView);
  }

  /**
   * Returns an ObservableList of the Restaurant's table list
   *
   * @return ObservableList of Tables
   */
  private ObservableList<Table> getTable() {
    ObservableList<Table> people = FXCollections.observableArrayList();
    people.addAll(tables);
    return people;
  }

  @Override
  public void handle(ActionEvent event) {
    if (((Button) event.getSource()).getText().equals("memes")) {
      System.out.println("more memes");
    }
  }
}
