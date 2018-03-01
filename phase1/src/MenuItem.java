import java.util.HashMap;

public class MenuItem {

  private String name;
  private int id;
  private int timeToPrepare;
  private HashMap<String, Integer> ingredients;

  public MenuItem(String name, int id, int timeToPrepare, HashMap<String, Integer> ingredients) {
    this.name = name;
    this.id = id;
    this.ingredients = ingredients;
    this.timeToPrepare = timeToPrepare;
  }

  public HashMap<String, Integer> getIngredients() {
    return ingredients;
  }

  public String getName() {
    return name;
  }

  public int getTimeToPrepare() {
    return timeToPrepare;
  }

  public int getId() {
    return id;
  }
}
