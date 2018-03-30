package Restaurant;

import MenuDishes.Dish;
import MenuDishes.MenuItem;
import java.util.ArrayList;

/** Server class records orders taken from customers and relays them to the chef. */
public class Server implements IWorker, Notifiable {

    private String name; // name of a server
    private ModelControllerInterface screen; // This server's screen.
    private WorkerType type = WorkerType.SERVER; // The Type of this worker.


    /**
     * Creates a new server for this restaurant
     *
     * @param name The name of the server
     */
    public Server(String name) {
        this.name = name;
    }

    /**
     * return name of the server.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    @Override
    public WorkerType getType() {
        return type;
    }

    /**
     * The server takes the order from a table and adds it to the list of orders to be cooked.
     *
     * @param table the table that places the order
     */
    public ArrayList<Dish> passOrder(Table table, ServingTable servingTable) {
        ArrayList<Dish> order = new ArrayList<>();
        for(Dish dish: table.getTableOrder()){
            if(dish.getDishStatus() == DishStatus.ORDERED ) {
                servingTable.addToBeCooked(dish);
                dish.setDishStatus(DishStatus.SENT);
                order.add(dish);
            } else if (dish.getDishStatus() == DishStatus.RETURNED) {
                servingTable.addToBeCooked(dish);
                order.add(dish);
            }
        }
        return order;
    }

    /**
     * Serves a dish to the customer.
     *
     * @param dish the dish.
     * @param restaurant the restaurant.
     */
    public void serveDish(Dish dish, Restaurant restaurant) {
        restaurant.getServingTable().serveDish(dish);
        dish.setDishStatus(DishStatus.SERVED);
        screen.updateScreen();
    }

    /**
     * The server informs the customer that a particular dish has been rejected.
     *
     * @param index index of the dish.
     * @param restaurant the restaurant.
     */
    public void rejectDish(int index, Restaurant restaurant) {
        Dish dish = restaurant.getServingTable().getRejectedDish(index);
        restaurant.getTable(dish.getTableName()).removeDish(dish);
        dish.setDishStatus(DishStatus.REJECTED);
    }

    /**
     * Return a dish back to the kitchen.
     * @param dish the dish to be returned.
     * @param servingTable
     */
    public void returnDish(Dish dish, ServingTable servingTable) {

        servingTable.addToBeCooked(dish);
        dish.setDishStatus(DishStatus.RETURNED);
    }

    /**
     * Adds a dish to a table's Order.
     *
     * @param table table to which dish will be added.
     * @param dish dish to be added to the tables order.
     */
    public void addOrder(Table table, int customerNumber, MenuItem dish) {
        Dish dish1 = new Dish(dish, table.getTableID(), customerNumber);
        table.addSingleOrder(dish1);
        dish1.setDishStatus(DishStatus.ORDERED);
    }

    /**
     * Occupies the table.
     *
     * @param table the table which is to be occupied.
     * @param numberOfCustomers The number of customers.
     */
    public void seatCustomer(Table table, int numberOfCustomers) {
        table.setOccupied(numberOfCustomers);


    }

    /**
     * Generates a bill one table.
     *
     * @param table The table that asked for the bill
     */
    public void generateTableBill(Table table) {
        Bill.outputBill(table);
    }

    /**
     * Generates a bill for the table customer.
     *
     * @param table The table that asked for the bill
     */
    public void generateSingleBill(Table table, int seatNum) {
        Bill.outputSingleBill(table, seatNum);
    }

    /**
     * Clear the table and set it to be unoccupied.
     *
     * @param table The table to be cleared.
     */
    public void clearTable(Table table) {
        table.clearTable();
    }

    /** When new stock has been received, updateScreen the stock.
     * @param amount the amount of the ingredient received.
     * @param ingredient The ingredient received
     * @param inventory the inventory
     *
     */
    @Override
    public void scanStock(Inventory inventory, String ingredient, int amount) {
        screen.openReceiverFunction(inventory, ingredient,amount);
    }
    /** Send a notification to the cook.
     * @param message The message to be sent.
     *
     */
    @Override
    public void sendNotification(String message) {
        screen.openNotification(message);
    }

    /** Send notifications
     * @param message The message to be sent.
     *
     */
    @Override
    public void sendNotifications(String message) {
        screen.updateScreen();
        screen.openNotification(message);
    }
    /** Update the screen.
     */
    @Override
    public void update() {
        screen.updateScreen();
    }

    /** Cancel a dish from a particular table.
     * @param table The table from which a dish is to be cancelled.
     * @param dish The dish to be cancelled.
     *
     */
    public void removeDish(Table table, Dish dish) {
        table.removeDish(dish);
    }
    /** Getter for the screen
     * @return the screen to be returned.
     *
     */
    public ModelControllerInterface getScreen() {
        return screen;
    }
    /** Setter for the screen.
     * @param screen The screen to be set.
     *
     */
    public void setScreen(ModelControllerInterface screen) {
        this.screen = screen;
    }
}
