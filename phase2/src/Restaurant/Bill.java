package Restaurant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Bill {

  public static double salestax = .13;
  public static double autoGratuity = .15;

  /**
   * Creates a bill from a given set of orders. This bill will be used for creating a full bill
   * where one person pays.
   *
   * @param table
   */
  public static String outputBill(Table table) {

    String billText = "";

    for (Dish order : table.getTableOrder()) {
      billText += order.getStringForBill() + System.lineSeparator();
    }

    double subtotal = getSubtotal(table.getTableOrder());
    billText += billTextHelper(subtotal, table);
    return billText;
  }

  /**
   * Creates a bill for one customer at a table.
   *
   * @param table The table the customer is seated at.
   * @param customerNum The customer number.s
   */
  public static String outputSingleBill(Table table, int customerNum) {

    String billText = "";
    billText += "Table: " + table.getTableID() + ", ";
    billText += "CustomerNumber: " + customerNum + ", ";
    for (Dish order : table.getCustomerOrder(customerNum)) {
      billText += order.getStringForBill() + System.lineSeparator();
    }
    double subtotal = getSubtotal(table.getCustomerOrder(customerNum));
    billText += billTextHelper(subtotal, table);
    return billText;
  }

  /**
   * String method for the final payment including a $ amount tip.
   *
   * @param table
   * @param customerNum
   * @param tip
   * @return
   */
  public static String finalPaymentSinglePerson(Table table, int customerNum, double tip) {
    String billText = "";
    billText += "Table: " + table.getTableID() + ", " + System.lineSeparator();
    billText += "CustomerNumber: " + customerNum + ", " + System.lineSeparator();
    for (Dish order : table.getCustomerOrder(customerNum)) {
      billText += order.getStringForBill() + System.lineSeparator();
    }

    double subtotal = getSubtotal(table.getCustomerOrder(customerNum));
    return billText + finalPaymentHelper(subtotal, tip, table);
  }

  /**
   * @param table
   * @param tip
   * @return
   */
  public static String finalPaymentBillTable(Table table, double tip) {
    String billText = "";
    ArrayList<Dish> orders = table.getTableOrder();
    for (Dish order : orders) {
      billText += order.getStringForBill() + System.lineSeparator();
    }
    double subtotal = getSubtotal(orders);
    return billText + finalPaymentHelper(subtotal, tip, table);
  }

  private static double get2Decimals(double number) {
    BigDecimal bd = new BigDecimal(number);
    bd = bd.setScale(2, RoundingMode.CEILING);
    return bd.doubleValue();
  }

  public static double getTotal(double subtotal, double tax) {
    BigDecimal bd = new BigDecimal(subtotal);
    BigDecimal bigTax = new BigDecimal(tax);
    bd = bd.add(bigTax);
    return get2Decimals(bd.doubleValue());
  }

  public static double getTax(double subtotal) {
    BigDecimal bd = new BigDecimal(subtotal);
    BigDecimal multiplier = new BigDecimal(salestax);
    bd = bd.multiply(multiplier);
    return get2Decimals(bd.doubleValue());
  }

  public static double getSubtotal(ArrayList<Dish> dishes) {
    double subtotal = 0;
    for (Dish order : dishes) {
      subtotal += order.getPrice();
    }
    return get2Decimals(subtotal);
  }

  public static double getPercentWithTip(double subtotal, double tip) {
    return Bill.get2Decimals(subtotal * tip / 100);
  }

  private static String billTextHelper(double subtotal, Table table) {
    String billText = "";
    double tax = getTax(subtotal);
    double total = getTotal(subtotal, tax);

    billText += "SubTotal: $" + subtotal + System.lineSeparator();
    if (table.getTableSize() >= 8) {
      billText += "Tip: $" + get2Decimals(subtotal * autoGratuity) + System.lineSeparator();
    }
    billText += "Tax: $" + tax + System.lineSeparator();
    billText += "Total: $" + total + System.lineSeparator();
    return billText;
  }

  private static String finalPaymentHelper(double subtotal, double tip, Table table) {
    String billText = "";
    double tipAm = 0;
    if (table.getTableSize() >= 8) {
      tipAm += subtotal * autoGratuity;
    }
    double tipAmount = get2Decimals(tip + tipAm);
    double tax = getTax(subtotal);
    double total = getTotal(subtotal + tipAmount, tax);
    billText += "SubTotal: $" + subtotal + System.lineSeparator();
    billText += "Tip: $" + tipAmount + System.lineSeparator();
    billText += "Tax: $" + tax + System.lineSeparator();
    billText += "Total: $" + total + System.lineSeparator();
    return billText;
  }
}
