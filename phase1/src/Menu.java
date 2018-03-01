import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Menu {

  private final String FILENAME = "menu.txt";
  private ArrayList<Dish> menuItems;

  public Menu(String menuText) {
    // Aggregate the data
    // Each MenuItem takes in a line from the menu.txt
  }

  public int getMenuSize() {
    return menuItems.size();
  }

  public ArrayList<Dish> readMenuItems() {
    ArrayList<Dish> menuItems = new ArrayList<Dish>();
    try (BufferedReader fileReader = new BufferedReader(new FileReader(FILENAME))) {
      String line = fileReader.readLine();
      while (line != null) {
        int itemID = Integer.valueOf(line.split("#")[0].trim());
        String name = line.split("#")[1].trim();
        int cookTime = Integer.valueOf(line.split("#")[2].trim());

        HashMap<String, int[]> ingredients = new HashMap<String, int[]>();
        //Create ingredients hashmap here

        Dish dish = new Dish(name, itemID, cookTime, ingredients);

      }

    } catch (Exception e) {
    }

    return menuItems;
  }
}
