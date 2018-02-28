import java.util.ArrayList;
import java.util.HashMap;

public class Dish {

  private MenuItem menuItem;
  private HashMap<String, Integer> complements;
  private HashMap<String, Integer> subtractions;

  public Dish(MenuItem menuItem, HashMap<String, Integer> complements) {
    this.menuItem = menuItem;
    this.complements = complements;
  }

  public MenuItem getMenuItem() {
    return menuItem;
  }

  // Getting all the ingredients for the Dish
  public HashMap<String, Integer> getAllIngredients() {
    HashMap<String, Integer> ingredients = new HashMap<>();

    for (String key : menuItem.getIngredients().keySet()) {
      ingredients.put(key, menuItem.getIngredients().get(key));
    }

    for (String key : complements.keySet()) {
      if (ingredients.containsKey(key)) {
        ingredients.put(key, ingredients.get(key) + complements.get(key));
      } else {
        ingredients.put(key, complements.get(key));
      }
    }

    for (String key : subtractions.keySet()) {
        ingredients.put(key, ingredients.get(key) - subtractions.get(key));
    }
    return ingredients;
  }

  @Override
  public String toString() {
    String name = menuItem.toString() + " w/ ";
    for (String key : complements.keySet()) {
      name += Integer.toString(complements.get(key)) + " " + key + " ";
    }
    return name;
  }
}
