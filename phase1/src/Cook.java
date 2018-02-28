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
    public void prepareDish(Dish dish){}

    public void dishReady(Dish dish){
        dishesReady.add(dish);
    }
}
