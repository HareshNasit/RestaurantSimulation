public class InventoryIngredient {

  private String name;
  private int currentQuantity;
  private int restockQuantity;
  private int lowerThreshold;

  public InventoryIngredient(String name, int amount, int restockQuantity, int lowerThreshold) {
    this.name = name;
    this.currentQuantity = amount;
    this.restockQuantity = restockQuantity;
    this.lowerThreshold = lowerThreshold;
  }

  public InventoryIngredient(String name, int amount) {
    this.name = name;
    this.currentQuantity = amount;
    this.restockQuantity = 20;
    this.lowerThreshold = 0;
  }

  public int getCurrentQuantity() {
    return currentQuantity;
  }

  /**
   * Sets the quantity of the ingredient.
   *
   * @param currentQuantity
   */
  public void setCurrentQuantity(int currentQuantity) {
    this.currentQuantity = currentQuantity;
  }

  /**
   * Increases the quantity by an amount.
   *
   * @param amount
   */
  public void increaseQuantity(int amount) {
    this.currentQuantity += amount;
  }

  /**
   * Decreases the quantity by an amount.
   *
   * @param amount
   */
  public void decreaseQuantity(int amount) {
    this.currentQuantity -= amount;
  }

  /**
   * Gets the name of Ingredient.
   *
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the InventoryIngredient.
   *
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the restock package quantity.
   *
   * @return
   */
  public int getRestockQuantity() {
    return restockQuantity;
  }

  /**
   * Sets the restock package quantity.
   *
   * @param restockQuantity
   */
  public void setRestockQuantity(int restockQuantity) {
    this.restockQuantity = restockQuantity;
  }

  /**
   * Gets the lower threshold.
   *
   * @return int
   */
  public int getLowerThreshold() {
    return lowerThreshold;
  }

  /**
   * Sets the lower threshold.
   *
   * @param lowerThreshold
   */
  public void setLowerThreshold(int lowerThreshold) {
    this.lowerThreshold = lowerThreshold;
  }
}
