import java.util.HashMap;

public class Dish {

  private String name;
  private int id;
  private int price;
  private HashMap<String, DishIngredient> ingredients;
  private String tableId;

  /**
   * @param name
   * @param id
   * @param time
   * @param ingredients
   * @param tableId
   */
  public Dish(
      String name, int id, int time, HashMap<String, DishIngredient> ingredients, String tableId) {
    this.name = name;
    this.id = id;
    this.price = time;
    this.ingredients = ingredients;
    this.tableId = tableId;
  }

  /**
   * Constructor to be used for the Menu.
   *
   * @param name
   * @param id
   * @param time
   * @param ingredients
   */
  public Dish(String name, int id, int time, HashMap<String, DishIngredient> ingredients) {
    this.name = name;
    this.id = id;
    this.price = time;
    this.ingredients = ingredients;
    this.tableId = "n/a";
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
   * Creates a copy of this dish with the table id.
   *
   * @param tableName
   * @param customerNumber
   * @return
   */
  public Dish createCopyWithTableId(String tableName, int customerNumber) {
    Dish dish = this.clone();
    dish.tableId = tableName + customerNumber;
    return dish;
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

  /**
   * Returns the price of the Dish
   *
   * @return
   */
  public int getPrice() {
    int finalPrice = price;
    for (String key : this.ingredients.keySet()) {
      DishIngredient dishIngredient = this.ingredients.get(key);
      int difference = dishIngredient.getAdditionAmount();
      finalPrice += difference * dishIngredient.getPrice();
    }
    return finalPrice;
  }

  public HashMap<String, DishIngredient> getIngredients() {
    return this.ingredients;
  }

  /**
   * Adds an ingredient to the dish. Precondition, the amount you are adding is within bounds of the
   * ingredient's bounds.
   *
   * @param ingredient
   * @param amount
   */
  public void addIngredient(String ingredient, int amount) {
    this.ingredients.get(ingredient).subtractAmount(amount);
  }

  /**
   * Subtracts an ingredient from the dish. Precondition, the amount you are subtracting is within
   * bounds of the ingredient's bounds.
   *
   * @param ingredient
   * @param amount
   */
  public void subtractIngredient(String ingredient, int amount) {
    this.ingredients.get(ingredient).subtractAmount(amount);
  }

  public HashMap<String, Integer> getIngredientAmounts() {
    HashMap<String, Integer> ingredientAmounts = new HashMap<>();
    for (String key : this.ingredients.keySet()) {
      ingredientAmounts.put(key, this.ingredients.get(key).getAmount());
    }
    return ingredientAmounts;
  }

  @Override
  public String toString() {
    return this.name + " w/ price: " + this.price;
  }
}
