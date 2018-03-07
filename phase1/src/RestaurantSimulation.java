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

      System.out.println(String.format("%s: serves order", server.getName()));
      server.serveDish(Integer.parseInt(input[3]), restaurant.getServingTable());

    }
  }

  private static void readCookAction(String[] input, Restaurant restaurant) {
      Cook cook = restaurant.getCook(input[1]);
        Dish order = (Dish)restaurant.getServingTable().getNextDishToBeCooked();
    if(input[2].equals("read")){
        if(cook.prepareDish(order,restaurant.getInventory(),restaurant.getServingTable())){
            System.out.println("Dish read and ready to be cooked.");
        }
        else{
            System.out.println("Dish rejected");
        }
    }
    else if(input[2].equals("cooked")){
        cook.dishReady(order,restaurant.getServingTable());
    }
  }

  private static void readManagerAction(String[] input, Restaurant restaurant) {
  }

}
