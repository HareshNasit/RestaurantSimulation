package BillScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BillScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("bill.fxml"));
        primaryStage.setTitle("Bill Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
