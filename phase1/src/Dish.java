import java.util.HashMap;

public class Dish {

  private String name;
  private int id;
  private int price;
  private HashMap<String, DishIngredient> ingredients;
  private String tableNumber;

  public Dish(
      String name,
      int id,
      int time,
      HashMap<String, DishIngredient> ingredients,
      String tableNumber) {
    this.name = name;
    this.id = id;
    this.price = time;
    this.ingredients = ingredients;
    this.tableNumber = tableNumber;
  }

  // constructor to be used for menu
  public Dish(String name, int id, int time, HashMap<String, DishIngredient> ingredients) {
    this.name = name;
    this.id = id;
    this.price = time;
    this.ingredients = ingredients;
    this.tableNumber = "n/a";
  }

  @Override
  public String toString() {
    return this.name;
  }

  /**
   * Creates a non-alias copy of this Dish
   *
   * @return Dish
   */
  public Dish clone() {
    return new Dish(this.name, this.id, this.price, cloneIngredients());
  }

  /**
   * Makes a non-alias copy of the ingredients.
   *
   * @return HashMap<String , DishIngredient> Ingredients hashmap
   */
  private HashMap<String, DishIngredient> cloneIngredients() {

    HashMap<String, DishIngredient> copy = new HashMap<>();

    for (String key : this.ingredients.keySet()) {
      copy.put(key, this.ingredients.get(key).clone());
    }
    return copy;
  }

  public HashMap<String, DishIngredient> getIngredients() {
    return this.ingredients;
  }

  public HashMap<String, Integer> getIngredientAmounts() {
    HashMap<String, Integer> ingredientAmounts = new HashMap<>();
    for (String key : this.ingredients.keySet()) {
      ingredientAmounts.put(key, this.ingredients.get(key).getAmount());
    }
    return ingredientAmounts;
  }
}
