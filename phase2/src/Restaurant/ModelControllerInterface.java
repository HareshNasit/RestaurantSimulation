package Restaurant;

public interface ModelControllerInterface {

  public void updateScreen();

  public void openNotification(String message);

  public void openReceiverFunction(Inventory inventory, String ingredient, int amount);

}
