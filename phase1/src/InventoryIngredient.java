public class InventoryIngredient {

    private String name;
    private int restockQuantity;
    private int lowerThreshold;

    public InventoryIngredient(String name, int restockQuantity, int lowerThreshold){
        this.name = name;
        this.restockQuantity = restockQuantity;
        this.lowerThreshold = lowerThreshold;
    }
}
