package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

import io.Ap.StardewValley.Common.Model.Plants.SaplingType;

public enum PierresSaplings {
    AppleSapling(SaplingType.AppleSapling, "Takes 28 days to produce a mature Apple tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty." , "Apple Sapling" , 4000 , Integer.MAX_VALUE),
    ApricotSapling(SaplingType.ApricotSapling, "Takes 28 days to produce a mature Apricot tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty." , "Apricot Sapling" , 2000 , Integer.MAX_VALUE),
    CherrySapling(SaplingType.CherrySapling, "Takes 28 days to produce a mature Cherry tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty." , "Cherry Sapling" , 3400 , Integer.MAX_VALUE),
    OrangeSapling(SaplingType.OrangeSapling, "Takes 28 days to produce a mature Orange tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty." , "Orange Sapling" , 4000 , Integer.MAX_VALUE),
    PeachSapling(SaplingType.PeachSapling, "Takes 28 days to produce a mature Peach tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty." , "Peach Sapling" , 6000 , Integer.MAX_VALUE),
    PomegranateSapling(SaplingType.PomegranateSapling, "Takes 28 days to produce a mature Pomegranate tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty." , "Pomegranate Sapling" , 6000 , Integer.MAX_VALUE);

    private final SaplingType type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    PierresSaplings(SaplingType type, String description, String name, int price, int dailyLimit) {
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

    public SaplingType getType() {
        return type;
    }
}
