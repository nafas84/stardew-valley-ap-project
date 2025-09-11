package io.Ap.StardewValley.Common.Model.Plants;

public enum FertilizerType {
    DeluxeRetainingSoil ("Deluxe Retaining Soil", 75, 1),
    SpeedGro ("Speed-Gro", 50, 2);

    private final String name;
    private final int sellPrice;
    private final int type;

    FertilizerType (String name, int sellPrice, int type) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getType() {
        return type;
    }
}
