package BillScreen;

import Restaurant.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BillScreen extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bill.fxml"));
        Parent root = loader.load();
        Restaurant restaurant = new Restaurant(new Menu(), new Inventory(), new ServingTable());
        restaurant.createNewReceiptFile();

        Table table = new Table("A");
        Menu menu = new Menu();
        table.addSingleOrder(menu.getDish(1.0, "A", 1));
        table.addSingleOrder(menu.getDish(2.0, "B", 2));
        table.addSingleOrder(menu.getDish(2.0, "B", 2));
        table.addSingleOrder(menu.getDish(3.0, "C", 3));

        BillScreenController controller = loader.getController();
        table.setTableSize(3);
        controller.setTable(table);
        controller.setRestaurant(restaurant);


        primaryStage.setTitle("Bill Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
