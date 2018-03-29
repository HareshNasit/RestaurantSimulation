package ComplementScreen;

import Restaurant.Dish;
import Restaurant.Inventory;
import Restaurant.Menu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ComplementScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Inventory inventory = new Inventory();
        inventory.readInventory();
        Menu menu = new Menu();


        Dish dish = menu.getDish(1.1, "A", 2);
        System.out.println(dish);

        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("complements.fxml"));
        Parent root = loader.load();
        ComplementScreenController controller = loader.getController();

        //make method
        controller.setDish(dish);
        controller.setIngredients();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Complement Screen");
        window.setScene(new Scene(root));
        window.setOnCloseRequest(event -> {
            controller.cancelEvent();
        });

      window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
