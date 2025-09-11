package io.Ap.StardewValley.Common.Model.Artisan;

public enum Loom {
    Cloth("A bolt of fine wool cloth." , 0 , 4 , 470);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    Loom(String description , int energy , int processingTime , int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
