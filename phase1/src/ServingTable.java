import java.util.ArrayList;
import java.util.Observable;
import java.util.PriorityQueue;

public class ServingTable {

  PriorityQueue<Dish> dishesToBeCooked;
  PriorityQueue<Dish> dishesToBeServed;
  ArrayList<ServingTableListener> servers;
  ArrayList<ServingTableListener> cooks;

  ServingTable(ArrayList<ServingTableListener> servers, ArrayList<ServingTableListener> cooks) {
    dishesToBeCooked = new PriorityQueue<>();
    dishesToBeServed = new PriorityQueue<>();
    this.servers = servers;
    this.cooks = cooks;
  }

  /**
   * Adds a table order to the list of dishes that needs to be cooked
   */
  public void addToBeCooked(ArrayList<Dish> order) {
    dishesToBeCooked.addAll(order);
  }


  /**
   * Add dish to be served
   * @param dish
   */
  public void addToBeServed(Dish dish) {
    dishesToBeServed.add(dish);
    notifyServers();
  }

  public void notifyServers() {
    for (ServingTableListener server : servers) {
      server.update();
    }
  }

  /**
   * Returns the next dish that needs to be returned by the chef
   * @return
   */
  public Dish getNextDishToBeCooked() {
    return dishesToBeCooked.poll();
  }


  /**
   * Remove Dish dish from list of dishes to be served. Doing so will mean that dish has been
   * delivered to the table
   * @param dish
   * @return the Dish that has been cooked, and needs to be served to the customer
   */
  public Dish getDishToBeServed(Dish dish) {
    return dishesToBeServed.poll();
  }

}
