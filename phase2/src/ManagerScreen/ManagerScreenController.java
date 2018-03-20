package ManagerScreen;

import Restaurant.Inventory;
import Restaurant.InventoryIngredient;
import Restaurant.Table;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagerScreenController implements Initializable {

  private final String  REQUESTFILE = "request.txt";

  @FXML
  private TextArea textFieldRequest;
  @FXML //Allows the variable to be encapsualted
  private Tab tabInventory;


  @Override
  public void initialize(URL location, ResourceBundle resources) {

    updateRequestText();

  }

  private ObservableList<InventoryIngredient> getInventoryTableData() {
    ObservableList<InventoryIngredient> ingredients = FXCollections.observableArrayList();

    return ingredients;
  }

  /**
   * Updates the request tab in the manager screen with the contents of request.txt
   */
  private void updateRequestText(){
    try (BufferedReader fileReader = new BufferedReader(new FileReader(REQUESTFILE))) {
      String line = fileReader.readLine();
      while (line != null) {
          textFieldRequest.appendText(line + System.lineSeparator());
          line = fileReader.readLine();
      }
    } catch (java.io.IOException e) {
    }
  }

  //---------------------- Getters and Setters --------------------

  public Tab getTabInventory() {
    return tabInventory;
  }

  public void setTabInventory(Tab tabInventory) {
    this.tabInventory = tabInventory;
  }

  public TextArea getTextFieldRequest() {
    return textFieldRequest;
  }

  public void setTextFieldRequest(TextArea textFieldRequest) {
    this.textFieldRequest = textFieldRequest;
  }
}
