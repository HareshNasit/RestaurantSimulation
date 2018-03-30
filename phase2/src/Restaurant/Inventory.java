package Restaurant;

import MenuDishes.Dish;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

  private final String INVENTORYFILE = "restaurantData/Inventory.txt";
  private final String REQUESTSFILE = "restaurantData/request.txt";
  private Notifiable manager;
  private HashMap<String, InventoryIngredient> inventory;
  private ArrayList<String> lowIngredients;

  /** Constructs a new Inventory object */
  public Inventory() {
    inventory = new HashMap<String, InventoryIngredient>();
    lowIngredients = new ArrayList<String>();
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
    writeToInventory();
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
      } else if (inventory.get(ingredient.trim()).getCurrentQuantity()
          < dishIngredients.get(ingredient)) {
        System.out.println("Not Enough: " + ingredient);
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the strings of low ingredients
   * @param dish
   * @return
   */
  public String getLowIngredientStrings(Dish dish) {
    HashMap<String, Integer> dishIngredients = dish.getIngredientAmounts();
    StringBuilder lowIngredients = new StringBuilder();
    for (String ingredient : dishIngredients.keySet()) {
      System.out.println("Checking: " + ingredient);
      if (!inventory.containsKey(ingredient.trim())) {
        lowIngredients.append(ingredient + " not available" + System.lineSeparator());
      } else if (inventory.get(ingredient.trim()).getCurrentQuantity()
          < dishIngredients.get(ingredient)) {
        int difference = Math.abs(inventory.get(ingredient.trim()).getCurrentQuantity()
            - dishIngredients.get(ingredient));
        lowIngredients.append(ingredient + "missing: " + String.valueOf(difference)
            + " units"+ System.lineSeparator());
      }
    }
    return lowIngredients.toString();
  }

  public InventoryIngredient getInventoryIngredient(String ingredient){
      return this.inventory.get(ingredient);
  }

  public Collection<InventoryIngredient> getInventoryAsCollection(){
    return inventory.values();
  }

  public void addNewIngredient(InventoryIngredient ingredient){
    inventory.put(ingredient.getName(), ingredient);
    writeToInventory();
  }

  public void removeIngredient(String ingredientName){
    inventory.remove(ingredientName);
    writeToInventory();
  }

  /**
   * Reduces the current stock of String ingredient by int amount if available
   *
   * @param ingredient The ingredient that is to be added to
   * @param amount The amount by which the ingredient stock is going to be reduced by
   */
  public void removeStock(String ingredient, int amount) {
    if (amount <= inventory.get(ingredient.trim()).getCurrentQuantity()) {
      writeToRequest(ingredient, amount);
      inventory.get(ingredient.trim()).decreaseQuantity(amount);
      writeToInventory();

      }
    }

  /**
   * Checks if ingredients needs to be written to request.txt (i.e ingredient is less than lower threshold)
   * @param ingredient ingredient to be checked
   * @param amount amount subtracted from ingredient
   */
  private void writeToRequest(String ingredient, int amount){
    InventoryIngredient ingredient1 = inventory.get(ingredient.trim());
    if (ingredient1.getCurrentQuantity() - amount < ingredient1.getLowerThreshold() &&
        !checkIfRequested(ingredient1.getName())){
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(REQUESTSFILE, true));
        writer.write(ingredient1.getName() + ": " + ingredient1.getRestockQuantity() + System.lineSeparator());
        lowIngredients.add(ingredient1.getName());
        writer.close();
        manager.sendNotifications("request.txt updated");
      } catch (IOException e){}

    }
  }

  /** Writes current inventory to the Inventory.txt file */
  public void writeToInventory() {
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(INVENTORYFILE)))) {
      for (InventoryIngredient ingredient : inventory.values()) {
        String line =
            ingredient.getName()
                + "#"
                + String.valueOf(ingredient.getCurrentQuantity())
                + "#"
                + String.valueOf(ingredient.getRestockQuantity())
                + "#"
                + String.valueOf(ingredient.getLowerThreshold());
        out.println(line);
      }
    } catch (Exception e) {

    }

  }

  /**
   * Checks if an ingredient has been requested
   * @param ingredientName Ingredient to be checked
   * @return if dish has been requested
   */
  private boolean checkIfRequested(String ingredientName){

    for (String name: lowIngredients){
      if (name.equals(ingredientName)){
        return true;
      }
    }
    return false;

  }

  /** Reads the inventory from Inventory.txt */
  public void readInventory() {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(INVENTORYFILE))) {
      String line = fileReader.readLine();
      while (line != null) {
        String name = line.split("#")[0].toLowerCase().trim();
        int amount = Integer.valueOf(line.split("#")[1].trim());
        int restockAmount = Integer.valueOf(line.split("#")[2].trim());
        int lowerBound = Integer.valueOf(line.split("#")[3].trim());
        InventoryIngredient ingredient =
            new InventoryIngredient(name, amount, restockAmount, lowerBound);
        inventory.put(name, ingredient);

        line = fileReader.readLine();
      }

    } catch (java.io.IOException e) {
    }
  }

  /**
   * Reads request to lowIngrdients
   */
  public void readRequest(){
    try (BufferedReader fileReader = new BufferedReader(new FileReader(REQUESTSFILE))) {
      String line = fileReader.readLine();
      while (line != null) {
        String name = line.split(":")[0].toLowerCase().trim();
        lowIngredients.add(name);

        line = fileReader.readLine();
      }

    } catch (java.io.IOException e) {
    }
  }


  /**
   * Returns the amount of ingredient
   *
   * @param name name of ingredient
   * @return amount in inventory
   */
  public int getIngredientAmount(String name) {
    return this.inventory.get(name).getCurrentQuantity();
  }

  /**
   * Returns the a string of the inventory which includes ingredient name and amount
   *
   * @return string of inventory stock
   */
  @Override
  public String toString() {
    String output = "";
    for (InventoryIngredient ingredient : inventory.values()) {
      output =
          output
              + ingredient.getName()
              + "|"
              + String.valueOf(ingredient.getCurrentQuantity())
              + System.lineSeparator();
    }
    return output;
  }

  /**
   * Set the manager as a listener of this class
   *
   * @param manager manager of restaurant
   */
  public void setManager(Notifiable manager) {
    this.manager = manager;
  }
}
