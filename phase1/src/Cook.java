import java.util.ArrayList;

/*
 * A cook decides whether the dish can be prepared and prepares the dish.
 */
public class Cook {
    private boolean isOccupied;
    private static int numOfCooks;
    private String name; // Name of the cook.
    private ArrayList<Dish> dishesInMaking; // Number of dishes this cook is preparing.
    private ArrayList<Dish> dishesReady; // Number of dishes ready to be delivered.

    public Cook(){
        numOfCooks++;
        this.dishesReady = new ArrayList<>();
        this.dishesInMaking = new ArrayList<>();

    }

    /**
     * Adds the dishes from the order to dishesInMaking which are being prepared.
     * @param order The Dish that is to be added to dishesInMaking.
     */
    public void prepareDish(Order order){

    }

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
    public void addIngredients(Inventory inventory) {
    }

}
