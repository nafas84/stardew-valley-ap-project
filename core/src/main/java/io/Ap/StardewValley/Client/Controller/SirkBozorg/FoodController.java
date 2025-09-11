package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProduct;
import io.Ap.StardewValley.Common.Model.Animals.Fish;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.Food;
import io.Ap.StardewValley.Common.Model.Cooking.FoodRecipe;
import io.Ap.StardewValley.Common.Model.Cooking.FoodType;
import io.Ap.StardewValley.Common.Model.Cooking.Ingredient;
import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Plants.Crop;
import io.Ap.StardewValley.Common.Model.Plants.ForagingCrop;
import io.Ap.StardewValley.Common.Model.Plants.Fruit;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;

public class FoodController {
    public static Result refrigeratorPick (String itemName) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Player player = App.getGame().getCurrentPlayer();
        Item item;
        if (itemName == null) {
            return new Result (false, "invalid item name!");
        }
        if ((item = player.getRefrigerator().hasItemWithName(itemName)) == null) {
            return new Result(false, "you don't have an item with this name in your refrigerator!");
        }
        if (!player.addItemToInventory(item, 1)) {
            return new Result(false, "your inventory doesn't have enough capacity!");
        }
        return new Result(true, "item added to inventory successfully.");
    }

    public static Result refrigeratorPut (String itemName) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Player player = App.getGame().getCurrentPlayer();
        Item item;
        if (itemName == null) {
            return new Result (false, "invalid item name!");
        }
        if ((item = player.getInventory().hasItemWithName(itemName)) == null) {
            return new Result(false, "you don't have an item with this name in your inventory!");
        }
        if (!((item instanceof Crop) || (item instanceof Food) || (item instanceof AnimalProduct) || (item instanceof Fish) ||
                (item instanceof Fruit) || (item instanceof Ingredient) || (item instanceof ForagingCrop))) {
            return new Result(false, "item must be edible!");
        }
        if (!player.getRefrigerator().addItem(item)) {
            return new Result(false, "your refrigerator doesn't have enough capacity!");
            //in hicgvaght nabayad khoroogi dadeh besheh amalan
        }
        return new Result(true, "item added to refrigerator successfully.");
    }

    public static Result showRecipes () {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        String result = "Cooking recipes:\n";
        for (FoodRecipe r : App.getGame().getCurrentPlayer().getFoodRecipes()) {
            result = result + r.getName() + ": " + r.getRecipeString() + "\n";
        }
        return new Result(true, result);
    }

    public static Result cook (String foodName) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Player player = App.getGame().getCurrentPlayer();
        if (App.getGame().getTile(player.getCoordinate()).getBuildingType() != BuildingType.House) {
            return new Result(false, "you must be at home for using this command!");
        }
        if (foodName == null) {
            return new Result(false, "invalid food name!");
        }
        if (player.getEnergy() < 3) {
            return new Result(false, "you don't have enough energy for cooking!");
        }
        if (player.getInventory().getRemainedCapacity() <= 0) {
            return new Result(false, "your inventory doesn't have enough capacity!");
        }
        if (!recipeExistsAtAll(foodName)) {
            return new Result(false, "this food doesn't exist!");
        }
        FoodRecipe recipe;
        if ((recipe = findFoodRecipe(foodName)) == null) {
            return new Result(false, "you haven't learned this recipe yet!");
        }

        boolean success = canMakeFood(recipe).isSuccessful();
        if (!success) {
            return canMakeFood(recipe);
        }
        for (Item i : recipe.getRecipe().keySet()) {
            if (!App.getGame().getCurrentPlayer().removeItemFromInventory(i.getName(), recipe.getRecipe().get(i))) {
                App.getGame().getCurrentPlayer().removeItemFromRefrigerator(i.getName(), recipe.getRecipe().get(i));
            }
        }
        if (findFoodType(recipe) == null) {
            return new Result(false, "invalid craft name!"); //never happens (inshallah)
        }
        App.getGame().getCurrentPlayer().addEnergy(-3);
        App.getGame().getCurrentPlayer().addItemToInventory(new Food(findFoodType(recipe)), 1);
        return new Result(true, foodName + " added to inventory.");
    }

    public static Result eat (String foodName) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Player player = App.getGame().getCurrentPlayer();
        if (foodName == null) {
            return new Result(false, "invalid food name!");
        }
        Item item;
        if ((item = player.getInventory().hasItemWithName(foodName)) == null) {
            return new Result(false, foodName + " doesn't exist in your inventory!");
        }
        if (item instanceof Food food) {
            food.eat(player);
            player.removeItemFromInventory(foodName, 1);
            return new Result(true, "ate successfully.");
        }
        if (item instanceof Crop crop) {
            if (!crop.isEdible()) {
                return new Result(false, foodName + " is not edible!");
            }
            player.addEnergy(crop.getEnergy());
            player.removeItemFromInventory(foodName, 1);
        }
        if (item instanceof Fruit fruit) {
            if (!fruit.isEdible()) {
                return new Result(false, foodName + " is not edible!");
            }
            player.addEnergy(fruit.getEnergy());
            player.removeItemFromInventory(foodName, 1);
        }
        return new Result(false, "you can't eat this!");
    }

    public static boolean recipeExistsAtAll (String name) {
        for (FoodRecipe r : FoodRecipe.values()) {
            if (r.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static FoodRecipe findFoodRecipe (String name) {
        for (FoodRecipe r : App.getGame().getCurrentPlayer().getFoodRecipes()) {
            if (r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    public static Result canMakeFood (FoodRecipe r) {
        for (Item i : r.getRecipe().keySet()) {
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber(i.getName(), r.getRecipe().get(i)) &&
                    !App.getGame().getCurrentPlayer().getRefrigerator().hasItemWithNumber(i.getName(), r.getRecipe().get(i))) {
                return new Result (false, "you don't have enough " + i.getName() + " in your inventory!");
            }
        }
        return new Result(true, "everything's available.");
    }

    public static FoodType findFoodType (FoodRecipe recipe) {
        for (FoodType c : FoodType.values()) {
            if (c.getRecipe() == recipe) {
                return c;
            }
        }
        return null;
    }





    // second phase:

    public static Result refrigeratorPickThroughScreen (String itemName) {
        Player player = App.getGame().getCurrentPlayer();
        Item item;
        if (itemName == null) {
            return new Result (false, "invalid item name!");
        }
        if ((item = player.getRefrigerator().hasItemWithName(itemName)) == null) {
            return new Result(false, "you don't have an item with this name in your refrigerator!");
        }
        if (!player.addItemToInventory(item, player.getRefrigerator().getItemQuantity(item))) {
            return new Result(false, "your inventory doesn't have enough capacity!");
        }
        player.getRefrigerator().removeItem(item.getName(), -1);
        return new Result(true, "item added to inventory successfully.");
    }

    public static Result refrigeratorPutThroughScreen (String itemName) {
        Player player = App.getGame().getCurrentPlayer();
        Item item;
        if (itemName == null) {
            return new Result (false, "invalid item name!");
        }
        if ((item = player.getInventory().hasItemWithName(itemName)) == null) {
            return new Result(false, "you don't have an item with this name in your inventory!");
        }
        if (!((item instanceof Crop) || (item instanceof Food) || (item instanceof AnimalProduct) || (item instanceof Fish) ||
                (item instanceof Fruit) || (item instanceof Ingredient) || (item instanceof ForagingCrop))) {
            return new Result(false, "item must be edible!");
        }
        if (!player.getRefrigerator().addItem(item, player.getInventory().getItemQuantity(item))) {
            return new Result(false, "your refrigerator doesn't have capacity!");
        }
        player.getInventory().removeItem(item.getName(), -1);
        return new Result(true, "item added to refrigerator successfully.");
    }

    public static Result cookThroughScreen (String foodName) {

        Player player = App.getGame().getCurrentPlayer();
        if (App.getGame().getTile(player.getCoordinate()).getBuildingType() != BuildingType.House) {
            return new Result(false, "you must be at home for using this command!");
        }
        if (foodName == null) {
            return new Result(false, "invalid food name!");
        }
        if (player.getEnergy() < 3) {
            return new Result(false, "you don't have enough energy for cooking!");
        }
        if (player.getInventory().getRemainedCapacity() <= 0) {
            return new Result(false, "your inventory doesn't have enough capacity!");
        }
        if (!recipeExistsAtAll(foodName)) {
            return new Result(false, "this food doesn't exist!");
        }
        FoodRecipe recipe;
        if ((recipe = findFoodRecipe(foodName)) == null) {
            return new Result(false, "you haven't learned this recipe yet!");
        }

        boolean success = canMakeFood(recipe).isSuccessful();
        if (!success) {
            return canMakeFood(recipe);
        }
        for (Item i : recipe.getRecipe().keySet()) {
            if (!App.getGame().getCurrentPlayer().removeItemFromInventory(i.getName(), recipe.getRecipe().get(i))) {
                App.getGame().getCurrentPlayer().removeItemFromRefrigerator(i.getName(), recipe.getRecipe().get(i));
            }
        }
        if (findFoodType(recipe) == null) {
            return new Result(false, "invalid craft name!"); //never happens (inshallah)
        }
        App.getGame().getCurrentPlayer().addEnergy(-3);
        App.getGame().getCurrentPlayer().addItemToInventory(new Food(findFoodType(recipe)), 1);
        return new Result(true, foodName + " added to inventory.");
    }

    public static Result eatThroughScreen (String foodName) {
        Player player = App.getGame().getCurrentPlayer();
        if (foodName == null) {
            return new Result(false, "invalid food name!");
        }
        Item item;
        if ((item = player.getInventory().hasItemWithName(foodName)) == null) {
            return new Result(false, foodName + " doesn't exist in your inventory!");
        }
        if (item instanceof Food food) {
            food.eat(player);
            player.removeItemFromInventory(foodName, 1);
            return new Result(true, "ate successfully.");
        }
        if (item instanceof Crop crop) {
            if (!crop.isEdible()) {
                return new Result(false, foodName + " is not edible!");
            }
            player.addEnergy(crop.getEnergy());
            player.removeItemFromInventory(foodName, 1);
        }
        if (item instanceof Fruit fruit) {
            if (!fruit.isEdible()) {
                return new Result(false, foodName + " is not edible!");
            }
            player.addEnergy(fruit.getEnergy());
            player.removeItemFromInventory(foodName, 1);
        }
        return new Result(false, "you can't eat this!");
    }
}
