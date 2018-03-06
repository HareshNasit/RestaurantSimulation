public class DishIngredient {

  private String name;
  private int amount;
  private int baseAmount;
  private int upperAmount;
  private int lowerAmount;
  private double price;

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
   * Checks if an amount can be added to the dish's ingredient
   *
   * @param amount amount being added
   * @return
   */
  public boolean amountCanBeAdded(int amount) {
    return this.amount + amount <= upperAmount;
  }

  /**
   * Checks if an amount can be subtracted to the dish's ingredient
   *
   * @param amount amount being subtracted
   * @return
   */
  public boolean amountCanBeSubtracted(int amount) {
    return this.amount - amount >= lowerAmount;
  }

  @Override
  public DishIngredient clone() {
    return new DishIngredient(
        this.name, this.amount, this.baseAmount, this.lowerAmount, this.upperAmount, this.price);
  }
  /** @return */
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

  public int getBaseAmount() {
    return this.baseAmount;
  }

  public int getAdditionAmount() {
    if (amount - baseAmount >= 0) {
      return amount - baseAmount;
    }
    return 0;
  }

  public int getBaseDifference() {
    return amount - baseAmount;
  }

  public double getPrice() {
    return this.price;
  }

  public String getName() {
    return this.name;
  }
}
