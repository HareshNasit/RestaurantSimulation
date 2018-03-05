import java.util.ArrayList;
import java.util.Observable;
import java.util.PriorityQueue;

public class ServingTable {

  private ArrayList<Dish> dishesToBeCooked;
  private ArrayList<Dish> dishesToBeServed;
  private ArrayList<ServingTableListener> servers;
  private ArrayList<ServingTableListener> cooks;

  ServingTable() {
    dishesToBeCooked = new ArrayList<Dish>();
    dishesToBeServed = new ArrayList<Dish>();
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
   * Returns dish to serving table. Sets it as top priority
   */
  public void returnDish(Dish dish, String comment) {
    dishesToBeCooked.add(0, dish);
    System.out.println("Dish Returned: " + comment);
  }

  /**
   * Add dish to be served
   * @param dish
   */
  public void addToBeServed(Dish dish) {
    dishesToBeServed.add(dish);
    notifyServers(dish);
  }

  /**
   * Notfiy servers about current dish state
   */
  public void notifyServers(Dish dish) {
    for (ServingTableListener server : servers) {
      server.update(dish);
    }
  }

  /**
   * Returns the next dish that needs to be returned by the chef
   * @return
   */
  public Dish getNextDishToBeCooked() {
    return dishesToBeCooked.remove(0);
  }


  /**
   * Remove Dish dish from list of dishes to be served. Doing so will mean that dish has been
   * delivered to the table
   * @param dish
   * @return the Dish that has been cooked, and needs to be served to the customer
   */
  public Dish getDishToBeServed(Dish dish) {
    return dishesToBeServed.remove(0);
  }

  public void addServers(ArrayList<ServingTableListener> servers) {
    this.servers = servers;
  }

  public void addCooks(ArrayList<ServingTableListener> cooks) {
    this.cooks = cooks;
  }

}
