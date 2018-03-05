import java.util.ArrayList;
import java.util.Observer;

public interface ServingTableListener {


  /**
   * That dish has been changed
   * @param dish - Dish that has been changed (i.e cooked, update, served)
   */
  public void update(Dish dish);


}
