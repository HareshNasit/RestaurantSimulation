import java.util.ArrayList;

/**
 * The table is where the customers sit and order food and also where their cooked food arrives.
 */

public class Table {
    private String tableNumber; // the id number of the table
    private int tableSize; // the number of people who can sit on this table
    private boolean isOccupied; // if the table already has customers
    private ArrayList<Dish> tableOrder; // the dishes the table ordered

    public Table(String tableNumber, int tableSize, boolean isOccupied){
        this.tableNumber = tableNumber;
        this.tableSize = tableSize;
        this.isOccupied = isOccupied;
    }

    public ArrayList<Dish> getTableOrder() {
        return tableOrder;
    }

    /**
     *
     * @return returns the number of dishes in the order
     */
    public int numberOfDishesInOrder(){
        int numberOfDishes = 0;
        for(int i = 0; i < tableOrder.size(); i++){
            numberOfDishes++;
        }
        return numberOfDishes;
    }
}
