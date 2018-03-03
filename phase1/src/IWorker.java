public interface IWorker {

  /**
   * Check if this IWorker is currently occupied or not
   */
  boolean isOccupied();
  void addIngredients(Inventory inventory, String ingredient, int amount);

}
