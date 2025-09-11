package io.Ap.StardewValley.Common.Model.NPC;

import io.Ap.StardewValley.Common.Model.Animals.AnimalProduct;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProductType;
import io.Ap.StardewValley.Common.Model.Animals.Fish;
import io.Ap.StardewValley.Common.Model.Animals.FishType;
import io.Ap.StardewValley.Common.Model.Cooking.Food;
import io.Ap.StardewValley.Common.Model.Cooking.FoodType;
import io.Ap.StardewValley.Common.Model.Cooking.Ingredient;
import io.Ap.StardewValley.Common.Model.Cooking.IngredientType;
import io.Ap.StardewValley.Common.Model.Crafting.Craft;
import io.Ap.StardewValley.Common.Model.Crafting.CraftType;
import io.Ap.StardewValley.Common.Model.Item.Coin;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.Plants.Crop;
import io.Ap.StardewValley.Common.Model.Plants.CropType;
import io.Ap.StardewValley.Common.Model.Plants.ForagingMineral;
import io.Ap.StardewValley.Common.Model.Plants.ForagingMineralType;

import java.util.ArrayList;

public enum NPCType {
    Ali("Ali", "Rapper"),
    Negar("Negar", "Dentist Assistant"),
    Farshad("Farshad", "Fitness"),
    Milad("Milad", "English Teacher"),
    Parastoo("Parastoo", "Pimp");

    private final String name;
    private final String job;
    private ArrayList<Item> favorites;
    private ArrayList<Quest> quests;

    NPCType(String name, String job) {
        this.name = name;
        this.job = job;
        this.favorites = new ArrayList<>();
        this.quests = new ArrayList<>();
        if (this.name.equals("Ali")) {
            favorites.add(new AnimalProduct(AnimalProductType.SheepWool));
            favorites.add(new Food(FoodType.Pizza));
            favorites.add(new Food(FoodType.PumpkinPie));
            quests.add(new Quest(new ForagingMineral(ForagingMineralType.Iron), 50,
                    new ForagingMineral(ForagingMineralType.Diamond), 2, 1, 1));
            quests.add(new Quest(new Food(FoodType.PumpkinPie), 1, new Coin(), 5000, 2, 2));
            quests.add(new Quest(new Stone(), 150, new ForagingMineral(ForagingMineralType.Quartz), 50, 1, 3));
        }
        if (this.name.equals("Negar")) {
            favorites.add(new Stone());
            favorites.add(new Ingredient(IngredientType.Coffee));
            favorites.add(new ForagingMineral(ForagingMineralType.Iron));
            quests.add(new Quest(new Food(FoodType.Bread), 10, new Fish(FishType.Salmon), 3, 1, 4));
            quests.add(new Quest(new Crop(CropType.Pumpkin), 1, new Coin(), 500, 2, 5));
            quests.add(new Quest(new Crop(CropType.Wheat), 50, new ForagingMineral(ForagingMineralType.Copper), 100, 1, 6));
        }
        if (this.name.equals("Farshad")) {
            favorites.add(new AnimalProduct(AnimalProductType.Egg));
            favorites.add(new Ingredient(IngredientType.Coffee));
            favorites.add(new Food(FoodType.FruitSalad));
            quests.add(new Quest(new Crop(CropType.Corn), 5, new Coin(), 750, 1, 7));
            quests.add(new Quest(new Fish(FishType.Salmon), 1, new Coin(), 500, 2, 8));
            quests.add(new Quest(new Coin() , 250, new Food(FoodType.Salad), 5, 3, 9));
        }
        if (this.name.equals("Milad")) {
            favorites.add(new Ingredient(IngredientType.Beer));
            favorites.add(new Food(FoodType.Cookie));
            favorites.add(new ForagingMineral(ForagingMineralType.Gold));
            quests.add(new Quest(new Wood(), 50, new Coin(), 500, 1, 10));
            quests.add(new Quest(new Fish(FishType.Salmon), 1, new Food(FoodType.SalmonDinner), 3, 2, 11));
            quests.add(new Quest(new Wood(), 200, new Craft(CraftType.DeluxeScarecrow), 500, 3, 12));
        }
        if (this.name.equals("Parastoo")) {
            favorites.add(new Food(FoodType.Pizza));
            favorites.add(new AnimalProduct(AnimalProductType.DinosaurEgg));
            favorites.add(new Wood());
            quests.add(new Quest(new Wood(), 80, new Coin(), 1000, 1, 13));
            quests.add(new Quest(new Wood(), 250, new Fish(FishType.Legend), 1, 2, 14));
            quests.add(new Quest(new Wood(), 1000, new Coin(), 25000, 3, 15));
        }
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
