import java.util.ArrayList;

public class Bill {

  public static void outputBill(ArrayList<Dish> orders) {
      String billText = "";
      for(Dish order: orders){
          billText += order.getStringForBill() + System.lineSeparator();
      }
      System.out.println(billText);
  }

}
