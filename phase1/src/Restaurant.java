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
  private HashMap<String, Table> tables;
  private Manager manager;

  public ServingTable getServingTable() {
    return servingTable;
  }

  private ServingTable servingTable;

  public Restaurant(Menu menu, Inventory inventory, ServingTable servingTable) {
    this.inventory = inventory;
    this.menu = menu;
    generateTables("tables.txt");
    this.servingTable = servingTable;
    this.generateWorkers("Workers.txt", this.servingTable);
  }

  public Server getServer(String name) {
    return servers.get(name);
  }

  public Cook getCook(String name) {
    return cooks.get(name);
  }

  public Table getTable(String id) {
    return tables.get(id);
  }

  public Menu getMenu() {
    return menu;
  }

  public Inventory getInventory() {
    return inventory;
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
  private void generateTables(String fileName) {

    File file = new File(fileName);
    tables = new HashMap<String, Table>();
    try {
      Scanner line = new Scanner(file);

      while (line.hasNextLine()) {
        String tableLine = line.nextLine();
        String tableID = tableLine.split(",")[0].trim();
        int tableSize = Integer.valueOf(tableLine.split(",")[1]);

        Table table = new Table(tableID, tableSize);
        tables.put(tableID, table);
      }
      line.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


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
  public void setManager(String managerName){
      this.manager = new Manager(managerName);
  }

    public Manager getManager() {
        return manager;
    }
}
