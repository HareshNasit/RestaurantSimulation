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

    String fileName = "Workers.txt";
      File file = new File(fileName);
      ArrayList<IWorker> workers = new ArrayList<>();

    ArrayList<ServingTableListener> servers = new ArrayList<>();
    ArrayList<ServingTableListener> cooks = new ArrayList<>();
      try {
          Scanner line = new Scanner(file);
          while (line.hasNextLine()) {
              String tableLine = line.nextLine();
              String[] splitString = tableLine.split("\\|");
              if (splitString[0].equals("Server")){
                Server server = new Server(splitString[1]);
                servers.add(server);
              } else if (splitString[0].equals("Manager")) {
                Manager manager = new Manager(splitString[1]);
              } else if (splitString[0].equals("Cook")) {
                Cook cook = new Cook(splitString[1], servingTable);
                cooks.add(cook);


              }



          }
          line.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
  }
}
