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
    public void prepareDish(Dish dish,Inventory inventory){}

    /**
     * Adds the dish to dishesReady after being prepared.
     * @param dish The Dish that is to be added.
     */


    public void dishReady(Dish dish){
        dishesReady.add(dish);
    }
    /**
     * Checks if the dish has enough ingredients.
     */
    public boolean lessIngredients(Dish dish){
        return false;
    }
    /**
     * The server receives and adds ingredients to the inventory.
     */
    public void addIngredients(Inventory inventory, String ingredient, int amount) {
        inventory.addStock(ingredient,amount);
    }
    public boolean isOccupied(){
        return isOccupied;
    }
    /**
     * Returns a boolean whether a dish can be prepared or no.
     * @param dish the dish.
     * @return boolean whether a dish can be prepared or no.
     */
    public boolean canBePrepared(Dish dish, Inventory inventory){
    for(DishIngredient ingredient: )
    }
}
