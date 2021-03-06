package startupscreen;

import ManagerScreen.ManagerScreenController;
import Restaurant.Restaurant;
import Restaurant.Manager;
import Restaurant.Cook;
import Restaurant.Server;
import Restaurant.WorkerType;

import ServingTableScreen.ServingScreen;
import TablesScreen.TablesScreen;
import java.lang.reflect.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.util.Pair;

public class LoginScreen extends AnchorPane {
  @FXML
  private PasswordField password; // The password entered by the user.
  @FXML
  private TextField userName; // The username entered by the user.
    @FXML
  private Label canLogin; // Text to display the login status.
  private final String WORKERS = "restaurantData/workers.txt"; // The workers file.
  private Restaurant restaurant; // The restaurant
  private ArrayList<LoginObject<WorkerType, Pair<String, String>>> userNames; // The details about the workers.

  public LoginScreen(Restaurant restaurant) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      this.restaurant = restaurant;
      this.userNames = new ArrayList<>();
      this.generateLoginObjects();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * this method creates the username and password credentials for workers
   */
  private void generateLoginObjects() {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(WORKERS))) {
      String line = fileReader.readLine();
      while (line != null) {
        String[] details = line.split("\\|");
        WorkerType type = checkWorkerType(details[0]);
        if (type != null) {
          Pair<String, String> credentials = new Pair<>(details[1], details[2]);
          LoginObject<WorkerType, Pair<String, String>> loginObject =
              new LoginObject<>(type, credentials);
          userNames.add(loginObject);
        }
        line = fileReader.readLine();
      }

    } catch (IOException e) {

    }
  }
    /**Check the type of worker
     * @param type The type of the worker
     * @return Returns the WorkerType
     */
  private WorkerType checkWorkerType(String type) {
    if (type.equals("Cook")) {
      return WorkerType.COOK;
    } else if (type.equals("Server")) {
      return WorkerType.SERVER;
    } else if (type.equals("Manager")) {
      return WorkerType.MANAGER;
    }
    return null;
  }
    /**
     *Checks whether the input from the user is correct and displays data essentially.
     * It also checks if the manager has not started the system, then the other workers cannot log in to the system.
     */
  public void checkWorkerLogin() {

    LoginObject<WorkerType, Pair<String, String>> details =
        checkUserIds(new Pair<>(userName.getText(), password.getText()));

    if (details == null) {
      canLogin.setText("Wrong Credentials");
      canLogin.setTextFill(Paint.valueOf("Red"));
    } else {

      if (details.getType() == WorkerType.MANAGER) {

        generateManagerScreen(details.getUserCredentials().getKey());


      } else if (restaurant.isActive() && details.getType() == WorkerType.SERVER) {
        generateServerScreen(details.getUserCredentials().getKey());

      } else if (restaurant.isActive() && details.getType() == WorkerType.COOK) {
        generateCookScreen(details.getUserCredentials().getKey());
      } else {
        canLogin.setText("System Not Active");
        canLogin.setTextFill(Paint.valueOf("Red"));
      }

    }

  }
    /**Checks the credentials from the user
     * @param credentials the username and password of the user
     * @return the credentials.
     */
  private LoginObject<WorkerType, Pair<String, String>> checkUserIds(
      Pair<String, String> credentials) {
    for (LoginObject<WorkerType, Pair<String, String>> details : userNames) {
      if (details.equals(credentials)) {
        return details;
      }

    }
    return null;

  }

    /** Generate the Managers screen after being logged in.
    * @param name the name of the worker.
    */
  private void generateManagerScreen(String name) {
    canLogin.setText("Logged in");
    canLogin.setTextFill(Paint.valueOf("Green"));

    Manager manager = new Manager(name);
    Stage window = new Stage();
    restaurant.addManager(manager);
    ManagerScreenController controller = new ManagerScreenController(manager, restaurant);
    restaurant.restaurantLogger.logWorkerLogin(manager);
    restaurant.getInventory().setManager(manager);
    window.initModality(Modality.WINDOW_MODAL);
    window.setTitle("Manager");
    window.setScene(new Scene(controller));
    window.show();
  }
    /** Generate the Servers screen after being logged in.
     * @param name the name of the worker.
     */
  private void generateServerScreen(String name) {
    Stage window = new Stage();
    Server server = new Server(name);
    restaurant.addServer(server);
    TablesScreen screen = new TablesScreen(server, restaurant);
    restaurant.restaurantLogger.logWorkerLogin(server);
    window.initModality(Modality.WINDOW_MODAL);
    window.setTitle("Server");
    window.setScene(new Scene(screen));
    window.show();
  }
    /** Generate the Cooks screen after being logged in.
     * @param name the name of the worker.
     */
  private void generateCookScreen(String name) {
    Stage window = new Stage();
    Cook cook = new Cook(name);
    restaurant.addCook(cook);
    restaurant.restaurantLogger.logWorkerLogin(cook);
    ServingScreen screen = new ServingScreen(restaurant, restaurant.getServingTable(), cook);
    window.initModality(Modality.WINDOW_MODAL);
    window.setTitle("Serving Screen");
    window.setScene(new Scene(screen));
    window.show();
  }
}
