import java.util.ArrayList;

/*
 * An order gets all the dishes for a particular table.
 */
public class Order {
    private int tableNum;
    private ArrayList<Dish> dishes;

    public Order(int tableNum){
        this.dishes = new ArrayList<Dish>();
        this.tableNum = tableNum;
    }
    /**
     * Adds the dish to the order.
     * @param dish The Dish that is to be added to dishes.
     */
    public void addDish(Dish dish){
        dishes.add(dish);
    }
}
