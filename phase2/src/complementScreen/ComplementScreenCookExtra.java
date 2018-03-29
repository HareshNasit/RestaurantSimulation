package complementScreen;

import Restaurant.Cook;
import MenuDishes.Dish;
import Restaurant.DishIngredient;
import Restaurant.Restaurant;
import java.util.HashMap;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 */
public class ComplementScreenCookExtra extends ComplementScreenController {

  private Restaurant restaurant;


  /**
   * For the add extras button. If the cook adds ingredients that are in the inventory, the
   * ingredients get removed. Otherwise, an alert pops up and tells the cook that there is not
   * enough of an ingredient. The cook should only be able to press the button if the dish was
   * returned.s
   */
  public void addIfIngredientsEnough() {

    HashMap<String, DishIngredient> dishIngredientsCopy = dish.cloneIngredients();
    Cook cook = new Cook("jeff");
    HashMap<String, DishIngredient> differences = dish
        .getPosDifBetweenTwoIngredientsList(dishIngredientsCopy);

    Dish newIngredients = dish.clone();

    newIngredients.setIngredients(differences);
    if (cook.canBePrepared(newIngredients, restaurant.getInventory())) {
      for (String key : differences.keySet()) {
        restaurant.getInventory().removeStock(key, differences.get(key).getAmount());
      }
    } else {
      String missingIngredients = "";
      for (String key : differences.keySet()) {
        int missingAmount = differences.get(key).getAmount();
        if (missingAmount > 0) {
          missingIngredients += "Missing: " + key + System.lineSeparator();
        }
      }
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
          "Sorry, there is not enough ingredients in stock right now to fulfill this order."
              + System.lineSeparator() + missingIngredients,
          ButtonType.OK);
      alert.showAndWait();
      dish.setIngredients(this.getIngredientsCopy());
    }
  }


  @Override
  public void acceptEvent() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you are finished?",
        ButtonType.YES, ButtonType.CANCEL);
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES) {
      this.addIfIngredientsEnough();
      this.closeWindow(accept);
    }
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }
}
