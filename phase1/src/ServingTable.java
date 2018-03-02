import java.util.ArrayList;
import java.util.Observable;
import java.util.PriorityQueue;

public class ServingTable extends Observable {

  PriorityQueue<Dish> dishesToBeCooked;
  PriorityQueue<Dish> dishesToBeServed;

  ServingTable() {
    dishesToBeCooked = new PriorityQueue<>();
    dishesToBeServed = new PriorityQueue<>();
  }

  public void addToBeCooked(ArrayList<Dish> order) {
    dishesToBeCooked.addAll(order);
  }

  public void addToBeServed(ArrayList<Dish> order) { dishesToBeServed.addAll(order);
  }

  public Dish getDishesToBeCooked() {
    return dishesToBeCooked.poll();
  }

  public Dish getDishesToBeServed(){ return dishesToBeServed.poll(); }

}
