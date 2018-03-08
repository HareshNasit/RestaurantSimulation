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

  private static void readEvents(String fileName, Restaurant restaurant) {
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

      server.seatCustomer(input[3], restaurant);

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
            order.subtractIngredient(ingredientName, Math.abs(change));
          }
        }

      } catch (ArrayIndexOutOfBoundsException e) {
        //If No Extras are added or removed
      }

      server.addOrder(restaurant.getTable(tableID), order, restaurant.getServingTable());

    } else if (input[2].equals("passOrder")) {

      server.passOrder(restaurant.getTable(input[3]), restaurant.getServingTable());

    } else if (input[2].equals("serve")) {

      server.serveDish(Integer.valueOf(input[3]), restaurant);

    } else if (input[2].equals("reject")) {

      server.rejectDish(Integer.valueOf(input[3]), restaurant);

    } else if (input[2].equals("return")) {

      server.returnDish(Integer.valueOf(input[4]), input[3], restaurant, input[5]);

    } else if (input[2].equals("bill")) {
      System.out
          .println(String.format(System.lineSeparator() + "Table %s requested bill:", input[3]));
      if (input[4].equals("single")) {
        System.out.println(("Printing bill"));
        server.generateSingleBill(restaurant.getTable(input[3]), 3);

      } else if (input[4].equals("split")) {
        System.out.println(("Printing split bill"));
        server.generateTableBill(restaurant.getTable(input[3]));

      }
      System.out.println(
          System.lineSeparator() + "Customers have paid. Table has been cleared for new customers");
    }
  }

  private static void readCookAction(String[] input, Restaurant restaurant) {

    Cook cook = restaurant.getCook(input[1]);

    if (input[2].equals("accept")) {

      cook.acceptCook(Integer.valueOf(input[3]), restaurant.getServingTable(),
          restaurant.getInventory());

    } else if (input[2].equals("transfer")) {

      cook.acceptNoCook(Integer.valueOf(input[3]), restaurant.getServingTable());

    } else if (input[2].equals("reject")) {

      cook.rejectDish(Integer.valueOf(input[3]), restaurant.getServingTable());

    } else if (input[2].equals("serve")) {

      cook.serveDish(Integer.valueOf(input[3]), restaurant.getServingTable());

    } else if (input[2].equals("check")) {

      Dish dish = restaurant.getServingTable().getDishesToBeCooked().get(Integer.valueOf(input[3]));
      if (!cook.canBePrepared(dish, restaurant.getInventory())) {
        System.out.println(String.format("Table%s%d %s cannot be prepared", dish.getTableName(),
            dish.getCustomerNum(), dish.getName()));
      } else {
        System.out.println(String.format("Table%s%d %s can be prepared", dish.getTableName(),
            dish.getCustomerNum(), dish.getName()));
      }


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
        manager.shutDown(restaurant.getInventory());
    }
  }
}
