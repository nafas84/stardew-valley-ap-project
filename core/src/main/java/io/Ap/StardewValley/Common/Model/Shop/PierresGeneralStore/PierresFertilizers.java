package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

import io.Ap.StardewValley.Common.Model.Plants.FertilizerType;

public enum PierresFertilizers {
    DeluxeRetainingSoil(FertilizerType.DeluxeRetainingSoil, "This soil has a 100% chance of staying watered overnight. Mix into tilled soil." , "Deluxe Retaining Soil" , 150 , Integer.MAX_VALUE),
    SpeedGro(FertilizerType.SpeedGro, "Makes the plants grow 1 day earlier." , "Speed-Gro" , 100 , Integer.MAX_VALUE),
    BasicRetainingSoil(FertilizerType.DeluxeRetainingSoil, "This soil has a chance of staying watered overnight. Mix into tilled soil." , "Basic Retaining Soil" , 100 , Integer.MAX_VALUE),
    QualityRetainingSoil(FertilizerType.DeluxeRetainingSoil, "This soil has a good chance of staying watered overnight. Mix into tilled soil." , "Quality Retaining Soil" , 150 , Integer.MAX_VALUE);

    private final FertilizerType type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    PierresFertilizers(FertilizerType type, String description, String name, int price, int dailyLimit) {
        this.type = type;
        this.description = description;
        this.name = name;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }


    public String getDescription() {
        return description;
    }


    public String getName() {
        return name;
    }


    public int getPrice() {
        return price;
    }


    public int getDailyLimit() {
        return dailyLimit;
    }

    public FertilizerType getType() {
        return type;
    }
}
