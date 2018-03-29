package startupscreen;

import ManagerScreen.ManagerScreenController;
import Restaurant.Restaurant;
import Restaurant.Manager;
import Restaurant.Cook;
import Restaurant.Server;

import ServingTableScreen.ServingScreen;
import TablesScreen.TablesScreen;
import javafx.event.ActionEvent;
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

public class LoginScreen extends AnchorPane{

    public PasswordField password;
    public TextField userName;
    public Label canLogin;
    public Button loginBtn;
    public AnchorPane workerLoginScreenAnchorPane;
    private boolean systemOn = false;
    private final String WORKERS = "workers.txt";
    private Restaurant restaurant;

    public LoginScreen(Restaurant restaurant){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.restaurant = restaurant;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void checkWorkerLogin() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(WORKERS))) {
            String line = fileReader.readLine();
            ArrayList workers = new ArrayList();
            while (line != null) {
                String[] details = line.split("\\|");
                workers.add(details);
                line = fileReader.readLine();
            }
            for(Object details: workers) {

                if(checkUserIds(userName.getText(),password.getText(),(String[]) details)) {
                    System.out.println("Yallaa it workssss");

                    if (((String[])details)[0].equals("Manager")) {
                        canLogin.setText("Logged in");
                        canLogin.setTextFill(Paint.valueOf("Green"));

                        Manager manager = new Manager(userName.getText());
                        Stage window = new Stage();
                        ManagerScreenController controller = new ManagerScreenController(manager, restaurant);
                        restaurant.getInventory().setManager(manager);
                        window.initModality(Modality.WINDOW_MODAL);
                        window.setTitle("Manager");
                        window.setScene(new Scene(controller));
                        window.show();

                    } else if (restaurant.isActive() && ((String[])details)[0].equals("Server")) {
                        Stage window = new Stage();
                        Server server = new Server(userName.getText());
                        restaurant.addServer(server);
                        TablesScreen screen = new TablesScreen(server, restaurant);
                        window.initModality(Modality.WINDOW_MODAL);
                        window.setTitle("Server");
                        window.setScene(new Scene(screen));
                        window.show();
                        break;
                    }else if (restaurant.isActive() && ((String[])details)[0].equals("Cook")){}
                        Stage window = new Stage();
                        Cook cook = new Cook(userName.getText());
                        restaurant.addCook(cook);
                        ServingScreen screen = new ServingScreen(restaurant, restaurant.getServingTable(), cook);
                        window.initModality(Modality.WINDOW_MODAL);
                        window.setTitle("Serving Screen");
                        window.setScene(new Scene(screen));
                        window.show();
                    } else{
                    canLogin.setText("Sorry wrong username or pass or system off");
                    canLogin.setTextFill(Paint.valueOf("Red"));
                }
            }
        } catch (java.io.FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + "'");
        }
    }
        private boolean checkUserIds(String username, String password, String[] details){
            if((details[1].equals(username)) && (details[2].equals(password))){
                return true;
            }
            else {
                System.out.println("FAILLLLLLL");
                return false;
            }
        }
}
