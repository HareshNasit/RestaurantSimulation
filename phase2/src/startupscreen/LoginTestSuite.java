package startupscreen;


import Restaurant.Inventory;
import Restaurant.Menu;
import Restaurant.Restaurant;
import Restaurant.ServingTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LoginTestSuite extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Restaurant restaurant = new Restaurant(new Menu(), new Inventory(), new ServingTable());
        LoginScreen root = new LoginScreen(restaurant);
        primaryStage.setTitle("Start Up Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }
    public static void main(String[] args) {
        launch(args);
    }
}
