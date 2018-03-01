/**
 * This is the Restaurant class. This is where all the moving parts move around.
 */
public class Restaurant {

  private Inventory inventory;
  private Menu menu;

  public Restaurant() {
    this.inventory = new Inventory();
    this.menu = new Menu();
  }

}
