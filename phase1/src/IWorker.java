public interface IWorker {

  /**
   * Check if this IWorker is currently occupied or not
   */
  boolean isOccupied();

  void scanStock(Inventory inventory, String ingredient, int amount);

}
