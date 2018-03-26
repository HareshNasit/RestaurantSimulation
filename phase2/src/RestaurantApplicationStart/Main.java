package RestaurantApplicationStart;

import ManagerScreen.ManagerScreenController;
import Restaurant.Restaurant;
import Restaurant.Menu;
import Restaurant.ServingTable;
import Restaurant.Server;
import Restaurant.Inventory;
import Restaurant.Restaurant;

import TablesScreen.TablesScreen;
import java.io.IOException;
import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ServingTable servingTable = new ServingTable();
        Menu menu = new Menu();
        Inventory inventory = new Inventory();
        Restaurant restaurant = new Restaurant(menu, inventory, servingTable);
        serverStart("John", restaurant);
    }


    private void serverStart(String name, Restaurant restaurant){
        try{Stage window = new Stage();
            FXMLLoader loader = new  FXMLLoader(getClass().getResource("../TablesScreen/TablesScreen.fxml"));
            Parent root = loader.load();

            TablesScreen controller = loader.getController();
            Server server = new Server(name);
            controller.setRestaurant(restaurant);
            controller.setServer(server);
            controller.update();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Server");
            window.setScene(new Scene(root));
            window.show();
        } catch (IOException e){}

    }


    public static void main(String[] args) {
        launch(args);
    }
}
