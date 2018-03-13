package TableScreen;

import Restaurant.Table;
import java.util.ArrayList;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TableScreen implements EventHandler<ActionEvent> {

  public VBox vBox;
  public TableView tableView; //Table of tables
  public Button tableButton; //Button that will open the Table's menu
  public Button servingTableButton; // Button that will open the ServingTableMenu
  public Button managerButton; //Button that will call the manager
  public TableColumn tableStatusColumn;
  public TableColumn occupiedTableColumn;
  public TableColumn tableIDColumn;
  FXMLLoader loader; //Loader of the fxml
  private ArrayList<Table> tables;

  //Controllers need to be public
  public TableScreen(){

    try {
      loader = new FXMLLoader(getClass().getResource("TableScreen.fxml"));
      ObservableList<Table> test = FXCollections.observableArrayList();
      test.add(new Table("A"));
      tableView.setItems(test);
    } catch (Exception e){


    }
  }

  public ArrayList<Table> getTables() {
    return tables;
  }

  public void setTables(ArrayList<Table> tables) {
    this.tables = tables;
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
