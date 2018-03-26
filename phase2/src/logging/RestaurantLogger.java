package logging;


import Restaurant.Dish;
import Restaurant.DishIngredient;
import Restaurant.Table;

import java.util.ArrayList;

public class RestaurantLogger extends SimpleLogger {

    public RestaurantLogger(String filePath) {
        super(filePath);
    }

    /**
     * Sends a message to the logger that an order has been placed.
     *
     * @param dishes The dishes that the server got from the table and is sending to the cooks.
     */
    public void logOrderMessage(ArrayList<Dish> dishes) {
        String content = "[NEW LOG - DISH ORDER]" + System.lineSeparator();

        for (Dish dish : dishes) {
            content += dish.toString() + System.lineSeparator();
        }
        content += "[END LOG]" + System.lineSeparator();
        this.writeToLogger(content);
    }

    public void logDishPrepared(Dish dish) {
        String content = "[NEW LOG - DISH PREPARED] ";
        content += "[ " + dish.toString() + "] has been prepared ";
        content += "[END LOG]" + System.lineSeparator();
        this.writeToLogger(content);
    }

    public void logDishDelivered(Dish dish) {
        String content = "[NEW LOG - DISH DELIVERED] ";
        content += "[ " + dish.toString() + "] has been delivered ";
        content += "[END LOG]" + System.lineSeparator();
        this.writeToLogger(content);
    }

    public void logDishReturned(Dish dish, String reason) {
        String content = "[NEW LOG - DISH DELIVERED] ";
        content += "[ " + dish.toString() + "] has been returned" + System.lineSeparator();
        content += "Reason: " + reason;
        content += "[END LOG]" + System.lineSeparator();
        this.writeToLogger(content);
    }

    public void logInventoryChanged(DishIngredient dishIngredient, int amount) {
        String content = "[NEW LOG - DISH DELIVERED] ";
        content += "[ " + dishIngredient.toString() + "] has changed by: ";
        content += "[END LOG]" + System.lineSeparator();
        this.writeToLogger(content);
    }

}
