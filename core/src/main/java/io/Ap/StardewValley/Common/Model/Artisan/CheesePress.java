package io.Ap.StardewValley.Common.Model.Artisan;

public enum CheesePress {
    Cheese_Milk("It's your basic cheese." , 100 , 3 , 230),
    Cheese_LargeMilk("It's your basic cheese." , 100 , 3 , 345),
    GoatCheese_GoatMilk("Soft cheese made from goat's milk." , 100 , 3 , 400),
    GoatCheese("Soft cheese made from goat's milk." , 100 , 3 , 600);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    CheesePress (String description , int energy , int processingTime , int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
