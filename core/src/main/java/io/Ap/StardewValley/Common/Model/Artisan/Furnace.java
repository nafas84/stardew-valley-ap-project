package io.Ap.StardewValley.Common.Model.Artisan;

public enum Furnace {
    AnyMetalBar("Turns ore and coal into metal bars." , 0 , 4 , 0);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    Furnace(String description, int energy, int processingTime, int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
