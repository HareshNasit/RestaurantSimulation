import java.util.ArrayList;

public class Bill {

  public static void outputBill(ArrayList<Dish> orders) {
    String billText = "";
    int subtotal = 0;
    for (Dish order : orders) {
      subtotal += order.getPrice();
      billText += order.toString() + System.lineSeparator();
    }
    billText += "Total: " + subtotal;

    System.out.println(billText);
  }

  public static void outputSingleBill(Table table, int customerNum) {
    String billText = "";
    billText += "Table: " + table.getName() + ", ";
    billText += "CustomerNumber: " + customerNum + ", ";
    int subtotal = 0;
    for (Dish order : table.getCustomerOrder(customerNum)) {
      billText += order.getStringForBill() + System.lineSeparator();
      subtotal += order.getPrice();
    }
    billText += "Total: $" + subtotal;
    System.out.println(billText);
  }
}
