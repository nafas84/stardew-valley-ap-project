package io.Ap.StardewValley.Common.Model.Shop.MarniesRanch;

import io.Ap.StardewValley.Common.Model.Animals.Animal;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.Ingredient;
import io.Ap.StardewValley.Common.Model.Cooking.IngredientType;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;
import io.Ap.StardewValley.Common.Model.Tool.MilkPail;
import io.Ap.StardewValley.Common.Model.Tool.Shear;

import java.util.ArrayList;
import java.util.HashMap;

public class MarniesRanch implements Shop {
    private final ShopType type;
    int milkPailNumber = 1;
    int shearNumber = 1;
    private HashMap<MarniesAnimals, Integer> animals = new HashMap<>();

    public MarniesRanch() {
        type = ShopType.MarniesRanch;
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
        String result = "** Marnie's Ranch shop all products:\n\n* shop inventory:\n\n";
        result = result + "+Hay:\ndescription: Dried grass used as animal food.\nprice: 50\ndaily limit: unlimited\n\n";
        result = result + "+Milk Pail:\ndescription: Gather milk from your animals.\nprice: 1,000g\ndaily limit: 1\n\n";
        result = result + "+Shears:\ndescription: Use this to collect wool from sheep\nprice: 1,000g\ndaily limit: 1\n\n";
        result = result + "* animals:\n\n";
        for (MarniesAnimals a : animals.keySet()) {
            result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                    "\nprice: " + a.getPrice() + "\ndaily limit: 2\n\n";
        }
        return result;
    }

    @Override
    public String showAvailableProducts() {
        String result = "** Marnie's Ranch shop available products:\n\n* shop inventory:\n\n";
        result = result + "+Hay:\ndescription: Dried grass used as animal food.\nprice: 50\ndaily limit: unlimited\n\n";
        if (milkPailNumber > 0) {
            result = result + "+Milk Pail:\ndescription: Gather milk from your animals.\nprice: 1,000g\ndaily limit: 1\n\n";
        }
        if (shearNumber > 0) {
            result = result + "+Shears:\ndescription: Use this to collect wool from sheep\nprice: 1,000g\ndaily limit: 1\n\n";
        }

        result = result + "* available animals:\n\n";
        for (MarniesAnimals a : animals.keySet()) {
            if (animals.get(a) > 0) {
                result = result + "+" + a.getName() + ":\ndescription: " + a.getDescription() +
                        "\nprice: " + a.getPrice() + "\ndaily limit: 2\n\n";
            }
        }
        return result;
    }

    @Override
    public Result buy(String productName, int number, String animalName) {
        if (productName == null) {
            return new Result(false, "invalid product name!");
        }
        if (productName.equalsIgnoreCase("hay")) {
            if (number * 50 > App.getGame().getCurrentPlayer().getCount()) {
                return new Result(false, "you don't have enough money!\n" +
                        number * 50 + "g is needed.");
            }
            if (!App.getGame().getCurrentPlayer().addItemToInventory(new Ingredient(IngredientType.Hay), number)) {
                return new Result(false, "can't add this item(s) to your inventory!");
            }
            return new Result(true, number + " of hay added to your inventory.");
        }

        if (productName.equalsIgnoreCase("milk pail")) {
            if (number > milkPailNumber) {
                return new Result(false, "due to the daily limit of this item, you can't buy it now!");
            }
            if (number * 1000 > App.getGame().getCurrentPlayer().getCount()) {
                return new Result(false, "you don't have enough money!\n" +
                        number * 1000 + "g is needed.");
            }
            if (App.getGame().getCurrentPlayer().getInventory().hasItemWithName("milk pail") != null) {
                return new Result(false, "you already have a milk pail!");
            }
            if (!App.getGame().getCurrentPlayer().addItemToInventory(new MilkPail(), number)) {
                return new Result(false, "can't add this item to your inventory!");
            }
            milkPailNumber = 0;
            return new Result(true, "milk pail added to your inventory.");
        }

        if (productName.equalsIgnoreCase("shears") || productName.equalsIgnoreCase("shear")) {
            if (number > shearNumber) {
                return new Result(false, "due to the daily limit of this item, you can't buy it now!");
            }
            if (number * 1000 > App.getGame().getCurrentPlayer().getCount()) {
                return new Result(false, "you don't have enough money!\n" +
                        number * 1000 + "g is needed.");
            }
            if (App.getGame().getCurrentPlayer().getInventory().hasItemWithName("shear") != null) {
                return new Result(false, "you already have a shear!");
            }
            if (!App.getGame().getCurrentPlayer().addItemToInventory(new Shear(), number)) {
                return new Result(false, "can't add this item to your inventory!");
            }
            shearNumber = 0;
            return new Result(true, "shears added to your inventory.");
        }

        if (animalName == null) {
            return new Result(false, "invalid animal name!");
        }
        for (MarniesAnimals a : animals.keySet()) {
            if (productName.equalsIgnoreCase(a.getName())) {
                if (1 > animals.get(a)) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (a.getPrice() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result(false, "you don't have enough money!\n" +
                            a.getPrice() + "g is needed.");
                }
                //TODO: Aynaz اقا قبلش چک کن ببین اصلا قفس یا طویله داره یا نه بعد لولش جک کن
                if (App.getGame().getCurrentPlayer().getFarmBuildingLevel(a.getBuildingType().getType()) < a.getBuildingType().getLevel()) {
                    return new Result(false,  a.getBuildingType().getName() + " or a better version is needed!");
                }
                if (App.getGame().getCurrentPlayer().getFarmBuildingCapacity(a.getBuildingType().getType()) <= 0) {
                    return new Result(false, "you don't have enough capacity!");
                }
                //TODO: Aynaz بعدش از ظرفیت اونجا کم کن جایی کم نمیشه
                App.getGame().getCurrentPlayer().addAnimal(new Animal(a.getType(), animalName));
                return new Result(true, productName + " named " + animalName + " is now added to your animals.");
            }
        }
        return new Result(false, "product name is invalid!");
    }

    @Override
    public void resetStock() {
        milkPailNumber = 1;
        shearNumber = 1;
        for(MarniesAnimals a : MarniesAnimals.values()) {
            animals.put(a, 2);
        }
    }

    @Override
    public ArrayList<ProductData> getProductData() {
        ArrayList<ProductData> productData = new ArrayList<>();
        for (MarniesInventory s : MarniesInventory.values()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), s.getDailyLimit(), s.getDescription()));
        }
        return productData;
    }
}
