public class InventoryIngredient {

    private String name;

  public int getCurrentQuantity() {
    return currentQuantity;
  }

  public void setCurrentQuantity(int currentQuantity) {
    this.currentQuantity = currentQuantity;
  }

  public void increaseQuantity(int amount) {
    this.currentQuantity += amount;
  }

  public void decreaseQuantity(int amount) {
    this.currentQuantity -= amount;
  }

  private int currentQuantity;
    private int restockQuantity;
    private int lowerThreshold;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRestockQuantity() {
    return restockQuantity;
  }

  public void setRestockQuantity(int restockQuantity) {
    this.restockQuantity = restockQuantity;
  }

  public int getLowerThreshold() {
    return lowerThreshold;
  }

  public void setLowerThreshold(int lowerThreshold) {
    this.lowerThreshold = lowerThreshold;
  }

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
}
