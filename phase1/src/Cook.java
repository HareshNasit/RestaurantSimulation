/*
 * A cook reads the order being taken by the server and confirms with
 * the server if the dish can be prepared and prepares the dish.
 */
public class Cook implements IWorker, ServingTableListener {
    private boolean isOccupied; // Boolean for if the cook is working or vacant.
    private String name; // Name of the cook.

  public Cook(String name) {
        this.name = name;
        this.isOccupied = false;
    }
    /**
     * Getter for the name of the cook.
     * @return String.
     */
    public String getName() {
        return name;
    } // Name of the Cook.
    /**
     * Getter for isOccupied to see if this cook is vacant or busy.
     * @return boolean.
     */
    public boolean isOccupied(){
        return isOccupied;
    }
    /**
     * Checks if the dish can be prepared and subtracts ingredients accordingly and returns a boolean and
     * adds it to the list of dishes being cooked currently.
     * @param dish The MenuItem that is to be added to dishesInMaking.
     * @return boolean Whether the dish can be prepared or no.
     */
    public boolean prepareDish(Dish dish, Inventory inventory, ServingTable screen) {
        if(canBePrepared(dish,inventory)){
            for(String ingredient: dish.getIngredients().keySet()){
                inventory.removeStock(ingredient,dish.getIngredientAmounts().get(ingredient));
            }
            screen.getDishesBeingCooked().add(dish);
            return true;
        }
        else{
            screen.getDishesRejected().add(dish);
            return false;
        }
    }
    /**
     * Adds the dish to dishesToBeServed after being prepared and removes it from dishesToBeCooked and dishBeingCooked.
     * @param dish The MenuItem that is to be added.
     */
    public void dishReady(Dish dish, ServingTable screen) {
        // The dish  prepared is added to the Servers list of dishes ready to be served.
        screen.getDishesToBeCooked().remove(dish);
        screen.getDishesBeingCooked().remove(dish);
        screen.addToBeServed(dish);
    }
    /**
     * The server receives and adds ingredients to the inventory from the distributor when called by the manager.
     */
    public void scanStock(Inventory inventory, String ingredient, int amount) {
        inventory.addStock(ingredient,amount);
    }
    /**
     * Returns a boolean whether a dish can be prepared or no.
     * @param dish the dish which is to be cooked.
     * @return boolean whether a dish can be prepared or no.
     */
    public boolean canBePrepared(Dish dish, Inventory inventory) {
        return inventory.hasEnoughIngredients(dish.getIngredientAmounts());
    }
  /**
   * Notify the cook that dish has been served
   */
  public void update(Dish dish) {
      System.out.println(String.format("cook has been notified"));
  }
}
