package io.Ap.StardewValley.Common.Model.Crafting;

import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;

import java.util.HashMap;
import java.util.Map;

public enum CraftType {
    CherryBomb("Cherry Bomb", CraftRecipe.CherryBomb, new HashMap<>(Map.of(Skill.Mining, 1)), null, 50),
    Bomb("Bomb", CraftRecipe.Bomb, new HashMap<>(Map.of(Skill.Mining, 2)), null, 50),
    MegaBomb("Mega Bomb", CraftRecipe.MegaBomb, new HashMap<>(Map.of(Skill.Mining, 3)), null, 50),

    Sprinkler("Sprinkler", CraftRecipe.Sprinkler, new HashMap<>(Map.of(Skill.Farming, 1)), null, 0),
    QualitySprinkler("Quality Sprinkler", CraftRecipe.QualitySprinkler, new HashMap<>(Map.of(Skill.Farming, 2)), null, 0),
    IridiumSprinkler("Iridium Sprinkler", CraftRecipe.IridiumSprinkler, new HashMap<>(Map.of(Skill.Farming, 3)), null, 0),

    MayonnaiseMachine("Mayonnaise Machine", CraftRecipe.MayonnaiseMachine, null, null, 0),
    BeeHouse("Bee House", CraftRecipe.BeeHouse, new HashMap<>(Map.of(Skill.Farming, 1)), null, 0),
    PreservesJar("Preserves Jar", CraftRecipe.PreservesJar, new HashMap<>(Map.of(Skill.Farming, 2)), null, 0),
    CheesePress("Cheese Press", CraftRecipe.CheesePress, new HashMap<>(Map.of(Skill.Farming, 2)), null, 0),
    Loom("Loom", CraftRecipe.Loom, new HashMap<>(Map.of(Skill.Farming, 3)), null, 0),
    Keg("Keg", CraftRecipe.Keg, new HashMap<>(Map.of(Skill.Farming, 3)), null, 0),
    OilMaker("Oil Maker", CraftRecipe.OilMaker, new HashMap<>(Map.of(Skill.Farming, 3)), null, 0),
    FishSmoker("Fish Smoker", CraftRecipe.FishSmoker, null, ShopType.FishShop, 0),
    Dehydrator("Dehydrator", CraftRecipe.Dehydrator, null, ShopType.PierresGeneralStore, 0),

    CharcoalKiln("Charcoal Kiln", CraftRecipe.CharcoalKiln, new HashMap<>(Map.of(Skill.Foraging, 1)), null, 0),
    Furnace("Furnace", CraftRecipe.Furnace, null, null, 0),
    GrassStarter("Grass Starter", CraftRecipe.GrassStarter, null, ShopType.PierresGeneralStore, 0),
    MysticTreeSeed("Mystic Tree Seed", CraftRecipe.MysticTreeSeed, new HashMap<>(Map.of(Skill.Foraging, 4)), null, 100),

    Scarecrow("Scarecrow", CraftRecipe.Scarecrow, null, null, 0),
    DeluxeScarecrow("Deluxe Scarecrow", CraftRecipe.DeluxeScarecrow, new HashMap<>(Map.of(Skill.Farming, 2)), null, 0)
    ;



    private final String name;
    private final CraftRecipe ingredient;  //TODO: ino joda zadam chon enumaye ingredient amade nistan. goftam in classo betoonam bezanam.
    private final HashMap<Skill, Integer> level;
    private final ShopType shop;
    private final int sellPrice;

    CraftType (String name, CraftRecipe ingredient, HashMap<Skill, Integer> level, ShopType shop, int sellPrice) {
        this.name = name;
        this.ingredient = ingredient;
        this.level = level;
        this.shop = shop;
        this.sellPrice = sellPrice;
    }

    public CraftRecipe getIngredient() {
        return ingredient;
    }

    public HashMap<Skill, Integer> getLevel() {
        return level;
    }

    public ShopType getShop() {
        return shop;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getName() {
        return name;
    }
}
