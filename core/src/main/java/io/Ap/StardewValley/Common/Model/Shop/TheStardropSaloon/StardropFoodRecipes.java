package io.Ap.StardewValley.Common.Model.Shop.TheStardropSaloon;

import io.Ap.StardewValley.Common.Model.Cooking.FoodRecipe;

public enum StardropFoodRecipes {
    HashbrownsRecipe(FoodRecipe.HashBrowns, "A recipe to make Hashbrowns" , "Hashbrowns Recipe" , 50 , 1),
    OmeletRecipe(FoodRecipe.Omelet, "A recipe to make Omelet" , "Omelet Recipe" , 100 , 1),
    PancakesRecipe(FoodRecipe.Pancakes, "A recipe to make Pancakes" , "Pancakes Recipe" , 100 , 1),
    BreadRecipe(FoodRecipe.Bread, "A recipe to make Bread" , "Bread Recipe" , 100 , 1),
    TortillaRecipe(FoodRecipe.Tortilla, "A recipe to make Tortilla" , "Tortilla Recipe" , 100 , 1),
    PizzaRecipe(FoodRecipe.Pizza, "A recipe to make Pizza" , "Pizza Recipe" , 150 , 1),
    MakiRollRecipe(FoodRecipe.MakiRoll, "A recipe to make Maki Roll" , "Maki Roll Recipe" , 300 , 1),
    TripleShotEspressoRecipe(FoodRecipe.TripleShotEspresso, "A recipe to make Triple Shot Espresso" , "Triple Shot Espresso Recipe" , 5000 , 1),
    CookieRecipe(FoodRecipe.Cookie, "A recipe to make Cookie" , "Cookie Recipe" , 300 , 1);

    private final FoodRecipe type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    StardropFoodRecipes(FoodRecipe type, String description, String name, int price, int dailyLimit) {
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

    public FoodRecipe getType() {
        return type;
    }
}
