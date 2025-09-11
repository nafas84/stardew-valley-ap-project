package io.Ap.StardewValley.Common.Model.Shop.TheStardropSaloon;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.Food;
import io.Ap.StardewValley.Common.Model.Cooking.Ingredient;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TheStardropSaloon implements Shop {
    private final ShopType type;
    private final ArrayList<StardropFood> food = new ArrayList<>(Arrays.asList(StardropFood.values()));
    private final ArrayList<StardropIngredients> ingredients = new ArrayList<>(Arrays.asList(StardropIngredients.values()));
    private HashMap<StardropFoodRecipes, Integer> recipes = new HashMap<>();

    public TheStardropSaloon () {
        this.type = ShopType.TheStarDropSaloon;
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
        String result = "** the Stardrop Saloon all products:\n\n";
        for (StardropIngredients i : ingredients) {
            result = result + "+" + i.getName() + ":\ndescription: " + i.getDescription() +
                    "\nprice: " + i.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (StardropFood f : food) {
            result = result + "+" + f.getName() + ":\ndescription: " + f.getDescription() +
                    "\nprice: " + f.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (StardropFoodRecipes r : recipes.keySet()) {
            result = result + "+" + r.getName() + ":\ndescription: " + r.getDescription() +
                    "\nprice: " + r.getPrice() + "\ndaily limit: " + r.getDailyLimit() + "\n\n";
        }
        return result;
    }

    @Override
    public String showAvailableProducts() {
        String result = "** the Stardrop Saloon available products:\n\n";
        for (StardropIngredients i : ingredients) {
            result = result + "+" + i.getName() + ":\ndescription: " + i.getDescription() +
                    "\nprice: " + i.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (StardropFood f : food) {
            result = result + "+" + f.getName() + ":\ndescription: " + f.getDescription() +
                    "\nprice: " + f.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        for (StardropFoodRecipes r : recipes.keySet()) {
            if (recipes.get(r) > 0) {
                result = result + "+" + r.getName() + ":\ndescription: " + r.getDescription() +
                        "\nprice: " + r.getPrice() + "\ndaily limit: " + r.getDailyLimit() + "\n\n";
            }
        }
        return result;
    }

    @Override
    public Result buy(String productName, int number, String animalName) {
        if (productName == null) {
            return new Result(false, "invalid product name!");
        }
        for (StardropFoodRecipes r : recipes.keySet()) {
            if (productName.equalsIgnoreCase(r.getName())) {
                if (number > 1) {
                    return new Result(false, "just one of each recipe can be bought!");
                }
                if (recipes.get(r) < number) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (r.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result(false, "you don't have enough money!\n" + r.getPrice() * number +
                            "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addToFoodRecipes(r.getType())) {
                    return new Result(false, "you already have this recipe!");
                }
                App.getGame().getCurrentPlayer().addCount(-1 * number * r.getPrice());
                recipes.put(r, 0);
                return new Result(true, "fish smoker added to your craft recipes.");
            }
        }
        for (StardropIngredients i : ingredients) {
            if (productName.equalsIgnoreCase(i.getName())) {
                if (i.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result(false, "you don't have enough money!\n" + i.getPrice() * number +
                            "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Ingredient(i.getType()), number)) {
                    return new Result(false, "you can't add this item to your inventory!");
                }
                return new Result(true, number + " of " + i.getName() + " is added to your inventory.");
            }
        }

        for (StardropFood f : food) {
            if (productName.equalsIgnoreCase(f.getName())) {
                if (f.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result(false, "you don't have enough money!\n" + f.getPrice() * number +
                            "g is needed.");
                }
                if (!App.getGame().getCurrentPlayer().addItemToInventory(new Food(f.getType()), number)) {
                    return new Result(false, "you can't add this item to your inventory!");
                }
                return new Result(true, number + " of " + f.getName() + " is added to your inventory.");
            }
        }

        return new Result(false, "product name is invalid!");
    }

    @Override
    public void resetStock() {
        for (StardropFoodRecipes r : StardropFoodRecipes.values()) {
            recipes.put(r, 1);
        }
    }

    public ArrayList<ProductData> getProductData() {
        ArrayList<ProductData> productData = new ArrayList<>();
        for (StardropFood s : food) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription()));
        }
        for (StardropFoodRecipes s : recipes.keySet()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), recipes.get(s), s.getDescription()));
        }
        for (StardropIngredients s : ingredients) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 10000, s.getDescription()));
        }
        return productData;
    }
}
