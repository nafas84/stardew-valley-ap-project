package io.Ap.StardewValley.Common.Model.Artisan;

public enum BeeHouse {
    Honey("It's a sweet syrup produced by bees." , 75 , 48 , 350);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    BeeHouse (String description , int energy , int processingTime , int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
