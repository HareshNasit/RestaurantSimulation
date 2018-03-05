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
  private ArrayList<Table> tables;

  public Restaurant(Menu menu,Inventory inventory) {
    this.inventory = inventory;
    this.menu = menu;
    workers = new ArrayList<>();
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
}
