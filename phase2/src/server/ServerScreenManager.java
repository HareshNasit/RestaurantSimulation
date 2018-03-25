package server;

import java.util.HashMap;
import javafx.scene.Node;

public class ServerScreenManager {

  private HashMap<String, Node> screens = new HashMap<>();

  /**
   *
   * @param name
   * @param node
   */
  public void addScreen(String name, Node node){
    screens.put(name, node);
  };

  public void loadScreen(String name, Node node){
    screens.put(name, node);
  };
}
