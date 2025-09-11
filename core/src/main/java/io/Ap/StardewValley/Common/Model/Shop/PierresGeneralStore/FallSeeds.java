package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Plants.SeedType;
import io.Ap.StardewValley.Common.Model.Time.Season;

public enum FallSeeds {
    GrassStarter(SeedType.GrassStarter, "Place this on your farm to start a new patch of grass." , "Grass Starter" , 100 , Integer.MAX_VALUE),

    EggplantSeeds(SeedType.EggplantSeeds, "Plant these in the fall. Takes 5 days to mature, and continues to produce after first harvest." , "Eggplant Seeds" , 30 , 5),
    CornSeeds(SeedType.CornSeeds, "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest." , "Corn Seeds" , 225 , 5),
    PumpkinSeeds(SeedType.PumpkinSeeds, "Plant these in the fall. Takes 13 days to mature." , "Pumpkin Seeds" , 150 , 5),
    BokChoySeeds(SeedType.BokChoySeeds, "Plant these in the fall. Takes 4 days to mature." , "Bok Choy Seeds" , 75 , 5),
    YamSeeds(SeedType.YamSeeds, "Plant these in the fall. Takes 10 days to mature." , "Yam Seeds" , 90 , 5),
    CranberrySeeds(SeedType.CranberrySeeds, "Plant these in the fall. Takes 7 days to mature, and continues to produce after first harvest." , "Cranberry Seeds" , 360 , 5),
    SunflowerSeeds(SeedType.SunflowerSeeds, "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest." , "Sunflower Seeds" , 300 , 5),
    FairySeeds(SeedType.FairySeeds, "Plant in fall. Takes 12 days to produce a mysterious flower. Assorted Colors." , "Fairy Seeds" , 300 , 5),
    AmaranthSeeds(SeedType.AmaranthSeeds, "Plant these in the fall. Takes 7 days to grow. Harvest with the scythe." , "Amaranth Seeds" , 105 , 5),
    GrapeStarter(SeedType.GrapeStarter, "Plant these in the fall. Takes 10 days to grow, but keeps producing after that. Grows on a trellis." , "Grape Starter" , 90 , 5),
    WheatSeeds(SeedType.WheatSeeds, "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe." , "Wheat Seeds" , 15 , 5),
    ArtichokeSeeds(SeedType.ArtichokeSeeds, "Plant these in the fall. Takes 8 days to mature." , "Artichoke Seeds" , 45 , 5);

    private final SeedType type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    FallSeeds(SeedType type, String description, String name, int price, int dailyLimit) {
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
        if (App.getGame().getCurrentTime().getSeason() == Season.Fall)
            return (price * 2) / 3;
        else
            return price;
    }


    public int getDailyLimit() {
        return dailyLimit;
    }

    public SeedType getType() {
        return type;
    }
}
