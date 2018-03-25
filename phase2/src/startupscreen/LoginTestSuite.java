package startupscreen;

import OrderScreen.OrderScreen;
import Restaurant.Dish;
import Restaurant.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LoginTestSuite extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new  FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


    }
    public static void main(String[] args) {
        launch(args);
    }
}
