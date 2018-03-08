import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * A Manager manages the overall restaurant and gets the ingredients if there
 * is a shortage.
 */
public class Manager implements IWorker, InventoryListener {
  private boolean isOccupied;
  private String name;
  private String requestIngredients = "request.txt";

  public Manager(String name) {
    this.name = name;
  }
    @Override
    public String getName() {
        return name;
    }
    /** Calls a server or a cook to collect the received ingredients. */
  public IWorker callWorker(Restaurant restaurant, String name) {
      return restaurant.MapOfWorkers().get(name);
  }

  @Override
  public void notifyLowStock(String message) {
    //to be replaced with gui notification
    System.out.println(message);
  }
  public boolean isOccupied() {
    return isOccupied;
  }
  /** The server receives and adds ingredients to the inventory. */
  public void scanStock(Inventory inventory, String ingredient, int amount) {
    inventory.addStock(ingredient, amount);
  }
  /** The Manager shuts the system and updates the inventory.txt file. */
  public void shutDown(Inventory inventory) {
    System.out.println("System Shutdown: Writing inventory to file");
    inventory.writeToInventory();
  }

  public void confirmReceived(String ingredient, int amount) {
    try (PrintWriter out =
        new PrintWriter(new BufferedWriter(new FileWriter(requestIngredients)))) {
      ArrayList<String> list = serializeRequests();
      String request = "";
      for (int i = 0; i < list.size()/4; i++) {
          String ingredientName = list.get(i*4);
          int quantity = Integer.parseInt(list.get(i*4+1));
          String received = list.get(i*4+2);
          String scanned = list.get(i*4+3);
          if(ingredientName.equals(ingredient) && quantity == amount && received.equals("notreceived")){
              request += ingredientName + "#" + quantity + "#received#" + scanned;
          }else{
              request += ingredientName + "#" + quantity + "#" + received + "#" + scanned;
          }
          out.println(request);
          request = "";
      }

    } catch (Exception e) {

    }
  }

  private ArrayList<String> serializeRequests() {
    ArrayList<String> list = new ArrayList<>();
    try (BufferedReader fileReader = new BufferedReader(new FileReader(requestIngredients))) {
      String line = fileReader.readLine();
      while (line != null) {
        String[] elements = line.split("#");
        list.add(elements[0]);
        list.add(elements[1]);
        list.add(elements[2]);
        list.add(elements[3]);
        line = fileReader.readLine();
      }
    } catch (java.io.IOException e) {
    }
    return list;
  }
}
