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
    protected ArrayList<Dish> cookedDishes; // the cooked dishes that should be served
    protected Table table;
    protected ServingTable servingTable;

    public Server(String name, ArrayList<Server> servers) {
        this.name = name;
        servers.add(this);
        numberOfServers++;
    }

    /**
     * The server takes the order from a table and adds it to the list of orders to be cooked
     * @param table the table that places the order
     */
    public void addOrder(Table table) {
        ArrayList<Dish> tempDishes = table.getTableOrder();
        for(int i = 0; i < table.numberOfDishesInOrder(); i++){
            servingTable.dishesToBeCooked.add(tempDishes.get(i));
        }
    }

    /**
     * The server serves the order to the table and removes it from the list of orders to be served
     * @param table the table whose order is being served
     */
    public void getCookedDishes(Table table) {
        servingTable.getDishesToBeServed();
    }

    /**
     * The server takes the dish from the table and returns it to the cook
     * @param dish the dish that is to be returned
     */
    public void returnOrder(Dish dish){
        servingTable.dishesToBeCooked.add(dish);
    }

    /**
     * The server adds an ingredient to a dish which is displayed in the order
     */
    public void addIngredient(String ingredient) {
    }

    /**
     * This method checks whether all the dishes the table ordered have been served or not.
     * @return
     */
    public boolean isOrderComplete(){
        return table.getNumberOfDishesServed() == table.numberOfDishesInOrder();
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
