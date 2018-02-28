import java.util.ArrayList;
import java.util.List;

/**
 * Server class records orders taken from customers and relays them to the chef.
 */

public class Server {
    private static int numberOfServers;
    private String name; // name of a server
    protected ArrayList<Dish> dishes; // list of dishes the server has taken from a table
    protected ArrayList<Table> tables; // the list of tables in the restaurant

    public Server(String name){
        this.name = name;
        numberOfServers++;
    }

    /**
     * The server takes the order from a table and adds it to the list of orders to be cooked
     * @param table the table that places the order
     */
    public void addOrder(Table table){}

    /**
     * The server serves the order to the table and removes it from the list of orders to be served
     * @param table the table whose order is being served
     */
    public void serveOrder(Table table){}

}
