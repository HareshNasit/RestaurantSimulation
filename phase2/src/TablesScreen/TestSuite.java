package TablesScreen;

import Restaurant.Table;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TestSuite extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Stage window = new Stage();
    FXMLLoader loader = new  FXMLLoader(getClass().getResource("TablesScreen.fxml"));
    Parent root = loader.load();

    TablesScreen controller = loader.getController();

    ArrayList<Table> tables = new ArrayList<>();
    tables.add(new Table("A"));
    tables.add(new Table("B"));
    tables.add(new Table("C"));

    controller.setTables(tables);
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Table Screen");
    window.setScene(new Scene(root));
    window.show();



  }
}
