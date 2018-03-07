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

  private final String INVENTORYFILE = "Inventory.txt";
  private final String REQUESTSFILE = "request.txt";
  private InventoryListener manager;

  private HashMap<String, InventoryIngredient> inventory;

  /**
   * Constructs a new Inventory object
   */
  Inventory() {
    inventory = new HashMap<String, InventoryIngredient>();
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
      inventory.get(ingredient).increaseQuantity(amount);
    } else {
      inventory.put(ingredient, new InventoryIngredient(ingredient, 0));
    }
  }

    /**
     * Checks whether a particular dish has enough ingredients to cook that dish.
     *
     * @param dishIngredients The ingredient that is to be added to
     * @return boolean returns true if the inventory has enough ingredients to prepare a dish.
     */
  public boolean hasEnoughIngredients(HashMap<String, Integer> dishIngredients) {
    for (String ingredient : dishIngredients.keySet()) {

      if (!inventory.containsKey(ingredient.trim())) {
        System.out.println("Not Available: " + ingredient);
        return false;
      } else if (inventory.get(ingredient.trim()).getCurrentQuantity() < dishIngredients
          .get(ingredient)) {
        System.out.println("Not Enough: " + ingredient);
        return false;
      }
    }
    return true;
  }

  /**
   * Reduces the current stock of String ingredient by int amount if available
   *
   * @param ingredient The ingredient that is to be added to
   * @param amount The amount by which the ingredient stock is going to be reduced by
   */
  public void removeStock(String ingredient, int amount) {
    if (amount <= inventory.get(ingredient.trim()).getCurrentQuantity()) {
      inventory.get(ingredient.trim()).decreaseQuantity(amount);

      if (inventory.get(ingredient.trim()).getCurrentQuantity() <
          inventory.get(ingredient.trim()).getCurrentQuantity())

      {
        manager.notifyLowStock(ingredient);
      }
      this.getLowIngredients();
    }
  }

  /**
   * Writes current inventory to the Inventory.txt file
   */
  public void writeToInventory() {
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(INVENTORYFILE)))) {
      for (InventoryIngredient ingredient : inventory.values()) {
        String line = ingredient.getName() + "#"
            + String.valueOf(ingredient.getCurrentQuantity()) + "#"
            + String.valueOf(ingredient.getLowerThreshold()) + "#"
            + String.valueOf(ingredient.getRestockQuantity());
        out.println(line);

      }
    } catch (Exception e) {

    }

  }

  /**
   * Reads the inventory from Inventory.txt
   */
  public void readInventory() {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(INVENTORYFILE))) {
      String line = fileReader.readLine();
      while (line != null) {
        String name = line.split("#")[0].toLowerCase().trim();
        int amount = Integer.valueOf(line.split("#")[1].trim());
        int lowerBound = Integer.valueOf(line.split("#")[2].trim());
        int restockAmount = Integer.valueOf(line.split("#")[3].trim());
        InventoryIngredient ingredient = new InventoryIngredient(name, amount, lowerBound,
            restockAmount);
        inventory.put(name, ingredient);

        line = fileReader.readLine();
      }

    } catch (java.io.IOException e) {
    }
  }

  /**
   * Writes the list of ingredients that need to be requested in request.txt
   *
   * @return String list of ingredients that need to be restocked
   */
  public void getLowIngredients() {
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(REQUESTSFILE)))) {
      for (InventoryIngredient ingredient : inventory.values()) {
        if (ingredient.getCurrentQuantity() < ingredient.getLowerThreshold()) {
          out.println(ingredient.getName() + ": " + ingredient.getRestockQuantity());
        }

      }

    } catch (java.io.IOException e) {
    }

  }

  public int getIngredientAmount(String name) {
    return this.inventory.get(name).getCurrentQuantity();
  }

  @Override
  public String toString() {
    String output = "";
    for (InventoryIngredient ingredient : inventory.values()) {
      output =
          output + ingredient.getName() + "|" + String.valueOf(ingredient.getCurrentQuantity()) +
              System.lineSeparator();
    }
    return output;
  }
}
