import java.util.ArrayList;

/**
 * Server class records orders taken from customers and relays them to the chef.
 */
public class Server implements IWorker, ServingTableListener {

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

  public String getName() {
    return name;
  }

  /**
   * The server takes the order from a table and adds it to the list of orders to be cooked
   *
   * @param table the table that places the order
   */
  public void passOrder(Table table, ServingTable servingTable) {
    System.out.println(
        String.format("%s sending Table %s's orders to cooks", getName(), table.getName()));
    servingTable.addToBeCooked(table.getTableOrder());
    System.out.println(servingTable);
  }

  public void serveDish(int index, Restaurant restaurant) {
    Dish dish = restaurant.getServingTable().serveDish(index);
    restaurant.getTable(dish.getTableName()).dishesServed();
    System.out.println(String.format("%s serving Table%s%d order: %s",
        getName(), dish.getTableName(), dish.getCustomerNum(), dish.getName()
    ));
    System.out.println(restaurant.getServingTable());
  }

  public void rejectDish(int index, Restaurant restaurant) {
    Dish dish = restaurant.getServingTable().getRejectedDish(index);
    restaurant.getTable(dish.getTableName()).removeDish(dish);
    System.out.println(String.format("%s informs Table%s%d that %s has been rejected by kitchen",
        getName(), dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    System.out.println(restaurant.getServingTable());
  }

  public void returnDish(int index, String tableID, Restaurant restaurant, String comment) {
    Dish dish = restaurant.getTable(tableID).getDish(index);
    dish.addComment(comment);
    System.out.println(String.format("Table%s%d %s returned for %s", dish.getTableName(),
        dish.getCustomerNum(), dish.getName(), dish.getComment()));
    restaurant.getServingTable().addToBeCooked(dish);
    System.out.println(restaurant.getServingTable());
  }

  /**
   * Adds a dish to a table's Order
   */
  public void addOrder(Table table, Dish dish, ServingTable servingTable) {
    System.out.println(String.format(
        "%s takes order from Table%sSeat%d: %s",
        getName(), dish.getTableName(), dish.getCustomerNum(), dish.getStringForBill()));
    table.addSingleOrder(dish);

  }

  public void seatCustomer(String tableID, Restaurant restaurant) {
    restaurant.getTable(tableID).setOccupied(true);
    System.out.println(String.format("%s seats Table %s", getName(), tableID));
  }

  /**
   * Generates a bill one table
   *
   * @param table The table that asked for the bill
   */
  public void generateTableBill(Table table) {
    Bill.outputBill(table.getTableOrder());
  }

  /**
   * Generates a bill for the table customer
   *
   * @param table The table that asked for the bill
   */
  public void generateSingleBill(Table table, int seatNum) {
    Bill.outputSingleBill(table, seatNum);
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

  @Override
  public void update(String message) {
    //Notify server on GUI
  }
}
