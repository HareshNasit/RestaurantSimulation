import java.util.ArrayList;
import java.util.Observable;

/**
 * The table is where the customers sit and order food and also where their cooked food arrives.
 */

public class Table extends Observable {
    private String tableNumber; // the id number of the table
    private int tableSize; // the number of people who can sit on this table
    private boolean isOccupied; // if the table already has customers
    private ArrayList<Dish> tableOrder; // the dishes the table ordered
    private boolean orderCompleted; // if the table's order has been completed or not
    private ArrayList<Dish> cookedOrder; // the table's cooked dishes that have been served
    protected Server server;
    private int numberOfDishesServed; // the number of dishes served to the table

    public boolean isOrderCompleted() {
        return orderCompleted;
    }

    public void setOrderCompleted(boolean orderCompleted) {
        this.orderCompleted = orderCompleted;
    }

    public Table(String tableNumber, int tableSize, boolean isOccupied){
        this.tableNumber = tableNumber;
        this.tableSize = tableSize;
        this.isOccupied = isOccupied;
    }

    public ArrayList<Dish> getTableOrder() {
        return tableOrder;
    }

    /**
     * The cooked dishes are served to the specified table
     * @param table the table that ordered the dish
     */
    public void serveOrder(Table table){
        server.getCookedDishes(table);
        numberOfDishesServed++;
    }

    /**
     * Returns the number of dishes that were ordered by a table.
     * @return returns the number of dishes in the order
     */
    public int numberOfDishesInOrder(){
        int numberOfDishes = 0;
        for(int i = 0; i < tableOrder.size(); i++){
            numberOfDishes++;
        }
        return numberOfDishes;
    }

    public int getNumberOfDishesServed() {
        return numberOfDishesServed;
    }


}
