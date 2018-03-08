/*
 * A cook reads the order being taken by the server and confirms with
 * the server if the dish can be prepared and prepares the dish.
 */
public class Cook implements IWorker, ServingTableListener {
    private String name; // Name of the cook.

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
    } // Name of the Cook.

    /**
     * Checks if the dish can be prepared and subtracts ingredients accordingly and returns a boolean and
     * adds it to the list of dishes being cooked currently.
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
     * The server receives and adds ingredients to the inventory from the distributor when called by the manager.
     */
    public void scanStock(Inventory inventory, String ingredient, int amount) {
        inventory.addStock(ingredient, amount);
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

    /**
     * Notify the cook that dish has been served
     */
    public void update(String message) {
        //message notification on GUI
    }

    public void acceptCook(int index, ServingTable servingTable, Inventory inventory) {
        Dish dish = servingTable.getDishToBeCooked(index);
        prepareDish(dish, inventory);
        servingTable.addToBeCooking(index);
        System.out.println(String.format("%s has agreed to cook Table%s%d %s", getName(),
                dish.getTableName(), dish.getCustomerNum(), dish.getName()));
        System.out.println(servingTable);
    }

    public void acceptNoCook(int index, ServingTable servingTable) {
        Dish dish = servingTable.getDishToBeCooked(index);
        servingTable.addToBeCooking(index);
        System.out.println(String.format("%s has agreed to cook Table%s%d %s", getName(),
                dish.getTableName(), dish.getCustomerNum(), dish.getName()));
        System.out.println(servingTable);
    }

    public void rejectDish(int index, ServingTable servingTable) {
        Dish dish = servingTable.getDishToBeCooked(index);
        servingTable.rejectDish(index);
        System.out.println(String.format("%s has rejected to cook Table%s%d %s", getName(),
                dish.getTableName(), dish.getCustomerNum(), dish.getName()));
        System.out.println(servingTable);
    }

    public void serveDish(int index, ServingTable servingTable) {
        servingTable.addToBeServe(index);
    }
}
