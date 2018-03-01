import java.util.ArrayList;

/**
 * This is the Restaurant class. This is where all the moving parts move around.
 */
public class Restaurant {

  private Inventory inventory;
  private Menu menu;
  private static ArrayList<IWorker> workers;
  public Restaurant() {
    this.inventory = new Inventory();
    this.menu = new Menu();
    workers = new ArrayList<>();
  }
  public void addWorker(IWorker worker){
      workers.add(worker);
  }

    public  ArrayList<IWorker> getWorkers() {
        return workers;
    }
}
