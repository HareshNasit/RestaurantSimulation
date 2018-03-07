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
        switch (line[0]) {
          case "Server":
            readServerAction(line);
            break;
          case "Cook":
            readCookAction(line);
            break;
          case "Manager":
            readManagerAction(line);
            break;
        }
      }
      events.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void readServerAction(String[] input) {
  }

  private void readCookAction(String[] input) {
  }

  private void readManagerAction(String[] input) {
  }

}
