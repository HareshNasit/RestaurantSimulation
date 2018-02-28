import java.util.HashMap;

public class MenuItem {

  private String name;
  private int id;
  private int timeToPrepare;
  private HashMap<String, Integer> ingredients;
  private HashMap<String, Integer> allowedComplements;
  private HashMap<String, Integer> allowedSubtractions;

  public MenuItem(
      String name,
      int id,
      HashMap<String, Integer> ingredients,
      HashMap<String, Integer> allowedComplements, HashMap<String, Integer> allowedSubtractions,
      int timeToPrepare) {
    this.name = name;
    this.id = id;
    this.ingredients = ingredients;
    this.allowedComplements = allowedComplements;
    this.allowedSubtractions = allowedSubtractions;
    this.timeToPrepare = timeToPrepare;
  }

  public HashMap<String, Integer> getIngredients() {
    return ingredients;
  }

  public HashMap<String, Integer> getAllowedComplements() {
    return allowedComplements;
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
