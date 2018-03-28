package RestaurantApplicationStart;

import ManagerScreen.ManagerScreenController;
import Restaurant.Restaurant;
import Restaurant.Menu;
import Restaurant.ServingTable;
import Restaurant.Server;
import Restaurant.Inventory;
import Restaurant.Cook;
import Restaurant.Manager;

import ServingTableScreen.ServingScreen;
import TablesScreen.TablesScreen;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

  public static final String MANAGER = "../ManagerScreen/ManagerScreen.fxml";
  public static final String SERVER = "../TablesScreen/TablesScreen.fxml";
  public static final String COOK = "../ServingTableScreen/ServingTableScreen.fxml";


  @Override
  public void start(Stage primaryStage) throws Exception {
    ServingTable servingTable = new ServingTable();
    Menu menu = new Menu();
    Inventory inventory = new Inventory();
    inventory.readInventory();
    Restaurant restaurant = new Restaurant(menu, inventory, servingTable);
    Manager manager = managerStart("Alfred", restaurant);

    serverStart("John", restaurant, manager);

    cookStart("harsh", restaurant, manager);
  }


  private void serverStart(String name, Restaurant restaurant, Manager manager) {
    try {
      Stage window = new Stage();
      FXMLLoader loader = new FXMLLoader(getClass().getResource(SERVER));
      Parent root = loader.load();

      TablesScreen controller = loader.getController();
      Server server = new Server(name);
      controller.setRestaurant(restaurant);
      controller.setServer(server);
      controller.updateScreen();
      manager.addWorker(server);
      restaurant.getServingTable().addServer(server);
      window.initModality(Modality.WINDOW_MODAL);
      window.setTitle("Server");
      window.setScene(new Scene(root));
      window.show();
    } catch (IOException e) {
    }

  }

  private Manager managerStart(String name, Restaurant restaurant) {
    Manager manager = new Manager(name);
    Stage window = new Stage();
    ManagerScreenController controller = new ManagerScreenController(manager, restaurant);
    window.initModality(Modality.WINDOW_MODAL);
    window.setTitle("Manager");
    window.setScene(new Scene(controller));
    window.show();
    return manager;

  }

  private void cookStart(String name, Restaurant restaurant, Manager manager) {

      Stage window = new Stage();
      Cook cook = new Cook(name);
      manager.addWorker(cook);
      ServingScreen screen = new ServingScreen(restaurant, restaurant.getServingTable(), cook);

      window.initModality(Modality.WINDOW_MODAL);
      window.setTitle("Serving Screen");
      window.setScene(new Scene(screen));
      window.show();


  }


  public static void main(String[] args) {
    launch(args);
  }
}
