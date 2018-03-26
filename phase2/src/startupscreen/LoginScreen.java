package startupscreen;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
}
