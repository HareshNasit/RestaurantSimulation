import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Inventory uses a a HashMap data structure where the key is a String ingredient and value is the
 * number of that ingredient available.
 */
public class Inventory {

  private final String FILENAME = "Inventory.txt";
  private HashMap<String, Integer> inventory;

  /**
   * Constructs a new Inventory object
   */
  Inventory() {
    inventory = new HashMap<String, Integer>();
  }

  /**
   * Increases the current stock of String ingredient by int amount
   *
   * @param ingredient The ingredient that is to be added to
   * @param amount The amount by which the ingredient stock is going to be increased by
   */
  public void addStock(String ingredient, int amount) {
    if (inventory.get(ingredient) != null) {
      inventory.put(ingredient, inventory.get(ingredient) + amount);
    } else {
      inventory.put(ingredient, amount);
    }


  }

  /**
   * Reduces the current stock of String ingredient by int amount if available
   * @param ingredient The ingredient that is to be added to
   * @param amount The amount by which the ingredient stock is going to be reduced by
   */
  public void removeStock(String ingredient, int amount) {
    if (amount <= inventory.get(ingredient)) {
      inventory.put(ingredient, inventory.get(ingredient) - amount);
    }
  }

  public void writeToInventory() {
    // Open the file for writing and write to it.
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILENAME)))) {
      for (String ingredient : inventory.keySet()) {
        StringBuilder line = new StringBuilder(ingredient);
        line.append(" | " + inventory.get(ingredient));
        out.println(line);
      }
    } catch (Exception e) {

    }

  }

}
