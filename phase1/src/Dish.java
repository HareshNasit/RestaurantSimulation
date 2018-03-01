import java.util.HashMap;

public class Dish {

  private String name;
  private int id;
  private int timeToPrepare;
  private HashMap<String, int[]> ingredients;
  private String tableNumber;

  public Dish(String name, int id, int time, HashMap<String, int[]> ingredients,
      String tableNumber) {
    this.name = name;
    this.id = id;
    this.timeToPrepare = time;
    this.ingredients = ingredients;
    this.tableNumber = tableNumber;
  }

  public Dish(String name, int id, int time, HashMap<String, int[]> ingredients) {
    this.name = name;
    this.id = id;
    this.timeToPrepare = time;
    this.ingredients = ingredients;
    this.tableNumber = "n/a";
  }

  @Override
  public String toString() {
    return this.name;
  }
}
