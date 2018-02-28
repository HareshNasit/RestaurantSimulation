import java.util.HashMap;

/**
 * Inventory uses a a HashMap data structure where the key is a String ingredient and value is the
 * number of that ingredient available.
 */
public class Inventory {

  private HashMap<String, Integer> invetory;

  /**
   * Constructs a new Inventory object
   */
  Inventory() {
    invetory = new HashMap<String, Integer>();
  }

  /**
   * Increases the current stock of String ingredient by int amount
   *
   * @param ingredient The ingredient that is to be added to
   * @param amount The amount by which the ingredient stock is going to be increased by
   */
  public void addStock(String ingredient, int amount) {
    invetory.put(ingredient, invetory.get(ingredient) + amount);
  }

  /**
   * Reduces teh current stock of String ingredient by int amount
   *
   * @param ingredient The ingredient that is to be added to
   * @param amount The amount by which the ingredient stock is going to be reduced by
   */
  public void removeStock(String ingredient, int amount) {
    invetory.put(ingredient, invetory.get(ingredient) - amount);
  }

}
