package Restaurant;

/**
 *
 */
public interface ModelControllerInterface {

  /**
   * Updates the screens to make sure all items that appear on the  screen are up to date 
   */
  public void updateScreen();

  /**
   * Sends a notification to the screen that the user is on
   * @param message the message that is being sent
   */
  public void openNotification(String message);

  /**
   * opens the receiver function which allows workers to collect stock when they click it
   * @param inventory
   * @param ingredient
   * @param amount
   */
  public void openReceiverFunction(Inventory inventory, String ingredient, int amount);

}
