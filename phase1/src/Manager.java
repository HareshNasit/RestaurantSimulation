import java.io.BufferedReader;
import java.io.FileReader;
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
    private HashMap<InventoryIngredient,Integer> orders;

  public Manager(String name) {
        this.name = name;
        orders = new HashMap<>();
    }
    /**
     * Calls a server or a cook to collect the received ingredients.
     */
    public IWorker callWorker(ArrayList<IWorker> workers) {
      for (IWorker worker : workers) {
            if(!worker.isOccupied()){
                return worker;
            }
        }
        return this;
    }

  @Override
  public void notifyLowStock(String ingredient) {
    System.out.println();
    System.out
        .println(String.format("Manager has been notified that %s is low on stock", ingredient));
    System.out.println(String.format("request.txt has been updated"));
    System.out.println();

  }

  /**
     * Reads the file about the new ingredients to be purchased and default value is 20.
     */
    public void SendMail(){
    System.out.println("Mail sent to the distributor.");
        try (BufferedReader fileReader = new BufferedReader(new FileReader(requestIngredients))) {
            String line = fileReader.readLine();
            String[] splitLine = line.split("\\|");
            if (line != null) {
              //   orders.put(new InventoryIngredient())
            }
        }
        catch (Exception e) {
        }
    }
    public boolean isOccupied(){
        return isOccupied;
    }
    /**
     * The Manager can check the inventory.
     */
    public void checkInventory(Inventory inventory){}

    /**
     * The server receives and adds ingredients to the inventory.
     */
    public void scanStock(Inventory inventory, String ingredient, int amount) {
        inventory.addStock(ingredient,amount);
    }
    /**
     * The Manager shuts the system and updates the inventory.txt file.
     */
    public void shutDown(Inventory inventory){
        inventory.writeToInventory();
    }

}
