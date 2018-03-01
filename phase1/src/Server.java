import java.util.ArrayList;

/**
 * Server class records orders taken from customers and relays them to the chef.
 */

public class Server {

    public static ArrayList<Server> servers; // the list of servers in the restaurant
    private static int numberOfServers; // the number of servers in a restaurant
    private String name; // name of a server
    private boolean isOccupied; // whether the server is currently serving a table or not
    protected ArrayList<Table> tables; // the list of tables in the restaurant
    protected ArrayList<Dish> dishesInOrder; // the dishes of a tables order
    protected Table table;

    public Server(String name, ArrayList<Server> servers) {
        this.name = name;
        servers.add(this);
        numberOfServers++;
    }

    /**
     * The server takes the order from a table and adds it to the list of orders to be cooked
     *
     * @param table the table that places the order
     * @param dishesInOrder the arraylist of dishes to be cooked
     */
    public void addOrder(Table table, Dish dish, ArrayList<Dish> dishesInOrder) {
        for(int i = 0; i < table.numberOfDishesInOrder(); i++){
            
        }
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
