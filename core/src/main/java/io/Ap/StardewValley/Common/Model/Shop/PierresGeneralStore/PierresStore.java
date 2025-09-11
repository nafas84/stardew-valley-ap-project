package io.Ap.StardewValley.Common.Model.Shop.PierresGeneralStore;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.Ingredient;
import io.Ap.StardewValley.Common.Model.Plants.Fertilizer;
import io.Ap.StardewValley.Common.Model.Plants.Sapling;
import io.Ap.StardewValley.Common.Model.Plants.Seed;
import io.Ap.StardewValley.Common.Model.Player.GiftItem;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PierresStore implements Shop {
    private final ShopType type;
    private HashMap<Backpacks, Integer> backpack = new HashMap<>();
    private HashMap<PierresCraftRecipes, Integer> recipes = new HashMap<>();
    private final ArrayList<PierresFertilizers> fertilizers = new ArrayList<>(Arrays.asList(PierresFertilizers.values()));
    private HashMap<PierresGifts, Integer> gifts = new HashMap<>();
    private final ArrayList<PierresIngredients> ingredients = new ArrayList<>(Arrays.asList(PierresIngredients.values()));
    private final ArrayList<PierresSaplings> sapling = new ArrayList<>(Arrays.asList(PierresSaplings.values()));
    private HashMap<SpringSeeds, Integer> springSeeds = new HashMap<>();
    private HashMap<SummerSeeds, Integer> summerSeeds = new HashMap<>();
    private HashMap<FallSeeds, Integer> fallSeeds = new HashMap<>();
    private HashMap<WinterSeeds, Integer> winterSeeds = new HashMap<>();

    public PierresStore () {
        type = ShopType.PierresGeneralStore;
        resetStock();
    }

    @Override
    public ShopType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.getShopName();
    }

    @Override
    public String getOwnerName() {
        return type.getOwnerName();
    }

    @Override
    public int getOpeningTime() {
        return type.getOpeningTime();
    }

    @Override
    public int getClosingTime() {
        return type.getClosingTime();
    }

    @Override
    public String getShopInformation() {
        return "shop name: " + type.getShopName() + "\nowner name: " +type.getOwnerName() +
                "\nopening time: " + type.getOpeningTime() + " AM\nclosing time: " +
                type.getClosingTime() + " PM";
    }

    @Override
    public String showAllProducts() {
        String result = "** The Pierre's General Store all products:\n\n* Permanent products:\n\n";
        for (PierresIngredients a : ingredients) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (PierresGifts a : gifts.keySet()) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
        }
        for (PierresCraftRecipes a : recipes.keySet()) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
        }
        for (PierresFertilizers a : fertilizers) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (PierresSaplings a : sapling) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (Backpacks a : backpack.keySet()) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
        }
        result = result + "* Spring products:\n\n";
        for (SpringSeeds a : springSeeds.keySet()) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
        }
        result = result + "* Summer products:\n\n";
        for (SummerSeeds a : summerSeeds.keySet()) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
        }
        result = result + "* Fall products:\n\n";
        for (FallSeeds a : fallSeeds.keySet()) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
        }
        result = result + "* Winter products:\n\n";
        for (WinterSeeds a : winterSeeds.keySet()) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
        }
        return result;
    }

    @Override
    public String showAvailableProducts() {
        String result = "** The Pierre's General Store available products:\n\n* Permanent products:\n\n";
        for (PierresIngredients a : ingredients) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (PierresGifts a : gifts.keySet()) {
            if (gifts.get(a) > 0) {
                result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                        "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
            }
        }
        for (PierresCraftRecipes a : recipes.keySet()) {
            if (recipes.get(a) > 0) {
                result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                        "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
            }
        }
        for (PierresFertilizers a : fertilizers) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (PierresSaplings a : sapling) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (Backpacks a : backpack.keySet()) {
            if (backpack.get(a) > 0) {
                result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                        "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
            }
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Spring) {
            result = result + "* Spring products:\n\n";
            for (SpringSeeds a : springSeeds.keySet()) {
                if (springSeeds.get(a) > 0) {
                    if (a != SpringSeeds.GrassStarter) {
                        result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                                "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
                    }
                    else {
                        result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                                "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
                    }
                }
            }
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Summer) {
            result = result + "* Summer products:\n\n";
            for (SummerSeeds a : summerSeeds.keySet()) {
                if (summerSeeds.get(a) > 0) {
                    if (a != SummerSeeds.GrassStarter) {
                        result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                                "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
                    }
                    else {
                        result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                                "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
                    }
                }
            }
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Fall) {
            result = result + "* Fall products:\n\n";
            for (FallSeeds a : fallSeeds.keySet()) {
                if (fallSeeds.get(a) > 0) {
                    if (a != FallSeeds.GrassStarter) {
                        result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                                "\nprice: " + a.getPrice() + "\ndaily limit: " + a.getDailyLimit() + "\n\n";
                    }
                    else {
                        result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                                "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
                    }
                }
            }
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Winter) {
            result = result + "* Winter products:\n\n";
            for (WinterSeeds a : winterSeeds.keySet()) {
                if (winterSeeds.get(a) > 0) {
                    result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                            "\nprice: " + a.getPrice() + "\ndaily limit: unlimited\n\n";
                }
            }
        }
        return result;
    }

    @Override
    public Result buy(String productName, int number, String animalName) {
        if (productName == null) {
            return new Result(false, "invalid product name!");
        }
        for (PierresIngredients a : ingredients) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Ingredient(a.getType()), number)) {
                    return new Result (false, "you can't add this item(s) to your inventory!");
                }
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                return new Result(true, number + " " + a.getName() + " added to inventory.");
            }
        }

        for (PierresGifts a : gifts.keySet()) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (gifts.get(a) < number) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new GiftItem(a.getType()), number)) {
                    return new Result (false, "you can't add this item(s) to your inventory!");
                }
                int newNumber = gifts.get(a) - number;
                gifts.put(a, newNumber);
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                return new Result(true, number + " " + a.getName() + " added to inventory.");
            }
        }

        for (PierresCraftRecipes a : recipes.keySet()) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (number > 1) {
                    return new Result(false, "number can't be more than 1!");
                }
                if (recipes.get(a) < number) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addToCraftRecipes(a.getType())) {
                    return new Result (false, "you already have this recipe!");
                }
                int newNumber = recipes.get(a) - number;
                recipes.put(a, newNumber);
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                return new Result(true, number + " " + a.getName() + " added to your recipes.");
            }
        }

        for (PierresFertilizers a : fertilizers) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Fertilizer(a.getType()), number)) {
                    return new Result (false, "you can't add this item(s) to your inventory!");
                }
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                return new Result(true, number + " " + a.getName() + " added to inventory.");
            }
        }

        for (PierresSaplings a : sapling) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Sapling(a.getType(), true), number)) {
                    return new Result (false, "you can't add this item(s) to your inventory!");
                }
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                return new Result(true, number + " " + a.getName() + " added to inventory.");
            }
        }

        for (Backpacks a : backpack.keySet()) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (number > 1) {
                    return new Result(false, "number can't be more than 1!");
                }
                if (backpack.get(a) < number) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (a.getCapacity() <= App.getGame().getCurrentPlayer().getInventory().getCapacity()) {
                    return new Result(false, "the capacity of your own backpack is not less than this backpack!");
                }
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                App.getGame().getCurrentPlayer().getInventory().setCapacity(a.getCapacity());
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                int newNumber = backpack.get(a) - number;
                backpack.put(a, newNumber);
                return new Result(true, "your backpack capacity upgraded.");
            }
        }

        for (SpringSeeds a : springSeeds.keySet()) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (a.equals(SpringSeeds.GrassStarter)) {
                    if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                        return new Result (false, "you don't have enough money!\n" +
                                number * a.getPrice() + "g is needed.");
                    }
                    if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(a.getType()), number)) {
                        return new Result (false, "you can't add this item(s) to your inventory!");
                    }
                    return new Result(true, number + " " + a.getName() + " added to inventory.");
                }
                if (App.getGame().getCurrentTime().getSeason() != Season.Spring) {
                    return new Result(false, "this item is not available now!");
                }
                if (springSeeds.get(a) < number) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(a.getType()), number)) {
                    return new Result (false, "you can't add this item(s) to your inventory!");
                }
                int newNumber = springSeeds.get(a) - number;
                springSeeds.put(a, newNumber);
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                return new Result(true, number + " " + a.getName() + " added to inventory.");
            }
        }

        for (SummerSeeds a : summerSeeds.keySet()) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (a.equals(SpringSeeds.GrassStarter)) {
                    if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                        return new Result (false, "you don't have enough money!\n" +
                                number * a.getPrice() + "g is needed.");
                    }
                    if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(a.getType()), number)) {
                        return new Result (false, "you can't add this item(s) to your inventory!");
                    }
                    return new Result(true, number + " " + a.getName() + " added to inventory.");
                }
                if (App.getGame().getCurrentTime().getSeason() != Season.Summer) {
                    return new Result(false, "this item is not available now!");
                }
                if (summerSeeds.get(a) < 0) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(a.getType()), number)) {
                    return new Result (false, "you can't add this item(s) to your inventory!");
                }
                int newNumber = summerSeeds.get(a) - number;
                summerSeeds.put(a, newNumber);
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                return new Result(true, number + " " + a.getName() + " added to inventory.");
            }
        }

        for (FallSeeds a : fallSeeds.keySet()) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (a.equals(SpringSeeds.GrassStarter)) {
                    if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                        return new Result (false, "you don't have enough money!\n" +
                                number * a.getPrice() + "g is needed.");
                    }
                    if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(a.getType()), number)) {
                        return new Result (false, "you can't add this item(s) to your inventory!");
                    }
                    return new Result(true, number + " " + a.getName() + " added to inventory.");
                }
                if (App.getGame().getCurrentTime().getSeason() != Season.Fall) {
                    return new Result(false, "this item is not available now!");
                }
                if (fallSeeds.get(a) < number) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result (false, "you don't have enough money!\n" +
                            number * a.getPrice() + "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(a.getType()), number)) {
                    return new Result (false, "you can't add this item(s) to your inventory!");
                }
                int newNumber = fallSeeds.get(a) - number;
                fallSeeds.put(a, newNumber);
                App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                return new Result(true, number + " " + a.getName() + " added to inventory.");
            }
        }

        for (WinterSeeds a : winterSeeds.keySet()) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (a.equals(SpringSeeds.GrassStarter)) {
                    if (number * a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                        return new Result (false, "you don't have enough money!\n" +
                                number * a.getPrice() + "g is needed.");
                    }
                    if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(a.getType()), number)) {
                        return new Result (false, "you can't add this item(s) to your inventory!");
                    }
                    App.getGame().getCurrentPlayer().addCount(-1 * number * a.getPrice());
                    return new Result(true, number + " " + a.getName() + " added to inventory.");
                }
            }
        }

        return new Result(false, "product name is not valid!");
    }

    @Override
    public void resetStock() {
        for (Backpacks a : Backpacks.values()) {
            backpack.put (a, a.getDailyLimit());
        }
        for (PierresCraftRecipes a : PierresCraftRecipes.values()) {
            recipes.put(a, a.getDailyLimit());
        }
        for (PierresGifts a : PierresGifts.values()) {
            gifts.put(a, a.getDailyLimit());
        }
        for (SpringSeeds a : SpringSeeds.values()) {
            springSeeds.put(a, a.getDailyLimit());
        }
        for (SummerSeeds a : SummerSeeds.values()) {
            summerSeeds.put(a, a.getDailyLimit());
        }
        for (FallSeeds a : FallSeeds.values()) {
            fallSeeds.put(a, a.getDailyLimit());
        }
        for (WinterSeeds a : WinterSeeds.values()) {
            winterSeeds.put(a, a.getDailyLimit());
        }
    }

    @Override
    public ArrayList<ProductData> getProductData() {
        Season season = App.getGame().getCurrentTime().getSeason();
        ArrayList<ProductData> productData = new ArrayList<>();
        for (Backpacks s : backpack.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), backpack.get(s), s.getDescription()));
        }
        for (PierresCraftRecipes s : recipes.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), recipes.get(s), s.getDescription()));
        }
        for (PierresFertilizers s : fertilizers) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription()));
        }
        for (PierresGifts s : gifts.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), gifts.get(s), s.getDescription()));
        }
        for (PierresIngredients s : ingredients) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription()));
        }
        for (SpringSeeds s : springSeeds.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription(), season == Season.Spring));
        }
        for (SummerSeeds s : summerSeeds.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription(), season == Season.Summer));
        }
        for (FallSeeds s : fallSeeds.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription(), season == Season.Fall));
        }
        for (WinterSeeds s : winterSeeds.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription(), season == Season.Winter));
        }
        return productData;
    }
}
