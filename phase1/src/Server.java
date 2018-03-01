import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Server class records orders taken from customers and relays them to the chef.
 */

public class Server {

    public static ArrayList<Server> servers; // the list of servers in the restaurant
    private static int numberOfServers;
    private String name; // name of a server
    private boolean isOccupied; // whether the server is currently serving a table or not
    protected ArrayList<Dish> dishes; // list of dishes the server has taken from a table
    protected ArrayList<Table> tables; // the list of tables in the restaurant

    public Server(String name) {
        this.name = name;
        numberOfServers++;
    }

    /**
     * The server takes the order from a table and adds it to the list of orders to be cooked
     *
     * @param table the table that places the order
     * @param queue the queue of dishes to be cooked
     */
    public void addOrder(Table table, PriorityQueue queue) {
    }

    /**
     * The server serves the order to the table and removes it from the list of orders to be served
     *
     * @param table the table whose order is being served
     */
    public void serveOrder(Table table) {
    }

    /**
     * The server takes the dish from the table and returns it to the cook
     * @param dish the dish that is to be returned 
     */
    public void returnOrder(Dish dish){}

    /**
     * The server adds an ingredient to a dish which is displayed in the order
     */
    public void addIngredient(String ingredient) {
    }


    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
