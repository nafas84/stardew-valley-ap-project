package io.Ap.StardewValley.Common.Model.Artisan;

public enum OilMaker {
    TruffleOil("A gourmet cooking ingredient." , 38 , 6 , 1065),
    Oil_Corn("All purpose cooking oil." , 13 , 6 , 100),
    Oil_SunflowerSeeds("All purpose cooking oil." , 13 , 24 , 100),
    Oil_Sunflower("All purpose cooking oil." , 13 , 1 , 100);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    OilMaker(String description , int energy , int processingTime , int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
