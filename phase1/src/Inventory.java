import java.io.FileNotFoundException;
import java.util.HashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Inventory uses a a HashMap data structure where the key is a String ingredient and value is the
 * number of that ingredient available.
 */
public class Inventory {

  private final String INVENTORYFILE = "Inventory.txt";
  private final String REQUESTSFILE = "request.txt";

  // Key is the ingredient name, and the Value for is an array where:
  // [0] - is the price of ingredient
  // [1] - is the current amount of ingredient
  // [2] - is the lowerBound amount of ingredient for it to be considered low on stock
  // [3] - is the default request amount for restock
  private HashMap<String, double[]> inventory;

  /**
   * Constructs a new Inventory object
   */
  Inventory() {
    inventory = new HashMap<String, double[]>();
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
      inventory.get(ingredient)[1] += amount;
    } else {
      inventory.put(ingredient, new double[]{0, amount, 0, 0});
    }


  }

  /**
   * Reduces the current stock of String ingredient by int amount if available
   *
   * @param ingredient The ingredient that is to be added to
   * @param amount The amount by which the ingredient stock is going to be reduced by
   */
  public void removeStock(String ingredient, int amount) {
    if (amount <= inventory.get(ingredient)[1]) {
      inventory.get(ingredient)[1] -= amount;
    }
  }

  /**
   * Writes current inventory to the Inventory.txt file to
   */
  public void writeToInventory() {
    // Open the file for writing and write to it.
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(INVENTORYFILE)))) {
      for (String ingredient : inventory.keySet()) {

        StringBuilder line = new StringBuilder(ingredient);
        String price = String.valueOf(inventory.get(ingredient)[0]);
        String amount = String.valueOf(inventory.get(ingredient)[1]);
        String lowerBounds = String.valueOf(inventory.get(ingredient)[2]);
        String restockAmount = String.valueOf(inventory.get(ingredient)[3]);
        line.append("#" + price + "#" + amount + "#" + lowerBounds + "#" + restockAmount);
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

        String ingredient = line.split("#")[0];
        double price = Double.valueOf(line.split("#")[1].trim());
        double amount = Double.valueOf(line.split("#")[2].trim());
        double lowerBound = Double.valueOf(line.split("#")[3].trim());
        double restockAmount = Double.valueOf(line.split("#")[4].trim());
        inventory.put(ingredient, new double[]{price, amount, lowerBound, restockAmount});

        line = fileReader.readLine();
      }

    } catch (java.io.IOException e) {
    }
  }

  /**
   * String list of ingredients that are low on stock
   *
   * @return String list of ingredients that need to be restocked
   */
  public void getLowIngredients() {
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(REQUESTSFILE)))) {
      for (String ingredient : inventory.keySet()) {

        if (inventory.get(ingredient)[1] < inventory.get(ingredient)[2]) {
          System.out.println(ingredient);
          out.println(ingredient + ": " + String.valueOf(inventory.get(ingredient)[3]));
        }

      }

    } catch (java.io.IOException e) {
    }

  }

  @Override
  public String toString() {
    String output = "";
    for (String ingredient : inventory.keySet()) {
      output = output + ingredient + "|" + String.valueOf(inventory.get(ingredient)[1]) + System
          .lineSeparator();
    }
    return output;
  }
}
