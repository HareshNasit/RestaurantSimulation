import java.util.ArrayList;

/**
 * This is the Restaurant class. This is where all the moving parts move around.
 */
public class Restaurant {

  private Inventory inventory; // The inventory of the restaurant.
  private Menu menu; // The menu of the restaurant.
  private ArrayList<IWorker> workers; // All the workers in this restaurant.
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
}
