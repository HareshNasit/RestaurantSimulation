package startupscreen;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LoginTestSuite extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load((getClass().getResource("loginScreen.fxml")));
        primaryStage.setTitle("Start Up Screen");
        primaryStage.setScene(new Scene(root,400,250));
        primaryStage.show();


    }
    public static void main(String[] args) {
        launch(args);
    }
}
