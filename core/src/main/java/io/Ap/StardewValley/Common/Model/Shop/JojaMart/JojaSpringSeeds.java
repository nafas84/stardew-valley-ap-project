package io.Ap.StardewValley.Common.Model.Shop.JojaMart;

import io.Ap.StardewValley.Common.Model.Plants.SeedType;

public enum JojaSpringSeeds {
    AncientSeed(SeedType.AncientSeeds, "Could these still grow?" , "Ancient Seed" , 500 , 1),
    GrassStarter(SeedType.GrassStarter, "Place this on your farm to start a new patch of grass." , "Grass Starter" ,125 , Integer.MAX_VALUE),

    ParsnipSeeds(SeedType.ParsnipSeeds, "Plant these in the spring. Takes 4 days to mature." , "Parsnip Seeds" , 25 ,5),
    BeanStarter(SeedType.BeanStarter, "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis." , "Bean Starter" , 75 , 5),
    CauliflowerSeeds(SeedType.CauliflowerSeeds, "Plant these in the spring. Takes 12 days to produce a large cauliflower." , "Cauliflower Seeds" , 100 , 5),
    PotatoSeeds(SeedType.PotatoSeeds, "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest." , "Potato Seeds" , 62 , 5),
    StrawberrySeeds(SeedType.StrawberrySeeds, "Plant these in spring. Takes 8 days to mature, and keeps producing strawberries after that." , "Strawberry Seeds" , 100 , 5),
    TulipBulb(SeedType.TulipBulb, "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors." , "Tulip Bulb" , 25 , 5),
    KaleSeeds(SeedType.KaleSeeds, "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe." , "Kale Seeds" , 87 , 5),
    CoffeeBeans(SeedType.CoffeeBean, "Plant in summer or spring. Takes 10 days to grow, Then produces coffee Beans every other day." , "Coffee Beans" , 200 , 1),
    CarrotSeeds(SeedType.CarrotSeeds, "Plant in the spring. Takes 3 days to grow." , "Carrot Seeds" , 5 , 10),
    RhubarbSeeds(SeedType.RhubarbSeeds, "Plant these in the spring. Takes 13 days to mature." , "Rhubarb Seeds" , 100 , 5),
    JazzSeeds(SeedType.JazzSeeds, "Plant in spring. Takes 7 days to produce a blue puffball flower." , "Jazz Seeds" , 37 , 5);


    private final SeedType seedType;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    JojaSpringSeeds(SeedType seedType, String description, String name, int price, int dailyLimit) {
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
