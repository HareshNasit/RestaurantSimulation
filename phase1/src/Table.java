/**
 * The table is where the customers sit and order food and also where their cooked food arrives.
 */

public class Table {
    private String tableNumber; // the id number of the table
    private int tableSize; // the number of people who can sit on this table
    private boolean isOccupied; // of the table already has customers

    public Table(String tableNumber, int tableSize, boolean isOccupied){
        this.tableNumber = tableNumber;
        this.tableSize = tableSize;
        this.isOccupied = isOccupied;
    }

    // what methods does table have?
}
