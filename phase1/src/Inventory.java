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
      inventory.put(ingredient, new double[]{0, amount, 0});
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
        line.append("#" + price + "#" + amount + "#" + lowerBounds);
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
        double price = Double.valueOf(line.split("#")[1]);
        double amount = Double.valueOf(line.split("#")[2]);
        double lowerBound = Double.valueOf(line.split("#")[3]);
        inventory.put(ingredient, new double[]{price, amount, lowerBound});
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
