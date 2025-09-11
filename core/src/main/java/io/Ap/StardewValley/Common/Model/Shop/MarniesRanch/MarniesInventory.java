package io.Ap.StardewValley.Common.Model.Shop.MarniesRanch;

public enum MarniesInventory {
    Hay("Dried grass used as animal food." , "Hay" , 50 , Integer.MAX_VALUE),
    MilkPail("Gather milk from your animals." , "Milk Pail" , 1000 , 1),
    Shears("Use this to collect wool from sheep" , "Shears" , 1000 , 1);

    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    MarniesInventory(String description, String name, int price, int dailyLimit) {
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
}
