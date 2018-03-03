import java.util.ArrayList;

/**
 * Server class records orders taken from customers and relays them to the chef.
 */

public class Server implements IWorker {

    public static ArrayList<Server> servers; // the list of servers in the restaurant
    private static int numberOfServers; // the number of servers in a restaurant
    private String name; // name of a server
    private boolean isOccupied; // whether the server is currently serving a table or not
    protected ArrayList<Table> tables; // the list of tables in the restaurant
    protected ArrayList<Dish> dishesInOrder; // the dishes of a tables order
  private static ArrayList<Dish> dishesToBeServed = new ArrayList<Dish>();
    protected Cook cook;

    public Server(String name, ArrayList<Server> servers) {
        this.name = name;
        servers.add(this);
        numberOfServers++;
    }

  public static void addDishToBeServed(Dish dish) {
    dishesToBeServed.add(dish);
  }

  public static void removeDishToBeServed(Dish dish) {
    dishesToBeServed.remove(dish);
  }

  /**
     * The server takes the order from a table and adds it to the list of orders to be cooked
     * @param table the table that places the order
     */
    public void addOrder(Table table, ArrayList<Dish> tableOrder) {
      table.setTableOrder(tableOrder);
      Cook.dishesToBeCooked.addAll(tableOrder);
    }

  public void generateTableBill(Table table) {
    Bill.outputBill(table.getTableOrder());
  }

    /**
     * The server serves the order to the table and removes it from the list of orders to be served
     * @param table the table whose order is being served
     */
    public void serveDishes(Table table) {
        table.setCookedOrder(dishesToBeServed);
    }

    /**
     * The server takes the dish from the table and returns it to the cook
     * @param dish the dish that is to be returned
     */
    public boolean returnOrder(Dish dish){
        Cook.dishesToBeCooked.add(dish);
        return true; // need to find a way to remove this.
    }

  @Override
  public void scanStock(Inventory inventory, String ingredient, int amount) {
    inventory.addStock(ingredient, amount);
  }

  /**
     * This method checks whether all the dishes the table ordered have been served or not.
     * @return
     */
    public boolean isOrderComplete(Table table) {
        if (table.getNumberOfDishesServed() == table.numberOfDishesInOrder()){
            return true;
        }
        // else if(){} have to check if the dish has been returned which then makes the order still incomplete
        else{
            return false;
        }
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
