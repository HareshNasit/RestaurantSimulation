package Restaurant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Bill {

  public static double salesTax = .13;
  public static double autoGratuity = .15;

  /**
   * Creates a bill from a given set of orders. This bill will be used for creating a full bill
   * where one person pays.
   */
  public static String outputBill(Table table) {

    String billText = "";
    billText += "Table: " + table.getTableID() + ", " + System.lineSeparator();
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
    billText += "Table: " + table.getTableID() + System.lineSeparator();
    billText += "CustomerNumber: " + customerNum + System.lineSeparator();
    for (Dish order : table.getCustomerOrder(customerNum)) {
      billText += order.getStringForBill() + System.lineSeparator();
    }
    double subtotal = getSubtotal(table.getCustomerOrder(customerNum));
    billText += billTextHelper(subtotal, table);
    return billText;
  }

  /**
   * String method for the final payment including a $ amount tip.
   */
  public static String finalPaymentSinglePerson(Table table, int customerNum, double tip) {
    String billText = "";
    billText += "Table: " + table.getTableID() + System.lineSeparator();
    billText += "CustomerNumber: " + customerNum + System.lineSeparator();
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
    billText += "Table: " + table.getTableID() + System.lineSeparator();
    ArrayList<Dish> orders = table.getTableOrder();
    for (Dish order : orders) {
      billText += order.getStringForBill() + System.lineSeparator();
    }
    double subtotal = getSubtotal(orders);
    return billText + finalPaymentHelper(subtotal, tip, table);
  }

  private static double get2Decimals(double number) {
    BigDecimal bd = new BigDecimal(number);
    bd = bd.setScale(2, RoundingMode.FLOOR);
    return bd.doubleValue();
  }

  public static double getTotal(double subtotal, double tip, double tax) {
    BigDecimal bd = new BigDecimal(subtotal);
    bd = bd.add(new BigDecimal(tip));
    bd = bd.add(new BigDecimal(tax));
    return get2Decimals(bd.doubleValue());
  }

  public static double getTax(double subtotal) {
    BigDecimal bd = new BigDecimal(subtotal);
    BigDecimal multiplier = new BigDecimal(salesTax);
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

    billText += "SubTotal: $" + doubleToCurrency(subtotal) + System.lineSeparator();
    double tip = 0;
    if (table.getTableSize() >= 8) {
      billText +=
          "Tip: $"
              + doubleToCurrency(get2Decimals(subtotal * autoGratuity))
              + System.lineSeparator();
      tip = get2Decimals(subtotal * autoGratuity);
    }
    double total = getTotal(subtotal, tip, tax);
    billText += "Tax: $" + doubleToCurrency(tax) + System.lineSeparator();
    billText += "Total: $" + doubleToCurrency(total) + System.lineSeparator();
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
    double total = getTotal(subtotal, tipAmount, tax);
    System.out.println(tipAmount + subtotal + tax);
    billText += "SubTotal: $" + doubleToCurrency(subtotal) + System.lineSeparator();
    billText += "Tip: $" + doubleToCurrency(tipAmount) + System.lineSeparator();
    billText += "Tax: $" + doubleToCurrency(tax) + System.lineSeparator();
    billText += "Total: $" + doubleToCurrency(total) + System.lineSeparator();
    return billText;
  }

  /**
   * Adds necessary 0's to the number. Ex. "3.0" turns into "3.00"
   */
  private static String doubleToCurrency(double number) {
    DecimalFormat decim = new DecimalFormat("0.00");
    return decim.format(number);
  }
}
