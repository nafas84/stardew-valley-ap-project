package io.Ap.StardewValley.Common.Model.Crafting;

import io.Ap.StardewValley.Common.Model.Cooking.Ingredient;
import io.Ap.StardewValley.Common.Model.Cooking.IngredientType;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.Plants.ForagingMineral;
import io.Ap.StardewValley.Common.Model.Plants.ForagingMineralType;
import io.Ap.StardewValley.Common.Model.Plants.Sapling;
import io.Ap.StardewValley.Common.Model.Plants.SaplingType;

import java.util.HashMap;

public enum CraftRecipe {
    CherryBomb("Cherry Bomb", "4 copper ore + 1 coal", new HashMap<>() {{
        put(new ForagingMineral(ForagingMineralType.Copper), 4);
        put(new ForagingMineral(ForagingMineralType.Coal), 1);
    }}),
    Bomb("Bomb", "4 iron ore + 1 coal", new HashMap<>() {{
        put(new ForagingMineral(ForagingMineralType.Iron), 4);
        put(new ForagingMineral(ForagingMineralType.Coal), 1);
    }}),
    MegaBomb("Mega Bomb", "4 gold ore + 1 coal", new HashMap<>() {{
        put(new ForagingMineral(ForagingMineralType.Gold), 4);
        put(new ForagingMineral(ForagingMineralType.Coal), 1);
    }}),
    Sprinkler("Sprinkler", "1 copper bar + 1 iron bar", new HashMap<>() {{
        put(new ForagingMineral(ForagingMineralType.Copper), 1);
        put(new ForagingMineral(ForagingMineralType.Iron), 1);
    }}),
    QualitySprinkler("Quality Sprinkler", "1 iron bar + 1 gold bar", new HashMap<>() {{
        put(new ForagingMineral(ForagingMineralType.Iron), 1);
        put(new ForagingMineral(ForagingMineralType.Gold), 1);
    }}),
    IridiumSprinkler("Iridium Sprinkler", "1 gold bar + 1 iridium bar", new HashMap<>() {{
        put(new ForagingMineral(ForagingMineralType.Gold), 1);
        put(new ForagingMineral(ForagingMineralType.Iriduim), 1);
    }}),
    CharcoalKiln("Charcoal Kiln", "20 wood + 2 copper bar", new HashMap<>() {{
        put(new Wood(), 20);
        put(new ForagingMineral(ForagingMineralType.Copper), 2);
    }}),
    Furnace("Furnace", "20 copper ore + 25 stone", new HashMap<>() {{
        put(new Stone(), 25);
        put(new ForagingMineral(ForagingMineralType.Copper), 20);
    }}),
    Scarecrow("Scarecrow", "50 wood + 1 coal + 20 fiber", new HashMap<>() {{
        put(new Wood(), 50);
        put(new ForagingMineral(ForagingMineralType.Coal), 1);
        put(new Ingredient(IngredientType.Fiber), 20);
    }}),
    DeluxeScarecrow("Deluxe Scarecrow", "50 wood + 1 coal + 20 fiber + 1 iridium ore", new HashMap<>() {{
        put(new Wood(), 50);
        put(new ForagingMineral(ForagingMineralType.Coal), 1);
        put(new Ingredient(IngredientType.Fiber), 20);
        put(new ForagingMineral(ForagingMineralType.Iriduim), 1);
    }}),
    BeeHouse("Bee House", "40 wood + 8 coal + 1 iron bar", new HashMap<>() {{
        put(new Wood(), 40);
        put(new ForagingMineral(ForagingMineralType.Coal), 8);
        put(new ForagingMineral(ForagingMineralType.Iron), 1);
    }}),
    CheesePress("Cheese Press", "45 wood + 45 stone + 1 copper bar", new HashMap<>() {{
        put(new Wood(), 45);
        put(new ForagingMineral(ForagingMineralType.Copper), 1);
        put(new Stone(), 45);
    }}),
    Keg("Keg", "30 wood + 1 copper bar + 1 iron bar", new HashMap<>() {{
        put(new Wood(), 30);
        put(new ForagingMineral(ForagingMineralType.Copper), 1);
        put(new ForagingMineral(ForagingMineralType.Iron), 1);
    }}),
    Loom("Loom", "60 wood + 30 fiber", new HashMap<>() {{
        put(new Wood(), 60);
        put(new Ingredient(IngredientType.Fiber), 30);
    }}),
    MayonnaiseMachine("Mayonnaise Machine", "15 wood + 15 stone + 1 copper bar", new HashMap<>() {{
        put(new Wood(), 15);
        put(new ForagingMineral(ForagingMineralType.Copper), 1);
        put(new Stone(), 15);
    }}),
    OilMaker("Oil Maker", "100 wood + 1 gold bar + 1 iron bar", new HashMap<>() {{
        put(new Wood(), 100);
        put(new ForagingMineral(ForagingMineralType.Gold), 1);
        put(new ForagingMineral(ForagingMineralType.Iron), 1);
    }}),
    PreservesJar("Preserves Jar", "50 wood + 40 stone + 8 coal", new HashMap<>() {{
        put(new Wood(), 50);
        put(new ForagingMineral(ForagingMineralType.Coal), 8);
        put(new Stone(), 40);
    }}),
    Dehydrator("Dehydrator", "30 wood + 20 stone + 30 fiber", new HashMap<>() {{
        put(new Wood(), 30);
        put(new Ingredient(IngredientType.Fiber), 30);
        put(new Stone(), 20);
    }}),

    GrassStarter("Grass Starter", "1 wood + 1 fiber", new HashMap<>() {{
        put(new Wood(), 1);
        put(new Ingredient(IngredientType.Fiber), 1);
    }}),
    FishSmoker("Fish Smoker", "50 wood + 3 iron bar + 10 coal", new HashMap<>() {{
        put(new Wood(), 50);
        put(new ForagingMineral(ForagingMineralType.Iron), 3);
        put(new ForagingMineral(ForagingMineralType.Coal), 50);
    }}),
    MysticTreeSeed("Mystic Tree Seed", "5 acorn + 5 maple seed + 5 pine cone + 5 mahogany seed", new HashMap<>() {{
        put(new Sapling(SaplingType.Acorns), 5);
        put(new Sapling(SaplingType.MapleSeeds), 5);
        put(new Sapling(SaplingType.PineCones), 5);
        put(new Sapling(SaplingType.MahoganySeeds), 5);
    }});

    private final String name;
    private final String recipeString;
    private final HashMap<Item, Integer> recipe;
    CraftRecipe (String name, String recipeString, HashMap<Item, Integer> recipe) {
        this.name = name;
        this.recipe = recipe;
        this.recipeString = recipeString;
    }

    public String getRecipeString() {
        return recipeString;
    }

    public HashMap<Item, Integer> getRecipe() {
        return recipe;
    }

    public String getName() {
        return name;
    }
}
