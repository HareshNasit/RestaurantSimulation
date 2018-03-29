package Restaurant;

import MenuDishes.Dish;
import java.util.ArrayList;

/**
 * A Serving Table is used between all chefs and servers. It depicts the state of the dish
 */
public class ServingTable {

  private ArrayList<Dish> dishesRejected; // List of dishes being rejected
  private ArrayList<Dish> dishesToBeCooked; // List of dishes to be cooked.
  private ArrayList<Dish> dishesToBeServed; // List of dishes cooked and ready to be served.
  private ArrayList<Dish> dishesBeingCooked; // List of dishes which are being cooked
  private ArrayList<Notifiable> servers; // List of all the servers.
  private ArrayList<Notifiable> cooks; // List of all the cooks.


  /**
   * Creates a new serving table.
   */
  public ServingTable() {
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
    notifyCooks("New Orders have been placed on the serving table");
  }

  /**
   * Adds a table order to the list of dishes that needs to be cooked.
   *
   * @param order A dish being ordered.
   */
  public void addToBeCooked(Dish order) {
    dishesToBeCooked.add(order);
    String message = String.format("Table%s%d's %s has been placed on serving table ",
        order.getTableName(), order.getCustomerNum(), order.getName());
    notifyWorkers(cooks,message);
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

  public boolean checkPriority(Dish dish){
    return (dishesToBeCooked.indexOf(dish) != 0);

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
   * @param dish to be rejected
   */
  public void rejectDish(Dish dish) {
    // Dish dish = dishesToBeCooked.remove(index);
    dishesToBeCooked.remove(dish);
    dishesRejected.add(dish);
    String message = String.format("Table%s%d's %s has been rejected",
        dish.getTableName(), dish.getCustomerNum(), dish.getName());
    notifyWorkers(servers,message);
  }

  /**
   * Dish at index has been cooked. Dish is moved to list of dishes that need to be served
   *
   * @param dish which is ready to be served
   */
  public void addToBeServed(Dish dish) {
    // Dish dish = dishesBeingCooked.remove(index);
    dishesBeingCooked.remove(dish);
    dishesToBeServed.add(dish);
    String message = String.format("Table%s%d's %s is ready to be served",
        dish.getTableName(), dish.getCustomerNum(), dish.getName());
    notifyWorkers(servers,message);
    System.out.println(this);
  }

  /**
   * Dish at index is moved from needing to be cooked to being cooked.
   */
  public void addToBeCooking(Dish dish) {
    // Dish dish = dishesToBeCooked.remove(index);
    dishesToBeCooked.remove(dish);
    dishesBeingCooked.add(dish);
    String message = String.format("Table%s%d's %s is now cooking",
        dish.getTableName(), dish.getCustomerNum(), dish.getName());
    dish.setDishStatus(DishStatus.COOKING);
    notifyWorkers(servers, message);
  }

  public boolean hasDishesToServe(){
    return !dishesToBeServed.isEmpty();
  }

  /**
   * Removes and returns dish at index on dishesToBeServed. Cooks are notified that the dish has
   * been served.
   *
   * @return dish at index of dishesToBeServed
   */
  public Dish serveDish(Dish dish) {
    getDishesToBeServed().remove(dish);
    String message = String.format(String.format(
        "Table%s%d's %s has been served",
        dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    notifyWorkers(cooks,message);
    updateWorkers(servers);
    return dish;
  }

  /**
   * Adds the server as a ServingTableListener for this serving table.
   *
   * @param server the server.
   */
  public void addServer(Notifiable server) {
    servers.add(server);
  }

  /**
   * Adds the cook.
   *
   * @param cook cook
   */
  public void addCook(Notifiable cook) {
    cooks.add(cook);
  }

  /**
   * Notifies all servers with message.
   *
   * @param message Message that needs to be passed to servers
   */
  private void notifyServers(String message) {
    System.out.println(message);
    for (Notifiable server : servers) {
      server.sendNotifications(message);
    }
  }

  private void notifyWorkers(ArrayList<Notifiable> type, String message){
    for (Notifiable worker : type) {
      worker.sendNotifications(message);
    }
  }

  private void updateWorkers(ArrayList<Notifiable> type){
    for (Notifiable worker : type) {
      worker.update();
    }
  }

  /**
   * Notifies all cooks with message.
   *
   * @param message Message that needs to be passed
   */
  private void notifyCooks(String message) {
    System.out.println(message);
    for (Notifiable cook : cooks) {
      cook.sendNotifications(message);
    }
  }

  /**
   * prints the ServingTable information.
   */
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

  public ArrayList<Dish> getUndeliveredDishes(){
    ArrayList<Dish> dishes = new ArrayList<Dish>();
    dishes.addAll(dishesBeingCooked);
    dishes.addAll(dishesToBeCooked);
    dishes.addAll(dishesToBeServed);
    return dishes;
  }

  /**
   * Getter for Dishes to be cooked.
   *
   * @return ArrayList<Dish>
   */
  public ArrayList<Dish> getDishesToBeCooked() {
    return dishesToBeCooked;
  }

  public ArrayList<Dish> getDishesToBeServed() {
    return dishesToBeServed;
  }

  public ArrayList<Dish> getDishesBeingCooked() {

    return dishesBeingCooked;
  }
}
