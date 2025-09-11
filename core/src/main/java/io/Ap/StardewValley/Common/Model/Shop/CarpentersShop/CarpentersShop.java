package io.Ap.StardewValley.Common.Model.Shop.CarpentersShop;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.FarmBuilding;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CarpentersShop implements Shop {
    private final ShopType type;
    private final ArrayList<CarpenterStock> stock = new ArrayList<>(List.of(CarpenterStock.Wood, CarpenterStock.Stone));
    private final HashMap<CarpenterBuildings, Integer> buildings = new HashMap<>();


    public CarpentersShop () {
        type = ShopType.CarpentersShop;
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
        String result = "** Carpenter's shop all products:\n\n* permanent stock:\n\n";
        for (CarpenterStock s : stock) {
            result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                    "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        result = result + "* farm buildings:\n\n";
        for (CarpenterBuildings s : buildings.keySet()) {
            if (s != CarpenterBuildings.ShippingBin) {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\ncost: " + s.getCost() + "g " + s.getWoodCost() + " woods " + s.getStonesCost() +
                        " stones\ndaily limit: 1\n\n";
            }
            else {
                result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                        "\ncost: " + s.getCost() + "g " + s.getWoodCost() + " woods " + s.getStonesCost() +
                        " stones\ndaily limit: unlimited\n\n";
            }
        }
        return result;
    }

    @Override
    public String showAvailableProducts() {
        String result = "** Carpenter's shop available products:\n\n* permanent stock:\n\n";
        for (CarpenterStock s : stock) {
            result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                    "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        result = result + "* farm buildings:\n\n";
        for (CarpenterBuildings s : buildings.keySet()) {
            if (buildings.get(s) > 0) {
                if (s != CarpenterBuildings.ShippingBin) {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\ncost: " + s.getCost() + "g " + s.getWoodCost() + " woods " + s.getStonesCost() +
                            " stones\ndaily limit: 1\n\n";
                }
                else {
                    result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                            "\ncost: " + s.getCost() + "g " + s.getWoodCost() + " woods " + s.getStonesCost() +
                            " stones\ndaily limit: unlimited\n\n";
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
        if (productName.equalsIgnoreCase("stone")) {
            if (20 * number > App.getGame().getCurrentPlayer().getCount()) {
                return new Result(false, "you don't have enough money!");
            }
            App.getGame().getCurrentPlayer().addCount(-20 * number);
            if (!App.getGame().getCurrentPlayer().addItemToInventory(new Stone(), number)) {
                return new Result(false, "can't add this item(s) to your inventory!");
            }
            return new Result(true, number + " stone added to inventory.");
        }
        if (productName.equalsIgnoreCase("wood")) {
            if (10 * number > App.getGame().getCurrentPlayer().getCount()) {
                return new Result(false, "you don't have enough money!");
            }
            App.getGame().getCurrentPlayer().addCount(-10 * number);
            if (!App.getGame().getCurrentPlayer().addItemToInventory(new Wood(), number)) {
                return new Result(false, "can't add this item(s) to your inventory!");
            }
            return new Result(true, number + " wood added to inventory.");
        }
        if (animalName == null) {
            return new Result(true, "use build command for buildings!");
        }
        for (CarpenterBuildings s : buildings.keySet()) {
            if (productName.equalsIgnoreCase(s.getName())) {
                if (buildings.get(s) <= 0 || number != 1) {
                    return new Result(false, "due to the daily limit of this item, you can't buy it now!");
                }
                if (number * s.getCost() > App.getGame().getCurrentPlayer().getCount()) {
                    return new Result(false, "you don't have enough money!");
                }
                if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("stone", s.getStonesCost() * number)) {
                    return new Result(false, "you don't have enough stones!\n" + s.getStonesCost() + " stones are needed.");
                }
                if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("wood", s.getWoodCost() * number)) {
                    return new Result(false, "you don't have enough woods!\n" + s.getWoodCost() + " woods are needed.");
                }
                for (FarmBuilding b : App.getGame().getCurrentPlayer().getMyFarmBuildings()) {
                    if (b.getType().getType() == s.getType().getType() && b.getType().getLevel() >= s.getType().getLevel()) {
                        return new Result(false, "you already have this building!");
                    }
                }
                // TODO: Aynaz بهت زنگ میزنم تهخ
                App.getGame().getCurrentPlayer().addCount(-1 * number * s.getCost());
                App.getGame().getCurrentPlayer().removeItemFromInventory("stone", s.getStonesCost());
                App.getGame().getCurrentPlayer().removeItemFromInventory("wood", s.getWoodCost());
                App.getGame().getCurrentPlayer().addToFarmBuildings(new FarmBuilding(s.getType()));
                return new Result(true, s.getName() + " added to farm buildings");
            }
        }
        return new Result(false, "invalid product name!");
    }

    @Override
    public void resetStock() {
        buildings.put(CarpenterBuildings.Barn, 1);
        buildings.put(CarpenterBuildings.BigBarn, 1);
        buildings.put(CarpenterBuildings.DeluxeBarn, 1);
        buildings.put(CarpenterBuildings.Coop, 1);
        buildings.put(CarpenterBuildings.BigCoop, 1);
        buildings.put(CarpenterBuildings.DeluxeCoop, 1);
        buildings.put(CarpenterBuildings.Well, 1);
        buildings.put(CarpenterBuildings.ShippingBin, Integer.MAX_VALUE);
    }

    @Override
    public ArrayList<ProductData> getProductData() {
        ArrayList<ProductData> productData = new ArrayList<>();
        for (CarpenterStock s : CarpenterStock.values()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), 100000, s.getDescription()));
        }
        return productData;
    }


    public ArrayList<ProductData> getBuildingData() {
        ArrayList<ProductData> productData = new ArrayList<>();
        for (CarpenterBuildings s : buildings.keySet()) {
            productData.add(new ProductData(s.getName(), s.getCost(), buildings.get(s), s.getDescription()));
        }
        return productData;
    }
}
