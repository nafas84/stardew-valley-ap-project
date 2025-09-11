// <<<<<<< HEAD
package io.Ap.StardewValley.Client.View;

import io.Ap.StardewValley.Client.Controller.GameMenuController;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.*;
import io.Ap.StardewValley.Common.Model.App;

import io.Ap.StardewValley.Common.Model.Command.GameMenuCommand;
import io.Ap.StardewValley.Common.Model.Command.Menu;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if (GameMenuCommand.CurrentMenu.getMatcher(input) != null) {
            System.out.println(GameMenuController.currentMenu());
        }
        else if (GameMenuCommand.CurrentPlayer.getMatcher(input) != null) {
            System.out.println(GameMenuController.currentPlayer());
        }
        else if ((matcher = GameMenuCommand.NewGame.getMatcher(input)) != null) {
            Result result = GameMenuController.newGame(matcher.group("username1"), matcher.group("username2"), matcher.group("username3"));
            System.out.println(result);

            if (result.isSuccessful()) {
                ArrayList<Player> players = App.getGame().getPlayers();

                for (int i = 0; i < players.size(); i++) {
                    Player player = players.get(i);
                    System.out.println(player.getUsername() + ", choose your farm number (1-2):");

                    while (true) {
                        input = scanner.nextLine().trim();
                        if ((matcher = GameMenuCommand.ChooseMap.getMatcher(input)) != null) {
                            result = GameMenuController.chooseMap(i ,Integer.parseInt(matcher.group("mapNumber")));
                            if (result.isSuccessful()) break;
                        } else {
                            System.out.println("Use this format: game map <1-2>");
                        }
                    }
                }
                System.out.println(GameMenuController.loadNewGame());
            }
        }
        else if (GameMenuCommand.LoadGame.getMatcher(input) != null) {
            System.out.println(GameMenuController.loadGame());
        }
        else if (GameMenuCommand.ExitGame.getMatcher(input) != null) {
            System.out.println(GameMenuController.exitGame());
        }
        else if (GameMenuCommand.DeleteGame.getMatcher(input) != null) {
            int playerCount = App.getGame().getPlayers().size();
            int crPlayer = App.getGame().getPlayers().indexOf(App.getGame().getCurrentPlayer());
            for (int i = 1; i < playerCount; i++) {
                System.out.println(App.getGame().getPlayers().get((i + crPlayer) % playerCount).getUsername() + " do you want to delete this game? (yse, no)");
                while (true) {
                    input = scanner.nextLine().trim();
                    Result result = GameMenuController.deleteGame(i, input);
                    if (result.isSuccessful()) break;
                }
            }
            System.out.println(GameMenuController.resultDeleteGame());
        }
        else if (GameMenuCommand.NextTurn.getMatcher(input) != null) {
            System.out.println(GameMenuController.nextTurn());
        }
        else if (GameMenuCommand.GotoNextDay.isMatch(input)) {
            System.out.println(GameMenuController.gotoNextDay());
        }
        else if (GameMenuCommand.Time.getMatcher(input) != null) {
            System.out.println(TimeController.time(input));
        }
        else if ((matcher = GameMenuCommand.CheatTime.getMatcher(input)) != null) {
            System.out.println(TimeController.cheatTime(matcher.group("time")));
        }
        else if ((matcher = GameMenuCommand.CheatDate.getMatcher(input)) != null) {
            System.out.println(TimeController.cheatDate(matcher.group("date")));
        }
        else if (GameMenuCommand.Season.getMatcher(input) != null) {
            System.out.println(TimeController.season());
        }
        else if (GameMenuCommand.Weather.getMatcher(input) != null) {
            System.out.println(TimeController.weather());
        }
        else if (GameMenuCommand.WeatherForecast.getMatcher(input) != null) {
            System.out.println(TimeController.weatherForecast());
        }
        else if ((matcher = GameMenuCommand.CheatThor.getMatcher(input)) != null) {
            System.out.println(TimeController.cheatThor(matcher.group("x"), matcher.group("y")));
        }
        else if ((matcher = GameMenuCommand.CheatWeather.getMatcher(input)) != null) {
            System.out.println(TimeController.cheatWeather(matcher.group("type")));
        }
        else if (GameMenuCommand.BuildGreenhouse.getMatcher(input) != null) {
            System.out.println(MapController.buildGreenHouse());
        }
        else if ((matcher = GameMenuCommand.Walk.getMatcher(input)) != null) {
            Result result = MapController.walk(matcher.group("x"), matcher.group("y"));
            System.out.println(result);
            if (result.isSuccessful()) {
                input = scanner.nextLine();
                System.out.println(MapController.walk(input, matcher.group("x"), matcher.group("y")));
            }
        }
        else if (GameMenuCommand.PrintAllMap.isMatch(input)) {
            System.out.println(MapController.printAllMap());
        }
        else if (GameMenuCommand.PrintFarm.isMatch(input)) {
            System.out.println(MapController.printFarm());
        }
        else if ((matcher = GameMenuCommand.PrintMap.getMatcher(input)) != null) {
            System.out.println(MapController.printMap(matcher.group("x"), matcher.group("y"), matcher.group("size")));
        }
        else if (GameMenuCommand.HelpMap.getMatcher(input) != null) {
            System.out.println(MapController.helpMap());
        }
        else if ((matcher = GameMenuCommand.TileInfo.getMatcher(input)) != null) {
            System.out.println(MapController.tileInfo(matcher.group("x"), matcher.group("y")));
        }

        else if (GameMenuCommand.ShowEnergy.isMatch(input)) {
            System.out.println(PlayerController.showEnergy());
        }
        else if ((matcher = GameMenuCommand.CheatEnergy.getMatcher(input)) != null) {
            System.out.println(PlayerController.cheatEnergy(matcher.group("value")));
        }
        else if (GameMenuCommand.CheatUnlimitedEnergy.isMatch(input)) {
            System.out.println(PlayerController.unlimitedEnergy());
        }
        else if (GameMenuCommand.ShowInventory.isMatch(input)) {
            System.out.println(PlayerController.showInventory());
        }
        else if ((matcher = GameMenuCommand.InventoryTrashWithNumber.getMatcher(input)) != null) {
            System.out.println(PlayerController.inventoryTrash(matcher.group("itemName").trim(), matcher.group("number")));
        }
        else if ((matcher = GameMenuCommand.InventoryTrashWithoutNumber.getMatcher(input)) != null) {
            System.out.println(PlayerController.inventoryTrashWithoutNumber(matcher.group("itemName").trim()));
        }
        else if (GameMenuCommand.ShowAbility.isMatch(input)) {
            System.out.println(PlayerController.showAbility());
        }
        else if ((matcher = GameMenuCommand.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(PlayerController.cheatItem(matcher.group("type"), matcher.group("name"), matcher.group("count")));
        }
        else if ((matcher = GameMenuCommand.EquipTool.getMatcher(input)) != null) {
            System.out.println(ToolController.equip(matcher.group("toolName")));
        }
        else if (GameMenuCommand.ShowCurrentTool.isMatch(input)) {
            System.out.println(ToolController.showCurrentTool());
        }
        else if (GameMenuCommand.ShowAvailableTool.isMatch(input)) {
            System.out.println(ToolController.showAvailableTools());
        }
        else if ((matcher = GameMenuCommand.UpgradeTool.getMatcher(input)) != null) {
            System.out.println(ToolController.upgradeTool(matcher.group("toolName")));
        }
        else if ((matcher = GameMenuCommand.UseTool.getMatcher(input)) != null) {
            System.out.println(ToolController.useTool(matcher.group("direction")));
        }
        else if ((matcher = GameMenuCommand.CropInfo.getMatcher(input)) != null) {
            System.out.println(PlantController.craftInfo(matcher.group("craftName")));
        }
        else if ((matcher = GameMenuCommand.Plant.getMatcher(input)) != null) {
            System.out.println(PlantController.plant(matcher.group("seed"), matcher.group("direction")));
        }
        else if ((matcher = GameMenuCommand.ShowPlant.getMatcher(input)) != null) {
            System.out.println(PlantController.showPlant(matcher.group("x"), matcher.group("y")));
        }
        else if ((matcher = GameMenuCommand.Fertilize.getMatcher(input)) != null) {
            System.out.println(PlantController.fertilize(matcher.group("fertilizer"), matcher.group("direction")));
        }
        else if (GameMenuCommand.ShowWater.getMatcher(input) != null) {
            System.out.println(PlantController.howMuchWater());
        }
        else if (GameMenuCommand.ShowCraftingRecipes.getMatcher(input) != null) {
            System.out.println(CraftController.showRecipes());
        }
        else if ((matcher = GameMenuCommand.Crafting.getMatcher(input)) != null) {
            System.out.println(CraftController.makeCraft(matcher.group("itemName")));
        }
        else if ((matcher = GameMenuCommand.PlaceItem.getMatcher(input)) != null) {
            System.out.println(CraftController.placeCraft(matcher.group("itemName"), matcher.group("direction")));
        }
        else if ((matcher = GameMenuCommand.CheatCrafting.getMatcher(input)) != null) {
            System.out.println(CraftController.cheatAddItem(matcher.group("itemName"), matcher.group("count"), matcher.group("type")));
        }
        else if ((matcher = GameMenuCommand.PutRefrigerator.getMatcher(input)) != null) {
            System.out.println(FoodController.refrigeratorPut(matcher.group("item")));
        }
        else if ((matcher = GameMenuCommand.PickRefrigerator.getMatcher(input)) != null) {
            System.out.println(FoodController.refrigeratorPick(matcher.group("item")));
        }
        else if (GameMenuCommand.ShowCookingRecipes.getMatcher(input) != null) {
            System.out.println(FoodController.showRecipes());
        }
        else if ((matcher = GameMenuCommand.Cooking.getMatcher(input)) != null) {
            System.out.println(FoodController.cook(matcher.group("recipeName")));
        }
        else if ((matcher = GameMenuCommand.Eat.getMatcher(input)) != null) {
            System.out.println(FoodController.eat(matcher.group("foodName")));
        }

        else if ((matcher = GameMenuCommand.Build.getMatcher(input)) != null) {
            System.out.println(MapController.buildFarmBuilding(matcher.group("name"), matcher.group("x"), matcher.group("y")));
        }
        else if (GameMenuCommand.ShowListFarmBuilding.isMatch(input)) {
            System.out.println(MapController.showListFarmBuilding());
        }

        else if ((matcher = GameMenuCommand.BuyAnimal.getMatcher(input)) != null) {
            System.out.println(AnimalController.buyAnimal(matcher.group("animal"), matcher.group("name")));
        }
        else if ((matcher = GameMenuCommand.Pet.getMatcher(input)) != null) {
            System.out.println(AnimalController.pet(matcher.group("name")));
        }
        else if (GameMenuCommand.ShowAnimalsInfo.getMatcher(input) != null) {
            System.out.println(AnimalController.showListAnimals());
        }
        else if ((matcher = GameMenuCommand.CheatFriendShipAnimal.getMatcher(input)) != null) {
            System.out.println(AnimalController.cheatFriendShipAnimal(matcher.group("name"), matcher.group("amount")));
        }
        else if ((matcher = GameMenuCommand.ShepherdAnimal.getMatcher(input)) != null) {
            System.out.println(AnimalController.shepherdAnimal(matcher.group("name"), matcher.group("x"), matcher.group("y")));
        }
        else if ((matcher = GameMenuCommand.FeedAnimal.getMatcher(input)) != null) {
            System.out.println(AnimalController.feedAnimal(matcher.group("name")));
        }
        else if (GameMenuCommand.ShowAnimalProduceInfo.getMatcher(input) != null) {
            System.out.println(AnimalController.showListProductAnimals());
        }
        else if ((matcher = GameMenuCommand.CollectAnimalProduce.getMatcher(input)) != null) {
            System.out.println(AnimalController.collectAnimalProduce(matcher.group("name")));
        }
        else if ((matcher = GameMenuCommand.SellAnimal.getMatcher(input)) != null) {
            System.out.println(AnimalController.sellAnimal(matcher.group("name")));
        }
        else if ((matcher = GameMenuCommand.Fishing.getMatcher(input)) != null) {
            System.out.println(AnimalController.fishing(matcher.group("fishingPole")));
        }


        else if ((matcher = GameMenuCommand.ArtisanUse.getMatcher(input)) != null) {
            //TODO Aynaz
        }
        else if ((matcher = GameMenuCommand.ArtisanGet.getMatcher(input)) != null) {
            //TODO Aynaz
        }


        else if (GameMenuCommand.ShowShopProduct.getMatcher(input) != null) {
            System.out.println(ShopController.showAllProducts());
        }
        else if (GameMenuCommand.ShowShopAvailableProduct.getMatcher(input) != null) {
            System.out.println(ShopController.showAvailableProducts());
        }
        else if ((matcher = GameMenuCommand.PurchaseWithNumber.getMatcher(input)) != null) {
            System.out.println(ShopController.purchaseWithNumber(matcher.group("productName"), matcher.group("count")));
        }
        else if ((matcher = GameMenuCommand.Purchase.getMatcher(input)) != null) {
            System.out.println(ShopController.purchaseWithNumber(matcher.group("productName"), "1"));
        }
        else if ((matcher = GameMenuCommand.CheatAddCount.getMatcher(input)) != null) {
            System.out.println(ShopController.cheatAddCount(matcher.group("count")));
        }
        else if ((matcher = GameMenuCommand.SellProduct.getMatcher(input)) != null) {
            System.out.println(ShopController.sell(matcher.group("type"), matcher.group("name"), matcher.group("count")));
        }

        else if (GameMenuCommand.FriendsShipPlayerList.getMatcher(input) != null) {
            System.out.println(InteractionController.showFriendships());
        }
        else if ((matcher = GameMenuCommand.Talk.getMatcher(input)) != null) {
            System.out.println(InteractionController.talk(matcher.group("username"), matcher.group("message")));
        }
        else if ((matcher = GameMenuCommand.TalkHistory.getMatcher(input)) != null) {
            System.out.println(InteractionController.showTalkHistory(matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.Gift.getMatcher(input)) != null) {
            System.out.println(InteractionController.gift(matcher.group("username"),
                    matcher.group("item"), matcher.group("amount")));
        }
        else if (GameMenuCommand.GiftList.getMatcher(input) != null) {
            System.out.println(InteractionController.giftList());
        }
        else if ((matcher = GameMenuCommand.GiftRate.getMatcher(input)) != null) {
            System.out.println(InteractionController.rateGift(matcher.group("giftNumber"), matcher.group("rate")));
        }
        else if ((matcher = GameMenuCommand.GiftHistory.getMatcher(input)) != null) {
            System.out.println(InteractionController.giftHistory(matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.Hug.getMatcher(input)) != null) {
            System.out.println(InteractionController.hug(matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.Flower.getMatcher(input)) != null) {
            System.out.println(InteractionController.giveFlower(matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.AskMarriage.getMatcher(input)) != null) {
            System.out.println(InteractionController.askMarriage(matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.RespondMarriageAccept.getMatcher(input)) != null) {
            System.out.println(InteractionController.respondMarriage("accept", matcher.group("username")));
        }
        else if ((matcher = GameMenuCommand.RespondMarriageReject.getMatcher(input)) != null) {
            System.out.println(InteractionController.respondMarriage("reject", matcher.group("username")));
        }

        else if (GameMenuCommand.TradeMenu.getMatcher(input) != null) {
            App.setCurrentMenu(Menu.TradeMenu);
        }

        else if ((matcher = GameMenuCommand.MeetNPC.getMatcher(input)) != null) {
            System.out.println(npcController.meetNPC(matcher.group("npcName"), matcher.group("message")));
        }
        else if ((matcher = GameMenuCommand.GiftNPC.getMatcher(input)) != null) {
            System.out.println(npcController.giftNPC(matcher.group("npcName"), matcher.group("item")));
        }
        else if (GameMenuCommand.FriendShipNPCList.getMatcher(input) != null) {
            System.out.println(npcController.friendshipNPCList());
        }
        else if (GameMenuCommand.QuestList.getMatcher(input) != null) {
            System.out.println(npcController.questList());
        }
        else if ((matcher = GameMenuCommand.QuestFinish.getMatcher(input)) != null) {
            //TODO
        }

        else {
            System.out.println("invalid command");
        }
    }
}
// =======
// package io.Ap.StardewValley.View;

// import io.Ap.StardewValley.Controller.GameMenuController;

// import io.Ap.StardewValley.Controller.SirkBozorg.*;
// import io.Ap.StardewValley.Model.App;

// import io.Ap.StardewValley.Model.Command.GameMenuCommand;
// import io.Ap.StardewValley.Model.Command.Menu;
// import io.Ap.StardewValley.Model.Player.Player;
// import io.Ap.StardewValley.Model.Result;

// import java.util.ArrayList;
// import java.util.Scanner;
// import java.util.regex.Matcher;

// public class GameMenu implements AppMenu {
//     @Override
//     public void check(Scanner scanner) {
//         String input = scanner.nextLine().trim();
//         Matcher matcher;

//         if (GameMenuCommand.CurrentMenu.getMatcher(input) != null) {
//             System.out.println(GameMenuController.currentMenu());
//         }
//         else if (GameMenuCommand.CurrentPlayer.getMatcher(input) != null) {
//             System.out.println(GameMenuController.currentPlayer());
//         }
//         else if ((matcher = GameMenuCommand.NewGame.getMatcher(input)) != null) {
//             Result result = GameMenuController.newGame(matcher.group("username1"), matcher.group("username2"), matcher.group("username3"));
//             System.out.println(result);

//             if (result.isSuccessful()) {
//                 ArrayList<Player> players = App.getGame().getPlayers();

//                 for (int i = 0; i < players.size(); i++) {
//                     Player player = players.get(i);
//                     System.out.println(player.getUsername() + ", choose your farm number (1-2):");

//                     while (true) {
//                         input = scanner.nextLine().trim();
//                         if ((matcher = GameMenuCommand.ChooseMap.getMatcher(input)) != null) {
//                             result = GameMenuController.chooseMap(i ,Integer.parseInt(matcher.group("mapNumber")));
//                             if (result.isSuccessful()) break;
//                         } else {
//                             System.out.println("Use this format: game map <1-2>");
//                         }
//                     }
//                 }
//                 System.out.println(GameMenuController.loadNewGame());
//             }
//         }
//         else if (GameMenuCommand.LoadGame.getMatcher(input) != null) {
//             System.out.println(GameMenuController.loadGame());
//         }
//         else if (GameMenuCommand.ExitGame.getMatcher(input) != null) {
//             System.out.println(GameMenuController.exitGame());
//         }
//         else if (GameMenuCommand.DeleteGame.getMatcher(input) != null) {
//             int playerCount = App.getGame().getPlayers().size();
//             int crPlayer = App.getGame().getPlayers().indexOf(App.getGame().getCurrentPlayer());
//             for (int i = 1; i < playerCount; i++) {
//                 System.out.println(App.getGame().getPlayers().get((i + crPlayer) % playerCount).getUsername() + " do you want to delete this game? (yse, no)");
//                 while (true) {
//                     input = scanner.nextLine().trim();
//                     Result result = GameMenuController.deleteGame(i, input);
//                     if (result.isSuccessful()) break;
//                 }
//             }
//             System.out.println(GameMenuController.resultDeleteGame());
//         }
//         else if (GameMenuCommand.NextTurn.getMatcher(input) != null) {
//             System.out.println(GameMenuController.nextTurn());
//             GameMenuController.printMessagesReceived();
//         }
//         else if (GameMenuCommand.GotoNextDay.isMatch(input)) {
//             System.out.println(GameMenuController.gotoNextDay());
//         }
//         else if (GameMenuCommand.Time.getMatcher(input) != null) {
//             System.out.println(TimeController.time(input));
//         }
//         else if ((matcher = GameMenuCommand.CheatTime.getMatcher(input)) != null) {
//             System.out.println(TimeController.cheatTime(matcher.group("time")));
//         }
//         else if ((matcher = GameMenuCommand.CheatDate.getMatcher(input)) != null) {
//             System.out.println(TimeController.cheatDate(matcher.group("date")));
//         }
//         else if (GameMenuCommand.Season.getMatcher(input) != null) {
//             System.out.println(TimeController.season());
//         }
//         else if (GameMenuCommand.Weather.getMatcher(input) != null) {
//             System.out.println(TimeController.weather());
//         }
//         else if (GameMenuCommand.WeatherForecast.getMatcher(input) != null) {
//             System.out.println(TimeController.weatherForecast());
//         }
//         else if ((matcher = GameMenuCommand.CheatThor.getMatcher(input)) != null) {
//             System.out.println(TimeController.cheatThor(matcher.group("x"), matcher.group("y")));
//         }
//         else if ((matcher = GameMenuCommand.CheatWeather.getMatcher(input)) != null) {
//             System.out.println(TimeController.cheatWeather(matcher.group("type")));
//         }
//         else if (GameMenuCommand.BuildGreenhouse.getMatcher(input) != null) {
//             System.out.println(MapController.buildGreenHouse());
//         }
//         else if ((matcher = GameMenuCommand.Walk.getMatcher(input)) != null) {
//             Result result = MapController.walk(matcher.group("x"), matcher.group("y"));
//             System.out.println(result);
//             if (result.isSuccessful()) {
//                 input = scanner.nextLine();
//                 System.out.println(MapController.walk(input, matcher.group("x"), matcher.group("y")));
//             }
//         }
//         else if (GameMenuCommand.PrintAllMap.isMatch(input)) {
//             System.out.println(MapController.printAllMap());
//         }
//         else if (GameMenuCommand.PrintFarm.isMatch(input)) {
//             System.out.println(MapController.printFarm());
//         }
//         else if ((matcher = GameMenuCommand.PrintMap.getMatcher(input)) != null) {
//             System.out.println(MapController.printMap(matcher.group("x"), matcher.group("y"), matcher.group("size")));
//         }
//         else if (GameMenuCommand.HelpMap.getMatcher(input) != null) {
//             System.out.println(MapController.helpMap());
//         }
//         else if ((matcher = GameMenuCommand.TileInfo.getMatcher(input)) != null) {
//             System.out.println(MapController.tileInfo(matcher.group("x"), matcher.group("y")));
//         }

//         else if (GameMenuCommand.ShowEnergy.isMatch(input)) {
//             System.out.println(PlayerController.showEnergy());
//         }
//         else if ((matcher = GameMenuCommand.CheatEnergy.getMatcher(input)) != null) {
//             System.out.println(PlayerController.cheatEnergy(matcher.group("value")));
//         }
//         else if (GameMenuCommand.CheatUnlimitedEnergy.isMatch(input)) {
//             System.out.println(PlayerController.unlimitedEnergy());
//         }
//         else if (GameMenuCommand.ShowInventory.isMatch(input)) {
//             System.out.println(PlayerController.showInventory());
//         }
//         else if ((matcher = GameMenuCommand.InventoryTrashWithNumber.getMatcher(input)) != null) {
//             System.out.println(PlayerController.inventoryTrash(matcher.group("itemName").trim(), matcher.group("number")));
//         }
//         else if ((matcher = GameMenuCommand.InventoryTrashWithoutNumber.getMatcher(input)) != null) {
//             System.out.println(PlayerController.inventoryTrashWithoutNumber(matcher.group("itemName").trim()));
//         }
//         else if (GameMenuCommand.ShowAbility.isMatch(input)) {
//             System.out.println(PlayerController.showAbility());
//         }
//         else if ((matcher = GameMenuCommand.CheatAddItem.getMatcher(input)) != null) {
//             System.out.println(PlayerController.cheatItem(matcher.group("type"), matcher.group("name"), matcher.group("count")));
//         }
//         else if ((matcher = GameMenuCommand.EquipTool.getMatcher(input)) != null) {
//             System.out.println(ToolController.equip(matcher.group("toolName")));
//         }
//         else if (GameMenuCommand.ShowCurrentTool.isMatch(input)) {
//             System.out.println(ToolController.showCurrentTool());
//         }
//         else if (GameMenuCommand.ShowAvailableTool.isMatch(input)) {
//             System.out.println(ToolController.showAvailableTools());
//         }
//         else if ((matcher = GameMenuCommand.UpgradeTool.getMatcher(input)) != null) {
//             System.out.println(ToolController.upgradeTool(matcher.group("toolName")));
//         }
//         else if ((matcher = GameMenuCommand.UseTool.getMatcher(input)) != null) {
//             System.out.println(ToolController.useTool(matcher.group("direction")));
//         }
//         else if ((matcher = GameMenuCommand.CropInfo.getMatcher(input)) != null) {
//             System.out.println(PlantController.craftInfo(matcher.group("craftName")));
//         }
//         else if ((matcher = GameMenuCommand.Plant.getMatcher(input)) != null) {
//             System.out.println(PlantController.plant(matcher.group("seed"), matcher.group("direction")));
//         }
//         else if ((matcher = GameMenuCommand.ShowPlant.getMatcher(input)) != null) {
//             System.out.println(PlantController.showPlant(matcher.group("x"), matcher.group("y")));
//         }
//         else if ((matcher = GameMenuCommand.Fertilize.getMatcher(input)) != null) {
//             System.out.println(PlantController.fertilize(matcher.group("fertilizer"), matcher.group("direction")));
//         }
//         else if (GameMenuCommand.ShowWater.getMatcher(input) != null) {
//             System.out.println(PlantController.howMuchWater());
//         }
//         else if (GameMenuCommand.ShowCraftingRecipes.getMatcher(input) != null) {
//             System.out.println(CraftController.showRecipes());
//         }
//         else if ((matcher = GameMenuCommand.Crafting.getMatcher(input)) != null) {
//             System.out.println(CraftController.makeCraft(matcher.group("itemName")));
//         }
//         else if ((matcher = GameMenuCommand.PlaceItem.getMatcher(input)) != null) {
//             System.out.println(CraftController.placeCraft(matcher.group("craftName"), matcher.group("direction")));
//         }
//         else if ((matcher = GameMenuCommand.CheatCrafting.getMatcher(input)) != null) {
//             System.out.println(CraftController.cheatAddItem(matcher.group("itemName"), matcher.group("count"), matcher.group("type")));
//         }
//         else if ((matcher = GameMenuCommand.PutRefrigerator.getMatcher(input)) != null) {
//             System.out.println(FoodController.refrigeratorPut(matcher.group("item")));
//         }
//         else if ((matcher = GameMenuCommand.PickRefrigerator.getMatcher(input)) != null) {
//             System.out.println(FoodController.refrigeratorPick(matcher.group("item")));
//         }
//         else if (GameMenuCommand.ShowCookingRecipes.getMatcher(input) != null) {
//             System.out.println(FoodController.showRecipes());
//         }
//         else if ((matcher = GameMenuCommand.Cooking.getMatcher(input)) != null) {
//             System.out.println(FoodController.cook(matcher.group("recipeName")));
//         }
//         else if ((matcher = GameMenuCommand.Eat.getMatcher(input)) != null) {
//             System.out.println(FoodController.eat(matcher.group("foodName")));
//         }

//         else if ((matcher = GameMenuCommand.Build.getMatcher(input)) != null) {
//             System.out.println(MapController.buildFarmBuilding(matcher.group("name"), matcher.group("x"), matcher.group("y")));
//         }
//         else if (GameMenuCommand.ShowListFarmBuilding.isMatch(input)) {
//             System.out.println(MapController.showListFarmBuilding());
//         }

//         else if ((matcher = GameMenuCommand.BuyAnimal.getMatcher(input)) != null) {
//             System.out.println(AnimalController.buyAnimal(matcher.group("animal"), matcher.group("name")));
//         }
//         else if ((matcher = GameMenuCommand.Pet.getMatcher(input)) != null) {
//             System.out.println(AnimalController.pet(matcher.group("name")));
//         }
//         else if (GameMenuCommand.ShowAnimalsInfo.getMatcher(input) != null) {
//             System.out.println(AnimalController.showListAnimals());
//         }
//         else if ((matcher = GameMenuCommand.CheatFriendShipAnimal.getMatcher(input)) != null) {
//             System.out.println(AnimalController.cheatFriendShipAnimal(matcher.group("name"), matcher.group("amount")));
//         }
//         else if ((matcher = GameMenuCommand.ShepherdAnimal.getMatcher(input)) != null) {
//             System.out.println(AnimalController.shepherdAnimal(matcher.group("name"), matcher.group("x"), matcher.group("y")));
//         }
//         else if ((matcher = GameMenuCommand.FeedAnimal.getMatcher(input)) != null) {
//             System.out.println(AnimalController.feedAnimal(matcher.group("name")));
//         }
//         else if (GameMenuCommand.ShowAnimalProduceInfo.getMatcher(input) != null) {
//             System.out.println(AnimalController.showListProductAnimals());
//         }
//         else if ((matcher = GameMenuCommand.CollectAnimalProduce.getMatcher(input)) != null) {
//             System.out.println(AnimalController.collectAnimalProduce(matcher.group("name")));
//         }
//         else if ((matcher = GameMenuCommand.SellAnimal.getMatcher(input)) != null) {
//             System.out.println(AnimalController.sellAnimal(matcher.group("name")));
//         }
//         else if ((matcher = GameMenuCommand.Fishing.getMatcher(input)) != null) {
//             System.out.println(AnimalController.fishing(matcher.group("fishingPole")));
//         }


//         else if ((matcher = GameMenuCommand.ArtisanUse.getMatcher(input)) != null) {
//             //TODO Aynaz
//         }
//         else if ((matcher = GameMenuCommand.ArtisanGet.getMatcher(input)) != null) {
//             //TODO Aynaz
//         }


//         else if (GameMenuCommand.ShowShopProduct.getMatcher(input) != null) {
//             System.out.println(ShopController.showAllProducts());
//         }
//         else if (GameMenuCommand.ShowShopAvailableProduct.getMatcher(input) != null) {
//             System.out.println(ShopController.showAvailableProducts());
//         }
//         else if ((matcher = GameMenuCommand.PurchaseWithNumber.getMatcher(input)) != null) {
//             System.out.println(ShopController.purchaseWithNumber(matcher.group("productName"), matcher.group("count")));
//         }
//         else if ((matcher = GameMenuCommand.Purchase.getMatcher(input)) != null) {
//             System.out.println(ShopController.purchaseWithNumber(matcher.group("productName"), "1"));
//         }
//         else if ((matcher = GameMenuCommand.CheatAddCount.getMatcher(input)) != null) {
//             System.out.println(ShopController.cheatAddCount(matcher.group("count")));
//         }
//         else if ((matcher = GameMenuCommand.SellProduct.getMatcher(input)) != null) {
//             System.out.println(ShopController.sell(matcher.group("type"), matcher.group("name"), matcher.group("count")));
//         }

//         else if (GameMenuCommand.FriendsShipPlayerList.getMatcher(input) != null) {
//             System.out.println(InteractionController.showFriendships());
//         }
//         else if ((matcher = GameMenuCommand.Talk.getMatcher(input)) != null) {
//             System.out.println(InteractionController.talk(matcher.group("username"), matcher.group("message")));
//         }
//         else if ((matcher = GameMenuCommand.TalkHistory.getMatcher(input)) != null) {
//             System.out.println(InteractionController.showTalkHistory(matcher.group("username")));
//         }
//         else if ((matcher = GameMenuCommand.Gift.getMatcher(input)) != null) {
//             System.out.println(InteractionController.gift(matcher.group("username"),
//                     matcher.group("item"), matcher.group("amount")));
//         }
//         else if (GameMenuCommand.GiftList.getMatcher(input) != null) {
//             System.out.println(InteractionController.giftList());
//         }
//         else if ((matcher = GameMenuCommand.GiftRate.getMatcher(input)) != null) {
//             System.out.println(InteractionController.rateGift(matcher.group("giftNumber"), matcher.group("rate")));
//         }
//         else if ((matcher = GameMenuCommand.GiftHistory.getMatcher(input)) != null) {
//             System.out.println(InteractionController.giftHistory(matcher.group("username")));
//         }
//         else if ((matcher = GameMenuCommand.Hug.getMatcher(input)) != null) {
//             System.out.println(InteractionController.hug(matcher.group("username")));
//         }
//         else if ((matcher = GameMenuCommand.Flower.getMatcher(input)) != null) {
//             System.out.println(InteractionController.giveFlower(matcher.group("username")));
//         }
//         else if ((matcher = GameMenuCommand.AskMarriage.getMatcher(input)) != null) {
//             System.out.println(InteractionController.askMarriage(matcher.group("username")));
//         }
//         else if ((matcher = GameMenuCommand.RespondMarriageAccept.getMatcher(input)) != null) {
//             System.out.println(InteractionController.respondMarriage("accept", matcher.group("username")));
//         }
//         else if ((matcher = GameMenuCommand.RespondMarriageReject.getMatcher(input)) != null) {
//             System.out.println(InteractionController.respondMarriage("reject", matcher.group("username")));
//         }

//         else if (GameMenuCommand.TradeMenu.getMatcher(input) != null) {
//             App.setCurrentMenu(Menu.TradeMenu);
//         }

//         else if ((matcher = GameMenuCommand.MeetNPC.getMatcher(input)) != null) {
//             System.out.println(npcController.meetNPC(matcher.group("npcName"), matcher.group("message")));
//         }
//         else if ((matcher = GameMenuCommand.GiftNPC.getMatcher(input)) != null) {
//             System.out.println(npcController.giftNPC(matcher.group("npcName"), matcher.group("item")));
//         }
//         else if (GameMenuCommand.FriendShipNPCList.getMatcher(input) != null) {
//             System.out.println(npcController.friendshipNPCList());
//         }
//         else if (GameMenuCommand.QuestList.getMatcher(input) != null) {
//             System.out.println(npcController.questList());
//         }
//         else if ((matcher = GameMenuCommand.QuestFinish.getMatcher(input)) != null) {
//             //TODO
//         }

//         else {
//             System.out.println("invalid command");
//         }
//     }
// }
// >>>>>>> origin/menu
