package startupscreen;

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

public class LoginScreen implements Initializable{

    public Button managerBtn;
    public Button cookBtn;
    public Button serverBtn;
    public PasswordField password;
    public TextField userName;
    public Label canLogin;
    public Button cookLoginBtn;
    public TextField cookNameLbl;
    public AnchorPane loginScreenAnchorPane;
    public Button loginBtn;
    public AnchorPane workerLoginScreenAnchorPane;
    private boolean systemOn = false;
    private final String WORKERS = "workers.txt";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cookLoginScreen(javafx.event.ActionEvent mouseEvent) {
        try {
            Parent managerScreen = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
            Scene managerScene = new Scene(managerScreen);
            Stage mainScreen = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Button back1 = new Button();
            back1.setOnAction(event -> {
                LoginScreen loginScreen = new LoginScreen();
                loginScreenAnchorPane.getChildren().setAll((Collection<? extends Node>) loginScreen);
            });
            back1.setLayoutX(50);
            back1.setLayoutY(10);
            workerLoginScreenAnchorPane.getChildren().add(back1);
            mainScreen.setScene(managerScene);
            mainScreen.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverLoginScreen(ActionEvent mouseEvent) {
        try {
            Parent managerScreen = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
            Scene managerScene = new Scene(managerScreen);
            Stage mainScreen = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            mainScreen.setScene(managerScene);
            mainScreen.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void workerLoginScreen(javafx.event.ActionEvent actionEvent) {
        try {
            Parent managerScreen = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
            Scene managerScene = new Scene(managerScreen);
            Stage mainScreen = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            mainScreen.setScene(managerScene);
            mainScreen.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkCookLogin(javafx.event.ActionEvent actionEvent) {
        if(cookNameLbl.getText().equals("harsh")){
            System.out.println("yayyy login successful");
        }
    }

    public void checkWorkerLogin(ActionEvent actionEvent) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(WORKERS))) {
            String line = fileReader.readLine();
            while (line != null) {
                String[] details = line.split("\\|");
                if(checkUserIds(userName.getText(),password.getText(),details)) {
                    System.out.println("Yallaa it workssss");

                    if (details[0].equals("Manager")) {
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
                line = fileReader.readLine();
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
        public boolean checkUserIds(String username, String password, String[] details){
            if((details[1].equals(username)) && (details[2].equals(password))){
                return true;
            }
            else {
                System.out.println("FAILLLLLLL");
                return false;
            }
        }
}
