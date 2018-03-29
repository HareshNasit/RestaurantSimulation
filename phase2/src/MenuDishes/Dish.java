package MenuDishes;

import Restaurant.DishIngredient;
import Restaurant.DishStatus;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * A dish inherits the properties of a menuitem but is tailored to a table.
 */
public class Dish extends MenuItem {

  protected String tableName;
  protected int customerNum;
  protected String comment;
  protected DishStatus dishStatus;

  /**
   * A dish that a customer orders. The Dishes get passed around the restaurant until the customer
   * receives it.
   *
   * @param dish The food that is on this dish.
   * @param tableName The name of the table that this dish is served to.
   * @param customerNum The seat number of the customer at the table.
   */
  public Dish(MenuItem dish, String tableName, int customerNum) {

    super(dish.getName(), dish.getId(), dish.getPrice(), dish.getIngredients());
    this.tableName = tableName;
    this.customerNum = customerNum;
    this.comment = "";
    this.setDishStatus(DishStatus.ORDERED);
  }


  /**
   * Adds a comment to the dish for the Cook. The server will add specs in the comment that the
   * customer requests.
   *
   * @param comment The Comment.
   */
  public void addComment(String comment) {
    this.comment = comment;
  }

  /**
   * Gets the customer number for this dish.
   *
   * @return int
   */
  public int getCustomerNum() {
    return customerNum;
  }

  /**
   * Returns the string representation of the Bill.
   *
   * @return String
   */
  public String toString() {
    String billText = "";
    billText += "Table: " + this.tableName + ", ";
    billText += "CustomerNumber: " + this.customerNum + ", ";
    billText += getStringForBill();
    return billText;
  }

  /**
   * Creates a text representation of the Dish for creating the Bill.
   *
   * @return String
   */
  public String getStringForBill() {
    String billText = "";
    billText += this.getName();
    if (!this.getComplementsString().equals("")) {
      billText += " - " + this.getComplementsString();
    }
    billText += " - $" + doubleToCurrency(this.getPrice());
    return billText;
  }

  /**
   * Returns a string with each ingredient and the amount.
   */
  public String getIngredientString() {
    String ingredientOutput = "";
    for (String key : this.getIngredients().keySet()) {
      ingredientOutput +=
          key + ": " + this.getIngredients().get(key).getAmount() + System.lineSeparator();
    }
    return ingredientOutput;
  }

  /**
   * Returns a string with all the complements.
   */
  public String getComplementsString() {
    String extras = "";
    String subtractions = "";
    HashMap<String, Integer> differenceMap = this.getDifferenceAmounts();

    for (String key : differenceMap.keySet()) {
      if (differenceMap.get(key) > 0) {
        extras +=
            "+" + differenceMap.get(key) + " " + this.getIngredients().get(key).getName() + ", ";
      } else if (differenceMap.get(key) < 0) {
        subtractions +=
            differenceMap.get(key) + " " + this.getIngredients().get(key).getName() + ", ";
      }
    }
    String complements = "";
    complements += extras;
    complements += subtractions;
    if (complements.length() == 0) {
      return "";
    } else {
      return complements.substring(0, complements.length() - 2);
    }
  }

  /**
   * Getter for tableName.
   *
   * @return String tableName.
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Creates a copy of this dish with the table id.
   *
   * @param tableName the table that the dish is for
   * @param customerNumber the customer that ordered the dish
   * @return the dish that has been ordered
   */
  public static Dish getDishFromMenuItem(MenuItem menu, String tableName, int customerNumber) {
    MenuItem newDish = menu.clone();
    Dish dish = new Dish(newDish, tableName, customerNumber);
    return dish;
  }

  /**
   * Gets the comment attached to the dish.
   *
   * @return the comment.
   */
  public String getComment() {
    return comment;
  }

  /**
   * Sets the comment attached to the dish.
   *
   * @param comment comment about the dish.
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * Sets the dish ingredients to back to baseAmounts.
   */
  public void setToBaseIngredients() {
    HashMap<String, DishIngredient> ingredients = this.getIngredients();
    for (String key : ingredients.keySet()) {
      DishIngredient ingredient = ingredients.get(key);
      ingredients.get(key).setAmount(ingredient.getBaseAmount());
    }
  }

  /**
   * Adds necessary 0's to the number. Ex. "3.0" turns into "3.00"
   */
  private static String doubleToCurrency(double number) {
    DecimalFormat decim = new DecimalFormat("0.00");
    return decim.format(number);
  }

  public DishStatus getDishStatus() {
    return dishStatus;
  }

  public void setDishStatus(DishStatus dishStatus) {
    this.dishStatus = dishStatus;
  }

  public Dish clone() {
    MenuItem menuItem = new MenuItem(this.name, this.id, this.price, this.cloneIngredients());
    return new Dish(menuItem, this.tableName, this.customerNum);
  }


}
