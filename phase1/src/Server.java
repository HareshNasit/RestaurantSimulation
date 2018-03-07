import java.util.ArrayList;

/**
 * Server class records orders taken from customers and relays them to the chef.
 */
public class Server implements IWorker, ServingTableListener {
  public String getName() {
    return name;
  }

  private String name; // name of a server
  private boolean isOccupied; // whether the server is currently serving a table or not


  /**
   * Creates a new server for this restaurant
   *
   * @param name The name of the server
   */
  public Server(String name) {
    this.name = name;
  }

  /**
   * The server takes the order from a table and adds it to the list of orders to be cooked
   *
   * @param table the table that places the order
   */
  public void addOrder(Table table, ArrayList<Dish> tableOrder, ServingTable servingTable) {
    table.setTableOrder(tableOrder);
    servingTable.addToBeCooked(tableOrder);
  }

  public void addOrder(Table table, Dish singleOrder, ServingTable servingTable) {
    table.addSingleOrder(singleOrder);
    servingTable.addToBeCooked(singleOrder);
  }

  /**
   * Generates a bill for the table
   *
   * @param table The table that asked for the bill
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
    //Cook.dishesToBeCooked.add(dish);
    return true; // need to find a way to remove this.
  }

  /** When new stock has been received, update the stock */
  @Override
  public void scanStock(Inventory inventory, String ingredient, int amount) {
    inventory.addStock(ingredient, amount);
  }

  /**
   * This method checks whether all the dishes the table ordered have been served or not.
   */
  public boolean isOrderComplete(Table table) {
    return (table.getNumberOfDishesServed() == table.numberOfDishesInOrder());
  }

  public boolean isOccupied() {
    return isOccupied;
  }

  public void setOccupied(boolean occupied) {
    isOccupied = occupied;
  }

  /**
   * Notify the server that the ServingTable has been changed. i.e MenuItem needs to be served
   */
  public void update(Dish dish) {
      String text = "Table ";
      text += dish.getTableName() + "dish: " + dish.getName() + " served.";
    System.out.println(text);
  }

  /**
   * The server calls the manager in case there is a problem with a customer
   */
  public void callManager(){

  }

  /**
   * The server serves a dish to the table that ordered it
   */
  public void serveDish(int index, ServingTable servingTable) {
    servingTable.getDishToBeServed(index);
  }
}
