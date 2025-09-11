package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Plants.SeedType;
import io.Ap.StardewValley.Common.Model.Time.Season;

public enum SummerSeeds {
    GrassStarter(SeedType.GrassStarter, "Place this on your farm to start a new patch of grass." , "Grass Starter" , 100 , Integer.MAX_VALUE),
    MelonSeeds(SeedType.MelonSeeds, "Plant these in the summer. Takes 12 days to mature." , "Melon Seeds" , 120 , 5),
    TomatoSeeds(SeedType.TomatoSeeds, "Plant these in the summer. Takes 11 days to mature, and continues to produce after first harvest." , "Tomato Seeds" , 75 , 5),
    BlueberrySeeds(SeedType.BlueberrySeeds, "Plant these in the summer. Takes 13 days to mature, and continues to produce after first harvest." , "Blueberry Seeds" , 120 , 5),
    PepperSeeds(SeedType.PepperSeeds, "Plant these in the summer. Takes 5 days to mature, and continues to produce after first harvest." , "Pepper Seeds" , 60 , 5),
    WheatSeeds(SeedType.WheatSeeds, "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe." , "Wheat Seeds", 15 , 5),
    RadishSeeds(SeedType.RadishSeeds, "Plant these in the summer. Takes 6 days to mature." , "Radish Seeds" , 60 , 5),
    PoppySeeds(SeedType.PoppySeeds, "Plant in summer. Produces a bright red flower in 7 days." , "Poppy Seeds" , 150 , 5),
    SpangleSeeds(SeedType.SpangleSeeds, "Plant in summer. Takes 8 days to produce a vibrant tropical flower. Assorted colors." , "Spangle Seeds" , 75 , 5),
    HopsStarter(SeedType.HopsStarter, "Plant these in the summer. Takes 11 days to grow, but keeps producing after that. Grows on a trellis." , "Hops Starter" , 90 , 5),
    CornSeeds(SeedType.CornSeeds, "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest." , "Corn Seeds" , 225 , 5),
    SunflowerSeeds(SeedType.SunflowerSeeds, "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest." , "Sunflower Seeds" , 300 , 5),
    RedCabbageSeeds(SeedType.RedCabbageSeeds, "Plant these in the summer. Takes 9 days to mature." , "Red Cabbage Seeds" , 150 , 5);

    private final SeedType type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    SummerSeeds(SeedType type, String description, String name, int price, int dailyLimit) {
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
        if (App.getGame().getCurrentTime().getSeason() == Season.Summer)
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
