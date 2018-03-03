public interface IWorker {

  /**
   * Check if this IWorker is currently occupied or not
   */
  boolean isOccupied();

  /**
   * When new stock of ingredients has been recieved, make this IWorker scan it to update the current Inventory
   * @return
   */
  boolean scanStock();
}
