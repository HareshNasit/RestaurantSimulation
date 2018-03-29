package Restaurant;

import MenuDishes.Dish;
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
   * Returns the final bill that includes tips
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

  /**
   * Returns the number with two decimals
   * @param number the number that is being rounded
   * @return the new number with rounded to two decimal places
   */
  private static double roundToCents(double number) {
    BigDecimal bd = new BigDecimal(number);
    bd = bd.setScale(2, RoundingMode.FLOOR);
    return bd.doubleValue();
  }

  /**
   * Returns the final bill inclusive of tip and tax
   * @param subtotal the final price of all the dishes together
   * @param tip the tip paid by the customer
   * @param tax the tax on the bill
   * @return the final price
   */
  public static double getTotal(double subtotal, double tip, double tax) {
    BigDecimal bd = new BigDecimal(subtotal);
    bd = bd.add(new BigDecimal(tip));
    bd = bd.add(new BigDecimal(tax));
    return roundToCents(bd.doubleValue());
  }

  /**
   * Returns amount of tax for a bill
   * @param subtotal the sum of the dishes' prices'
   * @return the tax depending on the subtotal value
   */
  public static double getTax(double subtotal) {
    BigDecimal bd = new BigDecimal(subtotal);
    BigDecimal multiplier = new BigDecimal(salesTax);
    bd = bd.multiply(multiplier);
    return roundToCents(bd.doubleValue());
  }

  /**
   * Returns the sum of all the dishes'prices'
   * @param dishes the dishes ordered
   * @return the final price of all the dishes together
   */
  public static double getSubtotal(ArrayList<Dish> dishes) {
    double subtotal = 0;
    for (Dish order : dishes) {
      subtotal += order.getPrice();
    }
    return roundToCents(subtotal);
  }

  /**
   * Returns the tip if it is paid as a percentage of the subtotal
   * @param subtotal the final price of all the dishes together
   * @param tip the tip that the customer gave as a percentage of the subtotal
   * @return the tip as a double
   */
  public static double getPercentWithTip(double subtotal, double tip) {
    return Bill.roundToCents(subtotal * tip / 100);
  }

  /**
   * a helper method for the bill which returns the final bill text without the tip usually for the customer to check
   * @param subtotal the final price of all the dishes together
   * @param table the table that is paying the bill
   * @return the final bill text
   */
  private static String billTextHelper(double subtotal, Table table) {
    String billText = "";
    double tax = getTax(subtotal);

    billText += "SubTotal: $" + doubleToCurrency(subtotal) + System.lineSeparator();
    double tip = 0;
    if (table.getTableSize() >= 8) {
      billText +=
          "Tip: $"
              + doubleToCurrency(roundToCents(subtotal * autoGratuity))
              + System.lineSeparator();
      tip = roundToCents(subtotal * autoGratuity);
    }
    double total = getTotal(subtotal, tip, tax);
    billText += "Tax: $" + doubleToCurrency(tax) + System.lineSeparator();
    billText += "Total: $" + doubleToCurrency(total) + System.lineSeparator();
    return billText;
  }

  /**
   * a helper method for the bill which returns the final bill text with the tip which is kept by the restaurant
   * @param subtotal the final price of all the dishes together
   * @param tip the tip entered by the customer
   * @param table the table that is paying the bill
   * @return the final bill text
   */
  private static String finalPaymentHelper(double subtotal, double tip, Table table) {
    String billText = "";
    double tipAm = 0;
    if (table.getTableSize() >= 8) {
      tipAm += subtotal * autoGratuity;
    }
    double tipAmount = roundToCents(tip + tipAm);
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
