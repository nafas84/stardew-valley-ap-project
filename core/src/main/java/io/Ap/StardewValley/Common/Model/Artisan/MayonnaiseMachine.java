package io.Ap.StardewValley.Common.Model.Artisan;

public enum MayonnaiseMachine {
    Mayonnaise_Egg("It looks spreadable." , 50 , 3 , 190),
    Mayonnaise_LargeEgg("It looks spreadable." , 50 , 3 , 237),
    DuckMayonnaise("It's a rich, yellow mayonnaise." , 75 , 3 , 37),
    DinosaurMayonnaise("It's thick and creamy, with a vivid green hue.\nIt smells like grass and leather." , 125 , 3 , 800);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    MayonnaiseMachine(String description , int energy , int processingTime , int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
