package RestaurantApplicationStart;

import Restaurant.Restaurant;
import Restaurant.Menu;
import Restaurant.ServingTable;
import Restaurant.Server;
import Restaurant.Inventory;
import Restaurant.Cook;

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

        serverStart("John", restaurant);
        managerStart("OOP", restaurant);
        cookStart("harsh", restaurant);
    }


    private void serverStart(String name, Restaurant restaurant){
        try{Stage window = new Stage();
            FXMLLoader loader = new  FXMLLoader(getClass().getResource(SERVER));
            Parent root = loader.load();

            TablesScreen controller = loader.getController();
            Server server = new Server(name);
            controller.setRestaurant(restaurant);
            controller.setServer(server);
            controller.updateScreen();
            window.initModality(Modality.WINDOW_MODAL);
            window.setTitle("Server");
            window.setScene(new Scene(root));
            window.show();
        } catch (IOException e){}

    }

    private void managerStart(String name, Restaurant restaurant){
      try {
        Stage window = new Stage();
        FXMLLoader loader = new  FXMLLoader(getClass().getResource(MANAGER));
        Parent root = loader.load();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle("Manager");
        window.setScene(new Scene(root));
        window.show();
      } catch (IOException e){

      }

    }

  private void cookStart(String name, Restaurant restaurant){
    try {
      Stage window = new Stage();
      FXMLLoader loader = new  FXMLLoader(getClass().getResource("../ServingTableScreen/ServingTableScreen.fxml"));
      Parent root = loader.load();
      Cook cook = new Cook(name);
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
