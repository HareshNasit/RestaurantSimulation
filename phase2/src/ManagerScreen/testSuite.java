package ManagerScreen;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class testSuite extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Stage window = new Stage();
    FXMLLoader loader = new  FXMLLoader(getClass().getResource("ManagerScreen.fxml"));
    Parent root = loader.load();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Manager");
    window.setScene(new Scene(root));
    window.show();


  }
}
