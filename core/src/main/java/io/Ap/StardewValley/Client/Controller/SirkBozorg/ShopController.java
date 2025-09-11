package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Map.TileType;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Item.ItemType;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;
import io.Ap.StardewValley.Common.Model.Tool.Tool;

import java.util.ArrayList;

public class ShopController {
    public static Result cheatAddCount(String stringCount) {
        int count = Integer.parseInt(stringCount);
        if (count < 0) {
            return new Result(false, "Count should be positive!");
        }
        App.getGame().getCurrentPlayer().addCount(count);
        return new Result(true, "Now your account has been updated to: "  + App.getGame().getCurrentPlayer().getCount());
    }

    public static Result showAllProducts () {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getType() != TileType.Building) {
            return new Result (false, "you must be in a shop to use this command!");
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.Blacksmith) {
            return new Result(true, App.getGame().getShop(ShopType.Blacksmith).showAllProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.CarpentersShop) {
            return new Result(true, App.getGame().getShop(ShopType.CarpentersShop).showAllProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.FishShop) {
            return new Result(true, App.getGame().getShop(ShopType.FishShop).showAllProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.JojaMart) {
            return new Result(true, App.getGame().getShop(ShopType.JojaMart).showAllProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.MarniesRanch) {
            return new Result(true, App.getGame().getShop(ShopType.MarniesRanch).showAllProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.PierresGeneralStore) {
            return new Result(true, App.getGame().getShop(ShopType.PierresGeneralStore).showAllProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.TheStarDropSaloon) {
            return new Result(true, App.getGame().getShop(ShopType.TheStarDropSaloon).showAllProducts());
        }
        return new Result (false, "you must be in a shop to use this command!");
    }

    public static Result showAvailableProducts() {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getType() != TileType.Building) {
            return new Result (false, "you must be in a shop to use this command!");
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.Blacksmith) {
            return new Result(true, App.getGame().getShop(ShopType.Blacksmith).showAvailableProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.CarpentersShop) {
            return new Result(true, App.getGame().getShop(ShopType.CarpentersShop).showAvailableProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.FishShop) {
            return new Result(true, App.getGame().getShop(ShopType.FishShop).showAvailableProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.JojaMart) {
            return new Result(true, App.getGame().getShop(ShopType.JojaMart).showAvailableProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.MarniesRanch) {
            return new Result(true, App.getGame().getShop(ShopType.MarniesRanch).showAvailableProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.PierresGeneralStore) {
            return new Result(true, App.getGame().getShop(ShopType.PierresGeneralStore).showAvailableProducts());
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.TheStarDropSaloon) {
            return new Result(true, App.getGame().getShop(ShopType.TheStarDropSaloon).showAvailableProducts());
        }
        return new Result (false, "you must be in a shop to use this command!");
    }

    public static Result purchaseWithNumber (String productName, String count) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        int number;
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getType() != TileType.Building) {
            return new Result (false, "you must be in a shop to use this command!");
        }
        if ((number = Integer.parseInt(count)) <= 0) {
            return new Result(false, "\"count\" must be a positive number!");
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.Blacksmith) {
            return App.getGame().getShop(ShopType.Blacksmith).buy(productName, number, null);
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.CarpentersShop) {
            return App.getGame().getShop(ShopType.CarpentersShop).buy(productName, number, null);
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.FishShop) {
            return App.getGame().getShop(ShopType.FishShop).buy(productName, number, null);
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.JojaMart) {
            return App.getGame().getShop(ShopType.JojaMart).buy(productName, number, null);
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.MarniesRanch) {
            return App.getGame().getShop(ShopType.MarniesRanch).buy(productName, number, null);
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.PierresGeneralStore) {
            return App.getGame().getShop(ShopType.PierresGeneralStore).buy(productName, number, null);
        }
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() == BuildingType.TheStarDropSaloon) {
            return App.getGame().getShop(ShopType.TheStarDropSaloon).buy(productName, number, null);
        }
        return new Result (false, "you must be in a shop to use this command!");
    }

    public static Result sell(String type, String name, String stringCount) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        int count = (stringCount == null ) ? -1 : Integer.parseInt(stringCount);
        Item item = PlayerController.getItemByTypeName(type, name);
        int inventoryCount = App.getGame().getCurrentPlayer().getInventory().getItemQuantity(item);


        // location error:
        if (!isShippingBinAroundMe()) {
            return new Result(false, "To sell an item, you need to be within 8 tiles of shipping bin!");
        }

        // item error
        else if (count < 1 && count != -1) {
            return new Result(false, "Count must be a positive number!");
        } else if (!PlayerController.isTypeValid(type)) {
            StringBuilder validType = new StringBuilder();
            for (ItemType itemType: ItemType.values()) {
                validType.append(itemType.getName()).append(" ");
            }
            return new Result(false, "Type is invalid. Valid types: {" + validType + "}");
        } else if (type.equals("tool")) {
            return new Result(false, "You can't sell tools!");
        } else if (item == null) {
            return new Result(false, "Name is invalid!");
        } else if (item.getPrice() == 0) {
            return new Result(false, "You can't sell this item!");
        }

        // inventory error:
        else if (inventoryCount == -1) {
            return new Result(false, "You don't have this item in your inventory!");
        } else if (count != -1 && inventoryCount < count) {
            return new Result(false, "You don't have " + count +" of this item in your inventory!");
        }

        if (count == -1) count = inventoryCount;
        App.getGame().getCurrentPlayer().addItemToShippingBin(item, count);
        if (!App.getGame().getCurrentPlayer().getInventory().removeItem(item.getName(), count)) {
            return new Result(false, "Oh shit here we go again(you can't remove this item from your inventory)");
        }

        return new Result(true, "Now you send this item to hell(shipping bin). Tomorrow به حسابت زده میشه");
    }

    private static boolean isShippingBinAroundMe() {
        Tile[][] fullMap = App.getGame().getMap().getFullMap();

        int x = App.getGame().getCurrentPlayer().getCoordinate().getX();
        int y = App.getGame().getCurrentPlayer().getCoordinate().getY();

        int[] dx = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if ((newX < 0 || newX >= 90) || (newY < 0 || newY >= 120)) continue;

            Tile tile = fullMap[newX][newY];
            if (tile.getType().equals(TileType.Building) && tile.getBuildingType().equals(BuildingType.ShippingBin)) {
                return true;
            }
        }

        return false;
    }

    public static Result sellThroughScreen(String name) {
        Player player = App.getGame().getCurrentPlayer();
        ArrayList<Item> items = player.getInventory().getItemList();
        Item wanted = null;
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(name)) {
                wanted = i;
            }
        }
        if (wanted instanceof Tool) {
            return new Result(false, "you can't sell a tool!");
        }
        player.removeItemFromInventory(name, 1);
        player.addCount(wanted.getPrice());

        return new Result(true, "Now you send this item to hell(shipping bin). Tomorrow به حسابت زده میشه");
    }

}
