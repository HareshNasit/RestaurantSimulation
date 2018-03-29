package Restaurant;

import MenuDishes.Dish;
import java.util.ArrayList;

/*
 * A cook reads the order being taken by the server and confirms with
 * the server if the dish can be prepared and prepares the dish.
 */
public class Cook implements IWorker, Notifiable {

    private String name; // Name of the cook.
    private WorkerType type = WorkerType.COOK; // Type of worker.
    private ModelControllerInterface screen; // The screen of the cook.
    private ArrayList<Dish> currentDishes; // The dishes this cook is cooking right now.

    public Cook(String name) {
        this.name = name;
        currentDishes = new ArrayList<Dish>();
    }
    /**
     * Add the dish this cook has confirmed to cook.
     *
     * @param dish the dish this cook had selected to cook.
     */
    public void addDish(Dish dish){
        currentDishes.add(dish);
    }
    /**
     * Remove the dish this cook has cooked.
     *
     * @param dish the dish this cook cooked.
     */
    public void removeDish(Dish dish){
        currentDishes.remove(dish);
    }
    /**
     * Check if this cook is cooking any dishes.
     *
     * @return boolean.
     */
    public boolean hasDishes(){
        return !currentDishes.isEmpty();
    }
    /**
     * Getter for the name of the cook.
     *
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the type of the worker.
     *
     * @return  WorkerType
     */
    @Override
    public WorkerType getType() {
        return type;
    }

    /**
     * Checks if the dish can be prepared and subtracts ingredients accordingly and returns a boolean
     * and adds it to the list of dishes being cooked currently.
     *
     * @param dish The MenuItem that is to be added to dishesInMaking.
     */
    private void prepareDish(Dish dish, Inventory inventory) {
        if (canBePrepared(dish, inventory)) {
            for (String ingredient : dish.getIngredients().keySet()) {
                inventory.removeStock(ingredient, dish.getIngredientAmounts().get(ingredient));
            }
        }
    }

    /**
     * The server receives and adds ingredients to the inventory from the distributor when called by
     * the manager.
     * @param inventory The inventory of the restaurant.
     * @param ingredient The ingredient.
     * @param amount The quantity of the ingredient.
     */
    public void scanStock(Inventory inventory, String ingredient, int amount) {
        screen.openReceiverFunction(inventory, ingredient,amount );
    }

    @Override
    public void sendNotification(String message) {
        screen.openNotification(message);
    }

    /**
     * Returns a boolean whether a dish can be prepared or no.
     *
     * @param dish the dish which is to be cooked.
     * @param inventory The inventory.
     * @return boolean whether a dish can be prepared or no.
     */
    public boolean canBePrepared(Dish dish, Inventory inventory) {
        return inventory.hasEnoughIngredients(dish.getIngredientAmounts());
    }

    /** Notify the cook that dish has been served.
     *  @param message the message to be sent.
     */
    public void sendNotifications(String message) {
        screen.updateScreen();
        screen.openNotification(message);
    }

    /**
     * Update the screen
     */
    @Override
    public void update() {
        screen.updateScreen();
    }

    /**
     * Cook accepts to cook the dish and removes ingredients.
     *
     * @param dish that need to be cooked
     * @param servingTable serving table that needs to be changed
     * @param inventory inventory of restaurant
     */
    public void acceptCook(Dish dish, ServingTable servingTable, Inventory inventory) {
        prepareDish(dish, inventory);
        servingTable.addToBeCooking(dish);
        currentDishes.add(dish);
    }

    /**
     * Cook accepts to prepare a dish. No ingredient removal needed
     *
     * @param dish that needs to be cooked
     * @param servingTable serving table of restaurant
     */
    public void acceptNoCook(Dish dish, ServingTable servingTable) {
        servingTable.addToBeCooking(dish);
        currentDishes.add(dish);
    }

    /**
     * Reject a dish that needs to be cooked.
     *
     * @param dish that is rejected
     * @param servingTable serving table of the restaurant
     */
    public void rejectDish(Dish dish, ServingTable servingTable) {
        servingTable.rejectDish(dish);
        dish.setDishStatus(DishStatus.REJECTED);
    }

    /**
     * Dish has been cooked and is placed in list of dishes that needs to be served.
     *
     * @param dish which is cooked
     * @param servingTable serving table of restaurant
     */
    public void serveDish(Dish dish, ServingTable servingTable) {
        servingTable.addToBeServed(dish);
        currentDishes.remove(dish);
        dish.setDishStatus(DishStatus.PICKUP);
    }
    /**
     * Getter for the screen.
     *
     * @return  screen The screen to be returned.
     */
    public ModelControllerInterface getScreen() {
        return screen;
    }

    /**
     * Setter for the screen.
     *
     * @param screen The screen to be set.
     */
    public void setScreen(ModelControllerInterface screen) {
        this.screen = screen;
    }
}
