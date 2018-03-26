package startupscreen;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cookLoginScreen(MouseEvent mouseEvent) {
    }

    public void serverLoginScreen(MouseEvent mouseEvent) {
    }

    public void managerLoginScreen(javafx.event.ActionEvent actionEvent) {
        try {
            Parent managerScreen = FXMLLoader.load(getClass().getResource("ManagerLogin.fxml"));
            Scene managerScene = new Scene(managerScreen);
            Stage mainScreen = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            mainScreen.setScene(managerScene);
            mainScreen.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkLogin(javafx.event.ActionEvent actionEvent) {
        if(userName.getText().equals("harsh") && password.getText().equals("halamadrid")){
            canLogin.setText("Login successfull");
            canLogin.setTextFill(Paint.valueOf("Green"));
        }
        else{
            canLogin.setText("Sorry, login Unsuccessful");
            canLogin.setTextFill(Paint.valueOf("Red"));
        }
    }
}
