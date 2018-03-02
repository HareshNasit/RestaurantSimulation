public class Ingredient {

  private String name;
  private int amount;
  private int lowerThreshold;

  Ingredient(String name) {
    this.name = name;
    this.amount = 0;
    this.lowerThreshold = 0;
  }

  Ingredient(String name, int amount, int lowerThreshold) {
    this.name = name;
    this.amount = amount;
    this.lowerThreshold = lowerThreshold;
  }

  public int getAmount() {
    return amount;
  }

  public int getLowerThreshold() {
    return lowerThreshold;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
