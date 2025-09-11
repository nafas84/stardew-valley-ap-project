package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Map.TileType;
import io.Ap.StardewValley.Common.Model.Plants.*;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;
import io.Ap.StardewValley.Common.Model.Time.Season;
import io.Ap.StardewValley.Common.Model.Tool.WateringCan;

import java.util.ArrayList;
import java.util.List;

public class PlantController {

    public static Result craftInfo (String craftName) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        craftName = craftName.toLowerCase();
        for (CropType t : CropType.values()) {
            if (t == null || t.getName() == null) {
                continue;
            }
            if (t.getName().toLowerCase().equals(craftName)) {
                return new Result(true, t.getInformation());
            }
        }
        for (ForagingCropType t : ForagingCropType.values()) {
            if (t == null || t.getName() == null) {
                continue;
            }
            if (t.getName().toLowerCase().equals(craftName)) {
                return new Result(true, t.getInformation());
            }
        }
        for (ForagingMineralType t : ForagingMineralType.values()) {
            if (t == null || t.getName() == null) {
                continue;
            }
            if (t.getName().toLowerCase().equals(craftName)) {
                return new Result(true, t.getInformation());
            }
        }
        for (FruitType t : FruitType.values()) {
            if (t == null || t.getName() == null) {
                continue;
            }
            if (t.getName().toLowerCase().equals(craftName)) {
                return new Result(true, t.getInformation());
            }
        }
        for (SaplingType t : SaplingType.values()) {
            if (t == null || t.getName() == null) {
                continue;
            }
            if (t.getName().toLowerCase().equals(craftName)) {
                return new Result(true, t.getInformation());
            }
        }
        for (SeedType t : SeedType.values()) {
            if (t == null || t.getName() == null) {
                continue;
            }
            if (t.getName().toLowerCase().equals(craftName)) {
                return new Result(true, t.getInformation());
            }
        }
        for (TreeType t : TreeType.values()) {
            if (t == null || t.getName() == null) {
                continue;
            }
            if (t.getName().toLowerCase().equals(craftName)) {
                return new Result(true, t.getInformation());
            }
        }
        return new Result (false, "invalid plant/ fruit name!");
    }

    public static Result plant (String seedName, String direction) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        SeedType seedType;
        SaplingType saplingType = getSaplingTypeByName(seedName);
        if (seedName == null) {
            return new Result(false, "invalid seed/ sapling name!");
        }
        if ((seedType = getSeedTypeByName(seedName)) == null && saplingType == null) {
            return new Result(false, "invalid seed/ sapling name!");
        }
        if (App.getGame().getCurrentPlayer().getInventory().hasItemWithName(seedName) == null) {
            return new Result(false, "you don't have " + seedName + " in your inventory!");
        }
        if (seedType != null) {
            if (seedName.equalsIgnoreCase("mixed seeds")) {
                 App.getGame().getCurrentPlayer().removeItemFromInventory(seedName, 1);
                 return plantInTile(GameMenuController.getCoordinateByDirection(direction), new Seed(getRandomSeed(), true), GameMenuController.getTileByDirection(direction),
                         App.getGame().getCurrentTime());
            }
            App.getGame().getCurrentPlayer().removeItemFromInventory(seedName, 1);
            return plantInTile(GameMenuController.getCoordinateByDirection(direction), new Seed(seedType, true), GameMenuController.getTileByDirection(direction),
                    App.getGame().getCurrentTime());
        }
        App.getGame().getCurrentPlayer().removeItemFromInventory(seedName, 1);
        return plantInTile(GameMenuController.getCoordinateByDirection(direction), new Sapling(saplingType, true), GameMenuController.getTileByDirection(direction),
                App.getGame().getCurrentTime());
    }

    public static Result showPlant (String X, String Y) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        if (!PlayerController.isParsableInt(X) || !PlayerController.isParsableInt(Y)) {
            return new Result(false, "invalid coordinate!");
        }
        int x = Integer.parseInt(X);
        int y = Integer.parseInt(Y);
        if (x >= 90 || x < 0 || y >= 120 || y < 0) {
            return new Result(false, "invalid coordinate!");
        }
        Tile tile = App.getGame().getTile(new Coordinate(x, y));
        if (tile == null) {
            return new Result(false, "invalid tile!");
        }
        Item item = tile.getItem();
        if (item == null) {
            return new Result(false, "this tile is empty!");
        }
        else if (item instanceof Plant) {
            String result = ((Plant) item).showPlantInfo() + "\nlast time watered: ";
            if (tile.getLastTimeWatered() == null) {
                result = result + "this plant has never been watered!\nfertilize: ";
            }
            else if (tile.getLastTimeWatered().getDay() == 1) {
                result = result + "the 1st day\nfertilize: ";
            }
            else if (tile.getLastTimeWatered().getDay() == 2) {
                result = result + "the 2nd day\nfertilize: ";
            }
            else if (tile.getLastTimeWatered().getDay() == 3) {
                result = result + "the 3rd day\nfertilize: ";
            }
            else {
                result = result + "the " + tile.getLastTimeWatered().getDay() + "th day\nfertilize: ";
            }

            if (App.getGame().getTile(new Coordinate(x, y)).getFertilize() == 0) {
                result = result + "not fertilized";
            }
            else if (App.getGame().getTile(new Coordinate(x, y)).getFertilize() == 1) {
                result = result + "retaining soil";
            }
            else if (App.getGame().getTile(new Coordinate(x, y)).getFertilize() == 2) {
                result = result + "speed-gro";
            }
            return new Result(true, result);
        }
        return new Result(false, "no plants in this tile!");
    }

    public static Result fertilize (String fertilizer, String direction) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Tile tile = GameMenuController.getTileByDirection(direction);
        if (tile == null) {
            return new Result(false, "invalid direction!");
        }
        if (tile.getItem() != null) {
            return new Result(false, "the selected tile is not empty!\nyou must fertilize tiles before planting.");
        }
        if (!tile.isPlowed()) {
            return new Result(false, "the selected tile is not plowed!\n");
        }
        if (fertilizer == null) {
            return new Result(false, "invalid fertilizer name!");
        }
        if (tile.getFertilize() != 0) {
            return new Result(false, "the selected tile has already been fertilized!");
        }
        if (fertilizer.equalsIgnoreCase("deluxe relating soil") || fertilizer.equalsIgnoreCase("basic relating soil") ||
                fertilizer.equalsIgnoreCase("quality relating soil") || fertilizer.equalsIgnoreCase("relating soil")) {

            tile.setFertilize(1);
            return new Result(true, "the selected tile is now fertilized with " + fertilizer);
        }
        if (fertilizer.equalsIgnoreCase("speed-gro") || fertilizer.equalsIgnoreCase("speed gro")) {
            tile.setFertilize(2);
            return new Result(true, "the selected tile is now fertilized with " + fertilizer);
        }
        return new Result(false, "invalid fertilizer name!");
    }

    public static Result howMuchWater () {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Item wateringCan = App.getGame().getCurrentPlayer().getInventory().hasItemWithName("watering can");
        if (wateringCan == null) {
            return new Result(false, "an error occurred!");
        }
        else if (!(wateringCan instanceof WateringCan)) {
            return new Result(false, "an error occurred!");
        }
        int howMuch = ((WateringCan) wateringCan).getWaterAmount();
        return new Result(true, "water amount in your watering can: " + howMuch);
    }



    public static String stagesToString(int[] stages) {
        if (stages == null) {
            return "";
        }
        String result = "" + stages[0];
        int i = 1;
        for (int a : stages) {
            if (i > 0) {
                i = 0;
                continue;
            }
            result = result + "-" + a;
        }
        return result;
    }

    public static String SeasonsToString(List<Season> seasons) {
        if (seasons.isEmpty()) {
            return "";
        }
        String result = seasons.get(0).name();
        int i = 1;
        for (Season s : seasons) {
            if (i > 0) {
                i = 0;
                continue;
            }
            result = result + "-" + s.name();
        }
        return result;
    }

    public static SeedType getSeedTypeByName (String seedName) {
        if (seedName == null) {
            return null;
        }
        seedName = seedName.toLowerCase();
        for (SeedType s : SeedType.values()) {
            if (s.getName().toLowerCase().equals(seedName)) {
                return s;
            }
        }
        return null;
    }

    public static SaplingType getSaplingTypeByName (String saplingName) {
        if (saplingName == null) {
            return null;
        }
        saplingName = saplingName.toLowerCase();
        for (SaplingType s : SaplingType.values()) {
            if (s.getName().toLowerCase().equals(saplingName)) {
                return s;
            }
        }
        return null;
    }

    public static Result plantInTile (Coordinate coordinate, Seed seed, Tile tile, DateAndTime time) {
        if (tile == null) {
            return new Result(false, "invalid tile!");
        }
        if (!(tile.getType() == TileType.Ground || (tile.getBuildingType() == BuildingType.GreenHouseBuild))) {
            return new Result(false, "you must plant on the ground or in the greenhouse!");
        }
        if (!seed.getSeasons().contains(App.getGame().getCurrentTime().getSeason()) && tile.getType() == TileType.Ground) {
            return new Result(false, "this plant can't get planted outside of the greenhouse in this season!");
        }
        if (!App.getGame().getCurrentPlayer().isMyFarm(coordinate)) {
            return new Result(false, "you are not allowed to plant here!");
        }
        if (tile.getItem() != null) {
            return new Result(false, "the selected tile isn't empty!");
        }
        if (!tile.isPlowed()) {
            return new Result(false, "this tile has not been plowed");
        }
//        Crop crop;
//        if (seed.getSeedType().getCropName().equalsIgnoreCase("Mixed")) {
//            Seed randomSeed = new Seed(SeedType.values()[NightController.rand.nextInt(35)]);
//            crop = new Crop(time, randomSeed.getCrop(),true);
//        }
//        else {
//            crop = new Crop (time, seed.getCrop(), true);
//        }

        Crop crop = PlayerController.getCrop(seed.getSeedType().getCropName());

        if (crop == null) {
            return new Result(false, "seed type invalid!");
        }

        if (tile.getFertilize() == 2) {
            if (!crop.getName().equalsIgnoreCase("grass")) {
                crop.setTotalHarvestTime(crop.getHarvestTime() - 1);
            }
        }

        tile.setItem(crop);
        return new Result(true, seed.getName() + " is now planted in selected tile.");
    }

    public static Result plantInTile (Coordinate coordinate, Sapling sapling, Tile tile, DateAndTime time) {
        if (tile == null) {
            return new Result(false, "invalid tile!");
        }
        if (!(tile.getType() == TileType.Ground || (tile.getBuildingType() == BuildingType.GreenHouseBuild))) {
            return new Result(false, "you must plant on the ground or in the greenhouse!");
        }
        if (!sapling.getSeasons().contains(App.getGame().getCurrentTime().getSeason()) && tile.getType() == TileType.Ground) {
            return new Result(false, "this plant can't get planted outside of the greenhouse in this season!");
        }
        if (!App.getGame().getCurrentPlayer().isMyFarm(coordinate)) {
            return new Result(false, "you are not allowed to plant here!");
        }
        if (tile.getItem() != null) {
            return new Result(false, "tile isn't empty!");
        }
        if (!tile.isPlowed()) {
            return new Result(false, "this tile has not been plowed");
        }
        Tree tree = new Tree(sapling.getTreeType(), time, true);
        if (tile.getFertilize() == 2) {
            tree.setTotalHarvestTime(tree.getTotalHarvestTime() - 1);
        }
        tile.setItem(tree);
        return new Result(true, sapling.getName() + " is now planted in selected tile.");
    }



    public static ArrayList<SeedType> getSeasonSeeds (Season s) {
        ArrayList<SeedType> seeds = new ArrayList<>();
        for (SeedType seed : SeedType.values()) {
            if (seed.getSeasons().contains(s)) {
                seeds.add(seed);
            }
        }
        return seeds;
    }

    private static SeedType getRandomSeed () {
        int size = getSeasonSeeds(App.getGame().getCurrentTime().getSeason()).size();
        int index = NightController.rand.nextInt(Math.max(0, size -1));
        return getSeasonSeeds(App.getGame().getCurrentTime().getSeason()).get(index);
    }



    public static Result plantThroughScreen (String seedName, String direction) {
        SeedType seedType;
        SaplingType saplingType = getSaplingTypeByName(seedName);
        if (seedName == null) {
            return new Result(false, "invalid seed/ sapling name!");
        }
        if ((seedType = getSeedTypeByName(seedName)) == null && saplingType == null) {
            return new Result(false, "invalid seed/ sapling name!");
        }
        if (App.getGame().getCurrentPlayer().getInventory().hasItemWithName(seedName) == null) {
            return new Result(false, "you don't have " + seedName + " in your inventory!");
        }
        if (seedType != null) {
            if (seedName.equalsIgnoreCase("mixed seeds")) {
                App.getGame().getCurrentPlayer().removeItemFromInventory(seedName, 1);
                return plantInTile(GameMenuController.getCoordinateByDirection(direction), new Seed(getRandomSeed(), true), GameMenuController.getTileByDirection(direction),
                        App.getGame().getCurrentTime());
            }
            App.getGame().getCurrentPlayer().removeItemFromInventory(seedName, 1);
            return plantInTile(GameMenuController.getCoordinateByDirection(direction), new Seed(seedType, true), GameMenuController.getTileByDirection(direction),
                    App.getGame().getCurrentTime());
        }
        App.getGame().getCurrentPlayer().removeItemFromInventory(seedName, 1);
        return plantInTile(GameMenuController.getCoordinateByDirection(direction), new Sapling(saplingType, true), GameMenuController.getTileByDirection(direction),
                App.getGame().getCurrentTime());
    }
}
