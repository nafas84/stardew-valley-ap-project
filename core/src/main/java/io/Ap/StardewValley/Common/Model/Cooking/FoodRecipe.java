package io.Ap.StardewValley.Common.Model.Cooking;

import io.Ap.StardewValley.Common.Model.Animals.AnimalProduct;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProductType;
import io.Ap.StardewValley.Common.Model.Animals.Fish;
import io.Ap.StardewValley.Common.Model.Animals.FishType;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Plants.*;

import java.util.HashMap;

public enum FoodRecipe {
    FriedEgg("Fried egg", "1 egg", new HashMap<>() {{
        put(new AnimalProduct(AnimalProductType.Egg), 1);
    }}),
    BakedFish("Baked Fish", "1 sardine\n1 salmon\n1 wheat", new HashMap<>() {{
        put(new Ingredient(IngredientType.WheatFlour), 1);
        put(new Fish(FishType.Salmon), 1);
        put(new Fish(FishType.Sardine), 1);
    }}),
    Salad("Salad", "1 leek\n1 dandelion", new HashMap<>() {{
        put(new ForagingCrop(ForagingCropType.Leek), 1);
        put(new ForagingCrop(ForagingCropType.Dandelion), 1);
    }}),
    Omelet("Omelet", "1 egg\n1 cow milk", new HashMap<>() {{
        put(new AnimalProduct(AnimalProductType.Egg), 1);
        put(new AnimalProduct(AnimalProductType.CowMilk), 1);
    }}),
    PumpkinPie("Pumpkin pie", "1 pumpkin\n1 wheat flour\n1 cow milk\n1 sugar", new HashMap<>() {{
        put(new Ingredient(IngredientType.WheatFlour), 1);
        put(new Crop(CropType.Pumpkin), 1);
        put(new AnimalProduct(AnimalProductType.CowMilk), 1);
        put(new Ingredient(IngredientType.WheatFlour), 1);
    }}),
    Spaghetti("Spaghetti", "1 wheat flour\n1 tomato", new HashMap<>() {{
        put(new Ingredient(IngredientType.WheatFlour), 1);
        put(new Crop(CropType.Tomato), 1);
    }}),
    Pizza("Pizza", "1 wheat flour\n1 tomato\n1 cow milk", new HashMap<>() {{
        put(new Ingredient(IngredientType.WheatFlour), 1);
        put(new Crop(CropType.Tomato), 1);
        put(new AnimalProduct(AnimalProductType.CowMilk), 1);
    }}),
    Tortilla("Tortilla", "1 corn", new HashMap<>() {{
        put(new Crop(CropType.Corn), 1);
    }}),
    MakiRoll("Maki Roll", "1 Salmon\n1 rice\n1 fiber", new HashMap<>() {{
        put(new Ingredient(IngredientType.Rice), 1);
        put(new Ingredient(IngredientType.Fiber), 1);
        put(new Fish(FishType.Salmon), 1);
    }}),
    TripleShotEspresso("Triple Shot Espresso", "3 coffee", new HashMap<>() {{
        put(new Ingredient(IngredientType.Coffee), 1);
    }}),
    Cookie("Cookie", "1 wheat flour\n1 sugar\n1 egg", new HashMap<>() {{
        put(new Ingredient(IngredientType.WheatFlour), 1);
        put(new Ingredient(IngredientType.Sugar), 1);
        put(new AnimalProduct(AnimalProductType.Egg), 1);
    }}),
    HashBrowns("Hash Browns", "1 potato\n1 oil", new HashMap<>() {{
        put(new Ingredient(IngredientType.Oil), 1);
        put(new Crop(CropType.Potato), 1);
    }}),
    Pancakes("Pancakes", "1 wheat flour\n1 egg", new HashMap<>() {{
        put(new Ingredient(IngredientType.WheatFlour), 1);
        put(new AnimalProduct(AnimalProductType.Egg), 1);
    }}),
    FruitSalad("Fruit Salad", "1 blueberry\n1 melon\n1 apricot", new HashMap<>() {{
        put(new Fruit(FruitType.Apricot), 1);
        put(new Crop(CropType.Blueberry), 1);
        put(new Crop(CropType.Melon), 1);
    }}),
    RedPlate("Red Plate", "1 red cabbage\n1 radish", new HashMap<>() {{
        put(new Crop(CropType.RedCabbage), 1);
        put(new Crop(CropType.Radish), 1);
    }}),
    Bread("Bread", "1 wheat flour", new HashMap<>() {{
        put(new Ingredient(IngredientType.WheatFlour), 1);
    }}),
    SalmonDinner("Salmon Dinner", "1 salmon\n1 amaranth\n1 kale", new HashMap<>() {{
        put(new Crop(CropType.Kale), 1);
        put(new Fish(FishType.Salmon), 1);
        put(new Crop(CropType.Amaranth), 1);
    }}),
    VegetableMedley("Vegetable Medley", "1 tomato\n1 beet", new HashMap<>() {{
        put(new Crop(CropType.Tomato), 1);
        put(new Crop(CropType.Beet), 1);
    }}),
    FarmersLunch("Farmer's Lunch", "1 omelet\n1 parsnip", new HashMap<>() {{
        put(new Food(FoodType.Omelet), 1);
        put(new Crop(CropType.Parsnip), 1);
    }}),
    SurvivalBurger("Survival Burger", "1 bread\n1 carrot\n1 eggplant", new HashMap<>() {{
        put(new Food(FoodType.Bread), 1);
        put(new Crop(CropType.Carrot), 1);
        put(new Crop(CropType.Eggplant), 1);
    }}),
    DishOTheSea("Dish O' the Sea", "2 sardines\n1 hash browns", new HashMap<>() {{
        put(new Food(FoodType.HashBrowns), 1);
        put(new Fish(FishType.Sardine), 2);
    }}),
    SeaformPudding("Seaform Pudding", "1 flounder\n1 midnight carp", new HashMap<>() {{
        put(new Fish(FishType.MidnightCarp), 1);
        put(new Fish(FishType.Flounder), 1);
    }}),
    MinersTreat("Miner's Treat", "2 carrot\n1 sugar\n1 cow milk", new HashMap<>() {{
        put(new Ingredient(IngredientType.Sugar), 1);
        put(new Crop(CropType.Carrot), 1);
        put(new AnimalProduct(AnimalProductType.CowMilk), 1);
    }}),
    TroutSoup("Trout Soup", "", new HashMap<>());



    private final String name;
    private final String recipeString;
    private final HashMap<Item, Integer> recipe;

    FoodRecipe(String name, String recipeString, HashMap<Item, Integer> recipe) {
        this.name = name;
        this.recipeString = recipeString;
        this.recipe = recipe;
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
