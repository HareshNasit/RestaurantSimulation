package Restaurant;

import java.util.ArrayList;

/** A Serving Table is used between all chefs and servers. It depicts the state of the dish */
public class ServingTable {

  private ArrayList<Dish> dishesRejected; // List of dishes being rejected
  private ArrayList<Dish> dishesToBeCooked; // List of dishes to be cooked.
  private ArrayList<Dish> dishesToBeServed; // List of dishes cooked and ready to be served.
  private ArrayList<Dish> dishesBeingCooked; // List of dishes which are being cooked
  private ArrayList<ServingTableListener> servers; // List of all the servers.
  private ArrayList<ServingTableListener> cooks; // List of all the cooks.

  /** Creates a new serving table. */
  ServingTable() {
    dishesBeingCooked = new ArrayList<>();
    dishesRejected = new ArrayList<>();
    dishesToBeCooked = new ArrayList<>();
    dishesToBeServed = new ArrayList<>();
    servers = new ArrayList<>();
    cooks = new ArrayList<>();
  }

  /**
   * Adds a table order to the list of dishes that needs to be cooked.
   *
   * @param order An ArrayList of dishes being ordered.
   */
  public void addToBeCooked(ArrayList<Dish> order) {
    dishesToBeCooked.addAll(order);
  }

  /**
   * Adds a table order to the list of dishes that needs to be cooked.
   *
   * @param order A dish being ordered.
   */
  public void addToBeCooked(Dish order) {
    dishesToBeCooked.add(order);
  }

  /**
   * Removes and return the dish at index of rejected dishes list.
   *
   * @param index index of dish
   * @return dish
   */
  public Dish getRejectedDish(int index) {
    Dish dish = dishesRejected.remove(index);
    return dish;
  }

  /**
   * Get the dish that needs to be cooked at index from the list of dishes that need to be cooked.
   */
  public Dish getDishToBeCooked(int index) {
    return dishesToBeCooked.get(index);
  }

  /**
   * Reject a dish at index of dishes that need to be cooked It is added to the list of rejected.
   * dishes to be informed to the servers
   *
   * @param index - index of dish
   */
  public void rejectDish(int index) {
    Dish dish = dishesToBeCooked.remove(index);
    dishesRejected.add(dish);
  }

  /**
   * Dish at index has been cooked. Dish is moved to list of dishes that need to be served
   *
   * @param index - index of dish on dishesBeingCooked list
   */
  public void addToBeServed(int index) {
    Dish dish = dishesBeingCooked.remove(index);
    dishesToBeServed.add(dish);
    notifyServers(
        String.format(
            "Table %s%d %s is ready to be served",
            dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    System.out.println(this);
  }

  /** Dish at index is moved from needing to be cooked to being cooked. */
  public void addToBeCooking(int index) {
    Dish dish = dishesToBeCooked.remove(index);
    dishesBeingCooked.add(dish);
  }

  /**
   * Removes and returns dish at index on dishesToBeServed. Cooks are notified that the dish has
   * been served.
   *
   * @param index index of dish on dishesToBeServed
   * @return dish at index of dishesToBeServed
   */
  public Dish serveDish(int index) {
    Dish dish = dishesToBeServed.remove(index);
    notifyCooks(
        String.format(
            "Table %s%d %s has been served",
            dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    return dish;
  }

  /**
   * Adds the server as a ServingTableListener for this serving table.
   *
   * @param server the server.
   */
  public void addServer(ServingTableListener server) {
    servers.add(server);
  }

  /**
   * Adds the cook.
   *
   * @param cook cook
   */
  public void addCook(ServingTableListener cook) {
    cooks.add(cook);
  }

  /**
   * Notifies all servers with message.
   *
   * @param message Message that needs to be passed to servers
   */
  private void notifyServers(String message) {
    System.out.println("Servers notified that " + message);
    for (ServingTableListener server : servers) {
      server.update(message);
    }
  }

  /**
   * Notifies all cooks with message.
   *
   * @param message Message that needs to be passed
   */
  private void notifyCooks(String message) {
    System.out.println("Cooks notified that " + message);
    for (ServingTableListener cook : cooks) {
      cook.update(message);
    }
  }

  /** prints the ServingTable information. */
  @Override
  public String toString() {
    String finalString =
        System.lineSeparator()
            + "ServingTable: "
            + System.lineSeparator()
            + "Dishes to be cooked: ";
    for (Dish d : dishesToBeCooked) {
      finalString += d.getTableName() + d.getCustomerNum() + "|" + d.getId() + " # ";
    }
    finalString += System.lineSeparator() + "Dishes currently cooking: ";
    for (Dish e : dishesBeingCooked) {
      finalString += e.getTableName() + e.getCustomerNum() + "|" + e.getId() + " # ";
    }
    finalString += System.lineSeparator() + "Dishes to be Served: ";
    for (Dish e : dishesToBeServed) {
      finalString += e.getTableName() + e.getCustomerNum() + "|" + e.getId() + " # ";
    }
    finalString += System.lineSeparator() + "Dishes rejected: ";
    for (Dish e : dishesRejected) {
      finalString += e.getTableName() + e.getCustomerNum() + "|" + e.getId() + " # ";
    }
    return finalString + System.lineSeparator();
  }
}
