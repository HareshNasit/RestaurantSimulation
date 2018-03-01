import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {

  private final String FILENAME = "menu.txt";
  private ArrayList<Dish> menuItems;

  public Menu() {
    // Aggregate the data
    // Each MenuItem takes in a line from the menu.txt

  }

  /** Iterates through all the lines in the txt file to produce each dish on the menu */
  private void createMenu() {
    File file = new File(FILENAME);

    try {
      Scanner menu = new Scanner(file);

      while (menu.hasNextLine()) {
        String line = menu.nextLine();
        addDishToMenu(line);
      }
      menu.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
  /** Creates a dish using a line from the txt file */
  private void addDishToMenu(String line) {
    String[] separatedByHashTag = line.split("#\\s");

    String name = separatedByHashTag[1];
    int id = Integer.parseInt(separatedByHashTag[0]);
    int timeToPrepare = Integer.parseInt(separatedByHashTag[2]);
    HashMap<String, int[]> ingredients = new HashMap<>();

    for (int i = 3; i < separatedByHashTag.length; i++) {
      String[] ingredient = separatedByHashTag[i].split(",");
      int[] bounds = new int[3];
      for (int k = 0; k < 3; k++) {
        bounds[k] = Integer.parseInt(ingredient[k + 1]);
      }
      ingredients.put(ingredient[0], bounds);
    }
    Dish dish = new Dish(name, id, timeToPrepare, ingredients);
    menuItems.add(dish);
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
        // Create ingredients hashmap here

        Dish dish = new Dish(name, itemID, cookTime, ingredients);
      }

    } catch (Exception e) {
    }

    return menuItems;
  }
}
