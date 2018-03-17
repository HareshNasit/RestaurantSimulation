package ServingTableScreen;

import Restaurant.*;
import TablesScreen.TablesScreen;
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
//        Parent root = FXMLLoader.load(getClass().getResource("ServingTableScreen.fxml"));
//        primaryStage.setTitle("ServingScreen");
//        primaryStage.setScene(new Scene(root, 500, 500));
//
//        primaryStage.show();
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
        controller.setCookTable(dishes);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Serving Screen");
        window.setScene(new Scene(root));
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
