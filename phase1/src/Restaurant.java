import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the Restaurant class. This is where all the moving parts move around.
 */
public class Restaurant {

  private Inventory inventory; // The inventory of the restaurant.
  private Menu menu; // The menu of the restaurant.
  private ArrayList<IWorker> workers; // All the workers in this restaurant.
  private ArrayList<ServingTableListener> servers;
  private ArrayList<ServingTableListener> cooks;
  private ArrayList<Table> tables;
  private ServingTable servingTable;

  public Restaurant(Menu menu, Inventory inventory, ServingTable servingTable) {
    this.inventory = inventory;
    this.menu = menu;
    tables = generateTables("tables.txt");
    this.servingTable = servingTable;
    ArrayList<ArrayList> sumWorkers = generateWorkers("Workers.txt");
    workers = sumWorkers.get(0);
    servers = sumWorkers.get(1);
    cooks = sumWorkers.get(2);
    servingTable.setServers(servers);
    servingTable.setCooks(cooks);
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

  private ArrayList<ArrayList> generateWorkers(String fileName) {
    ArrayList<IWorker> workers = new ArrayList<IWorker>();
    ArrayList<ServingTableListener> servers = new ArrayList<ServingTableListener>();
    ArrayList<ServingTableListener> cooks = new ArrayList<ServingTableListener>();
    ArrayList<ArrayList> output = new ArrayList<ArrayList>();
    try {
      Scanner line = new Scanner(new File(fileName));
      while (line.hasNextLine()) {
        String tableLine = line.nextLine();
        String[] splitString = tableLine.split("\\|");
        if (splitString[0].equals("Server")) {
          Server server = new Server(splitString[1]);
          servers.add(server);
          workers.add(server);
        } else if (splitString[0].equals("Manager")) {
          Manager manager = new Manager(splitString[1]);
          workers.add(manager);
        } else if (splitString[0].equals("Cook")) {
          Cook cook = new Cook(splitString[1], servingTable);
          cooks.add(cook);
          workers.add(cook);
        }
      }
      line.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    output.add(workers);
    output.add(servers);
    output.add(cooks);
    return output;
  }
}
