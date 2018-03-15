package OrderScreen;

import Restaurant.Dish;
import Restaurant.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderScreenSuite extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Menu menu = new Menu();
        Stage window = new Stage();

        FXMLLoader loader = new  FXMLLoader(getClass().getResource("orders.fxml"));
        Parent root = loader.load();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Order Screen");
        window.setScene(new Scene(root));
        window.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
