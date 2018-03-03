import java.util.ArrayList;

/**
 * Server class records orders taken from customers and relays them to the chef.
 */
public class Server implements IWorker, ServingTableListener {

  public static ArrayList<Server> servers; // the list of servers in the restaurant
  private static int numberOfServers; // the number of servers in a restaurant
  private String name; // name of a server
  private boolean isOccupied; // whether the server is currently serving a table or not

  ServingTable servingTable;

  /**
   * Creates a new server for this restaurant
   *
   * @param name
   * @param servers
   * @param servingTable
   */
  public Server(String name, ArrayList<Server> servers, ServingTable servingTable) {
    this.name = name;
    servers.add(this);
    numberOfServers++;
    this.servingTable = servingTable;
  }

  /**
   * Server has accepted the cooked dish and delivered it to customer
   *
   * @param dish - Dish that has been cooked
   */
  public void removeDishToBeServed(Dish dish) {
    servingTable.getDishToBeServed(dish);
  }

  /**
   * The server takes the order from a table and adds it to the list of orders to be cooked
   *
   * @param table the table that places the order
   */
  public void addOrder(Table table, ArrayList<Dish> tableOrder) {
    table.setTableOrder(tableOrder);
    servingTable.addToBeCooked(tableOrder);
  }

  /**
   * Generates a bill for the table
   *
   * @param table
   */
  public void generateTableBill(Table table) {
    Bill.outputBill(table.getTableOrder());
  }

  /**
   * The server takes the dish from the table and returns it to the cook
   *
   * @param dish the dish that is to be returned
   */
  public boolean returnOrder(Dish dish) {
    Cook.dishesToBeCooked.add(dish);
    return true; // need to find a way to remove this.
  }

  @Override
  /** When new stock has been received, update the stock */
  public void scanStock(Inventory inventory, String ingredient, int amount) {
    inventory.addStock(ingredient, amount);
  }

  /**
   * This method checks whether all the dishes the table ordered have been served or not.
   */
  public boolean isOrderComplete(Table table) {
    if (table.getNumberOfDishesServed() == table.numberOfDishesInOrder()) {
      return true;
    }
    // else if(){} have to check if the dish has been returned which then makes the order still
    // incomplete
    else {
      return false;
    }
  }

  public boolean isOccupied() {
    return isOccupied;
  }

  public void setOccupied(boolean occupied) {
    isOccupied = occupied;
  }

  /**
   * Notify the server that the ServingTable has been changed. i.e Dish needs to be served
   */
  public void update(Dish dish) {
      String text = "Table ";
      text += dish.getTableName() + "dish: " + dish.getName() + " served.";
    System.out.println(text);
  }

  // ------------------------------To Be Considered------------------------------------------------

  ////    protected ArrayList<Table> tables; // the list of tables in the restaurant
  ////    protected ArrayList<Dish> dishesInOrder; // the dishes of a tables order
  ////  private static ArrayList<Dish> dishesToBeServed = new ArrayList<Dish>();
  //    protected Cook cook;

  /**
   * The server serves the order to the table and removes it from the list of orders to be served
   *
   * @param table the table whose order is being served
   */
  //    public void serveDishes(Table table) {
  //        table.setCookedOrder(dishesToBeServed);
  //    }

  //  public static void addDishToBeServed(Dish dish) {
  //    dishesToBeServed.add(dish);
  //  }

}
