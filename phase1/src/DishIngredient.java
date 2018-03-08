public class DishIngredient {

  private String name;
  private int amount;
  private int baseAmount;
  private int upperAmount;
  private int lowerAmount;
  private double price;

  /**
   * DishIngredient represents how much of an ingredient is in the dish. It stores necessary values
   * for making additions to the Dish.
   *
   * @param name name of the ingredient
   * @param baseAmount the original amount of the ingredient on the dish.
   * @param amount the current amount of the ingredient on the dish.
   * @param lowerAmount the lowest amount of the ingredient you can have on the dish.
   * @param upperAmount the highest amount of the ingredient you can have on the dish.
   * @param price the price of an addition for this ingredient for the dish.
   */
  public DishIngredient(
      String name, int baseAmount, int amount, int lowerAmount, int upperAmount, Double price) {
    this.name = name;
    this.baseAmount = baseAmount;
    this.amount = amount;
    this.upperAmount = upperAmount;
    this.lowerAmount = lowerAmount;
    this.price = price;
  }

  /**
   * Adds an amount to the ingredient.
   *
   * @param amount amount added to the ingredient.
   */
  public void addAmount(int amount) {
    this.amount += amount;
  }

  /**
   * Gets the amount of the Ingredient this DishIngredient has.
   *
   * @return
   */
  public int getAmount() {
    return this.amount;
  }

  /**
   * Adds an amount to the ingredient.
   *
   * @param amount amount subtracted from the ingredient.
   */
  public void subtractAmount(int amount) {
    this.amount -= amount;
  }

  /**
   * Returns a non-alias copy of the DishIngredient.
   *
   * @return DishIngredient.
   */
  @Override
  public DishIngredient clone() {
    return new DishIngredient(
        this.name, this.amount, this.baseAmount, this.lowerAmount, this.upperAmount, this.price);
  }

  /**
   * Returns a string representation of the DishIngredient.
   *
   * @return
   */
  @Override
  public String toString() {
    return "The "
        + this.name
        + " for the dish has "
        + "(amount: "
        + this.amount
        + " lower bound: "
        + this.lowerAmount
        + " upper bound: "
        + this.upperAmount
        + " price: "
        + this.price
        + ")";
  }

  /**
   * Gets the base amount of this ingredient.
   *
   * @return int
   */
  public int getBaseAmount() {
    return this.baseAmount;
  }

  /**
   * Gets the amount that has been added to this Ingredient.
   *
   * @return int
   */
  public int getAdditionAmount() {
    if (amount - baseAmount >= 0) {
      return amount - baseAmount;
    }
    return 0;
  }

  /**
   * Gets the difference between the current amount and baseamount.
   *
   * @return int
   */
  public int getBaseDifference() {
    return amount - baseAmount;
  }

  /**
   * Gets the price of the Ingredient.
   *
   * @return double
   */
  public double getPrice() {
    return this.price;
  }

  /**
   * Gets the name of the DishIngredient.
   *
   * @return String
   */
  public String getName() {
    return this.name;
  }

  /**
   * Checks if an amount can be added to the dish's ingredient.
   *
   * @param amount amount being added
   * @return
   */
  public boolean amountCanBeAdded(int amount) {
    return this.amount + amount <= upperAmount;
  }

  /**
   * Checks if an amount can be subtracted to the dish's ingredient.
   *
   * @param amount amount being subtracted
   * @return
   */
  public boolean amountCanBeSubtracted(int amount) {
    return this.amount - amount >= lowerAmount;
  }
}
