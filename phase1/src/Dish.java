import java.util.HashMap;

/**
 * A dish inherits the properties of a menuitem but is tailored to a table
 */
public class Dish extends MenuItem {

  private String tableName;
  private int customerNum;
  private String comment;

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

    billText += "MenuItem Name: " + this.getName() + ", ";
    billText += "ID: " + this.getId() + ", ";
    billText += "Price $" + this.getPrice() + ", ";
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
    billText += extras + subtractions;
    billText = billText.substring(0, billText.length() - 2);

    return billText;
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
}
