package io.Ap.StardewValley.Common.Model.Shop.TheStardropSaloon;

import io.Ap.StardewValley.Common.Model.Cooking.FoodType;

public enum StardropFood {

    Salad(FoodType.Salad, "A healthy garden salad." , "Salad" ,  220 , Integer.MAX_VALUE),
    Bread(FoodType.Bread, "A crusty baguette." , "Bread" , 120 , Integer.MAX_VALUE),
    Spaghetti(FoodType.Spaghetti, "An old favorite." , "Spaghetti" ,240 , Integer.MAX_VALUE),
    Pizza(FoodType.Pizza, "It's popular for all the right reasons." , "Pizza" , 600 , Integer.MAX_VALUE);

    private final FoodType type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    StardropFood(FoodType type, String description, String name, int price, int dailyLimit) {
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

    public FoodType getType() {
        return type;
    }
}
