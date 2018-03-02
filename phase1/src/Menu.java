import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {

  private final String FILENAME = "menu.txt";
  private ArrayList<Dish> menuItems;

  public Menu() {

    createMenu();
  }

  /** Iterates through all the lines in the txt file to produce each dish on the menu */
  private void createMenu() {
    // Aggregate the data
    // Each MenuItem takes in a line from the menu.txt
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
    int price = Integer.parseInt(separatedByHashTag[2]);
    HashMap<String, DishIngredient> ingredients = new HashMap<>();

    for (int i = 3; i < separatedByHashTag.length; i++) {
      String[] ingredient = separatedByHashTag[i].split(",");
      int[] bounds = new int[3];
      for (int k = 0; k < 3; k++) {
        bounds[k] = Integer.parseInt(ingredient[k + 1]);
      }
      String ingredientName = ingredient[0];
      int amount = Integer.parseInt(ingredient[1]);
      int lowerAmount = Integer.parseInt(ingredient[2]);
      int upperAmount = Integer.parseInt(ingredient[3]);

      ingredients.put(
          ingredient[0], new DishIngredient(ingredientName, amount, lowerAmount, upperAmount));
    }
    Dish dish = new Dish(name, id, price, ingredients);
    menuItems.add(dish);
  }

  public int getMenuSize() {
    return menuItems.size();
  }
}
