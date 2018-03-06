import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This is the Restaurant class. This is where all the moving parts move around.
 */
public class Restaurant {

  private Inventory inventory; // The inventory of the restaurant.
  private Menu menu; // The menu of the restaurant.
  private ArrayList<IWorker> workers; // All the workers in this restaurant.
  private HashMap<String, Server> servers;
  private HashMap<String, Cook> cooks;
  private ArrayList<Table> tables;
  private ServingTable servingTable;

  public Restaurant(Menu menu, Inventory inventory, ServingTable servingTable) {
    this.inventory = inventory;
    this.menu = menu;
    tables = generateTables("tables.txt");
    this.servingTable = servingTable;
    this.generateWorkers("Workers.txt", this.servingTable);
  }

  /**
   * Adds the worker to the list of workers.
   * @param worker The worker.
   */
  public void addWorker(IWorker worker){
    workers.add(worker);
  }

  /**
   * returns the list of workers.
   * @return workers.
   */
  public  ArrayList<IWorker> getWorkers() {
    return workers;
  }

  /**
   * Returns a new list of tables from fileName Format: TableID,tableSize
   */
  private ArrayList<Table> generateTables(String fileName) {

    File file = new File(fileName);
    ArrayList<Table> tables = new ArrayList<Table>();
    try {
      Scanner line = new Scanner(file);

      while (line.hasNextLine()) {
        String tableLine = line.nextLine();
        String tableID = tableLine.split(",")[0].trim();
        int tableSize = Integer.valueOf(tableLine.split(",")[1]);

        Table table = new Table(tableID, tableSize);
        tables.add(table);
      }
      line.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return tables;

  }

  private void generateWorkers(String fileName, ServingTable servingTable) {
    this.workers = new ArrayList<IWorker>();
    this.servers = new HashMap<String, Server>();
    this.cooks = new HashMap<String, Cook>();
    try {
      Scanner line = new Scanner(new File(fileName));
      while (line.hasNextLine()) {

        String tableLine = line.nextLine();
        String[] splitString = tableLine.split("\\|");

        if (splitString[0].equals("Server")) {

          Server server = new Server(splitString[1]);
          servingTable.addServer(server);
          this.workers.add(server);
          this.servers.put(server.getName(), server);

        } else if (splitString[0].equals("Manager")) {

          Manager manager = new Manager(splitString[1]);
          this.workers.add(manager);

        } else if (splitString[0].equals("Cook")) {

          Cook cook = new Cook(splitString[1]);
          servingTable.addCook(cook);
          this.workers.add(cook);
          this.cooks.put(cook.getName(), cook);

        }
      }
      line.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
