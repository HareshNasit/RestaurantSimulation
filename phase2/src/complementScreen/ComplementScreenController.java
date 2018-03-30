package complementScreen;

import MenuDishes.Dish;
import Restaurant.DishIngredient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

/**
 * This class handles the complements screen and its methods
 */
public class ComplementScreenController extends GridPane implements EventHandler<ActionEvent> {


  @FXML
  public Button cancel;
  @FXML
  public Button addition;
  @FXML
  public Button accept;
  @FXML
  public Button subtract;
  @FXML
  public Dish dish;
  @FXML
  public TableView tableView;
  @FXML
  public TableColumn ingredientColumn;
  @FXML
  public TableColumn amountColumn;
  @FXML
  public TableColumn priceColumn;
  @FXML
  public TextArea dishStatusLabel;

  private HashMap<String, DishIngredient> ingredients;
  private HashMap<String, DishIngredient> ingredientsCopy;
  private String selectedIngredient;

  public ComplementScreenController(Dish dish){
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("complements.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      initialize();
      setDish(dish);
      setIngredients();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public ComplementScreenController(){
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("complements.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
      initialize();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  /**
   * Sets up the main GUI components
   */
  public void initialize() {
    getIngredientColumn()
        .setCellValueFactory(new PropertyValueFactory<DishIngredient, String>("name"));
    getAmountColumn()
        .setCellValueFactory(new PropertyValueFactory<DishIngredient, Integer>("amount"));
    getPriceColumn()
        .setCellValueFactory(new PropertyValueFactory<DishIngredient, Integer>("price"));
    this.selectedIngredient = "";
    this.dishStatusLabel.setText("Pick a complement");
    this.setRowAction();

  }


  @Override
  public void handle(ActionEvent event) {
    try {
      this.updateSelectedIngredient();
      this.updateButtonAddSubDisabled();
      this.addAndSubtractEvent(event);
      this.updateButtonAddSubDisabled();
      this.tableView.refresh();
    } catch (NullPointerException e) {
      Alert alert = new Alert(Alert.AlertType.WARNING, "Select a complement!",
          ButtonType.OK);
      alert.showAndWait();
    }

  }

  /**
   * When the cancel button gets pressed, the server gets returned to the previous screen.
   */
  public void acceptEvent() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you are finished?",
        ButtonType.YES, ButtonType.CANCEL);
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES) {
      this.closeWindow(accept);
    }
  }

  /**
   * When the cancel button gets pressed, the server gets returned to the previous screen, and all
   * the ingredients gets set back to their original amounts.
   */
  public void cancelEvent() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?",
        ButtonType.YES, ButtonType.CANCEL);
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES) {
      if (this.dish != null) {
        dish.setIngredients(this.ingredientsCopy);
      }
      this.closeWindow(cancel);
    }
  }

  private void updateDishLabel() {
    this.dishStatusLabel.setText(this.dish.toString());
  }

  /**
   * When the add or subtract button get pressed. The selected ingredient gets increased or
   * decreased by 1.
   *
   * @param event mouse click.
   */
  public void addAndSubtractEvent(ActionEvent event) {

    if ((event.getSource()) == addition) {
      if (this.dish.getIngredients().get(selectedIngredient).amountCanBeAdded(1)) {
        this.dish.addIngredient(selectedIngredient, 1);
      }
    } else if ((event.getSource()) == subtract) {
      if (this.dish.getIngredients().get(selectedIngredient).amountCanBeSubtracted(1)) {
        this.dish.subtractIngredient(selectedIngredient, 1);
      }
    }
    this.updateDishLabel();
  }


  /**
   * Updates the selected row ingredient.
   */
  public void updateSelectedIngredient() {
    DishIngredient ingredient = (DishIngredient) getTableView().getSelectionModel()
        .getSelectedItem();
    this.selectedIngredient = ingredient.getName();
  }

  /**
   * Disables the add and subtract buttons respectively when you can no longer subtract from that
   * ingredient (Whether it reached its lower bound or upper bound).
   */
  public void updateButtonAddSubDisabled() {
    if (!this.selectedIngredient.equals("")) {
      if (this.dish.getIngredients().get(selectedIngredient).amountCanBeAdded(1)) {
        addition.setDisable(false);
      } else {
        addition.setDisable(true);
      }
      if (this.dish.getIngredients().get(selectedIngredient).amountCanBeSubtracted(1)) {
        subtract.setDisable(false);
      } else {
        subtract.setDisable(true);
      }
    }
  }

  /**
   * When a row gets selected, the add and subtraction buttons get updated. The
   * selectedDishIngredient also gets changed to the selected row ingredient.
   */
  private void setRowAction() {
    getTableView().setRowFactory(tv -> {
      TableRow<DishIngredient> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty()) {
          DishIngredient rowData = row.getItem();
          updateSelectedIngredient();
          updateButtonAddSubDisabled();
        }
      });
      return row;
    });
  }

  /**
   * Returns an ObservableList of the Restaurant's table list.
   *
   * @return ObservableList of Tables
   */
  private ObservableList<DishIngredient> getDishIngredient() {
    ObservableList<DishIngredient> dishIngredients = FXCollections.observableArrayList();
    ArrayList<DishIngredient> ingredients = new ArrayList<>();
    for (String key : this.ingredients.keySet()) {
      ingredients.add(this.ingredients.get(key));
    }
    dishIngredients.addAll(ingredients);
    return dishIngredients;
  }

  /**
   * Sets the dish for the complement controller.
   *
   * @param dish dish being set.
   */
  public void setDish(Dish dish) {
    this.dish = dish;
    this.ingredients = dish.getIngredients();
    this.ingredientsCopy = new HashMap<>();
    for (String ingredient : this.ingredients.keySet()) {
      this.ingredientsCopy.put(ingredient, this.ingredients.get(ingredient).clone());
    }

  }

  /**
   * Returns a hashMap of the ingredients
   * @return the hash map of the ingredients
   */
  public HashMap<String, DishIngredient> getIngredientsCopy() {
    return ingredientsCopy;
  }

  /**
   * Sets the selectedIngredient.
   *
   * @param ingredient ingredient that selectedIngredient is being changed to
   */
  private void setSelectedIngredient(String ingredient) {
    this.selectedIngredient = ingredient;
  }


  /**
   * Sets the UI tables to show the Restaurant list of tables.
   */
  public void setIngredients() {
    getTableView().setItems(getDishIngredient());

  }


  /**
   * returns the priceColumn.
   *
   * @return priceColumn
   */
  private TableColumn getPriceColumn() {
    return priceColumn;
  }

  /**
   * returns the amountColumn.
   *
   * @return amountColumn
   */
  private TableColumn getAmountColumn() {
    return amountColumn;
  }


  /**
   * returns the ingredientColumn.
   *
   * @return ingredientColumn
   */
  private TableColumn getIngredientColumn() {
    return ingredientColumn;
  }

  /**
   * returns the tableView.
   *
   * @return tableView
   */
  private TableView getTableView() {
    return tableView;
  }

  /**
   * Closes the window.
   *
   * @param button Button that's being used to close the scene.
   */
  public void closeWindow(Button button) {
    Stage stage = (Stage) button.getScene().getWindow();
    stage.close();
  }


}
