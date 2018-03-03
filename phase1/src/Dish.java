import java.util.HashMap;

public class Dish {

  private String name;
  private double id;
  private double price;
  private HashMap<String, DishIngredient> ingredients;
  private String tableName;

  public int getCustomerNum() {
    return customerNum;
  }

  private int customerNum;

  /**
   * @param name
   * @param id
   * @param price
   * @param ingredients
   * @param tableId
   */
  public Dish(
      String name,
      double id,
      double price,
      HashMap<String, DishIngredient> ingredients,
      String tableId,
      int customerNum) {
    this.name = name;
    this.id = id;
    this.price = price;
    this.ingredients = ingredients;
    this.tableName = tableId;
    this.customerNum = customerNum;
  }

  /**
   * Constructor to be used for the Menu.
   *
   * @param name
   * @param id
   * @param price
   * @param ingredients
   */
  public Dish(String name, double id, double price, HashMap<String, DishIngredient> ingredients) {
    this.name = name;
    this.id = id;
    this.price = price;
    this.ingredients = ingredients;
    this.tableName = "n/a";
    this.customerNum = -1;
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
    dish.tableName = tableName;
    dish.customerNum = customerNumber;
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
  public double getPrice() {
    double finalPrice = price;
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

  private HashMap<String, Integer> getDifferenceAmounts() {
    HashMap<String, Integer> differenceMap = new HashMap<>();
    for (String key : this.ingredients.keySet()) {
      int dif = this.ingredients.get(key).getBaseDifference();
      differenceMap.put(key, dif);
    }
    return differenceMap;
  }

  public String toString() {
    String billText = "";
    billText += "Table: " + this.tableName + ", ";
    billText += "CustomerNumber: " + this.customerNum + ", ";
    billText += getStringForBill();
    return billText;
  }

  public String getStringForBill() {
    String billText = "";

    billText += "Dish Name: " + this.name + ", ";
    billText += "Price $" + this.getPrice() + ", ";
    String extras = "";
    String subtractions = "";
    HashMap<String, Integer> differenceMap = this.getDifferenceAmounts();
    for (String key : differenceMap.keySet()) {
      if (differenceMap.get(key) > 0) {
        extras += "+" + differenceMap.get(key) + " " + this.ingredients.get(key).getName() + ", ";
      } else if (differenceMap.get(key) < 0) {
        subtractions +=
            "+" + differenceMap.get(key) + " " + this.ingredients.get(key).getName() + ", ";
      }
    }
    billText += extras + subtractions;
    billText = billText.substring(0, billText.length() - 2);

    return billText;
  }
}
