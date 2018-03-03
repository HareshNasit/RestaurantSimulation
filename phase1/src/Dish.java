import java.util.HashMap;

public class Dish {

  private String name;
  private int id;
  private int price;
  private int complementsPrice;
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
    this.complementsPrice = 0;
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
    this.complementsPrice = 0;
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
    return this.price + this.complementsPrice;
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
    this.complementsPrice += amount * this.ingredients.get(ingredient).getPrice();
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
    DishIngredient dishIngredient = this.ingredients.get(ingredient);
    // the price shouldn't go down if the customer removes a baseAmount of ingredients
    // For example, if a customer removes a bun from the hamburger, the price shouldn't go down
    // this is the reasoning for the if statement.
    if (dishIngredient.getAmount() - amount >= dishIngredient.getBaseAmount()) {
      this.complementsPrice -= amount * dishIngredient.getPrice();
    } else {
      this.complementsPrice -=
          dishIngredient.getPrice() * (dishIngredient.getPrice() - dishIngredient.getBaseAmount());
    }

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
