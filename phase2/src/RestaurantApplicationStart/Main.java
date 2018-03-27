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
    public void start(Stage primaryStage) throws Exception{
        ServingTable servingTable = new ServingTable();
        Menu menu = new Menu();
        Inventory inventory = new Inventory();
        inventory.readInventory();
        Restaurant restaurant = new Restaurant(menu, inventory, servingTable);
        Manager manager = managerStart("Alfred", restaurant);

        serverStart("John", restaurant, manager);

        cookStart("harsh", restaurant, manager);
    }


    private void serverStart(String name, Restaurant restaurant, Manager manager ){
        try{Stage window = new Stage();
            FXMLLoader loader = new  FXMLLoader(getClass().getResource(SERVER));
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
        } catch (IOException e){}

    }

    private Manager managerStart(String name, Restaurant restaurant){
      Manager manager = new Manager(name);
      try {

        Stage window = new Stage();
        FXMLLoader loader = new  FXMLLoader(getClass().getResource(MANAGER));
        Parent root = loader.load();
        ManagerScreenController controller = loader.getController();
        controller.setManager(manager);
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle("Manager");
        window.setScene(new Scene(root));
        window.show();
      } catch (IOException e){
        e.printStackTrace();

      }
      return manager;

    }

  private void cookStart(String name, Restaurant restaurant, Manager manager){
    try {
      Stage window = new Stage();
      FXMLLoader loader = new  FXMLLoader(getClass().getResource("../ServingTableScreen/ServingTableScreen.fxml"));
      Parent root = loader.load();
      Cook cook = new Cook(name);
      manager.addWorker(cook);

      ServingScreen controller = loader.getController();
      controller.setCookTable(restaurant.getServingTable().getDishesToBeCooked());
      controller.restaurant = restaurant;
      controller.setCook(cook);
      cook.setScreen(controller);
      controller.updateScreen();
      restaurant.getServingTable().addCook(cook);

      window.initModality(Modality.WINDOW_MODAL);
      window.setTitle("Serving Screen");
      window.setScene(new Scene(root));
      window.show();
    } catch (IOException e){

    }

  }


    public static void main(String[] args) {
        launch(args);
    }
}
