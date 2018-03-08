import java.util.ArrayList;

/** The table is where the customers sit and order food and also where their cooked food arrives. */
public class Table {

  private String tableName; // the id number of the table
  private boolean isOccupied; // if the table already has customers
  private ArrayList<Dish> tableOrder; // the dishes the table ordered

  public Table(String tableName) {
    this.tableName = tableName;
    this.isOccupied = false;
    this.tableOrder = new ArrayList<Dish>();
  }

  public boolean isOccupied() {
    return isOccupied;
  }

  public void setOccupied(boolean occupied) {
    isOccupied = occupied;
  }

  public void setTableOrder(ArrayList<Dish> tableOrder) {
    this.tableOrder = tableOrder;
  }

  public void addSingleOrder(Dish dish) {
    this.tableOrder.add(dish);
  }


  public ArrayList<Dish> getTableOrder() {
    return tableOrder;
  }

  /**
   * Removes the served dish and reduces the number of dishes served by 1
   * @param dish to be removed from the list.
   */
  public void removeDish(Dish dish) {
    tableOrder.remove(dish);
  }

  /**
   * Removes the served dish and reduces the number of dishes served by 1
   *
   * @param index to be removed from the list.
   */
  public Dish removeDish(int index) {
    return tableOrder.remove(index);
  }
  
  public String getTableName() {
    return tableName;
  }

  /** Returns the dishes ordered by a specifc customerNumber on this table */
  public ArrayList<Dish> getCustomerOrder(int customerNumber) {
    ArrayList<Dish> customerDishes = new ArrayList<>();
    for (Dish dish : tableOrder) {
      if (dish.getCustomerNum() == customerNumber) {
        customerDishes.add(dish);
      }
    }
    return customerDishes;
  }

  public Dish getDish(int index) {
    return getTableOrder().get(index);
  }

  public String getName() {
    return this.tableName;
  }

  public void clearTable(){
    this.tableOrder = new ArrayList<Dish>();
    this.isOccupied = false;
  }

}
