package logging;

import MenuDishes.Dish;
import Restaurant.IWorker;
import Restaurant.InventoryIngredient;

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
    String content = this.getTime() + "[NEW LOG - DISH ORDER]" + System.lineSeparator();

    for (Dish dish : dishes) {
      content += dish.toString() + System.lineSeparator();
    }
    content += "[END LOG]" + System.lineSeparator();
    this.writeToLogger(content);
  }

  /**
   * Sends a message about a dish being delivered.
   */
  public void logDishPrepared(Dish dish) {
    String content = "[NEW LOG - DISH PREPARED] ";
    content += "[ " + dish.toString() + "] has been prepared ";
    content += "[END LOG]" + System.lineSeparator();
    this.writeToLogger(content);
  }

  /**
   * Sends a message about a dish being delivered.
   *
   * @param dish Dish being delivered.
   */
  public void logDishDelivered(Dish dish) {
    String content = this.getTime() + "[NEW LOG - DISH DELIVERED] ";
    content += "[ " + dish.toString() + "] has been delivered ";
    content += "[END LOG]" + System.lineSeparator();
    this.writeToLogger(content);
  }

  /**
   * Sends a message about a dish being returned.
   *
   * @param dish Dish being returned.
   * @param reason Reason for the return.
   */
  public void logDishReturned(Dish dish, String reason) {
    String content = this.getTime() + "[NEW LOG - DISH RETURNED] ";
    content += "[ " + dish.toString() + "] has been returned" + System.lineSeparator();
    content += "Reason: " + reason;
    content += " [END LOG]" + System.lineSeparator();
    this.writeToLogger(content);
  }

  /**
   * Sends a message to the log about an Ingredient amount being changed in the Inventory. Ex. -2
   * means that the amount decreased by 2, and "2" means that the amount increased by 2.
   *
   * @param inventoryIngredient Ingredient whose amount is being changed
   * @param amount amount being changed.
   */
  public void logInventoryChanged(InventoryIngredient inventoryIngredient, int amount) {
    String content = this.getTime() + "[NEW LOG - INVENTORY CHANGED] ";
    content += "[ " + inventoryIngredient.toString() + "] has changed by: " + amount;
    content += "[END LOG]" + System.lineSeparator();
    this.writeToLogger(content);
  }

  /**
   * Sends a message to the log about a worker request.
   *
   * @param worker worker that has been sent request
   * @param request request info
   */
  public void logRequest(IWorker worker, String request) {
    String content = this.getTime() + "[NEW LOG - WORKER REQUEST] ";
    content += "[ " + worker.getName() + "] has been sent request from Manager: " + request;
    content += " [END LOG]" + System.lineSeparator();
    this.writeToLogger(content);
  }

  /**
   * Logs when a worker logs into the restaurant.
   *
   * @param worker worker that has logged in.
   */
  public void logWorkerLogin(IWorker worker) {
    String content = this.getTime() + "[NEW LOG - WORKER LOGIN] ";
    content += "[ " + worker.getName() + "] has logged into the system: ";
    content += "[END LOG]" + System.lineSeparator();
    this.writeToLogger(content);
  }


}
