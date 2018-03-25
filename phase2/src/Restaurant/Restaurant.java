package Restaurant;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/** This is the Restaurant class. This is where all the moving parts move around. */
public class Restaurant {

  private Inventory inventory; // The inventory of the restaurant.
  private Menu menu; // The menu of the restaurant.
  private ArrayList<IWorker> workers; // All the workers in this restaurant.
  private HashMap<String, Server> servers; // HashMap of servers name and the server.
  private HashMap<String, Cook> cooks; // HashMap of cooks name and the cook.

  public HashMap<String, Table> getTables() {
    return tables;
  }

  public void setTables(HashMap<String, Table> tables) {
    this.tables = tables;
  }

  private HashMap<String, Table> tables; // HashMap of table name and the table.
  private Manager manager; // The manager of the restaurant.
  private ServingTable servingTable; // The serving table of this restaurant.
  private final String TABLEFILE = "tables.txt";
  private final String WORKERFILE = "workers.txt";
  private String RECEIPTFILE;

  /**
   * Generates a new restaurant with menu, inventory, and serving table
   *
   * @param menu menu of restaurant
   * @param inventory inventory of restaurant
   * @param servingTable serving table of restaurant
   */
  public Restaurant(Menu menu, Inventory inventory, ServingTable servingTable) {
    this.inventory = inventory;
    this.menu = menu;
    generateTables(TABLEFILE);
    this.servingTable = servingTable;
    this.generateWorkers(WORKERFILE, this.servingTable);
  }

  /**
   * Returns a server with the given name.
   *
   * @param name of the server.
   * @return Server.
   */
  public Server getServer(String name) {
    return servers.get(name);
  }

  /**
   * Returns a cook with the given name.
   *
   * @param name of the cook.
   * @return Cook.
   */
  public Cook getCook(String name) {
    return cooks.get(name);
  }

  /**
   * Returns the table with the given id.
   *
   * @param id of the Table.
   * @return Table.
   */
  public Table getTable(String id) {
    return tables.get(id);
  }

  /**
   * Returns the menu.
   *
   * @return Menu.
   */
  public Menu getMenu() {
    return menu;
  }

  /**
   * Returns the Inventory.
   *
   * @return Inventory.
   */
  public Inventory getInventory() {
    return inventory;
  }

  /**
   * Returns a HashMap of workers name as the key and IWorker as value.
   *
   * @return HashMap<String , IWorker>
   */
  public HashMap<String, IWorker> MapOfWorkers() {
    HashMap<String, IWorker> workersMap = new HashMap<>();
    for (IWorker worker : this.workers) {
      workersMap.put(worker.getName(), worker);
    }
    return workersMap;
  }

  /** generates a new list of tables from fileName Format: TableID,tableSize */
  private void generateTables(String fileName) {

    File file = new File(fileName);
    tables = new HashMap<>();
    try {
      Scanner line = new Scanner(file);

      while (line.hasNextLine()) {
        String tableLine = line.nextLine();
        String tableID = tableLine.split(",")[0].trim();

        Table table = new Table(tableID);
        tables.put(tableID, table);
      }
      line.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generates workers by taking input from a .txt file
   *
   * @param fileName the file that has the info of the workers
   * @param servingTable the serving table that they belong to
   */
  private void generateWorkers(String fileName, ServingTable servingTable) {
    this.workers = new ArrayList<>();
    this.servers = new HashMap<>();
    this.cooks = new HashMap<>();
    try {
      Scanner line = new Scanner(new File(fileName));
      while (line.hasNextLine()) {

        String tableLine = line.nextLine();
        String[] splitString = tableLine.split("\\|");

        if (splitString[0].equals("Server")) {

          Server server = new Server(splitString[1].trim());
          servingTable.addServer(server);
          this.workers.add(server);
          this.servers.put(server.getName(), server);

        } else if (splitString[0].equals("Manager")) {
          Manager manager = new Manager(splitString[1]);
          this.workers.add(manager);
          inventory.setManager(manager);
          this.manager = manager;

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

  /**
   * Returns Manager with the given name.
   *
   * @param managerName name of the Manager.
   */
  public void setManager(String managerName) {
    this.manager = new Manager(managerName);
  }

  /**
   * Returns the manager.
   *
   * @return Manager.
   */
  public Manager getManager() {
    return manager;
  }

  /** Returns the serving table of the restaurant */
  public ServingTable getServingTable() {
    return servingTable;
  }

  /**
   * Sets the receipt storage file name
   *
   * @param fileName
   */
  public void setRECEIPTFILE(String fileName) {
    this.RECEIPTFILE = fileName;
  }

  /** Creates the receipt txt that keeps track of all the payments/bills. */
  public void createNewReceiptFile() {
    String pattern = "dd-MM-yyyy";
    String dateInString = new SimpleDateFormat(pattern).format(new Date());
    String name = "receipts/receipt" + dateInString + ".txt";
    System.out.println(name);
    File file = new File(name);
    try{
        PrintWriter writer = new PrintWriter(name, "UTF-8");

    }catch(IOException e){
        System.out.println("No file");
    }

    setRECEIPTFILE(name);
  }

  public void writeToRECEIPTFILE(String content) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(RECEIPTFILE, true));
      writer.write(content);
      writer.close();
    } catch (IOException e) {

    }
  }


}
