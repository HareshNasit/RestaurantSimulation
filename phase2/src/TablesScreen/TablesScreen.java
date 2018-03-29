package TablesScreen;

import OrderScreen.OrderScreen;
import Restaurant.Inventory;
import Restaurant.Restaurant;
import Restaurant.Server;
import Restaurant.Table;
import Restaurant.ModelControllerInterface;
import javafx.scene.layout.VBox;
import notification.Notification;
import java.io.IOException;
import java.util.Collection;
import ServingTableScreen.ServingScreen;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


/**
 * The controller for the TablesScreen. Manages all user interactions regarding this GUI
 */
public class TablesScreen extends VBox implements ModelControllerInterface  {

  private Server server;
  private Restaurant restaurant;

  @FXML private TableView<Table> tableView; //Table of tables
  @FXML private Button tableButton; //Button that will open the Table's menu
  @FXML private Button servingTableButton; // Button that will open the ServingTableMenu
  @FXML private Button managerButton; //Button that will call the manager
  @FXML private TableColumn tableStatusColumn;
  @FXML private TableColumn occupiedTableColumn;
  @FXML private TableColumn tableIDColumn;
  @FXML private HBox hBox; //The container that contains all of this
  @FXML private Label labelServerName;
  @FXML private Pane notificationArea;
  @FXML private VBox vBox;
  private Notification notification;

  public TablesScreen(Server server, Restaurant restaurant){
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TablesScreen.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);


    try {
      fxmlLoader.load();
      initialize();
      setServer(server);
      this.restaurant = restaurant;
      restaurant.getServingTable().addServer(server);
      updateScreen();

    } catch (IOException exception) {
      exception.printStackTrace();
    }


  }

  /**
   * Opens the selected restaurant table's menu from the TablesScreen tables
   */
  public void openTableMenu(){
    Table table = tableView.getSelectionModel().getSelectedItem();
    try{

      if (table != null) {
        OrderScreen controller = new OrderScreen(server,table,restaurant);
        if (!table.getIsOccupied()){
        controller.addOptionsToComboBox(table);}
        if(table.getTableSize() != 0){
          vBox.getChildren().setAll(controller);
        }
      }


    } catch (NullPointerException e){
      System.out.println("Choose a file bro");
    }


  }

  /**
   * Opens the serving table for the server to see
   */
  public void openServingTable() throws IOException{
    ServingScreen controller = new ServingScreen(restaurant, restaurant.getServingTable(), this.server);

    controller.updateScreen();
    hBox.getChildren().setAll(controller);
  }

  /**
   * Calls the manager for special events
   */
  public void callManager() throws IOException {


  }

  /**
   * After the constructor is called, this is called.
   */
  public void initialize() {
    tableIDColumn.setCellValueFactory(new PropertyValueFactory<Table, String>("tableID"));
    occupiedTableColumn.setCellValueFactory(new PropertyValueFactory<Table, Boolean>("isOccupied"));

    notification = new Notification();
    notificationArea.getChildren().setAll(notification);

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
  /**
   * Updates the screen to show new changes
   */
  public void updateScreen() {

    restrictToDeliver(restaurant.getServingTable().hasDishesToServe());
    tableView.setItems(getObservableTablesList(restaurant.getTables().values()));
    tableView.refresh();
    labelServerName.setText(server.getName());
  }

  private void restrictToDeliver(boolean restrict){
    tableButton.setDisable(restrict);
    if (restrict){
      tableButton.setText("Dishes Need To Be Delivered First");
    } else {
      tableButton.setText("Open Table");
    }
  }

  @Override
  /**
   * Pushes a new notification to the screen
   */
  public void openNotification(String message) {
   notification.pushNotification(message);

  }

  @Override
  public void openReceiverFunction(Inventory inventory, String ingredient, int amount) {
    notification.openScanner();
    notification.getButtonPickUp().setOnAction(event -> inventory.addStock(ingredient, amount));
  }

  /**
   * Gets the server of this screen
   * @return server of this screen
   */
  public Server getServer() {
    return server;
  }

  /**
   * Sets the server of this screen. Sets this screen as the screen of the server
   * @param server server of this screen
   */
  public void setServer(Server server) {
    this.server = server;
    server.setScreen(this);
  }

  /**
   * Gets the Restaurant object of this class
   * @return
   */
  public Restaurant getRestaurant() {
    return restaurant;
  }

  /**
   * Sets the restaurant object of this class
   * @param restaurant
   */
  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

}
