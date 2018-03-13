package TableScreen;
import com.sun.tools.javac.util.Name.Table;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TableScreen implements EventHandler<ActionEvent> {

  public Button button;
  public VBox vBox;
  Map<String, Object> fxmlNamespace; //Map of All UI components in this fxml
  FXMLLoader loader; //Loader of the fxml
  ArrayList<Table> tables;

  //Controllers need to be public
  public TableScreen(){

    try {
      this.tables = tables;
      loader = new FXMLLoader(getClass().getResource("TableScreen.fxml"));
      this.fxmlNamespace = loader.getNamespace();

    } catch (Exception e){


    }
  }

  public void displayScreen() throws Exception{
    Stage window = new Stage();

    //This means that you can't change any other windows besides this one.
    window.initModality(Modality.APPLICATION_MODAL);
    window.setTitle("Table Screen");
    Parent root = loader.load();
    window.setScene(new Scene(root));
    window.show();

  }

  public void doSomething(){
    System.out.println("memes");

    Button bub = new Button();
    bub.setOnAction(this);
    bub.setText("memes");
    vBox.getChildren().add(bub);
  }

  @Override
  public void handle(ActionEvent event) {
    if(((Button) event.getSource()).getText().equals("memes")){
      System.out.println("more memes");
    }


  }
}
