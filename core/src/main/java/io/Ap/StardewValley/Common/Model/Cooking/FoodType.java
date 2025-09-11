package io.Ap.StardewValley.Common.Model.Cooking;


import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;

import java.util.HashMap;
import java.util.Map;

public enum FoodType {
    FriedEgg ("Fried Egg", FoodRecipe.FriedEgg, 50, player -> {}, null, null, 35),
    Omelet ("Omelet", FoodRecipe.Omelet, 100, player -> {}, null, ShopType.TheStarDropSaloon, 125),
    Salad ("Salad", FoodRecipe.Salad, 113, player -> {}, null, null, 110),
    BakedFish ("Baked Fish", FoodRecipe.BakedFish, 75, player -> {}, null, null, 100),
    PumpkinPie ("Pumpkin Pie", FoodRecipe.PumpkinPie, 225, player -> {}, null, ShopType.TheStarDropSaloon, 385),
    Spaghetti ("Spaghetti", FoodRecipe.Spaghetti, 75, player -> {}, null, ShopType.TheStarDropSaloon, 120),
    Pizza ("Pizza", FoodRecipe.Pizza, 150, player -> {}, null, ShopType.TheStarDropSaloon, 300),
    Tortilla ("Tortilla", FoodRecipe.Tortilla, 50, player -> {}, null, ShopType.TheStarDropSaloon, 50),
    MakiRoll ("Maki Roll", FoodRecipe.MakiRoll, 100, player -> {}, null, ShopType.TheStarDropSaloon, 220),
    TripleShotEspresso ("Triple Shot Espresso", FoodRecipe.TripleShotEspresso, 200, player -> {player.addMaxEnergyBuff(5, 100);}, null, ShopType.TheStarDropSaloon, 450),
    Cookie ("Cookie", FoodRecipe.Cookie, 90, player -> {}, null, ShopType.TheStarDropSaloon, 140),
    HashBrowns ("Hash Browns", FoodRecipe.HashBrowns, 90, player -> {player.addSkillBuff(Skill.Farming, 5);}, null, ShopType.TheStarDropSaloon, 120),
    Pancakes ("Pancakes", FoodRecipe.Pancakes, 90, player -> {player.addSkillBuff(Skill.Foraging, 11);}, null, ShopType.TheStarDropSaloon, 80),
    FruitSalad ("Fruit Salad", FoodRecipe.FruitSalad, 263, player -> {}, null, ShopType.TheStarDropSaloon, 450),
    RedPlate ("Red Plate", FoodRecipe.RedPlate, 240, player -> {player.addMaxEnergyBuff(3, 50);}, null, ShopType.TheStarDropSaloon, 400),
    Bread ("Bread", FoodRecipe.Bread, 50, player -> {}, null, ShopType.TheStarDropSaloon, 60),
    SalmonDinner ("Salmon Dinner", FoodRecipe.SalmonDinner, 125, player -> {}, null, null, 300),
    VegetableMedley ("Vegetable Medley", FoodRecipe.VegetableMedley, 165, player -> {}, new HashMap<>(Map.of(Skill.Foraging, 2)), null, 120),
    FarmersLunch ("Farmer's Lunch", FoodRecipe.FarmersLunch, 200, player -> {player.addSkillBuff(Skill.Farming, 5);}, new HashMap<>(Map.of(Skill.Farming, 1)), null, 150),
    SurvivalBurger ("Survival Burger", FoodRecipe.SurvivalBurger, 125, player -> {player.addSkillBuff(Skill.Foraging, 5);}, new HashMap<>(Map.of(Skill.Foraging, 3)), null, 180),
    DishOTheSea ("Dish O' the Sea", FoodRecipe.DishOTheSea, 150, player -> {player.addSkillBuff(Skill.Fishing, 5);}, new HashMap<>(Map.of(Skill.Fishing, 2)), null, 220),
    SeafoamPudding ("Seafoam Pudding", FoodRecipe.SeaformPudding, 175, player -> {player.addSkillBuff(Skill.Fishing, 10);}, new HashMap<>(Map.of(Skill.Fishing, 3)), null, 300),
    MinersTreat ("Miner's Treat", FoodRecipe.MinersTreat, 125, player -> {player.addSkillBuff(Skill.Mining, 5);}, new HashMap<>(Map.of(Skill.Mining, 1)), null, 200),
    TroutSoup ("Trout Soup", FoodRecipe.TroutSoup, 100, player -> {}, null, ShopType.FishShop, 250);


    private final String name;
    private final FoodRecipe recipe;
    private final int energy;
    private final BuffCheck buff;
    private final HashMap<Skill, Integer> level;
    private final ShopType shop;
    private final int sellPrice;

    FoodType (String name, FoodRecipe recipe, int energy, BuffCheck buff,
              HashMap<Skill, Integer> level, ShopType shop, int sellPrice) {
        this.name = name;
        this.recipe = recipe;
        this.level = level;
        this.shop = shop;
        this.energy = energy;
        this.buff = buff;
        this.sellPrice = sellPrice;
    }


    public String getName() {
        return name;
    }

    public FoodRecipe getRecipe() {
        return recipe;
    }

    public HashMap<Skill, Integer> getLevel() {
        return level;
    }

    public ShopType getShop() {
        return shop;
    }

    public int getEnergy() {
        return energy;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void applyBuff (Player player) {
        buff.applyBuff(player);
    }
}
