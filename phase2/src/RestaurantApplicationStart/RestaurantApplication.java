package RestaurantApplicationStart;

import Restaurant.Inventory;
import Restaurant.Menu;
import Restaurant.Restaurant;
import Restaurant.ServingTable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import startupscreen.LoginScreen;

public class RestaurantApplication extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Menu menu = new Menu();
    ServingTable servingTable = new ServingTable();
    Inventory inventory = new  Inventory();
    Restaurant restaurant = new Restaurant(menu, inventory, servingTable);

    LoginScreen loginScreen = new LoginScreen(restaurant);
    primaryStage.setScene(new Scene(loginScreen));
    primaryStage.show();

  }
}
