package Restaurant;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Bill {

    public static double tax = .13;

    /**
     * Creates a bill from a given set of orders. This bill will be used for creating a full bill
     * where one person pays.
     *
     * @param orders The dishes from the table.
     */
    public static String outputBill(ArrayList<Dish> orders) {

        String billText = "";

        for (Dish order : orders) {
            billText += order.getStringForBill() + System.lineSeparator();
        }

        double subtotal = getSubtotal(orders);
        double tax = getTax(subtotal);
        billText += "SubTotal: $" + subtotal  + System.lineSeparator();
        billText += "Tax: $" + tax  + System.lineSeparator();

        BigDecimal bd = new BigDecimal(subtotal);
        BigDecimal bigTax = new BigDecimal(tax);
        bd = bd.add(bigTax);
        bd = bd.setScale(2, RoundingMode.CEILING);
        double total = bd.doubleValue();


        billText += "Total: $" + total  + System.lineSeparator();
        return billText;
    }

    /**
     * Creates a bill for one customer at a table.
     *
     * @param table       The table the customer is seated at.
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
        double tax = getTax(subtotal);
        BigDecimal bd = new BigDecimal(subtotal);
        BigDecimal bigTax = new BigDecimal(tax);
        bd = bd.add(bigTax);
        bd = bd.setScale(2, RoundingMode.CEILING);
        double total = bd.doubleValue();

        billText += "SubTotal: $" + subtotal + System.lineSeparator();
        billText += "Tax: $" + tax + System.lineSeparator();
        billText += "Total: $" + total  + System.lineSeparator();
        return billText;
    }

    public static double getSubtotal(ArrayList<Dish> dishes) {
        double subtotal = 0;
        for (Dish order : dishes) {
            subtotal += order.getPrice();
        }
        return subtotal;
    }

    public static double getTax(double subtotal) {
        BigDecimal bd = new BigDecimal(subtotal);
        BigDecimal multiplier = new BigDecimal(tax);
        bd = bd.multiply(multiplier);
        bd = bd.setScale(2, RoundingMode.CEILING);

        return bd.doubleValue();
    }


    public static String outSinglePaymentBill(Table table, int customerNum, Boolean isPercent, double tip){
        String billText = "";
        billText += "Table: " + table.getTableID() + ", ";
        billText += "CustomerNumber: " + customerNum + ", ";
        for (Dish order : table.getCustomerOrder(customerNum)) {
            billText += order.getStringForBill() + System.lineSeparator();
        }

        double subtotal = getSubtotal(table.getCustomerOrder(customerNum));
        double tax = getTax(subtotal);
        BigDecimal bd = new BigDecimal(subtotal);
        BigDecimal bigTax = new BigDecimal(tax);
        bd = bd.add(bigTax);
        bd = bd.setScale(2, RoundingMode.CEILING);
        double total = bd.doubleValue();

        billText += "SubTotal: $" + subtotal + System.lineSeparator();
        billText += "Tax: $" + tax + System.lineSeparator();
        billText += "Total: $" + total  + System.lineSeparator();
        return billText;
    }

    public static String outputFullTablePaymentBill(ArrayList<Dish> orders, Boolean isPercent, double tip){
        String billText = "";

        for (Dish order : orders) {
            billText += order.getStringForBill() + System.lineSeparator();
        }

        double subtotal = getSubtotal(orders);
        double tax = getTax(subtotal);
        billText += "SubTotal: $" + subtotal  + System.lineSeparator();
        billText += "Tax: $" + tax  + System.lineSeparator();

        BigDecimal bd = new BigDecimal(subtotal);
        BigDecimal bigTax = new BigDecimal(tax);
        bd = bd.add(bigTax);
        bd = bd.setScale(2, RoundingMode.CEILING);
        double total = bd.doubleValue();


        billText += "Total: $" + total  + System.lineSeparator();
        return billText;
    }

}
