package Restaurant;

import logging.RestaurantLogger;
import logging.SimpleLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/** This is the Restaurant class. This is where all the moving parts move around. */
public class Restaurant {

  private Inventory inventory; // The inventory of the restaurant.
  private Menu menu; // The menu of the restaurant.
  private HashMap<String, Table> tables; // HashMap of table name and the table.
  private Manager manager; // The manager of the restaurant.
  private ServingTable servingTable; // The serving table of this restaurant.
  private final String TABLEFILE = "tables.txt";
  private final String WORKERFILE = "workers.txt";
  private String RECEIPTFILE;
  private boolean isActive;
  private ArrayList<IWorker> workers; // All the workers in this restaurant.
  private ArrayList<Notifiable> servers;
  private ArrayList<Notifiable> cooks;
  private ArrayList<Notifiable> managers;

  public SimpleLogger receiptsLogger = new SimpleLogger("");
  public RestaurantLogger restaurantLogger = new RestaurantLogger("");

  public HashMap<String, Table> getTables() { return tables; }

  public void addManager(Manager manager){
    managers.add(manager);

  }

  public void notifyWorker(WorkerType workerType,String message){

    if (workerType == WorkerType.SERVER){
      for(Notifiable server: servers){
        server.sendNotifications(message);
      }
    } else if (workerType == WorkerType.COOK) {
      for(Notifiable cook: cooks){
        cook.sendNotifications(message);
      }
    } else if (workerType == WorkerType.MANAGER){
      for(Notifiable manager: managers){
        manager.sendNotifications(message);
      }
    }

  }


  public void updateWorker(WorkerType workerType){

    if (workerType == WorkerType.SERVER){
      for(Notifiable server: servers){
        server.update();
      }
    } else if (workerType == WorkerType.COOK) {
      for(Notifiable cook: cooks){
        cook.update();
      }
    } else if (workerType == WorkerType.MANAGER){
      for(Notifiable manager: managers){
        manager.update();
      }
    }

  }
  public void addServer(Server server){
    servers.add(server);
    getWorkers().add(server);
    updateWorker(WorkerType.MANAGER);

  }
  public void addCook(Cook cook){
    cooks.add(cook);
    getWorkers().add(cook);
    updateWorker(WorkerType.MANAGER);
  }

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
    this.servingTable = servingTable;
    this.servers = new ArrayList<Notifiable>();
    this.cooks = new ArrayList<Notifiable>();
    this.workers = new ArrayList<IWorker>();
    this.managers = new ArrayList<Notifiable>();
    generateTables(TABLEFILE);
    setActive(false);
    startSystem();
  }
    /** Start the Restaurant system.
     */
  public void startSystem(){
    setActive(true);
    createNewReceiptFile();
    createNewLogFile();
  }
    /** Shutdown the Restaurant system.
     */
  public void shutDownSystem(){
    setActive(false);
    inventory.writeToInventory();
    System.exit(0);
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
    for (IWorker worker : this.getWorkers()) {
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

  public void createNewReceiptFile(){
    this.receiptsLogger.createAndSetNewLoggerFile("receipts", "receipt");
    System.out.println(this.receiptsLogger.getFilePath() + "2");
  }
  public void createNewLogFile(){
    this.restaurantLogger.createAndSetNewLoggerFile("logger", "log");
  }

  public RestaurantLogger getRestaurantLogger() {
    return restaurantLogger;
  }

  public void writeToRECEIPTFILE(String content){
    this.receiptsLogger.writeToLogger(content);
  }

  public SimpleLogger getReceiptsLogger() {
    return receiptsLogger;
  }

  public ArrayList<IWorker> getWorkers() {
    return workers;
  }

  public void setWorkers(ArrayList<IWorker> workers) {
    this.workers = workers;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
}
