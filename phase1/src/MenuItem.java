import java.util.HashMap;

public class MenuItem {

    private String name;
    private double id;
    private double price;
    private HashMap<String, DishIngredient> ingredients;

    /**
     * @param name The name of the dish
     * @param id The id assigned to the dis
     * @param price The price of the dish
     * @param ingredients The ingredients needed to make the dish
     */
    public MenuItem(String name, double id, double price, HashMap<String, DishIngredient> ingredients) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.ingredients = ingredients;
    }


    public Double getId(){
        return this.id;
    }
    /**
     * Creates a non-alias copy of this MenuItem
     *
     * @return MenuItem
     */
    public MenuItem clone() {
        return new MenuItem(this.name, this.id, this.price, cloneIngredients());
    }

    /**
     * Makes a non-alias copy of the ingredients.
     *
     * @return HashMap<String , DishIngredient> Ingredients hashmap
     */
    private HashMap<String, DishIngredient> cloneIngredients() {

        HashMap<String, DishIngredient> copy = new HashMap<>();

        for (String key : this.ingredients.keySet()) {
            copy.put(key, this.ingredients.get(key).clone());
        }
        return copy;
    }

    /**
     * Returns the price of the MenuItem
     *
     * @return
     */
    public double getPrice() {
        double finalPrice = price;
        for (String key : this.ingredients.keySet()) {
            DishIngredient dishIngredient = this.ingredients.get(key);
            int difference = dishIngredient.getAdditionAmount();
            finalPrice += difference * dishIngredient.getPrice();
        }
        return finalPrice;
    }

    public HashMap<String, DishIngredient> getIngredients() {
        return this.ingredients;
    }

    /**
     * Adds an ingredient to the dish. Precondition, the amount you are adding is within bounds of the
     * ingredient's bounds.
     *
     * @param ingredient The ingredient being added to the dish
     * @param amount The amount of that ingredient that is being added
     */
    public void addIngredient(String ingredient, int amount) {

      this.ingredients.get(ingredient).addAmount(amount);
    }

    /**
     * Subtracts an ingredient from the dish. Precondition, the amount you are subtracting is within
     * bounds of the ingredient's bounds.
     *
     * @param ingredient the ingredient that is being removed from the dish
     * @param amount the amount of that ingredient that is being removed
     */
    public void subtractIngredient(String ingredient, int amount) {
        this.ingredients.get(ingredient).subtractAmount(amount);
    }

    /** @return */
    public HashMap<String, Integer> getIngredientAmounts() {
        HashMap<String, Integer> ingredientAmounts = new HashMap<>();
        for (String key : this.ingredients.keySet()) {
            ingredientAmounts.put(key, this.ingredients.get(key).getAmount());
        }
        return ingredientAmounts;
    }

    protected HashMap<String, Integer> getDifferenceAmounts() {
        HashMap<String, Integer> differenceMap = new HashMap<>();
        for (String key : this.ingredients.keySet()) {
            int dif = this.ingredients.get(key).getBaseDifference();
            differenceMap.put(key, dif);
        }
        return differenceMap;
    }

    /**
     * Getter for name of the dish.
     *
     * @return String name.
     */
    public String getName() {
        return name;
    }
}
