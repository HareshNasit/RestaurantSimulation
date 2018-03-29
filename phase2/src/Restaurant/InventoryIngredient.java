package Restaurant;

/**
 * This class represents an ingredient in the inventory which allows us to check if we are running low and need to
 * restock the ingredient
 */
public class InventoryIngredient {

    private String name;
    private int currentQuantity;
    private int restockQuantity;
    private int lowerThreshold;

    public InventoryIngredient(String name, int amount, int restockQuantity, int lowerThreshold) {
        this.name = name;
        this.currentQuantity = amount;
        this.restockQuantity = restockQuantity;
        this.lowerThreshold = lowerThreshold;
    }

    public InventoryIngredient(String name, int amount) {
        this.name = name;
        this.currentQuantity = amount;
        this.restockQuantity = 20;
        this.lowerThreshold = 0;
    }

    /**
     * Sets the quantity of the ingredient.
     *
     * @param currentQuantity the current quantity of this ingredient.
     */
    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    /**
     * Increases the quantity by an amount.
     *
     * @param amount the amount to be increased by.
     */
    public void increaseQuantity(int amount) {
        this.currentQuantity += amount;
    }

    /**
     * Decreases the quantity by an amount.
     *
     * @param amount the amount to be decreased by.
     */
    public void decreaseQuantity(int amount) {
        this.currentQuantity -= amount;
    }

    /**
     * Gets the name of Ingredient.
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    public int getCurrentQuantity() {
        return this.currentQuantity;
    }

    /**
     * Sets the name of the InventoryIngredient.
     *
     * @param name The new name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the restock package quantity.
     *
     * @return int restockQuantity
     */
    public int getRestockQuantity() {
        return restockQuantity;
    }

    /**
     * Sets the restock package quantity.
     *
     * @param restockQuantity Set the restockQuantity.
     */
    public void setRestockQuantity(int restockQuantity) {
        this.restockQuantity = restockQuantity;
    }

    /**
     * Gets the lower threshold.
     *
     * @return int
     */
    public int getLowerThreshold() {
        return lowerThreshold;
    }

    /**
     * Sets the lower threshold.
     *
     * @param lowerThreshold Set the lowerThreshold for this ingredient.
     */
    public void setLowerThreshold(int lowerThreshold) {
        this.lowerThreshold = lowerThreshold;
    }
}
