public class DishIngredient {

  private String name;
  private int amount;
  private int upperAmount;
  private int lowerAmount;

  public DishIngredient(String name, int amount, int lowerAmount, int upperAmount) {
    this.name = name;
    this.amount = amount;
    this.upperAmount = upperAmount;
    this.lowerAmount = lowerAmount;
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
    return new DishIngredient(this.name, this.amount, this.lowerAmount, this.upperAmount);
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
        + ")";
  }
}
