package TablesScreen;

import OrderScreen.OrderScreen;
import Restaurant.Restaurant;
import Restaurant.Server;
import Restaurant.Table;
import Restaurant.ModelControllerInterface;
import javafx.scene.layout.VBox;
import notification.Notification;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import ServingTableScreen.ServingScreen;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import notificationBox.NotificationBox;


/**
 * The controller for the TablesScreen. Manages all user interactions regarding this GUI
 */
public class TablesScreen implements Initializable, ModelControllerInterface  {

  private Server server;
  private Restaurant restaurant;

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
  @FXML private HBox hBox; //The container that contains all of this
  @FXML private Label labelServerName;
  @FXML private Pane layout;
  @FXML private VBox vBox;

  Notification cc;
  /**
   * Opens the selected restaurant table's menu from the TablesScreen tables
   */
  public void openTableMenu(){
    Table table = getTableView().getSelectionModel().getSelectedItem();
    try{

      if (table != null) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../OrderScreen/orders.fxml"));
        Parent root = loader.load();
        OrderScreen controller = loader.getController();
        controller.setTable(table);
        controller.setServer(server);
        controller.setRestaurant(restaurant);
        controller.updateScreen();
        controller.addOptionsToComboBox(table);
        vBox.getChildren().setAll(root);
      }

    } catch (IOException e){
      e.printStackTrace();
    } catch (NullPointerException e){
      System.out.println("Choose a file bro");
    }


  }

  /**
   * Opens the serving table for the server to see
   */
  public void openServingTable() throws IOException{
    System.out.println("Loading Serving Table.....");
    FXMLLoader loader = new FXMLLoader(getClass().getResource(
        "../ServingTableScreen/ServingTableScreen.fxml"));
    Parent root1 = loader.load();
    ServingScreen controller = loader.getController();
    controller.restaurant = restaurant;
    controller.updateScreen();
    hBox.getChildren().setAll(root1);
  }

  /**
   * Calls the manager for special events
   */
  public void callManager() throws IOException {


  }

  /**
   * After the constructor is called, this is called.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    getTableIDColumn().setCellValueFactory(new PropertyValueFactory<Table, String>("tableID"));
    getOccupiedTableColumn().setCellValueFactory(new PropertyValueFactory<Table, Boolean>("isOccupied"));

    cc = new Notification();
    layout.getChildren().setAll(cc);

  }

  /**
   * Returns an ObservableList of the Restaurant's table list
   * @return ObservableList of Tables
   */
  private ObservableList<Table> getObservableTablesList(Collection<Table> tables) {
    ObservableList<Table> observableList = FXCollections.observableArrayList();
    observableList.addAll(tables);
    return observableList;
  }

  @Override
  public void updateScreen() {
    tableView.setItems(getObservableTablesList(restaurant.getTables().values()));
    labelServerName.setText(server.getName());
  }


  @Override
  public void openNotification(String message) {
   cc.pushNotification(message);

  }


  //------------------------- GETTERS AND SETTERS BELOW --------------------
  //Probs remove setters
  //Getters are needed to generate

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

  public Label getLabelServerName() {
    return labelServerName;
  }

  public void setLabelServerName(Label labelServerName) {
    this.labelServerName = labelServerName;
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
    server.setScreen(this);
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

}
