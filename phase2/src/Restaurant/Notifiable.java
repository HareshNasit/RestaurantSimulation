package Restaurant;

/**
 * This class is for the notifications that are sent to the workers.
 */
public interface Notifiable {

  /**
   * Sends a message to the workers
   * @param message the message being sent
   */
  void sendNotifications(String message);

  void update();


}
