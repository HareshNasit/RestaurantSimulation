import java.util.ArrayList;
import java.util.Observable;

/**
 * The table is where the customers sit and order food and also where their cooked food arrives.
 */
public class Table {

  private String tableName; // the id number of the table
  private int tableSize; // the number of people who can sit on this table
  private boolean isOccupied; // if the table already has customers
  private ArrayList<Dish> tableOrder; // the dishes the table ordered
  private boolean orderCompleted; // if the table's order has been completed or not
  private ArrayList<Dish> cookedOrder; // the table's cooked dishes that have been served
  private int numberOfDishesServed; // the number of dishes served to the table

  public Table(String tableName, int tableSize, boolean isOccupied) {
    this.tableName = tableName;
    this.tableSize = tableSize;
    this.isOccupied = isOccupied;
  }

  public void setTableOrder(ArrayList<Dish> tableOrder) {
    this.tableOrder = tableOrder;
  }

  public boolean isOrderCompleted() {
    return orderCompleted;
  }

  public void setOrderCompleted(boolean orderCompleted) {
    this.orderCompleted = orderCompleted;
  }

  public ArrayList<Dish> getCookedOrder() {
    return cookedOrder;
  }

  public void setCookedOrder(ArrayList<Dish> cookedOrder) {
    this.cookedOrder = cookedOrder;
  }

  public ArrayList<Dish> getTableOrder() {
    return tableOrder;
  }

  /** The cooked dishes are served to the specified table */
  public void dishesServed() {
    numberOfDishesServed++;
  }

  public void removeServedDish(Dish dish) {
    numberOfDishesServed--;
  }

  public void setNumberOfDishesServed(int numberOfDishesServed) {
    this.numberOfDishesServed = numberOfDishesServed;
  }

  /**
   * Returns the number of dishes that were ordered by a table.
   *
   * @return returns the number of dishes in the order
   */
  public int numberOfDishesInOrder() {
    int numberOfDishes = 0;
    for (int i = 0; i < tableOrder.size(); i++) {
      numberOfDishes++;
    }
    return numberOfDishes;
  }

  public int getNumberOfDishesServed() {
    return numberOfDishesServed;
  }

  public String getTableName() {
    return tableName;
  }

  /**
   * Returns the dishes order by a specifc customerNumber on this table
   */
  public ArrayList<Dish> getCustomerOrder(int customerNumber) {
    ArrayList<Dish> customerDishes = new ArrayList<Dish>();
    for (Dish dish : tableOrder) {
      if (dish.getCustomerNum() == customerNumber) {
        customerDishes.add(dish);
      }
    }
    return customerDishes;
  }
}
