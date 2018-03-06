import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantSimulation {

  
  public static void main(String[] args) {

    ServingTable servingTable = new ServingTable();
    Menu menu = new Menu();
    Inventory inventory = new Inventory();
    Restaurant restaurant = new Restaurant(menu, inventory, servingTable);
    RestaurantSimulation.readEvents("events.txt", restaurant);


  }

  public static void readEvents(String fileName, Restaurant restaurant) {
    File file = new File(fileName);
    try {
      Scanner events = new Scanner(file);
      while (events.hasNextLine()) {
        String[] line = events.nextLine().split("\\|");
        if (line[0].equals("Server")) {
          readServerAction(line, restaurant);
        } else if (line[0].equals("Cook")) {
          readCookAction(line, restaurant);
        } else if (line[0].equals("Manager")) {
          readManagerAction(line, restaurant);
        }

      }
      events.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void readServerAction(String[] input, Restaurant restaurant) {
    Server server = restaurant.getServer(input[1]);
    if (input[2].equals("seatCustomer")) {

      restaurant.getTable(input[3]).setOccupied(true);
      System.out.println(String.format("Table %s: seated with %s", input[3], input[4]));

    } else if (input[2].equals("order")) {
      Double menuItemID = Double.parseDouble(input[5]);
      String tableID = input[4];
      int seatNum = Integer.parseInt(input[3]);
      Dish order = restaurant.getMenu().getDish(menuItemID, tableID, seatNum);

      System.out.println("%s takes order from Table%sSeat%i: %s");
      server.addOrder(restaurant.getTable(tableID), order, restaurant.getServingTable());

    } else if (input[2].equals("serve")) {

    }
  }

  private static void readCookAction(String[] input, Restaurant restaurant) {
  }

  private static void readManagerAction(String[] input, Restaurant restaurant) {
  }

}
