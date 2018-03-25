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
        Stage window = new Stage();

        FXMLLoader loader = new  FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        OrderScreen controller = loader.getController();
        LoginScreen loginScreen = new LoginScreen();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login Screen");
        window.setScene(new Scene(root));
        window.show();


    }
    public static void main(String[] args) {
        launch(args);
    }
}
