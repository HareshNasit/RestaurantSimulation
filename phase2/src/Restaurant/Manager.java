package Restaurant;

import java.io.*;

/*
 * A Manager manages the overall restaurant and gets the ingredients if there
 * is a shortage.
 */
public class Manager implements IWorker, InventoryListener {

  private String name;
  private final String RECEIVEDFILE = "receivedShipments.txt";

  public Manager(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getType() {
    return "Manager";
  }

  /** Calls a server or a cook to collect the received ingredients. */
  public IWorker callWorker(Restaurant restaurant, String name) {
    return restaurant.MapOfWorkers().get(name);
  }

  @Override
  public void notifyLowStock(String message) {
    // to be replaced with gui notification
    System.out.println(message);
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

  /**
   * The Manager inputs how much of an ingredient the Restaurant has received. This gets printed to
   * a new txt. The manager will compare between the request txt and receivedShipments txt to see
   * which shipments have been received. This is useful if a shipment has not come in, so the
   * manager can send an email to the supplier.
   *
   * @param ingredient ingredient that has been received
   * @param amount amount of ingredient that is received
   */
  public void confirmReceived(String ingredient, int amount) {
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(RECEIVEDFILE)))) {

      String shipmentMessage = ingredient + ": " + amount;
      out.println(shipmentMessage);

    } catch (Exception e) {

    }
  }

}
