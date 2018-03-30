package Restaurant;


import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * A Manager manages the overall restaurant and gets the ingredients if there
 * is a shortage.
 */
public class Manager implements IWorker, Notifiable {

  private String name;
  private WorkerType type = WorkerType.MANAGER;
  private ArrayList<IWorker> workers;
  private ModelControllerInterface screen;

  public Manager(String name) {
    this.name = name;
    workers = new ArrayList<IWorker>();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public WorkerType getType() {
    return type;
  }

  public void addWorker(IWorker worker){
    workers.add(worker);
    screen.updateScreen();
  }

  public ArrayList<IWorker> getWorkers(){
    return workers;
  }

  /** Calls a server or a cook to collect the received ingredients. */
  public IWorker callWorker(IWorker worker) {
    //TODO: WORKER FUNCTION
    return worker;
  }

  /** The server receives and adds ingredients to the inventory. */
  public void scanStock(Inventory inventory, String ingredient, int amount) {
    inventory.addStock(ingredient, amount);
  }

  @Override
  public void sendNotification(String message) {
    screen.openNotification(message);
  }

  /** The Manager shuts the system and updates the inventory.txt file. */
  public void shutDown(Inventory inventory) {
    System.out.println("System Shutdown: Writing inventory to file");
    inventory.writeToInventory();
  }

  /**
   * Starts the restaurant system for the day.
   * @param restaurant The restaurant that is being started
   */
  public void startSystem(Restaurant restaurant){
    restaurant.startSystem();
  }

  /**
   * Shuts down the restaurant system for the day.
   * @param restaurant The restaurant that is being shut down
   */
  public void shutDownSystem(Restaurant restaurant){
    restaurant.shutDownSystem();
  }


  public ModelControllerInterface getScreen() {
    return screen;
  }

  public void setScreen(ModelControllerInterface screen) {
    this.screen = screen;
  }

  @Override
  public void sendNotifications(String message) {
    screen.openNotification(message);
  }

  @Override
  public void update() {
    screen.updateScreen();
  }
}
