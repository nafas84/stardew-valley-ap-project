package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

import io.Ap.StardewValley.Common.Model.Crafting.CraftRecipe;

public enum PierresCraftRecipes {
    DehydratorRecipe(CraftRecipe.Dehydrator, "A recipe to make Dehydrator" , "Dehydrator (Recipe)" , 10000 , 1),
    GrassStarterRecipe(CraftRecipe.GrassStarter, "A recipe to make Grass Starter" , "Grass Starter (Recipe)" , 1000 , 1);

    private final CraftRecipe type;
    private final String description;
    private final String name;
    private final int price;
    private final int dailyLimit;

    PierresCraftRecipes(CraftRecipe type, String description, String name, int price, int dailyLimit) {
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

    public CraftRecipe getType() {
        return type;
    }
}
