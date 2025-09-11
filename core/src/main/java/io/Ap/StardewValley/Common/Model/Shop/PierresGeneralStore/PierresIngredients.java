package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

import io.Ap.StardewValley.Common.Model.Cooking.IngredientType;

public enum PierresIngredients {
    Rice(IngredientType.Rice, "A basic grain often served under vegetables." , "Rice" , 200 , Integer.MAX_VALUE),
    WheatFlour(IngredientType.WheatFlour, "A common cooking ingredient made from crushed wheat seeds." , "Wheat Flour" , 100 , Integer.MAX_VALUE),
    Sugar(IngredientType.Sugar, "Adds sweetness to pastries and candies. Too much can be unhealthy." , "Sugar" , 100 , Integer.MAX_VALUE),
    Oil(IngredientType.Oil, "All purpose cooking oil." , "Oil" , 200 , Integer.MAX_VALUE),
    Vinegar(IngredientType.Vinegar, "An aged fermented liquid used in many cooking recipes." , "Vinegar" , 200 , Integer.MAX_VALUE);

    private final IngredientType type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    PierresIngredients(IngredientType type, String description, String name, int price, int dailyLimit) {
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

    public IngredientType getType() {
        return type;
    }
}
