package io.Ap.StardewValley.Common.Model.Plants;

public enum FruitType implements PlantType{

    Apricot ("Apricot", 59, true, 38, TreeType.Apricot),
    Cherry("Cherry", 80, true, 38, TreeType.Cherry),
    Banana("Banana", 150, true, 75, TreeType.Banana),
    Mango("Mango", 130, true, 100, TreeType.Mango),
    Orange("Orange", 100, true, 38, TreeType.Orange),
    Peach("Peach", 140, true, 38, TreeType.Peach),
    Apple("Apple", 100, true, 38, TreeType.Apple),
    Pomegranate("Pomegranate", 140, true, 38, TreeType.Pomegranate),
    OakResin("Oak Resin", 150, false, 0, TreeType.Oak),
    MapleSyrup("Maple Syrup", 200, false, 0, TreeType.Maple),
    PineTar("Pine Tar", 100, false, 0, TreeType.Pine),
    Sap("Sap", 2, true, -2, TreeType.Mahogany),
    CommonMushroom("Common Mushroom", 40, true, 38, TreeType.Mushroom),
    MysticSyrup("Mystic Syrup", 1000, true, 500, TreeType.Mystic);



    private final String name;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final TreeType treeType;


    FruitType(String name, int baseSellPrice, boolean isEdible, int energy, TreeType treeType) {
        this.name = name;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.treeType = treeType;
    }

    public String getName() {
        return name;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    @Override
    public String getInformation () {
        return "fruit information:\nname: " + name + "\nbase sell price: " + baseSellPrice + "\nis edible: " +
                isEdible + "\nenergy: " + energy + "\ntree: " + treeType.name();
    }
}
