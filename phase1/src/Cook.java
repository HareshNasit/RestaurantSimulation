import java.util.ArrayList;

/*
 * A cook decides whether the dish can be prepared and prepares the dish.
 */
public class Cook implements IWorker{
    private boolean isOccupied;
    private static int numOfCooks;
    private String name; // Name of the cook.
    private ArrayList<Dish> dishesInMaking; // Number of dishes this cook is preparing.
    private ArrayList<Dish> dishesReady; // Number of dishes ready to be delivered.

    public Cook(String name, Restaurant restaurant){
        this.name = name;
        numOfCooks++;
        this.dishesReady = new ArrayList<Dish>();
        this.dishesInMaking = new ArrayList<Dish>();
        restaurant.getWorkers().add((IWorker)this);
        this.isOccupied = false;
    }

    /**
     * Adds the dishes from the order to dishesInMaking which are being prepared.
     * @param dish The Dish that is to be added to dishesInMaking.
     */
    public void prepareDish(Dish dish,Inventory inventory){
        if(canBePrepared(dish,inventory)){
            // REMOVE THE INGREDIENTS FROM THE INVENTORY AND ADD DISH TO READY.
            for(String ingredient: dish.getIngredients().keySet()){
                inventory.removeStock(ingredient,dish.getIngredientAmounts().get(ingredient));
            }
        }
        else{
            //  DOES COOK DECIDE IF A DISH CAN BE MADE OR COMPUTER IS A QUESTION.
        }
    }

    /**
     * Adds the dish to dishesReady after being prepared.
     * @param dish The Dish that is to be added.
     */
    public void dishReady(Dish dish){
        dishesReady.add(dish);
    }

    /**
     * The server receives and adds ingredients to the inventory.
     */
    public void addIngredients(Inventory inventory, String ingredient, int amount) {
        inventory.addStock(ingredient,amount);
    }

    /**
     * Getter for isOccupied to see if this cook is vacant or busy.
     */
    public boolean isOccupied(){
        return isOccupied;
    }

    /**
     * Returns a boolean whether a dish can be prepared or no.
     * @param dish the dish.
     * @return boolean whether a dish can be prepared or no.
     */
    private boolean canBePrepared(Dish dish, Inventory inventory) {
        return inventory.hasEnoughIngredients(dish.getIngredientAmounts());
    }
}
