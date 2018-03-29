package TablesScreen;

import Restaurant.Restaurant;
import Restaurant.Server;
import Restaurant.Menu;
import Restaurant.Inventory;
import MenuDishes.Dish;
import Restaurant.ServingTable;
import Restaurant.DishIngredient;
import MenuDishes.MenuItem;
import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TestSuite extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {


    Stage window = new Stage();
    FXMLLoader loader = new  FXMLLoader(getClass().getResource("TablesScreen.fxml"));
    Parent root = loader.load();

    TablesScreen controller = loader.getController();
    Server server = new Server("John");
    ServingTable servingTable = new ServingTable();
    Menu menu = new Menu();
    Inventory inventory = new Inventory();
    Restaurant restaurant = new Restaurant(menu, inventory, servingTable);
    HashMap<String,DishIngredient> ingredients = new HashMap<>();
    ingredients.put("tomato",new DishIngredient("tomato",12,12,12,12,12.0));
    Dish dish =  new Dish(new MenuItem("Pizza",5.0,5.0,ingredients),"A",24);
    servingTable.getDishesToBeCooked().add(dish);
    Scene scene = new Scene(root);
    controller.setRestaurant(restaurant);
    controller.setServer(server);

    controller.updateScreen();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Table Screen");
    window.setScene(scene);
    window.show();
    System.out.println(window.getX());



  }
}
