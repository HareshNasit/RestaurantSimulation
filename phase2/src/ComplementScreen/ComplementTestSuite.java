package ComplementScreen;

import Restaurant.Dish;
import Restaurant.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ComplementTestSuite extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Menu menu = new Menu();
        Dish dish = menu.getDish(1.1, "A", 2);
        System.out.println(dish);
        System.out.println(dish.getIngredients().get("sausage").amountCanBeAdded(1));

        Stage window = new Stage();
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("complements.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();

        controller.setDish(dish);
        controller.setSelectedIngredient("sausage");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Table Screen");
        window.setScene(new Scene(root));
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
