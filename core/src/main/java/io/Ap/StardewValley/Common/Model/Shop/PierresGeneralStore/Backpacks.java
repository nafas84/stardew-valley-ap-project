package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

public enum Backpacks {
    LargePack(24, "Unlocks the 2nd row of inventory (12 more slots, total 24)." , "Large Pack" , 2000 , 1),
    DeluxePack(Integer.MAX_VALUE, "Unlocks the 3rd row of inventory (infinite slots)." , "Deluxe Pack" , 10000 , 1);

    private final int capacity;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    Backpacks(int level, String description, String name, int price, int dailyLimit) {
        this.capacity = level;
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


    public int getCapacity() {
        return capacity;
    }
}
