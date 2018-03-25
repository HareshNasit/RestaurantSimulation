package OrderScreen;

import Restaurant.Dish;
import Restaurant.Menu;
import Restaurant.MenuItem;
import Restaurant.Restaurant;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrderScreenSuite extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        Menu menu = new Menu();
        Stage window = new Stage();

        FXMLLoader loader = new  FXMLLoader(getClass().getResource("orders.fxml"));
        Parent root = loader.load();
        OrderScreen controller = loader.getController();

        ArrayList<Dish> dishes = new ArrayList<>();
        dishes.add(menu.getDish(1.0, "A", 1));
        dishes.add(menu.getDish(2.0, "B", 2));
        dishes.add(menu.getDish(2.0, "B", 2));
        dishes.add(menu.getDish(3.0, "C", 3));

        //dishes.add(menu.getDish(19.0, "A", 2)); // gives error cause dish 19.0 does not exist

        controller.setDishes(dishes);

        OrderScreen orderScreen = new OrderScreen();


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Order Screen");
        window.setScene(new Scene(root));
        window.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}
