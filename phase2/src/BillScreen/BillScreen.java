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
  public void start(Stage primaryStage) throws Exception {

    Restaurant restaurant = new Restaurant(new Menu(), new Inventory(), new ServingTable());
    restaurant.createNewReceiptFile();
    Table table = new Table("A");
    Menu menu = new Menu();
    table.addSingleOrder(menu.getDish(1.0, "A", 1));
    table.addSingleOrder(menu.getDish(2.0, "A", 2));
    table.addSingleOrder(menu.getDish(2.0, "A", 3));
    table.addSingleOrder(menu.getDish(3.0, "A", 4));
    table.addSingleOrder(menu.getDish(1.0, "A", 5));
    table.addSingleOrder(menu.getDish(2.0, "A", 6));
    table.addSingleOrder(menu.getDish(2.0, "A", 7));
    table.addSingleOrder(menu.getDish(3.0, "A", 8));
      table.setTableSize(8);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("bill.fxml"));
    Parent root = loader.load();
    BillScreenController controller = loader.getController();

    controller.setTable(table);
    controller.setRestaurant(restaurant);

    primaryStage.setTitle("Bill Menu");
    primaryStage.setScene(new Scene(root));

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
