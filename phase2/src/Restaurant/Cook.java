package Restaurant;

/*
 * A cook reads the order being taken by the server and confirms with
 * the server if the dish can be prepared and prepares the dish.
 */
public class Cook implements IWorker, Notifiable {

  private String name; // Name of the cook.
  private ModelControllerInterface screen;

  public Cook(String name) {
    this.name = name;
  }

  /**
   * Getter for the name of the cook.
   *
   * @return String.
   */
  public String getName() {
    return name;
  }

  @Override
  public String getType() {
    return "Cook";
  }

  /**
   * Checks if the dish can be prepared and subtracts ingredients accordingly and returns a boolean
   * and adds it to the list of dishes being cooked currently.
   *
   * @param dish The MenuItem that is to be added to dishesInMaking.
   */
  private void prepareDish(Dish dish, Inventory inventory) {
    if (canBePrepared(dish, inventory)) {
      for (String ingredient : dish.getIngredients().keySet()) {
        inventory.removeStock(ingredient, dish.getIngredientAmounts().get(ingredient));
      }
    } else {
      System.out.println(dish.getName() + " cannot be prepared");
    }
  }

  /**
   * The server receives and adds ingredients to the inventory from the distributor when called by
   * the manager.
   */
  public void scanStock(Inventory inventory, String ingredient, int amount) {
    screen.openReceiverFunction(inventory, ingredient,amount );
  }

  @Override
  public void sendNotification(String message) {
    screen.openNotification(message);
  }

  /**
   * Returns a boolean whether a dish can be prepared or no.
   *
   * @param dish the dish which is to be cooked.
   * @return boolean whether a dish can be prepared or no.
   */
  public boolean canBePrepared(Dish dish, Inventory inventory) {
    return inventory.hasEnoughIngredients(dish.getIngredientAmounts());
  }

  /** Notify the cook that dish has been served. */
  public void sendNotifications(String message) {
    screen.updateScreen();
    screen.openNotification(message);
  }

  @Override
  public void update() {
    screen.updateScreen();
  }

  /**
   * Cook accepts to cook the dish and removes ingredients.
   *
   * @param dish that need to be cooked
   * @param servingTable serving table that needs to be changed
   * @param inventory inventory of restaurant
   */
  public void acceptCook(Dish dish, ServingTable servingTable, Inventory inventory) {
    prepareDish(dish, inventory);
    servingTable.addToBeCooking(dish);
    dish.setDishStatus(DishStatus.COOKING);
    System.out.println(
        String.format(
            "%s has agreed to cook Table%s%d %s",
            getName(), dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    System.out.println(servingTable);
  }

  /**
   * Cook accepts to prepare a dish. No ingredient removal needed
   *
   * @param dish that needs to be cooked
   * @param servingTable serving table of restaurant
   */
  public void acceptNoCook(Dish dish, ServingTable servingTable) {
    // Dish dish = servingTable.getDishToBeCooked(index);
    // servingTable.addToBeCooking(index);
      servingTable.addToBeCooking(dish);
    System.out.println(
        String.format(
            "%s has agreed to cook Table%s%d %s",
            getName(), dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    System.out.println(servingTable);
  }

  /**
   * Reject a dish that needs to be cooked.
   *
   * @param dish that is rejected
   * @param servingTable serving table of the restaurant
   */
  public void rejectDish(Dish dish, ServingTable servingTable) {
    // Dish dish = servingTable.getDishToBeCooked(index);
    // servingTable.rejectDish(index);
      servingTable.rejectDish(dish);
      dish.setDishStatus(DishStatus.REJECTED);
    System.out.println(
        String.format(
            "%s has rejected to cook Table%s%d %s",
            getName(), dish.getTableName(), dish.getCustomerNum(), dish.getName()));
    System.out.println(servingTable);
  }

  /**
   * Dish has been cooked and is placed in list of dishes that needs to be served.
   *
   * @param dish which is cooked
   * @param servingTable serving table of restaurant
   */
  public void serveDish(Dish dish, ServingTable servingTable) {
    servingTable.addToBeServed(dish);
    servingTable.getDishesBeingCooked().remove(dish);
    dish.setDishStatus(DishStatus.PICKUP);
  }

  public ModelControllerInterface getScreen() {
    return screen;
  }

  public void setScreen(ModelControllerInterface screen) {
    this.screen = screen;
  }
}
