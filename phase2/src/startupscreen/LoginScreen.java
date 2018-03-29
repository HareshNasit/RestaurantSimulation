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

import java.io.IOException;
import java.net.URL;
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
                
            });
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
        if(userName.getText().equals("harsh") && password.getText().equals("Messi>Ronaldo")){
            canLogin.setText("Login successful");
            canLogin.setTextFill(Paint.valueOf("Green"));
        }
        else{
            canLogin.setText("Sorry, login unsuccessful");
            canLogin.setTextFill(Paint.valueOf("Red"));
        }
    }
}
