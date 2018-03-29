package startupscreen;

import Restaurant.Restaurant;
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


    public void initialize(URL location, ResourceBundle resources) {}
    public void checkWorkerLogin(ActionEvent actionEvent) {
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
                        systemOn = true;
                        break;
                    } else if (systemOn) {
                        canLogin.setText("Logged in");
                        canLogin.setTextFill(Paint.valueOf("Green"));
                        break;
                    } else if (!systemOn) {
                        canLogin.setText("Sorry system off");
                        canLogin.setTextFill(Paint.valueOf("Red"));
                        break;
                    }
                }
                else{
                    canLogin.setText("Sorry wrong username or pass");
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
