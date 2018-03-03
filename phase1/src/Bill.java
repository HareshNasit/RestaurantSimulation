import java.util.ArrayList;

public class Bill {

  public static void outputBill(ArrayList<Dish> orders) {
    String billText = "";
    for (Dish order : orders) {
      billText += order.toString() + System.lineSeparator();
    }
    System.out.println(billText);
  }

  public static void outputSingleBill(Table table, int customerNum) {
    String billText = "";
    billText += "Table: " + table.getName() + ", ";
    billText += "CustomerNumber: " + customerNum + ", ";
    for (Dish order : table.getCustomerOrder(customerNum)) {
      billText += order.getStringForBill() + System.lineSeparator();
    }
    System.out.println(billText);
  }
}
