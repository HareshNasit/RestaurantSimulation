package Restaurant;

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
            billText += order.toString() + System.lineSeparator();
        }

        double subtotal = getSubtotal(orders);
        double tax = getTax(subtotal);
        double total = subtotal + tax;
        billText += "SubTotal: $" + subtotal;
        billText += "Tax: $" + tax;
        billText += "Total: $" + total;
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
        double total = subtotal + tax;
        billText += "SubTotal: $" + subtotal;
        billText += "Tax: $" + tax;
        billText += "Total: $" + total;
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
        bd.multiply(multiplier);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
