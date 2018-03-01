import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

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
   * Increases the current stock of String ingredient by int amount if ingredient is initially
   * present, else
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
   *
   * @param ingredient The ingredient that is to be added to
   * @param amount The amount by which the ingredient stock is going to be reduced by
   */
  public void removeStock(String ingredient, int amount) {
    if (amount <= inventory.get(ingredient)) {
      inventory.put(ingredient, inventory.get(ingredient) - amount);
    }
  }

  /**
   * Writes current inventory to the Inventory.txt file to
   */
  public void writeToInventory() {
    // Open the file for writing and write to it.
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(FILENAME)))) {
      for (String ingredient : inventory.keySet()) {
        StringBuilder line = new StringBuilder(ingredient);
        line.append("#" + inventory.get(ingredient));
        out.println(line);
      }
    } catch (Exception e) {

    }

  }

  /**
   * Reads the inventory from Inventory.txt
   */
  public void readInventory() {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(FILENAME))) {
      String line = fileReader.readLine();
      while (line != null) {
        String ingredient = line.split("#")[0];
        int amount = Integer.valueOf(line.split("#")[1]);
        inventory.put(ingredient, amount);
        line = fileReader.readLine();
      }

    } catch (Exception e) {
    }
  }

  @Override
  public String toString() {
    String output = "";
    for (String ingredient : inventory.keySet()) {
      output = output + ingredient + "|" + String.valueOf(inventory.get(ingredient)) + System
          .lineSeparator();
    }
    return output;
  }
}
