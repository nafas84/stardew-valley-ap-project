package io.Ap.StardewValley.Common.Model.Cooking;

public enum IngredientType {
    Hay ("Hay", 25),
    Beer ("Beer", 200),
    Coffee ("Coffee", 150),
    JojaCola ("Joja Cola", 30),
    Sugar ("Sugar", 60),
    WheatFlour ("Wheat Flour", 60),
    Rice ("Rice", 125),
    Oil ("Oil", 100),
    Vinegar ("Vinegar", 100),
    Fiber ("fiber", 20)
    ;

    private final String name;
    private final int sellPrice;
    IngredientType(String name, int price) {
        this.name = name;
        this.sellPrice = price;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
