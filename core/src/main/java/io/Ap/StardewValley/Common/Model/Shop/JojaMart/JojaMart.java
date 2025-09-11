package io.Ap.StardewValley.Common.Model.Shop.JojaMart;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.Ingredient;
import io.Ap.StardewValley.Common.Model.Plants.Seed;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class JojaMart implements Shop {
    private final ShopType type;
    private final ArrayList<JojaIngredients> ingredients = new ArrayList<>(Arrays.asList(JojaIngredients.values()));
    private HashMap<JojaSpringSeeds, Integer> springSeeds = new HashMap<>();
    private HashMap<JojaSummerSeeds, Integer> summerSeeds = new HashMap<>();
    private HashMap<JojaFallSeeds, Integer> fallSeeds = new HashMap<>();
    private HashMap<JojaWinterSeeds, Integer> winterSeeds = new HashMap<>();

    public JojaMart () {
        type = ShopType.JojaMart;
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
        String result = "** Joja Mart shop all products:\n\n* Permanent products:\n\n";
        for (JojaIngredients i : ingredients) {
            result = result + "+" + i.getName() + ":\ndescription: " + i.getDescription() +
                    "\nprice: " + i.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        result = result + "* Spring Seeds:\n\n";
        for (JojaSpringSeeds s : springSeeds.keySet()) {
            if (s != JojaSpringSeeds.GrassStarter) {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\nprice: " + s.getPrice() + "\ndaily limit: " + s.getDailyLimit() + "\n\n";
            }
            else {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
            }
        }
        result = result + "* Summer Seeds:\n\n";
        for (JojaSummerSeeds s : summerSeeds.keySet()) {
            if (s != JojaSummerSeeds.GrassStarter) {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\nprice: " + s.getPrice() + "\ndaily limit: " + s.getDailyLimit() + "\n\n";
            }
            else {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
            }
        }
        result = result + "* Fall Seeds:\n\n";
        for (JojaFallSeeds s : fallSeeds.keySet()) {
            if (s != JojaFallSeeds.GrassStarter) {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\nprice: " + s.getPrice() + "\ndaily limit: " + s.getDailyLimit() + "\n\n";
            }
            else {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
            }
        }
        result = result + "* Winter Seeds:\n\n";
        for (JojaWinterSeeds s : winterSeeds.keySet()) {
            if (s != JojaWinterSeeds.GrassStarter) {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\nprice: " + s.getPrice() + "\ndaily limit: " + s.getDailyLimit() + "\n\n";
            }
            else {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
            }
        }
        return result;
    }

    @Override
    public String showAvailableProducts() {
        String result = "** Joja Mart shop available products:\n\n* Permanent products:\n\n";
        for (JojaIngredients i : ingredients) {
            result = result + "+" + i.getName() + ":\ndescription: " + i.getDescription() +
                    "\nprice: " + i.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Spring) {
            result = result + "* Spring Seeds:\n\n";
            for (JojaSpringSeeds s : springSeeds.keySet()) {
                if (s != JojaSpringSeeds.GrassStarter && springSeeds.get(s) > 0) {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\nprice: " + s.getPrice() + "\ndaily limit: " + s.getDailyLimit() + "\n\n";
                }
                else {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
                }
            }
        }

        else if (App.getGame().getCurrentTime().getSeason() == Season.Summer) {
            result = result + "* Summer Seeds:\n\n";
            for (JojaSummerSeeds s : summerSeeds.keySet()) {
                if (s != JojaSummerSeeds.GrassStarter && summerSeeds.get(s) > 0) {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\nprice: " + s.getPrice() + "\ndaily limit: " + s.getDailyLimit() + "\n\n";
                }
                else {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
                }
            }
        }

        else if (App.getGame().getCurrentTime().getSeason() == Season.Fall) {
            result = result + "* Fall Seeds:\n\n";
            for (JojaFallSeeds s : fallSeeds.keySet()) {
                if (s != JojaFallSeeds.GrassStarter && fallSeeds.get(s) > 0) {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\nprice: " + s.getPrice() + "\ndaily limit: " + s.getDailyLimit() + "\n\n";
                }
                else {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
                }
            }
        }

        else if (App.getGame().getCurrentTime().getSeason() == Season.Fall) {
            result = result + "* Winter Seeds:\n\n";
            for (JojaWinterSeeds s : winterSeeds.keySet()) {
                if (s != JojaWinterSeeds.GrassStarter && winterSeeds.get(s) > 0) {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\nprice: " + s.getPrice() + "\ndaily limit: " + s.getDailyLimit() + "\n\n";
                }
                else {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
                }
            }
        }

        return result;
    }

    @Override
    public Result buy(String productName, int number, String animalName) {
        if (productName == null) {
            return new Result(false, "invalid name!");
        }
        for (JojaIngredients i : ingredients) {
            if (productName.equalsIgnoreCase(i.getName())) {
                if (i.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result(false, "you don't have enough money!\n" + i.getPrice() * number +
                            "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Ingredient(i.getType()), number)) {
                    return new Result(false, "you can't add this item to your inventory!");
                }
                return new Result(true, number + " of " + i.getName() + " added to your inventory.");
            }
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Spring) {
            for (JojaSpringSeeds s : springSeeds.keySet()) {
                if (productName.equalsIgnoreCase(s.getName())) {
                    if (springSeeds.get(s) < number) {
                        return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                    }
                    if (s.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
                        return new Result(false, "you don't have enough money!\n" + s.getPrice() * number +
                                "g is needed.");
                    }
                    if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(s.getSeedType()), number)) {
                        return new Result(false, "you can't add this item to your inventory!");
                    }
                    return new Result(true, number + " of " + s.getName() + " added to your inventory.");
                }
            }
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Summer) {
            for (JojaSummerSeeds s : summerSeeds.keySet()) {
                if (productName.equalsIgnoreCase(s.getName())) {
                    if (summerSeeds.get(s) < number) {
                        return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                    }
                    if (s.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
                        return new Result(false, "you don't have enough money!\n" + s.getPrice() * number +
                                "g is needed.");
                    }
                    if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(s.getSeedType()), number)) {
                        return new Result(false, "you can't add this item to your inventory!");
                    }
                    return new Result(true, number + " of " + s.getName() + " added to your inventory.");
                }
            }
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Fall) {
            for (JojaFallSeeds s : fallSeeds.keySet()) {
                if (productName.equalsIgnoreCase(s.getName())) {
                    if (fallSeeds.get(s) < number) {
                        return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                    }
                    if (s.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
                        return new Result(false, "you don't have enough money!\n" + s.getPrice() * number +
                                "g is needed.");
                    }
                    if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(s.getSeedType()), number)) {
                        return new Result(false, "you can't add this item to your inventory!");
                    }
                    return new Result(true, number + " of " + s.getName() + " added to your inventory.");
                }
            }
        }
        if (App.getGame().getCurrentTime().getSeason() == Season.Winter) {
            for (JojaWinterSeeds s : winterSeeds.keySet()) {
                if (productName.equalsIgnoreCase(s.getName())) {
                    if (winterSeeds.get(s) < number) {
                        return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                    }
                    if (s.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
                        return new Result(false, "you don't have enough money!\n" + s.getPrice() * number +
                                "g is needed.");
                    }
                    if (!App.getGame().getCurrentPlayer().addItemToInventory(new Seed(s.getSeedType()), number)) {
                        return new Result(false, "you can't add this item to your inventory!");
                    }
                    return new Result(true, number + " of " + s.getName() + " added to your inventory.");
                }
            }
        }
        return new Result(false, productName + " is not available");

    }

    @Override
    public void resetStock() {
        for (JojaSpringSeeds s : JojaSpringSeeds.values()) {
            springSeeds.put(s, s.getDailyLimit());
        }
        for (JojaSummerSeeds s : JojaSummerSeeds.values()) {
            summerSeeds.put(s, s.getDailyLimit());
        }
        for (JojaFallSeeds s : JojaFallSeeds.values()) {
            fallSeeds.put(s, s.getDailyLimit());
        }
        for (JojaWinterSeeds s : JojaWinterSeeds.values()) {
            winterSeeds.put(s, s.getDailyLimit());
        }
    }

    @Override
    public ArrayList<ProductData> getProductData() {
        Season season = App.getGame().getCurrentTime().getSeason();
        ArrayList<ProductData> productData = new ArrayList<>();
        for (JojaIngredients s : ingredients) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription()));
        }
        for (JojaSpringSeeds s : springSeeds.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription(), season == Season.Spring));
        }
        for (JojaSummerSeeds s : summerSeeds.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription(), season == Season.Summer));
        }
        for (JojaFallSeeds s : fallSeeds.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription(), season == Season.Fall));
        }
        for (JojaWinterSeeds s : winterSeeds.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription(), season == Season.Winter));
        }
        return productData;
    }
}
