import java.util.ArrayList;

public class ServingTable {

  private ArrayList<Dish> dishesToBeCooked;
  private ArrayList<Dish> dishesToBeServed;
  private ArrayList<ServingTableListener> servers;
  private ArrayList<ServingTableListener> cooks;

  public ArrayList<ServingTableListener> getServers() {
    return servers;
  }

  public void setServers(ArrayList<ServingTableListener> servers) {
    this.servers = servers;
  }

  public ArrayList<ServingTableListener> getCooks() {
    return cooks;
  }

  public void setCooks(ArrayList<ServingTableListener> cooks) {
    this.cooks = cooks;
  }

  ServingTable() {
    dishesToBeCooked = new ArrayList<Dish>();
    dishesToBeServed = new ArrayList<Dish>();
    servers = new ArrayList<ServingTableListener>();
    cooks = new ArrayList<ServingTableListener>();
  }

  /**
   * Adds a table order to the list of dishes that needs to be cooked
   */
  public void addToBeCooked(ArrayList<Dish> order) {
    dishesToBeCooked.addAll(order);
  }

  public void addToBeCooked(Dish order) {
    dishesToBeCooked.add(order);
  }

  /**
   * Returns dish to serving table. Sets it as top priority
   */
  public void returnDish(Dish dish, String comment) {
    dishesToBeCooked.add(0, dish);
    System.out.println("MenuItem Returned: " + comment);
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
   * Notify servers about current dish state
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
  public MenuItem getNextDishToBeCooked() {
    return dishesToBeCooked.remove(0);
  }


  /**
   * Remove the dish at the given index
   * @param index - index of the dish that needs to be served
   * @return
   */
  public Dish getDishToBeServed(int index) {

    Dish dish = dishesToBeServed.remove(index);
    //String of dish
    return dish;

  }

  public void addServers(ArrayList<ServingTableListener> servers) {
    this.servers = servers;
  }

  public void addServer(ServingTableListener server) {
    servers.add(server);
  }

  public void addCook(ServingTableListener cook) {
    cooks.add(cook);
  }

  public void addCooks(ArrayList<ServingTableListener> cooks) {
    this.cooks = cooks;
  }

  public ArrayList<Dish> getDishesToBeServed() {
    return dishesToBeServed;
  }

  public String toString(){
    String finalString = "Dishes to be cooked: ";
    for(Dish d : dishesToBeCooked){
      finalString += d.getId() + "|" + d.getTableName() + "|" + d.getCustomerNum() + " # ";
    }
    finalString += System.lineSeparator() + "Dishes to be Served: ";
    for(Dish e : dishesToBeServed){
      finalString += e.getId() + "|" + e.getTableName() + "|" + e.getCustomerNum() + " # ";
    }
    return finalString;
  }
}
