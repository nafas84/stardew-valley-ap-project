package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Plants.SeedType;
import io.Ap.StardewValley.Common.Model.Time.Season;

public enum SpringSeeds {
    GrassStarter(SeedType.GrassStarter, "Place this on your farm to start a new patch of grass." , "Grass Starter" , 100 , Integer.MAX_VALUE),
    ParsnipSeeds(SeedType.ParsnipSeeds, "Plant these in the spring. Takes 4 days to mature." , "Parsnip Seeds" , 30 , 5),
    BeanStarter(SeedType.BeanStarter, "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis." , "Bean Starter" , 90 , 5),
    CauliflowerSeeds(SeedType.CauliflowerSeeds, "Plant these in the spring. Takes 12 days to produce a large cauliflower." , "Cauliflower Seeds" , 120 , 5),
    PotatoSeeds(SeedType.PotatoSeeds, "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest." , "Potato Seeds", 50 , 5),
    TulipBulb(SeedType.TulipBulb, "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors." , "Tulip Bulb" , 30 , 5),
    KaleSeeds(SeedType.KaleSeeds, "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe." , "Kale Seeds" , 105 , 5),
    JazzSeeds(SeedType.JazzSeeds, "Plant in spring. Takes 7 days to produce a blue puffball flower." , "Jazz Seeds" , 45 , 5),
    GarlicSeeds(SeedType.GarlicSeeds, "Plant these in the spring. Takes 4 days to mature." , "Garlic Seeds" , 60 , 5),
    RiceShoot(SeedType.RiceShoot, "Plant these in the spring. Takes 8 days to mature. Grows faster if planted near a body of water. Harvest with the scythe." , "Rice Shoot" , 60 , 5);

    private final SeedType type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    SpringSeeds(SeedType type, String description, String name, int price, int dailyLimit) {
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
        if (App.getGame().getCurrentTime().getSeason() == Season.Spring)
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
