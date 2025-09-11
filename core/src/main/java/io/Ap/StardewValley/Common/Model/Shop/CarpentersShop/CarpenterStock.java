package io.Ap.StardewValley.Common.Model.Shop.CarpentersShop;

public enum CarpenterStock {
    Wood("wood", "A sturdy, yet flexible plant material with a wide variety of uses." , 10 , Integer.MAX_VALUE),
    Stone("stone", "A common material with many uses in crafting and building." , 20 , Integer.MAX_VALUE);

    private final String name;
    private final String description;
    private final int price;
    private final int dailyLimit;

    CarpenterStock(String name, String description, int price, int dailyLimit) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.dailyLimit = dailyLimit;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public String getName() {
        return name;
    }
}
