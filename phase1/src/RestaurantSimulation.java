import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantSimulation {

  public static void main(String[] args) {

    ServingTable servingTable = new ServingTable();
    Menu menu = new Menu();
    Inventory inventory = new Inventory();
    inventory.readInventory();

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
          restaurant.setManager(line[1]);
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
      String tableID = input[3];
      int seatNum = Integer.parseInt(input[4]);
      Dish order = restaurant.getMenu().getDish(menuItemID, tableID, seatNum);

      //Checks for the extras added or removed to order
      try {
        String[] extras = input[6].split("#");
        for (String specifications : extras) {
          String ingredientName = specifications.split(",")[0].toLowerCase().trim();
          int change = Integer.valueOf(specifications.split(",")[1].trim());
          if (change >= 0) {
            order.addIngredient(ingredientName, change);
          } else {
            order.subtractIngredient(ingredientName, change);
          }
        }

      } catch (ArrayIndexOutOfBoundsException e) {
        //If No Extras are added or removed
      }

      System.out.println(
          String.format(
              "%s takes order from Table%sSeat%d: %s",
              server.getName(), tableID, seatNum, order.getStringForBill()));

      server.addOrder(restaurant.getTable(tableID), order, restaurant.getServingTable());

    } else if (input[2].equals("passOrder")) {

      System.out.println(String.format("Sending Table %s's orders to cooks", input[3]));
      server.passOrder(restaurant.getTable(input[3]), restaurant.getServingTable());
      System.out.println();
      System.out.println("ServingTable:");
      System.out.println(restaurant.getServingTable());
      System.out.println();

    } else if (input[2].equals("serve")) {

      System.out.println(String.format("%s: serves order: ", server.getName()));
      server.serveDish(Integer.parseInt(input[3]), restaurant);

    } else if (input[2].equals("bill")) {
      System.out.println(String.format("Table %s requested bill:", input[3]));

      if (input[4].equals("single")) {
        System.out.println(String.format("Printing single bill"));
        server.generateSingleBill(restaurant.getTable(input[3]), 3);
      } else if (input[4].equals("split")) {
        System.out.println(String.format("Printing split bill"));
        server.generateTableBill(restaurant.getTable(input[3]));
      }
        System.out.println();
        System.out.println("Customers have paid. Table has been cleared for new customers");
    }
  }

  private static void readCookAction(String[] input, Restaurant restaurant) {

    Cook cook = restaurant.getCook(input[1]);
    Dish order = (Dish) restaurant.getServingTable().getNextDishToBeCooked();
    if (input[2].equals("read")) {

      if (cook.prepareDish(order, restaurant.getInventory(), restaurant.getServingTable())) {
        System.out.println(String.format("%s: %s read and being cooked.", cook.getName(), order.getName()));
        System.out.println();
      } else {
        System.out.println(String.format("%s rejected.", order.getName()));
      }
    } else if (input[2].equals("cooked")) {

      System.out
          .println(String.format("%s has been cooked", order.getName()));
      cook.dishReady(order, restaurant.getServingTable());
    }
  }

  private static void readManagerAction(String[] input, Restaurant restaurant) {
    Manager manager = restaurant.getManager();
    if(input[2].equals("scan stock")){
        System.out.println(String.format("Ingredient: %s scanned and amount: %s added to inventory", input[3],input[4]));
        IWorker worker = manager.callWorker(restaurant.getWorkers());
        worker.scanStock(restaurant.getInventory(),input[3],Integer.valueOf(input[4]));
      }
      else if(input[2].equals("shutdown")){
        System.out.println("System Shutdown");
        manager.shutDown(restaurant.getInventory());
    }
  }
}
