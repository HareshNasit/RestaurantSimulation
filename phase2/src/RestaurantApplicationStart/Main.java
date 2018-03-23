package RestaurantApplicationStart;

import ManagerScreen.ManagerScreenController;
import TablesScreen.TablesScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage window = new Stage();
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("../ManagerScreen/ManagerScreen.fxml"));
        Parent root = loader.load();

        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle("Manager");
        window.setScene(new Scene(root));
        window.show();

        Stage window2 = new Stage();
        FXMLLoader loader2 = new  FXMLLoader(getClass().getResource("../TablesScreen/TablesScreen.fxml"));
        Parent root2 = loader2.load();

        ManagerScreenController manager = loader.getController();
        TablesScreen server = loader2.getController();
        manager.setTest(server);

        window2.initModality(Modality.WINDOW_MODAL);
        window2.setTitle("Server");
        window2.setScene(new Scene(root2));
        window2.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
