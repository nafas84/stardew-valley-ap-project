package io.Ap.StardewValley.Common.Model.Artisan;

public enum FishSmoker {
    SmokedFish("A whole fish, smoked to perfection." , 0 , 1 , 0);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    FishSmoker(String description, int energy, int processingTime, int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
