package Restaurant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Bill {

    public static double salestax = .13;

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
        billText += billTextHelper(subtotal);
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
        billText += billTextHelper(subtotal);
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
    public static String finalPaymentSingleTip(Table table, int customerNum, double tip) {
        String billText = "";
        billText += "Table: " + table.getTableID() + ", ";
        billText += "CustomerNumber: " + customerNum + ", ";
        for (Dish order : table.getCustomerOrder(customerNum)) {
            billText += order.getStringForBill() + System.lineSeparator();
        }
        double tipAmount = get2Decimals(tip);
        double subtotal = getSubtotal(table.getCustomerOrder(customerNum));
        double tax = getTax(subtotal);
        double total = getTotal(subtotal + tip, tax);
        billText += "SubTotal: $" + subtotal + System.lineSeparator();
        billText += "Tip: $" + tipAmount + System.lineSeparator();
        billText += "Tax: $" + tax + System.lineSeparator();
        billText += "Total: $" + total + System.lineSeparator();
        return billText;
    }

    /**
     * @param orders
     * @param tip
     * @return
     */
    public static String finalPaymentBillPercent(ArrayList<Dish> orders, double tip) {
        String billText = "";
        for (Dish order : orders) {
            billText += order.getStringForBill() + System.lineSeparator();
        }
        double subtotal = getSubtotal(orders);
        double tax = getTax(subtotal);
        double tipAmount = get2Decimals(subtotal * (1 + tip / 100));
        double total = getTotal(subtotal + tipAmount, tax);
        billText += "SubTotal: $" + subtotal + System.lineSeparator();
        billText += "Tax: $" + tax + System.lineSeparator();
        billText += "Tip: $" + tipAmount + System.lineSeparator();
        billText += "Total: $" + total + System.lineSeparator();
        return billText;
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

    private static String billTextHelper(double subtotal) {
        String billText = "";
        double tax = getTax(subtotal);
        double total = getTotal(subtotal, tax);

        billText += "SubTotal: $" + subtotal + System.lineSeparator();
        billText += "Tax: $" + tax + System.lineSeparator();
        billText += "Total: $" + total + System.lineSeparator();
        return billText;
    }


}
