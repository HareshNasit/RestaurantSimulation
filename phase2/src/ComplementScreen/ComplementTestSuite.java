package ComplementScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ComplementTestSuite extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Controller tb = new Controller();

        tb.displayScreen();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
