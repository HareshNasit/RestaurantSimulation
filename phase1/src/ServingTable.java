import java.util.ArrayList;

public class ServingTable {
  private ArrayList<Dish> dishesRejected; // List of dishes being rejected due to low ingredients.
  private ArrayList<Dish> dishesToBeCooked; // List of dishes to be cooked.
  private ArrayList<Dish> dishesToBeServed; // List of dishes cooked and ready to be served.
  private ArrayList<Dish> dishesBeingCooked; // List of dishes which are being cooked currently for extra feature later.
  private ArrayList<ServingTableListener> servers; // List of all the servers.
  private ArrayList<ServingTableListener> cooks; // List of all the cooks.
     ServingTable() {
        dishesBeingCooked = new ArrayList<>();
        dishesRejected = new ArrayList<>();
        dishesToBeCooked = new ArrayList<>();
        dishesToBeServed = new ArrayList<>();
        servers = new ArrayList<>();
        cooks = new ArrayList<>();
    }
    /**
     * Returns an ArrayList of servers.
     * @return ArrayList<ServingTableListener>
     */
    public ArrayList<ServingTableListener> getServers() {
    return servers;
  }
    /**
     * Sets the servers.
     * @param servers An ArrayList of all the servers.
     */
     public void setServers(ArrayList<ServingTableListener> servers) {
    this.servers = servers;
  }
    /**
     * Returns an ArrayList of cooks.
     * @return ArrayList<ServingTableListener>
     */
     public ArrayList<ServingTableListener> getCooks() {
    return cooks;
  }
    /**
     * Sets the cooks.
     * @param cooks An ArrayList of all the cooks.
     */
    public void setCooks(ArrayList<ServingTableListener> cooks) {
    this.cooks = cooks;
  }
    /**
     * Returns an ArrayList of dishes which are rejected.
     * @return ArrayList
     */
    public ArrayList<Dish> getDishesRejected() {
        return dishesRejected;
    }
    /**
     * Returns an ArrayList of dishes which are to be cooked.
     * @return ArrayList
     */
    public ArrayList<Dish> getDishesToBeCooked() {
        return dishesToBeCooked;
    }
    /**
     * Returns an ArrayList of dishes which are being cooked currently.
     * @return ArrayList
     */
    public ArrayList<Dish> getDishesBeingCooked() {
        return dishesBeingCooked;
    }
    /**
     * Returns an ArrayList of dishes to be served.
     * @return ArrayList
     */
    public ArrayList<Dish> getDishesToBeServed() {
        return dishesToBeServed;
    }
    /**
   * Adds a table order to the list of dishes that needs to be cooked.
   * @param order An ArrayList of dishes being ordered.
   */
  public void addToBeCooked(ArrayList<Dish> order) {
    dishesToBeCooked.addAll(order);
  }
    /**
     * Adds a table order to the list of dishes that needs to be cooked.
     * @param order A dish being ordered.
     */
  public void addToBeCooked(Dish order) {
    dishesToBeCooked.add(order);
  }
  /**
   * Returns dish to serving table. Sets it as top priority
   * This is when the customer sends back the dish due to some problem.
   * @param dish the dish being sent back.
   * @param comment the reason why the customer sent back the dish.
   */
  public void returnDish(Dish dish, String comment) {
    dishesToBeCooked.add(0, dish);
    System.out.println("MenuItem Returned: " + comment);
  }
  /**
   * Add dish to be served.
   * @param dish the dish which is cooked and ready to be served.
   */
  public void addToBeServed(Dish dish) {
    dishesToBeServed.add(dish);
    notifyServers(dish);
  }
  /**
   * Notify servers about current dish state
   * @param dish the dish.
   */
  public void notifyServers(Dish dish) {
    for (ServingTableListener server : servers) {
      server.update(dish);
    }
  }

  public void notifyCooks(Dish dish){
    for (ServingTableListener cook : cooks) {
      cook.update(dish);
    }
  }


  /**
   * Remove the dish at the given index
   * @param index - index of the dish that needs to be served
   * @return dish to be served.
   */
  public Dish getDishToBeServed(int index) {

    Dish dish = dishesToBeServed.remove(index);
    return dish;
  }
    /**
     * Sets the servers.
     * @param servers ArrayList<ServingTableListener>
     */
  public void addServers(ArrayList<ServingTableListener> servers) {
    this.servers = servers;
  }
    /**
     * Adds the server.
     * @param server the server.
     */
  public void addServer(ServingTableListener server) {
    servers.add(server);
  }
    /**
     * Adds the cook.
     * @param cook ArrayList<ServingTableListener>
     */
  public void addCook(ServingTableListener cook) {
    cooks.add(cook);
  }
    /**
     * Sets the cooks.
     * @param cooks ArrayList<ServingTableListener>
     */
  public void addCooks(ArrayList<ServingTableListener> cooks) {
    this.cooks = cooks;
  }
    /**
     * prints the ServingTable information.
     */
    @Override
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

  public Dish getDishToBeCooked(int index) {
    return dishesToBeCooked.get(index);
  }

  public void rejectDish(int index) {
    Dish dish = dishesToBeCooked.remove(index);
    System.out.println(String.format("%s has been rejected", dish.getName()));
    dishesRejected.add(dish);

  }

  public void setDishToServe(int index) {
    Dish dish = dishesBeingCooked.remove(index);
    System.out.println(String.format("%s is ready to be served", dish.getName()));
    dishesToBeServed.add(dish);
  }

  /**
   * Removes the dish from index in dishestobecooked and added it to dishes being cooked
   */
  public Dish setDishToCooking(int index) {
    Dish dish = dishesToBeCooked.remove(index);
    dishesBeingCooked.add(dish);
    System.out.println(String.format("%s is now being cooked", dish.getName()));
    return dish;
  }

  public void serveDish(int index) {
    Dish dish = dishesToBeServed.remove(index);
    System.out.println(String.format("%s has been served", dish.getName()));

  }
}
