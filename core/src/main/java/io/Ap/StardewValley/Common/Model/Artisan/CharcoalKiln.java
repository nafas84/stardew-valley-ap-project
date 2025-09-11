package io.Ap.StardewValley.Common.Model.Artisan;

public enum CharcoalKiln {
    Coal("Turns 10 pieces of wood into one piece of coal." , 0 , 1 , 50);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    CharcoalKiln(String description , int energy , int processingTime , int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
