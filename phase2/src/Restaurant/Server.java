package Restaurant;

/** Server class records orders taken from customers and relays them to the chef. */
public class Server implements IWorker, ServingTableListener {

  private String name; // name of a server
  private ModelControllerInterface screen;

  /**
   * Creates a new server for this restaurant
   *
   * @param name The name of the server
   */
  public Server(String name) {
    this.name = name;
  }

  /**
   * return name of the server.
   *
   * @return String
   */
  public String getName() {
    return name;
  }

  @Override
  public String getType() {
    return "Server";
  }

  /**
   * The server takes the order from a table and adds it to the list of orders to be cooked.
   *
   * @param table the table that places the order
   */
  public void passOrder(Table table, ServingTable servingTable) {
    System.out.println(
        String.format("%s sending Table %s's orders to cooks", getName(), table.getTableID()));
    servingTable.addToBeCooked(table.getTableOrder());
    System.out.println(servingTable);
  }

  /**
   * Serves a dish to the customer.
   *
   * @param index index of the dish.
   * @param restaurant the restaurant.
   */
  public void serveDish(int index, Restaurant restaurant) {
    Dish dish = restaurant.getServingTable().serveDish(index);
    System.out.println(
        String.format(
            "%s serving Table%s%d order: %s",
            getName(), dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    System.out.println(restaurant.getServingTable());
  }

  /**
   * The server informs the customer that a particular dish has been rejected.
   *
   * @param index index of the dish.
   * @param restaurant the restaurant.
   */
  public void rejectDish(int index, Restaurant restaurant) {
    Dish dish = restaurant.getServingTable().getRejectedDish(index);
    restaurant.getTable(dish.getTableName()).removeDish(dish);
    System.out.println(
        String.format(
            "%s informs Table%s%d that %s has been rejected by kitchen",
            getName(), dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    System.out.println(restaurant.getServingTable());
  }

  /**
   * The customer returns a dish for a particular reason.
   *
   * @param restaurant the restaurant.
   * @param index the index of the dish.
   * @param comment the reason the customer returned the dish.
   * @param tableID the table id.
   */
  public void returnDish(int index, String tableID, Restaurant restaurant, String comment) {
    Dish dish = restaurant.getTable(tableID).getDish(index);
    dish.addComment(comment);
    System.out.println(
        String.format(
            "Table%s%d %s returned for %s",
            dish.getTableName(), dish.getCustomerNum(), dish.getName(), dish.getComment()));
    restaurant.getServingTable().addToBeCooked(dish);
    System.out.println(restaurant.getServingTable());
  }

  /**
   * Adds a dish to a table's Order.
   *
   * @param table table to which dish will be added.
   * @param dish dish to be added to the tables order.
   */
  public void addOrder(Table table, int customerNumber, MenuItem dish) {
    Dish dish1 = new Dish(dish, table.getTableID(), customerNumber);
    dish1.setComment("Cook");
    table.addSingleOrder(dish1);
  }

  /**
   * sets a table to be occupied.
   *
   * @param tableID id of the table.
   * @param restaurant restaurant.
   */
  public void seatCustomer(String tableID, Restaurant restaurant) {
    restaurant.getTable(tableID).setOccupied(5);
    System.out.println(String.format("%s seats Table %s", getName(), tableID));
  }

  /**
   * Generates a bill one table.
   *
   * @param table The table that asked for the bill
   */
  public void generateTableBill(Table table) {
    Bill.outputBill(table);
  }

  /**
   * Generates a bill for the table customer.
   *
   * @param table The table that asked for the bill
   */
  public void generateSingleBill(Table table, int seatNum) {
    Bill.outputSingleBill(table, seatNum);
  }

  public void clearTable(Table table) {
    System.out.println(String.format("Table %s is now free", table.getTableID()));
    table.clearTable();
  }

  /** When new stock has been received, update the stock. */
  @Override
  public void scanStock(Inventory inventory, String ingredient, int amount) {
    inventory.addStock(ingredient, amount);
  }

  @Override
  public void update(String message) {
    // Notify server on GUI
  }

  /**
   * Removes a dish from the given table.
   *
   * @param index of the dish in the list.
   * @param table the given table from which dish is removed.
   */
  public void removeDish(int index, Table table) {
    Dish dish = table.removeDish(index);
    System.out.println(
        String.format(
            "Cancelled %s from Table%s%d",
            dish.getName(), dish.getTableName(), dish.getCustomerNum()));
  }

  public ModelControllerInterface getScreen() {
    return screen;
  }

  public void setScreen(ModelControllerInterface screen) {
    this.screen = screen;
  }
}
