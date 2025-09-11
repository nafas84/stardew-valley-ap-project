package io.Ap.StardewValley.Common.Model.Shop.BlackSmith;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Plants.ForagingMineral;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ProductData;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;

import java.util.ArrayList;
import java.util.HashMap;

public class BlackSmith implements Shop {
    private final ShopType type;
    private HashMap<BlackSmithStock, Integer> stock = new HashMap<>();
    private int copperTool = 1;
    private int steelTool = 1;
    private int goldTool = 1;
    private int iridiumTool = 1;
    private int copperTrashCan = 1;
    private int steelTrashCan = 1;
    private int goldTrashCan = 1;
    private int iridiumTrashCan = 1;

    public BlackSmith () {
        type = ShopType.Blacksmith;
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
        String result = "** Black Smith shop all products:\n\n";
        for (BlackSmithStock s : stock.keySet()) {
            result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                    "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
        }
        result = result + "+upgrade to copper tool\ningredients: 25 copper ore, 5 coal\ncost: 2,000g\ndaily limit: 1\n\n" +
                "+upgrade to steel tool\ningredients: 25 iron ore, 5 coal\ncost: 5,000g\ndaily limit: 1\n\n" +
                "+upgrade to gold tool\ningredients: 25 gold ore, 5 coal\ncost: 10,000g\ndaily limit: 1\n\n" +
                "+upgrade to iridium tool\ningredients: 25 iridium ore, 5 coal\ncost: 25,000g\ndaily limit: 1\n\n" +
                "+upgrade to copper trash can\ningredients: 25 copper ore, 5 coal\ncost: 1,000g\ndaily limit: 1\n\n" +
                "+upgrade to iron trash can\ningredients: 25 iron ore, 5 coal\ncost: 2,500g\ndaily limit: 1\n\n" +
                "+upgrade to gold trash can\ningredients: 25 gold ore, 5 coal\ncost: 5,000g\ndaily limit: 1\n\n" +
                "+upgrade to iridium trash can\ningredients: 25 iridium ore, 5 coal\ncost: 12,500g\ndaily limit: 1";
        return result;
    }

    @Override
    public String showAvailableProducts() {
        String result = "** Black Smith shop available products:\n\n";
        for (BlackSmithStock s : stock.keySet()) {
            result = result + "+" + s.getName() + ":\ndescription: " + s.getDescription() +
                    "\nprice: " + s.getPrice() + "\ndaily limit: unlimited\n\n";
        }

        if (copperTool > 0) {
            result = result + "+upgrade to copper tool\ningredients: 25 copper ore, 5 coal\ncost: 2,000g\ndaily limit: 1\n\n";
        }
        if (steelTool > 0) {
            result = result + "+upgrade to steel tool\ningredients: 25 iron ore, 5 coal\ncost: 5,000g\ndaily limit: 1\n\n";
        }
        if (goldTool > 0) {
            result = result + "+upgrade to gold tool\ningredients: 25 gold ore, 5 coal\ncost: 10,000g\ndaily limit: 1\n\n";
        }
        if (iridiumTool > 0) {
            result = result + "+upgrade to iridium tool\ningredients: 25 iridium ore, 5 coal\ncost: 25,000g\ndaily limit: 1\n\n";
        }
        if (copperTrashCan > 0) {
            result = result +"+upgrade to copper trash can\ningredients: 25 copper ore, 5 coal\ncost: 1,000g\ndaily limit: 1\n\n";
        }
        if (steelTrashCan > 0) {
            result = result + "+upgrade to iron trash can\ningredients: 25 iron ore, 5 coal\ncost: 2,500g\ndaily limit: 1\n\n";
        }
        if (goldTrashCan > 0) {
            result = result + "+upgrade to gold trash can\ningredients: 25 gold ore, 5 coal\ncost: 5,000g\ndaily limit: 1\n\n";
        }
        if (iridiumTrashCan > 0) {
            result = result + "+upgrade to iridium trash can\ningredients: 25 iridium ore, 5 coal\ncost: 12,500g\ndaily limit: 1";
        }

        return result;
    }

    @Override
    public Result buy(String productName, int number, String animalName) {
        if (productName == null) {
            return new Result(false, "invalid product name!");
        }
        if (productName.equalsIgnoreCase("copper") || productName.equalsIgnoreCase("copper ore")) {
            return buyMineral(BlackSmithStock.CopperOre, number);
        }
        if (productName.equalsIgnoreCase("iron") || productName.equalsIgnoreCase("iron ore")) {
            return buyMineral(BlackSmithStock.IronOre, number);
        }
        if (productName.equalsIgnoreCase("coal")) {
            return buyMineral(BlackSmithStock.Coal, number);
        }
        if (productName.equalsIgnoreCase("gold") || productName.equalsIgnoreCase("gold ore")) {
            return buyMineral(BlackSmithStock.GoldOre, number);
        }
        if (productName.equalsIgnoreCase("copper trash can")) {
            if (copperTrashCan <= 0) {
                return new Result(false, "due to the daily limit of this item, you can't buy it now!");
            }
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("copper", 25)) {
                return new Result(false, "you don't have enough copper ores!\n25 copper ores are needed.");
            }
            else if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("coal", 5)) {
                return new Result(false, "you don't have enough coal!\n5 pieces are needed.");
            }
            else if (App.getGame().getCurrentPlayer().getCount() < 1000) {
                return new Result(false, "you don't have enough money!\ncost: 2000g.");
            }
            App.getGame().getCurrentPlayer().addCount(-1000);
            App.getGame().getCurrentPlayer().removeItemFromInventory("copper", 25);
            App.getGame().getCurrentPlayer().removeItemFromInventory("coal", 5);
            App.getGame().getCurrentPlayer().getInventory().setTrashCanLevel(2);
            copperTrashCan = 0;
            return new Result(true, "your trash can is now upgraded to a copper trash can.");
        }

        if (productName.equalsIgnoreCase("steel trash can")) {
            if (steelTrashCan <= 0) {
                return new Result(false, "due to the daily limit of this item, you can't buy it now!");
            }
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("iron", 25)) {
                return new Result(false, "you don't have enough iron ores!\n25 iron ores are needed.");
            }
            else if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("coal", 5)) {
                return new Result(false, "you don't have enough coal!\n5 pieces are needed.");
            }
            else if (App.getGame().getCurrentPlayer().getCount() < 2500) {
                return new Result(false, "you don't have enough money!\ncost: 5000g.");
            }
            App.getGame().getCurrentPlayer().addCount(-2500);
            App.getGame().getCurrentPlayer().removeItemFromInventory("iron", 25);
            App.getGame().getCurrentPlayer().removeItemFromInventory("coal", 5);
            App.getGame().getCurrentPlayer().getInventory().setTrashCanLevel(3);
            steelTrashCan = 0;
            return new Result(true, "your trash can is now upgraded to a steel trash can.");
        }

        if (productName.equalsIgnoreCase("gold trash can")) {
            if (goldTrashCan <= 0) {
                return new Result(false, "due to the daily limit of this item, you can't buy it now!");
            }
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("gold", 25)) {
                return new Result(false, "you don't have enough gold ores!\n25 gold ores are needed.");
            }
            else if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("coal", 5)) {
                return new Result(false, "you don't have enough coal!\n5 pieces are needed.");
            }
            else if (App.getGame().getCurrentPlayer().getCount() < 5000) {
                return new Result(false, "you don't have enough money!\ncost: 10000g.");
            }
            App.getGame().getCurrentPlayer().addCount(-5000);
            App.getGame().getCurrentPlayer().removeItemFromInventory("gold", 25);
            App.getGame().getCurrentPlayer().removeItemFromInventory("coal", 5);
            App.getGame().getCurrentPlayer().getInventory().setTrashCanLevel(4);
            goldTrashCan = 0;
            return new Result(true, "your trash can is now upgraded to a gold trash can.");
        }

        if (productName.equalsIgnoreCase("iridium trash can")) {
            if (iridiumTrashCan <= 0) {
                return new Result(false, "due to the daily limit of this item, you can't buy it now!");
            }
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("iridium", 25)) {
                return new Result(false, "you don't have enough iridium ores!\n25 iridium ores are needed.");
            }
            else if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("coal", 5)) {
                return new Result(false, "you don't have enough coal!\n5 pieces are needed.");
            }
            else if (App.getGame().getCurrentPlayer().getCount() < 12500) {
                return new Result(false, "you don't have enough money!\ncost: 25000g.");
            }
            App.getGame().getCurrentPlayer().addCount(-12500);
            App.getGame().getCurrentPlayer().removeItemFromInventory("iridium", 25);
            App.getGame().getCurrentPlayer().removeItemFromInventory("coal", 5);
            App.getGame().getCurrentPlayer().getInventory().setTrashCanLevel(5);
            iridiumTrashCan = 0;
            return new Result(true, "your trash can is now upgraded to an iridium trash can.");
        }

        else {
            return new Result(false, "invalid product name!");
        }
    }

    @Override
    public void resetStock() {
        stock.put(BlackSmithStock.CopperOre, Integer.MAX_VALUE);
        stock.put(BlackSmithStock.IronOre, Integer.MAX_VALUE);
        stock.put(BlackSmithStock.Coal, Integer.MAX_VALUE);
        stock.put(BlackSmithStock.GoldOre, Integer.MAX_VALUE);
        copperTool = 1;
        steelTool = 1;
        goldTool = 1;
        iridiumTool = 1;
        copperTrashCan = 1;
        steelTrashCan = 1;
        goldTrashCan = 1;
        iridiumTrashCan = 1;
    }

    @Override
    public ArrayList<ProductData> getProductData() {
        ArrayList<ProductData> productData = new ArrayList<>();
        for (BlackSmithStock s : BlackSmithStock.values()) {
            productData.add(new ProductData(s.getName(), s.getPrice(), stock.get(s), s.getDescription()));
        }
        return productData;
    }

    public int getCopperTool() {
        return copperTool;
    }

    public void setCopperTool() {
        this.copperTool = 0;
    }

    public int getSteelTool() {
        return steelTool;
    }

    public void setSteelTool() {
        this.steelTool = 0;
    }

    public int getGoldTool() {
        return goldTool;
    }

    public void setGoldTool() {
        this.goldTool = 0;
    }

    public int getIridiumTool() {
        return iridiumTool;
    }

    public void setIridiumTool() {
        this.iridiumTool = 0;
    }

    public int getCopperTrashCan() {
        return copperTrashCan;
    }

    public int getSteelTrashCan() {
        return steelTrashCan;
    }

    public int getGoldTrashCan() {
        return goldTrashCan;
    }

    public int getIridiumTrashCan() {
        return iridiumTrashCan;
    }

    public Result buyMineral (BlackSmithStock s, int number) {
        if (s.getPrice() * number > App.getGame().getCurrentPlayer().getCount()) {
            return new Result(false, "you don't have enough money!");
        }
        App.getGame().getCurrentPlayer().addCount(-1 * s.getPrice() * number);
        if (!App.getGame().getCurrentPlayer().addItemToInventory(new ForagingMineral(s.getMineralType()), number)) {
            return new Result(false, "can't add this item(s) to your inventory!");
        }
        return new Result(true, number + " " + s.getName() + " added to inventory.");

    }



}
