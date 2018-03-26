package ServingTableScreen;

import Restaurant.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class ServingTestSuite extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage window = new Stage();
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("ServingTableScreen.fxml"));
        Parent root = loader.load();

        ServingScreen controller = loader.getController();
        HashMap<String,DishIngredient>  ingredients = new HashMap<>();
        Restaurant restaurant = new Restaurant(new Menu(),new Inventory(),new ServingTable());
        ingredients.put("tomato",new DishIngredient("tomato",12,12,12,12,12.0));
        ArrayList<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish(new MenuItem("Pizza",5.0,5.0,ingredients),"A",24));
        dishes.add(new Dish(new MenuItem("Burritos",5.0,5.0,ingredients),"B",24));

        dishes.add(new Dish(new MenuItem("Hamus",5.0,5.0,ingredients),"C",24));
        // controller.setBeingCookedTable(dishes);
        dishes.add(new Dish(new MenuItem("Falafal",5.0,5.0,ingredients),"D",24));
        restaurant.getServingTable().getDishesToBeCooked().addAll(dishes);
        controller.setCookTable(restaurant.getServingTable().getDishesToBeCooked());
        // controller.setReadyTable(dishes);
        controller.restaurant = restaurant;
        controller.setCook(new Server("harsh"));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Serving Screen");
        window.setScene(new Scene(root));
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
