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
  private HashMap<InventoryIngredient, Integer> orders;

  public Manager(String name) {
    this.name = name;
    orders = new HashMap<>();
  }
  /** Calls a server or a cook to collect the received ingredients. */
  public IWorker callWorker(ArrayList<IWorker> workers) {
    for (IWorker worker : workers) {
      if (!worker.isOccupied()) {
        return worker;
      }
    }
    return this;
  }

  @Override
  public void notifyLowStock(String ingredient) {
    System.out.println();
    System.out.println(
        String.format("Manager has been notified that %s is low on stock", ingredient));
    System.out.println(String.format("request.txt has been updated"));
    System.out.println();
  }

  /** Reads the file about the new ingredients to be purchased and default value is 20. */
  public void SendMail() {
    System.out.println("Mail sent to the distributor.");
    try (BufferedReader fileReader = new BufferedReader(new FileReader(requestIngredients))) {
      String line = fileReader.readLine();
      String[] splitLine = line.split("\\|");
      if (line != null) {
        //   orders.put(new InventoryIngredient())
      }
    } catch (Exception e) {
    }
  }

  public boolean isOccupied() {
    return isOccupied;
  }
  /** The Manager can check the inventory. */
  public void checkInventory(Inventory inventory) {}

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
