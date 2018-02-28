import java.util.ArrayList;

/*
 * A cook decides whether the dish can be prepared and prepares the dish.
 */
public class Cook {
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
     * Adds the dish to dishesInMaking which is being prepared.
     * @param dish The Dish that is to be added to dishesInMaking.
     */
    public void prepareDish(Dish dish){}

    /**
     * Adds the dish to dishesReady after being prepared.
     * @param dish The Dish that is to be added.
     */
    public void dishReady(Dish dish){
        dishesReady.add(dish);
    }
}
