import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {

  private final String FILENAME = "menu.txt";
  private HashMap<Double, MenuItem> menuItems;

  /** Creates a Menu from the FILENAME txt file and adds all the dishes to an array. */
  public Menu() {
    this.menuItems = new HashMap<>();
    createMenu();
  }

  /** Iterates through all the lines in the txt file to produce each dish on the menu. */
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

  /** Creates a dish using a line from the txt file. */
  private void addDishToMenu(String line) {
    String[] separatedByHashTag = line.split("#");

    String name = separatedByHashTag[1];
    Double id = Double.parseDouble(separatedByHashTag[0].trim());
    Double price = Double.parseDouble(separatedByHashTag[2].trim());
    HashMap<String, DishIngredient> ingredients = new HashMap<>();

    for (int i = 3; i < separatedByHashTag.length; i++) {
      String[] ingredient = separatedByHashTag[i].split(",");

      String ingredientName = ingredient[0];
      int amount = Integer.parseInt(ingredient[1].trim());
      int baseAmount = Integer.parseInt(ingredient[1].trim());
      int lowerAmount = Integer.parseInt(ingredient[2].trim());
      int upperAmount = Integer.parseInt(ingredient[3].trim());
      int ingredientPrice = Integer.parseInt(ingredient[4].trim());

      ingredients.put(
          ingredient[0],
          new DishIngredient(
              ingredientName, amount, baseAmount, lowerAmount, upperAmount, ingredientPrice));
    }
    MenuItem dish = new MenuItem(name, id, price, ingredients);
    menuItems.put(id, dish);
  }

  public int getMenuSize() {
    return menuItems.size();
  }

  public MenuItem getDish(Double id, String tableName, int customerNum) {
    return Dish.getDishFromMenuItem(menuItems.get(id), tableName, customerNum);
  }
}
