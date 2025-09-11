package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.Plants.*;
import io.Ap.StardewValley.Common.Model.Player.*;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProduct;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProductType;
import io.Ap.StardewValley.Common.Model.Animals.Fish;
import io.Ap.StardewValley.Common.Model.Animals.FishType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.Food;
import io.Ap.StardewValley.Common.Model.Cooking.FoodType;
import io.Ap.StardewValley.Common.Model.Cooking.Ingredient;
import io.Ap.StardewValley.Common.Model.Cooking.IngredientType;
import io.Ap.StardewValley.Common.Model.Crafting.Craft;
import io.Ap.StardewValley.Common.Model.Crafting.CraftType;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Item.ItemType;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.Result;

public class PlayerController {

    public static Result showEnergy () {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        return new Result(true, "player " + App.getGame().getCurrentPlayer().getUsername() + " energy: " +
                App.getGame().getCurrentPlayer().getEnergy());
    }

    public static Result cheatEnergy (String value) {
        int v;
        if (!isParsableInt(value) || (v = Integer.parseInt(value)) < 0) {
            return new Result (false, "value must be a non-negative number!");
        }
        App.getGame().getCurrentPlayer().setEnergy(v);
        if (v > App.getGame().getCurrentPlayer().getMaxEnergy()) {
            App.getGame().getCurrentPlayer().setMaxEnergy(v);
        }
        return new Result (true, "player " + App.getGame().getCurrentPlayer().getUsername() +
                " energy is now " + value);
    }

    public static Result unlimitedEnergy () {
        App.getGame().getCurrentPlayer().setMaxEnergy(Integer.MAX_VALUE);
        App.getGame().getCurrentPlayer().setMaxMovesInTurn(Integer.MAX_VALUE);
        return new Result (true, "your max energy is now infinite.");
    }

    public static Result showInventory () {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        String result = "items in inventory:\n";
        Inventory inventory = App.getGame().getCurrentPlayer().getInventory();
        if (inventory.getItems().isEmpty()) {
            return new Result(true, "hich ani nadari");
        }
        for (String itemName : inventory.getItems().keySet()) {
            result = result + itemName + " : " + inventory.getItems().get(itemName).getCount() + "\n";
        }

        return new Result(true, result);
    }

    public static Result inventoryTrash (String itemName, String number) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        int quantity;
        if (!isParsableInt(number) || (quantity = Integer.parseInt(number)) <= 0) {
            return new Result(false, "number must be positive!");
        }
        if (itemName == null) {
            return new Result(false, "invalid item name!");
        }
        else if (!App.getGame().getCurrentPlayer().moveItemFromInventoryToTrash(itemName, quantity)) {
            return new Result(false, "you don't have this amount of " +
                    itemName + " in your inventory!");
        }
        if (quantity == 1) {
            return new Result(true, "one " + itemName.toLowerCase() + " has been removed from inventory.");
        }
        return  new Result(true, quantity + " " + itemName.toLowerCase() + "s have been removed from inventory.");
    }

    public static Result inventoryTrashWithoutNumber (String itemName) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        if (itemName == null) {
            return new Result(false, "invalid item name!");
        }
        if (!App.getGame().getCurrentPlayer().moveItemFromInventoryToTrash(itemName, -1)) {
            return new Result(false, "you don't have " +
                    itemName + " in your inventory!");
        }
        return new Result(true,  itemName.toLowerCase() + " has been completely removed from inventory.");
    }

    public static Result showAbility() {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Player player = App.getGame().getCurrentPlayer();
        String result = "ability levels:\nfarming : " + player.getAbilityLevel(Skill.Farming) +
                "\nmining : " + player.getAbilityLevel(Skill.Mining) + "\nforaging : " +
                player.getAbilityLevel(Skill.Foraging) + "\nfishing : " + player.getAbilityLevel(Skill.Fishing);

        return new Result(true, result);

    }

    public static Result cheatItem(String type, String name, String stringCount) {
        int count = Integer.parseInt(stringCount);
        Item item = getItemByTypeName(type, name);

        if (App.getGame().getCurrentPlayer().getInventory().getRemainedCapacity() < 1) { // TODO: Aynaz نمیدونم چطور مشخص میکنی جا داره یا نه ولی ارور مربوطه
            return new Result(false, "no space");
        } else if (count < 1) {
            return new Result(false, "Count must be a positive number!");
        } else if (!isTypeValid(type)) {
            StringBuilder validType = new StringBuilder();
            for (ItemType itemType: ItemType.values()) {
                validType.append(itemType.getName()).append(" ");
            }
            return new Result(false, "Type is invalid. Valid types: {" + validType + "}");
        } else if (item == null) {
            return new Result(false, "Name is invalid!");
        }

        App.getGame().getCurrentPlayer().getInventory().addItem(item, count);
        return new Result(true, "Now you have " + count +" of " + name + " in your inventory");
    }

    public static boolean isParsableInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isTypeValid(String type) {
        for (ItemType itemType: ItemType.values()) {
            if (itemType.getName().equals(type))
                return true;
        }
        return false;
    }

    public static Item getItemByTypeName(String type, String name) {
        //TODO: change some constructors and return items for each type
        switch (type) {
            case "tree":
                return getTree(name);
            case "sapling":
                return getSapling(name);
            case "crop":
                return getCrop(name);
            case "fruit":
                return getFruit(name);
            case "seed":
                return getSeed(name);
            case "foraging crop":
                return getForagingCrop(name);
            case "foraging mineral":
                return getForagingMineral(name);
            case "stone":
                if (name.equals("stone")) return new Stone();
                else return null;
            case "wood":
                if (name.equals("wood")) return new Wood();
                else return null;
            case "craft":
                return getCraft(name);
            case "food":
                return getFood(name);
            case "fish":
                return getFish(name);
            case "animal product":
                return getAnimalProduct(name);
                //TODO: Aynaz check
            case "ingredient":
                return getIngredient(name);
            case "fertilizer":
                return getFertilizer(name);
            case "gift":
                return getGift(name);
            default:
                return null;
        }
    }

    private static Tree getTree (String name) {
        for (TreeType t : TreeType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new Tree(t);
            }
        }
        return null;
    }

    private static Sapling getSapling (String name) {
        for (SaplingType t : SaplingType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new Sapling(t);
            }
        }
        return null;
    }

    public static Crop getCrop (String name) {
        for (CropType t : CropType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new Crop(t);
            }
        }
        return null;
    }

    private static Fruit getFruit (String name) {
        for (FruitType t : FruitType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new Fruit(t);
            }
        }
        return null;
    }

    private static Seed getSeed (String name) {
        for (SeedType t : SeedType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new Seed(t);
            }
        }
        return null;
    }

    private static ForagingCrop getForagingCrop (String name) {
        for (ForagingCropType t : ForagingCropType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new ForagingCrop(t);
            }
        }
        return null;
    }

    private static ForagingMineral getForagingMineral (String name) {
        for (ForagingMineralType t : ForagingMineralType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new ForagingMineral(t);
            }
        }
        return null;
    }

    private static Food getFood (String name) {
        for (FoodType t : FoodType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new Food(t);
            }
        }
        return null;
    }

    public static Craft getCraft (String name) {
        for (CraftType t : CraftType.values()) {
            if (t.getName().equalsIgnoreCase(name)) {
                return new Craft(t);
            }
        }
        return null;
    }

    private static Fish getFish (String name) {
        for (FishType f : FishType.values()) {
            if (f.getName().equalsIgnoreCase(name)) {
                return new Fish(f, 0);
            }
        }
        return null;
    }

    private static AnimalProduct getAnimalProduct (String name) {
        for (AnimalProductType a : AnimalProductType.values()) {
            if (a.getName().equalsIgnoreCase(name)) {
                return new AnimalProduct(a);
            }
        }
        return null;
    }

    private static Ingredient getIngredient (String name) {
        for (IngredientType i : IngredientType.values()) {
            if (i.getName().equalsIgnoreCase(name)) {
                return new Ingredient(i);
            }
        }
        return null;
    }

    private static GiftItem getGift (String name) {
        for (GiftType i : GiftType.values()) {
            if (i.getName().equalsIgnoreCase(name)) {
                return new GiftItem(i);
            }
        }
        return null;
    }

    private static Fertilizer getFertilizer (String name) {
        for (FertilizerType i : FertilizerType.values()) {
            if (i.getName().equalsIgnoreCase(name)) {
                return new Fertilizer(i);
            }
        }
        return null;
    }
}
