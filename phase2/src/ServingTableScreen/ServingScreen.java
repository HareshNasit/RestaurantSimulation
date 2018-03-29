package ServingTableScreen;

import ComplementScreen.ComplementScreenController;
import Restaurant.Inventory;
import Restaurant.Restaurant;
import Restaurant.Server;
import Restaurant.ServingTable;
import Restaurant.DishStatus;
import Restaurant.DishIngredient;
import Restaurant.ServingTableListener;
import TablesScreen.TablesScreen;
import java.io.IOException;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Restaurant.Dish;
import Restaurant.Cook;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import Restaurant.ModelControllerInterface;
import Restaurant.IWorker;
import java.util.ArrayList;
import javafx.stage.Stage;
import notification.Notification;

public class ServingScreen extends VBox implements ModelControllerInterface {


  @FXML
  private TableView tab1Table;
  @FXML
  private TableColumn tab1TableId;
  @FXML
  private TableColumn tab1Dish;
  @FXML
  private Button accept;
  @FXML
  private Button reject;
  @FXML
  private TableView tab2Table;
  @FXML
  private TableColumn tab2TableId;
  @FXML
  private TableColumn tab2Dish;
  @FXML
  private TableView tab3Table;
  @FXML
  private TableColumn tab3TableId;
  @FXML
  private TableColumn tab3Dish;
  @FXML
  private AnchorPane tab1;
  @FXML
  private Button DishReadyButton;
  @FXML
  private TableColumn tab1Comment;
  @FXML
  private TableColumn tab2Comment;
  @FXML
  private TableColumn tab3Comment;
  @FXML
  private Label checkLabel;
  @FXML
  private ImageView tick;
  @FXML
  private ImageView cross;
  @FXML
  private AnchorPane tab2;
  @FXML
  private ImageView tick1;
  @FXML
  private Pane notificationArea;
  @FXML
  private VBox vBox;
  @FXML
  private AnchorPane tab3;
  @FXML
  private TabPane tabsPane;
  @FXML
  private TableColumn columnDishStatus;
  private Notification notification;


  private Restaurant restaurant;
  private ServingTable servingTable;
  private IWorker cook;

  public ServingScreen(Restaurant restaurant, ServingTable servingTable, IWorker worker) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ServingTableScreen.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      this.restaurant = restaurant;
      this.servingTable = servingTable;
      setCook(worker);
      initialize();
      updateScreen();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }


  }

  public void setCookTable(ArrayList<Dish> Dishes) {
    ObservableList<Dish> dishesToBeCooked = FXCollections.observableArrayList();
    dishesToBeCooked.addAll(Dishes);
    this.tab1Dish.getTableView().setItems(dishesToBeCooked);
  }

  public void setBeingCookedTable(ArrayList<Dish> Dishes) {
    ObservableList<Dish> dishesBeingCooked = FXCollections.observableArrayList();
    dishesBeingCooked.addAll(Dishes);
    this.tab2Dish.getTableView().setItems(dishesBeingCooked);
  }

  public void setReadyTable(ArrayList<Dish> Dishes) {
    ObservableList<Dish> dishesToBeServed = FXCollections.observableArrayList();
    dishesToBeServed.addAll(Dishes);
    this.tab3Dish.getTableView().setItems(dishesToBeServed);
  }

  public void initialize() {

    tab1TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
    tab1Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
    tab1Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));

    tab2TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
    tab2Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
    tab2Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));

    tab3TableId.setCellValueFactory(new PropertyValueFactory<Dish, String>("tableName"));
    tab3Dish.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
    tab3Comment.setCellValueFactory(new PropertyValueFactory<Dish, String>("comment"));

    columnDishStatus.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishStatus"));

    notification = new Notification();
    notificationArea.getChildren().setAll(notification);


  }


  public <T, S> TableColumn<S, T> getTableIDColumn() {
    return tab1TableId;
  }

  public <T, S> TableColumn<S, T> getDishCol() {
    return tab1Dish;
  }

  public TableView getCookTable() {
    return tab1Table;
  }

  public void rowSelected(MouseEvent mouseEvent) {
    Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();
    try {
      if (((Cook) getCook()).canBePrepared(dish, getRestaurant().getInventory())) {
        checkLabel.setText("Can be Prepared");
        checkLabel.setTextFill(Paint.valueOf("Green"));
      } else {
        // checkLabel.setText("OOPS NOT ENOUGH INGREDIENTS");
        checkLabel.setText(getRestaurant().getInventory().getLowIngredientStrings(dish));
        checkLabel.setTextFill(Paint.valueOf("Red"));
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
  }

  public void acceptDish(ActionEvent actionEvent) {

    try {
      Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to accept?",
          ButtonType.YES, ButtonType.CANCEL);
      alert.showAndWait();
      if (alert.getResult() == ButtonType.YES) {
        if (dish.getDishStatus().equals(DishStatus.ORDERED)) {
          ((Cook) getCook()).acceptCook(dish, getRestaurant().getServingTable(), getRestaurant()
              .getInventory());
          setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
          setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
          setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
        } else {
          ((Cook) getCook()).acceptNoCook(dish, getRestaurant().getServingTable());
        }
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
    checkLabel.setText("");
    this.updateScreen();
  }

  public void rejectDish(ActionEvent actionEvent) {
    try {
      Dish dish = (Dish) tab1Table.getSelectionModel().getSelectedItem();
      Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reject?",
          ButtonType.YES, ButtonType.CANCEL);
      alert1.showAndWait();
      if (alert1.getResult() == ButtonType.YES) {
        ((Cook) getCook()).rejectDish(dish, getRestaurant().getServingTable());
        setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
    checkLabel.setText("");

  }

  public void dishReadyToBeServed() {
    try {
      Dish dish = (Dish) tab2Table.getSelectionModel().getSelectedItem();
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you?",
          ButtonType.YES, ButtonType.CANCEL);
      alert.showAndWait();
      if (alert.getResult() == ButtonType.YES) {
        ((Cook) getCook()).serveDish(dish, getRestaurant().getServingTable());
        setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
        setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
        setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
      }
    } catch (NullPointerException e) {
      System.out.println("No row selected");
    }
  }

  public void updateScreen() {
    setCookTable(getRestaurant().getServingTable().getDishesToBeCooked());
    setBeingCookedTable(getRestaurant().getServingTable().getDishesBeingCooked());
    setReadyTable(getRestaurant().getServingTable().getDishesToBeServed());
  }

  @Override
  public void openNotification(String message) {
    notification.pushNotification(message);
  }

  @Override
  public void openReceiverFunction(Inventory inventory, String ingredient, int amount) {
    notification.openScanner();
    notification.getButtonPickUp().setOnAction(event -> inventory.addStock(ingredient, amount));

  }

  public ServingTable getServingTable() {
    return servingTable;
  }

  public void setServingTable(ServingTable servingTable) {
    this.servingTable = servingTable;
  }

  public IWorker getCook() {
    return cook;
  }

  public void setCook(IWorker cook) {
    this.cook = cook;

    if (cook.getType().equals("Cook")) {
      servingTable.addCook((ServingTableListener) cook);
      ((Cook) cook).setScreen(this);
    } else if (cook.getType().equals("Server")) {
      tab1.getChildren().remove(accept);
      tab1.getChildren().remove(checkLabel);
      tab1.getChildren().remove(reject);
      tab1.getChildren().remove(tick);
      tab1.getChildren().remove(cross);
      tab2.getChildren().remove(tick1);
      tab2.getChildren().remove(DishReadyButton);
      Button back = new Button();
      back.setOnAction(event -> {
        TablesScreen tablesScreen = new TablesScreen((Server) cook, restaurant);
        vBox.getChildren().setAll(tablesScreen);
      });
      back.setLayoutX(400);
      back.setLayoutY(3);
      tab3.getChildren().add(back);
      Button serveDishBtn = new Button();
      serveDishBtn.setOnAction(event -> {
        try {
          Dish dish = (Dish) tab3Table.getSelectionModel().getSelectedItem();
          ((Server) cook).serveDish(dish, restaurant);
        } catch (NullPointerException e) {
          System.out.println("No row selected");
        }
      });
      tabsPane.getSelectionModel().select(2);
      serveDishBtn.setLayoutX(492);
      serveDishBtn.setLayoutY(282);
      serveDishBtn.setText("Serve Dish");
      tab3.getChildren().add(serveDishBtn);
      back.setText("Back");
      servingTable.addServer((ServingTableListener) cook);
      ((Server) cook).setScreen(this);
    }


  }

  public void addIngredientsIfEnough() {
    try {
      Dish dish = (Dish) this.tab1Table.getSelectionModel().getSelectedItem();
      if (dish != null) {

        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/ComplementScreen/complements.fxml"));
        Parent root = loader.load();
        ComplementScreen.ComplementScreenController controller = loader.getController();

        controller.setDish(dish);
        controller.setIngredients();

        primaryStage.setTitle("Complement Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(event -> {
          controller.cancelEvent();
        });
        primaryStage.show();

      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      System.out.println("Choose a dish to add compliments");
    }

  }

  public void firstThread(ComplementScreenController controller, Dish dish,
      HashMap<String, DishIngredient> dishIngredientsCopy) {

    Cook cook = new Cook("jeff");
    HashMap<String, DishIngredient> differences = dish
        .getPosDifBetweenTwoIngredientsList(dishIngredientsCopy);
    Dish newIngredients = dish.clone();
    newIngredients.setIngredients(differences);
    if (cook.canBePrepared(newIngredients, restaurant.getInventory())) {
      for (String key : differences.keySet()) {
        restaurant.getInventory().removeStock(key, differences.get(key).getAmount());
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
          "Sorry, there is not enough ingredients in stock right now to fulfill this order.",
          ButtonType.OK);
      alert.showAndWait();
      dish.setIngredients(controller.getIngredientsCopy());
    }
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }
}
