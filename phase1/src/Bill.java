import java.util.ArrayList;

/**
 * Bill object prints to screen the total price of orders
 */
public class Bill {

  /**
   * Creates a bill from a given set of orders. This bill will be used for creating a full bill
   * where one person pays.
   *
   * @param orders The dishes from the table.
   */
  public static void outputBill(ArrayList<Dish> orders) {

    String billText = "";
    double subtotal = 0;
    for (Dish order : orders) {
      subtotal += order.getPrice();
      billText += order.toString() + System.lineSeparator();
    }
    billText += "Total: " + subtotal;

    System.out.println(billText);
  }

  /**
   * Creates a bill for one customer at a table.
   *
   * @param table The table the customer is seated at.
   * @param customerNum The customer number.s
   */
  public static void outputSingleBill(Table table, int customerNum) {

    String billText = "";
    billText += "Table: " + table.getTableName() + ", ";
    billText += "CustomerNumber: " + customerNum + ", ";
    double subtotal = 0;
    for (Dish order : table.getCustomerOrder(customerNum)) {
      billText += order.getStringForBill() + System.lineSeparator();
      subtotal += order.getPrice();
    }
    billText += "Total: $" + subtotal;
    System.out.println(billText);
  }
}
