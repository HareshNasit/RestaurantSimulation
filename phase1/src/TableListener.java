import java.util.ArrayList;

public interface TableListener {

  public ArrayList<Dish> getTableOrder(String name);

  public void getBill();

  public void returnDish(Dish dish);

}
