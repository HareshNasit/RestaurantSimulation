public interface IWorker {

  /**
   * Check if this IWorker is currently occupied or not
   */
  boolean isOccupied();

  /**
   * Records what ingredient is being added to the inventory and how many units are being added
   * @param inventory The stored ingredients in the restaurant
   * @param ingredient The ingredient being added to the inventory
   * @param amount How much of that ingredient is being added
   */
  void scanStock(Inventory inventory, String ingredient, int amount);
  String getName();
}
