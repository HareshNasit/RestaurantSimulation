public interface InventoryListener {

  /**
   * Notifies this inventory listener that something has changed about the inventory
   *
   * @param message message to display on listener
   */
  void notifyLowStock(String message);
}
