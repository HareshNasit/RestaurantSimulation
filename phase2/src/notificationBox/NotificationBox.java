package notificationBox;



import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NotificationBox {

  public static void display(String message) {
      Stage window = new Stage(StageStyle.UNDECORATED);

      //Block events to other windows
      window.initModality(Modality.WINDOW_MODAL);
      window.setMinWidth(250);
      window.setMinHeight(100);

      Label label = new Label();
      label.setText(message);

      VBox layout = new VBox(10);
      layout.getChildren().addAll(label);
      layout.setAlignment(Pos.CENTER_RIGHT);

      window.setX(100);
      window.setY(100);
      layout.setOnMouseClicked(event -> window.close());
      Scene scene = new Scene(layout);
      FadeTransition ft = new FadeTransition(Duration.millis(3000), layout);

      ft.setFromValue(1.0);
      ft.setToValue(0.0);

      ft.setOnFinished(event -> window.close());
      ft.play();
      window.setScene(scene);
      window.show();

    }

  }

