package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.Tool.*;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Common.Model.Animals.Animal;
import io.Ap.StardewValley.Common.Model.Animals.Fish;
import io.Ap.StardewValley.Common.Model.Animals.FishType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Map.TileType;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;
import io.Ap.StardewValley.Common.Model.Time.Weather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AnimalController {
    public static Result buyAnimal (String animal, String name) {if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
        return new Result (false, "you have no more moves! enter next turn!");
    }
        GameMenuController.moveControl();
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() != BuildingType.MarniesRanch) {
            return new Result(false, "you must be in Marnie's Ranch to be able to buy an animal!");
        } else if (getAnimalByName(name) != null) {
            return new Result(false, "Pet names should be unique!");
        }

        return App.getGame().getShop(ShopType.MarniesRanch).buy(animal, 1, name);
    }

    public static Result pet(String name) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Animal animal = getAnimalByName(name);
        if (animal == null) {
            return new Result(false, "You don't have a pet with that name!");
        } else if (!isAnimalBesideMe(name)) {
            return new Result(false, "To pet an animal, you need to be within 8 tiles of it!");
        }

        animal.addFriendship(15);
        animal.setPetted(true);
        return new Result(true, "Aghoda che sahebe mehraboni *_*");
    }

    public static Result showListAnimals() {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        StringBuilder result = new StringBuilder();
        result.append("My Animals List:\n").append("_______________________________________\n");
        for (Animal animal: App.getGame().getCurrentPlayer().getMyAnimals()) {
            result.append(animal.toString());
        }
        return new Result(true, result.toString());
    }

    public static Result cheatFriendShipAnimal(String name, String stringAmount) {
        int amount = Integer.parseInt(stringAmount);
        Animal animal = getAnimalByName(name);
        if (animal == null) {
            return new Result(false, "You don't have a pet with that name.");
        } else if (amount > 1000 || amount < 0) {
            return new Result(false, "Invalid amount! It should be between: (0-1000)");
        }

        animal.setFriendship(amount);
        return new Result(true, "Animal friendship successfully changed to " + amount);
    }

    public static Result shepherdAnimal(String name, String stringX, String stringY)  {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        Coordinate coordinate = new Coordinate(x, y);
        Tile tile = App.getGame().getTile(coordinate);
        Animal animal = getAnimalByName(name);

        if (animal == null) {
            return new Result(false, "You don't have a pet with that name!");
        } else if ((x < 0 || x >= 90) || (y < 0 || y >= 120)) {
            return new Result(false, "Mashti x,y bein (0,0) - (89, 119)");
        } else if (!tile.isWalkable()) {
            return new Result(false, "It's a unwalkable tile!");
        }

        // Go out:
        BuildingType type = tile.getBuildingType();
        if (type == null || (!type.equals(BuildingType.Barn) && !type.equals(BuildingType.Coop))) {
            if (!App.getGame().getCurrentTime().getWeather().equals(Weather.Sunny)) {
                return new Result(false, "Animals can only go out in sunny weather!");
            }

            tile.setAnimal(animal);
            animal.setCoordinate(coordinate);
            animal.addFriendship(8);
            animal.setFeeded(true);
            return new Result(true, name + " successfully went outside.");
        }
        // Go inside:
        else {
            if (animal.getCoordinate() != null) {
                App.getGame().getTile(animal.getCoordinate()).setAnimal(null);
                animal.setCoordinate(null);
                return new Result(true,  name + " successfully entered.");
            } else {
                return new Result(true,  "همین الانشم توعه بس!");
            }
        }
    }

    public static Result feedAnimal(String name) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Animal animal = getAnimalByName(name);
        if (animal == null) {
            return new Result(false, "You don't have a pet with that name!");
        } else if (!App.getGame().getCurrentPlayer().getInventory().removeItem("hay", 1)) {
            return new Result(false, "You don't have hay!");
        }

        animal.addFriendship(10);
        animal.setFeeded(true);

        // inventory check with removeItem
        return new Result(true,  name + " was successfully fed.");
    }

    public static Result showListProductAnimals() {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        StringBuilder result = new StringBuilder();
        result.append("My Animal's products List:\n").append("_______________________________________\n");
        for (Animal animal: App.getGame().getCurrentPlayer().getMyAnimals()) {
            if (animal.getProduct() != null) {
                result.append("Animal mame: ").append(animal.getName()).append("\n").append("Animal type: "). append(animal.getType()).append("\n");
                result.append(animal.getProductString());
            }
        }
        return new Result(true, result.toString());
    }

    public static Result collectAnimalProduce(String name) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        App.getGame().getCurrentPlayer().addEnergy(-1 * MilkPail.getEnergyForAnimals());
        App.getGame().getCurrentPlayer().addAbility(Skill.Farming, 5);
        Animal animal = getAnimalByName(name);
        if (animal == null) {
            return new Result(false, "You don't have a pet with that name!");
        } else if (animal.getProduct() == null) {
            return new Result(false, name + " doesn't have any product to harvest!");
        } else if (toolHandleCollect(animal) != null) {
            return toolHandleCollect(animal);
        }

        if (!App.getGame().getCurrentPlayer().getInventory().addItem(animal.getProduct())) {
            return new Result(true, "Inventory is full. You picked up the product, ama be che gheimati?");
        }

        animal.setProduct(null);
        return new Result(true, "You successfully collect " + animal.getName() + "'s produce.");
    }

    public static Result sellAnimal(String name) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Animal animal = getAnimalByName(name);
        if (animal == null) {
            return new Result(false, "You don't have a pet with that name!");
        }

        App.getGame().getCurrentPlayer().addCount(animal.getSellPrice());
        App.getGame().getCurrentPlayer().getMyAnimals().remove(animal);
        if (animal.getCoordinate() != null) {
            App.getGame().getTile(animal.getCoordinate()).setAnimal(null);
        }
        return new Result(true, "You successfully sold " + animal.getName() + " for " + animal.getSellPrice());
    }

    public static Result fishing(String fishingPole) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Tool currentTool = App.getGame().getCurrentPlayer().getCurrentTool();
        FishingPoleType type = getFishingPoleTypeByName(fishingPole);

        // Tool error:
        if (currentTool == null || !currentTool.getType().equals(ToolType.FishingPole)) {
            return new Result(false, "Your current tool isn't fishing pole!");
        } else if (type == null) {
            StringBuilder valid = new StringBuilder();
            for (FishingPoleType f: FishingPoleType.values()) {
                valid.append(f.getName()).append(" ");
            }
            return new Result(false, "Invalid fishing pole name! Valid names: {" + valid + "}");
        } else if (currentTool instanceof FishingPole playerPole) {
            if (!playerPole.getFishingPoleType().equals(type)) {
                return new Result(false, "Your fishing pole type isn't the same with your current tool type!");
            }
        }
        // location error:
        if (!isWaterBesideMe()) {
            return new Result(false, "You have to be near the water to fishing!");
        }

        StringBuilder result = new StringBuilder();
        List<Fish> fishes = getFishes(type);
        Player player = App.getGame().getCurrentPlayer();

        result.append("You caught ").append(fishes.size()).append(" fish").append(fishes.size() > 1 ? "es" : "").append(":\n");

        for (Fish fish : fishes) {
            result.append("- ").append(fish.getName())
                    .append(" quality: ").append(String.format("(%.2f)", fish.getQuality())).append(fish.getQualityString()).append("\n");
            if (!player.getInventory().addItem(fish)) {
                return new Result(false, "Inventory is full!");
            }
        }
        App.getGame().getCurrentPlayer().addAbility(Skill.Fishing, 5);
        return new Result(true, result.toString());
    }

    private static List<Fish> getFishes(FishingPoleType poleType) {

        // number of fishes:
        Random random = new Random();
        double M = switch (App.getGame().getCurrentTime().getWeather()) { //TODO: add to weather field
            case Sunny -> 1.5;
            case Rain -> 1.2;
            case Storm -> 0.5;
            default -> 1.0;
        };

        double R = random.nextDouble();
        int fishCount = (int) Math.ceil(R * M * (App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Fishing) + 2));
        fishCount = Math.min(6, fishCount);

        // type of fishes:
        List<FishType> seasonalFishesTypes = new ArrayList<>();

        for (FishType fish : FishType.values()) {
            if (fish.getSeason().equals(App.getGame().getCurrentTime().getSeason())) {
                if (!fish.isLegendary())
                    seasonalFishesTypes.add(fish);
                else {
                    if (App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Fishing) == 4) {
                        seasonalFishesTypes.add(fish);
                    }
                }
            }
        }
        Collections.shuffle(seasonalFishesTypes);

        // quality:
        R = random.nextDouble();
        double quality = R * (App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Fishing) + 2) * poleType.getFishingFactor() / (7 - M);

        List<Fish> result = new ArrayList<>();
        int count = Math.min(fishCount, seasonalFishesTypes.size());
        for (int i = 0; i < count; i++) {
            result.add(new Fish(seasonalFishesTypes.get(i), quality));
        }

        return result;
    }

    private static FishingPoleType getFishingPoleTypeByName(String name) {
        for (FishingPoleType f: FishingPoleType.values()) {
            if (f.getName().equalsIgnoreCase(name)) {
                return f;
            }
        }
        return null;
    }

    private static Animal getAnimalByName(String name) {
        for (Animal animal: App.getGame().getCurrentPlayer().getMyAnimals()) {
            if (animal.getName().equals(name))
                return animal;
        }

        return null;
    }

    private static boolean isAnimalBesideMe(String name) {
        Tile[][] fullMap = App.getGame().getMap().getFullMap();

        int x = App.getGame().getCurrentPlayer().getCoordinate().getX();
        int y = App.getGame().getCurrentPlayer().getCoordinate().getY();

        int[] dx = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            // Phase 1:
            if ((newX < 0 || newX >= 240) || (newY < 0 || newY >= 290)) continue;

            Tile tile = fullMap[newX][newY];
            if (tile != null && tile.getAnimal() != null) {
                if (tile.getAnimal().getName().equals(name))
                    return true;
            }
        }

        return false;
    }

    private static boolean isWaterBesideMe () {
        Tile[][] fullMap = App.getGame().getMap().getFullMap();

        int x = App.getGame().getCurrentPlayer().getCoordinate().getX();
        int y = App.getGame().getCurrentPlayer().getCoordinate().getY();

        int[] dx = {-1, -1, -1,  0, 0,  1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            // Phase 1:
            if ((newX < 0 || newX >= 240) || (newY < 0 || newY >= 290)) continue;

            Tile tile = fullMap[newX][newY];
            if (tile.getType().equals(TileType.Water)) {
                return true;
            }
        }

        return false;
    }

    private static Result toolHandleCollect(Animal animal) {
        switch (animal.getType()) {
            case Cow, Goat:
                if (App.getGame().getCurrentPlayer().getCurrentTool().getType().equals(ToolType.MilkPail)) {
                    return null;
                } else {
                    return new Result(false, "For collect goat/cow product, you need milk pail tool!");
                }
            case Sheep:
                if (App.getGame().getCurrentPlayer().getCurrentTool().getType().equals(ToolType.Shear)) {
                    return null;
                } else {
                    return new Result(false, "For collect sheep product, you need shear tool!");
                }
            case Pig:
                if (animal.getCoordinate() != null) {
                    return null;
                } else {
                    return new Result(false, "For collect pig product, it should be outside!");
                }
            default:
                return null;
        }
    }
}
