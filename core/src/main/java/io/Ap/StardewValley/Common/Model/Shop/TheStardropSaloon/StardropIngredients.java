package io.Ap.StardewValley.Common.Model.Shop.TheStardropSaloon;

import io.Ap.StardewValley.Common.Model.Cooking.IngredientType;

public enum StardropIngredients {
    Beer(IngredientType.Beer, "Drink in moderation." , "Beer" , 400 , Integer.MAX_VALUE),
    Coffee(IngredientType.Coffee, "It smells delicious. This is sure to give you a boost." , "Coffee" ,300 , Integer.MAX_VALUE);

    private final IngredientType type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    StardropIngredients(IngredientType type, String description, String name, int price, int dailyLimit) {
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
