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


  }

  public void readEvents(String fileName) {
    File file = new File(fileName);

    try {
      Scanner events = new Scanner(file);
      while (events.hasNextLine()) {
        String[] line = events.nextLine().split("|");
        if (line[0].equals("Server")) {

        } else if (line[0].equals("Cook")) {

        } else if (line[0].equals("Manager")) {

        }

      }
      events.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void readServerAction(String[] input) {
  }

  public void readCookAction(String[] input) {
  }

  public void readManagerAction(String[] input) {
  }

}
