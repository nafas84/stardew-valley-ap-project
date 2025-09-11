package io.Ap.StardewValley.Common.Model.Shop.JojaMart;

import io.Ap.StardewValley.Common.Model.Plants.SeedType;

public enum JojaSummerSeeds {
    AncientSeed(SeedType.AncientSeeds, "Could these still grow?" , "Ancient Seed" , 500 , 1),
    GrassStarter(SeedType.GrassStarter, "Place this on your farm to start a new patch of grass." , "Grass Starter" ,125 , Integer.MAX_VALUE),

    TomatoSeeds(SeedType.TomatoSeeds, "Plant these in the summer. Takes 11 days to mature, and continues to produce after first harvest." , "Tomato Seeds" , 62 , 5),
    PepperSeeds(SeedType.PepperSeeds, "Plant these in the summer. Takes 5 days to mature, and continues to produce after first harvest." , "Pepper Seeds" , 50 , 5),
    WheatSeeds(SeedType.WheatSeeds, "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe." , "Wheat Seeds" , 12 , 10),
    SummerSquashSeeds(SeedType.SummerSquashSeeds, "Plant in the summer. Takes 6 days to grow, and continues to produce after first harvest." , "Summer Squash Seeds" , 10 , 10),
    RadishSeeds(SeedType.RadishSeeds, "Plant these in the summer. Takes 6 days to mature." , "Radish Seeds" , 50 ,5),
    MelonSeeds(SeedType.MelonSeeds, "Plant these in the summer. Takes 12 days to mature." , "Melon Seeds" , 100 , 5),
    HopsStarter(SeedType.HopsStarter, "Plant these in the summer. Takes 11 days to grow, but keeps producing after that. Grows on a trellis." , "Hops Starter" , 75 , 5),
    PoppySeeds(SeedType.PoppySeeds, "Plant in summer. Produces a bright red flower in 7 days." , "Poppy Seeds" , 125 , 5),
    SpangleSeeds(SeedType.SpangleSeeds, "Plant in summer. Takes 8 days to produce a vibrant tropical flower. Assorted colors." , "Spangle Seeds" , 62 , 5),
    StarfruitSeeds(SeedType.StarfruitSeeds, "Plant these in the summer. Takes 13 days to mature." , "Starfruit Seeds" , 400 , 5),
    CoffeeBeans(SeedType.CoffeeBean, "Plant in summer or spring. Takes 10 days to grow, Then produces coffee Beans every other day." , "Coffee Beans" , 200 , 1),
    SunflowerSeeds(SeedType.SunflowerSeeds, "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest." , "Sunflower Seeds" , 125 , 5);

    private final SeedType seedType;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    JojaSummerSeeds(SeedType seedType, String description, String name, int price, int dailyLimit) {
        this.seedType = seedType;
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

    public SeedType getSeedType() {
        return seedType;
    }
}
