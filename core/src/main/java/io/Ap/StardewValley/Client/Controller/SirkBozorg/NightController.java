package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.Animals.Animal;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProduct;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProductType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Crafting.Craft;
import io.Ap.StardewValley.Common.Model.Crafting.CraftType;
import io.Ap.StardewValley.Common.Model.Interaction.Friend;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Map.TileType;
import io.Ap.StardewValley.Common.Model.Plants.*;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;
import io.Ap.StardewValley.Common.Model.Time.Season;
import io.Ap.StardewValley.Common.Model.Time.Weather;

import java.util.*;

public class NightController {
    public static Random rand = new Random();
    private static Coordinate thorCoordinate = null;

    private static final int randomPercentForagingPlant = 10;
    private static final int randomPercentForagingMineral = 10;
    private static final int randomPercentStoneWood = 15;

    public static void nightControl() {
        // Map:
        plantController();

        //Time:
        setWeather();
        setTomorrowWeather();

        thorEffect();

        // Player:
        movePlayers();
        shippingBinControl();

        //Shops:
        shopStockReset();

        // Animal:
        calculateFriendshipAnimal();
        resetAnimals();

        // Interaction
        resetFriends();

        goToNextDay();
        rainyWeatherEffect();
        randomForagingPlants();
        randomForagingMinerals();
        //randomStoneWood();
        //crowControl();
    }

    // Randoms:
    public static void randomForagingPlants() {
        foragingPlantsForEachFarm(new Coordinate(0, 0), new Coordinate(65, 80));
        if (App.getGame().getPlayers().size() >= 2) {
            foragingPlantsForEachFarm(new Coordinate(0, 210), new Coordinate(65, 290));
        }
        if (App.getGame().getPlayers().size() >= 3) {
            foragingPlantsForEachFarm(new Coordinate(175, 210), new Coordinate(240, 290));
        }
        if (App.getGame().getPlayers().size() >= 4) {
            foragingPlantsForEachFarm(new Coordinate(175, 0), new Coordinate(240, 80));
        }
    }

    public static void foragingPlantsForEachFarm(Coordinate c1, Coordinate c2) {
        ArrayList<Forageable> listOfPlants = ForageableFactory.getSeasonPlantForageables(App.getGame().
                getCurrentTime().getSeason());
        for (int x = c1.getX(); x < c2.getX(); x++) {
            for (int y = c1.getY(); y < c2.getY(); y++) {
                if (App.getGame().getTile(new Coordinate(x, y)).getItem() == null &&
                        App.getGame().getTile(new Coordinate(x, y)).getType() == TileType.Ground){
                    if (rand.nextInt(100) < randomPercentForagingPlant) {
                        plantForageable(App.getGame().getTile(new Coordinate(x, y)),
                                listOfPlants.get(rand.nextInt(listOfPlants.size())));
                    }
                }
            }
        }
    }

    public static void randomForagingMinerals() {
        foragingMineralsForEachFarm(new Coordinate(0, 0), new Coordinate(65, 80));
        if (App.getGame().getPlayers().size() >= 2) {
            foragingMineralsForEachFarm(new Coordinate(0, 210), new Coordinate(65, 290));
        }
        if (App.getGame().getPlayers().size() >= 3) {
            foragingMineralsForEachFarm(new Coordinate(175, 210), new Coordinate(240, 290));
        }
        if (App.getGame().getPlayers().size() >= 4) {
            foragingMineralsForEachFarm(new Coordinate(175, 0), new Coordinate(240, 80));
        }
    }

    private static void foragingMineralsForEachFarm(Coordinate c1, Coordinate c2) {
        ArrayList<Forageable> listOfMinerals = ForageableFactory.getMineralForageables();
        for (int x = c1.getX(); x < c2.getX(); x++) {
            for (int y = c1.getY(); y < c2.getY(); y++) {
                if (App.getGame().getTile(new Coordinate(x, y)).getItem() == null &&
                        App.getGame().getTile(new Coordinate(x, y)).getType() == TileType.Mine){
                    if (rand.nextInt(100) < randomPercentForagingMineral) {
                        App.getGame().getTile(new Coordinate(x, y)).
                                setItem(new ForagingMineral((ForagingMineralType) listOfMinerals.get(rand.nextInt(listOfMinerals.size()))));

                    }
                }
            }
        }
    }

    public static void randomStoneWood() {
        stoneWoodForEachFarm(new Coordinate(0, 0), new Coordinate(65, 80));
        if (App.getGame().getPlayers().size() >= 2) {
            stoneWoodForEachFarm(new Coordinate(0, 210), new Coordinate(65, 290));
        }
        if (App.getGame().getPlayers().size() >= 3) {
            stoneWoodForEachFarm(new Coordinate(175, 210), new Coordinate(240, 290));
        }
        if (App.getGame().getPlayers().size() >= 4) {
            stoneWoodForEachFarm(new Coordinate(175, 0), new Coordinate(240, 80));
        }
    }

    public static void stoneWoodForEachFarm(Coordinate c1, Coordinate c2) {
        for (int x = c1.getX(); x < c2.getX(); x++) {
            for (int y = c1.getY(); y < c2.getY(); y++) {
                if (App.getGame().getTile(new Coordinate(x, y)).getItem() == null &&
                        App.getGame().getTile(new Coordinate(x, y)).getType() == TileType.Ground){
                    if (rand.nextInt(100) < randomPercentStoneWood) {
                        int mio = rand.nextInt(10);
                        if (mio < 3) {
                            App.getGame().getTile(new Coordinate(x, y)).
                                    setItem(new Stone());
                        } else if (mio < 6){
                            App.getGame().getTile(new Coordinate(x, y)).
                                    setItem(new Wood());
                        } else {
                            App.getGame().getTile(new Coordinate(x, y)).
                                    setItem(new ForagingCrop(ForagingCropType.Grass));
                        }
                    }
                }
            }
        }
    }

    private static void plantForageable (Tile tile, Forageable plantType) {
        if (tile == null) return;
        if (plantType == null) return;
        if (plantType instanceof ForagingCropType) {
            tile.setItem(new ForagingCrop((ForagingCropType) plantType, false));
        }
        else if (plantType instanceof SeedType seedType) {
            if (seedType == null) return;
            if (seedType.getCrop() == null) return;
            Crop crop;

            if (((SeedType) plantType).getName().equalsIgnoreCase("Mixed Seeds")) {
                Seed randomSeed = new Seed(SeedType.values()[NightController.rand.nextInt(SeedType.values().length - 5)]);
                crop = new Crop(randomSeed.getCrop(),false);
            }
            else {
                crop = new Crop (seedType.getCrop(), false);
            }
            tile.setItem(crop);
        }
    }

    private static void setWeather() {
        App.getGame().getCurrentTime().setWeather(App.getGame().getTomorrowWeather());
    }

    private static void setTomorrowWeather() {
        Season season = App.getGame().getCurrentTime().getSeason();
        Weather randomWeather = season.getWeathers().get(rand.nextInt(season.getWeathers().size()));
        App.getGame().setTomorrowWeather(randomWeather);
    }

    private static void rainyWeatherEffect() {
        if (App.getGame().getCurrentTime().getWeather().equals(Weather.Rain) || App.getGame().getCurrentTime().getWeather().equals(Weather.Storm)) {
            rainyEffectForEachFarm(new Coordinate(0, 0), new Coordinate(65, 80));
            if (App.getGame().getPlayers().size() >= 2) {
                rainyEffectForEachFarm(new Coordinate(0, 210), new Coordinate(65, 290));
            }
            if (App.getGame().getPlayers().size() >= 3) {
                rainyEffectForEachFarm(new Coordinate(175, 210), new Coordinate(240, 290));
            }
            if (App.getGame().getPlayers().size() >= 4) {
                rainyEffectForEachFarm(new Coordinate(175, 0), new Coordinate(240, 80));
            }
        }
    }

    private static void rainyEffectForEachFarm(Coordinate c1, Coordinate c2) {
        for (int x = c1.getX(); x < c2.getX(); x++) {
            for (int y = c1.getY(); y < c2.getY(); y++) {
                if (/*App.getGame().getTile(new Coordinate(x, y)).getItem() == null &&*/
                        App.getGame().getTile(new Coordinate(x, y)).getType() == TileType.Ground){
                    App.getGame().getTile(new Coordinate(x, y)).
                            setWatered(true);
                }
            }
        }
    }

    private static void thorEffect() {
        if (thorCoordinate != null) {
            Tile tile = App.getGame().getTile(thorCoordinate);
            if (tile.getType().equals(TileType.Ground)) {
                if (tile.getItem() instanceof Tree tree) {
                    tree.burn();
                } else {
                    tile.setItem(null);
                }
            }
            thorCoordinate = null;
        } else {
            if (App.getGame().getCurrentTime().getWeather().equals(Weather.Storm)) {
                for (Tile tile: getRandomTilesFromFarm(1, 3)) {
                    if (tile.getItem() instanceof Tree tree) {
                        tree.burn();
                    } else {
                        tile.setItem(null);
                    }
                }
            }
        }
    }

    private static void movePlayers() {
        for (Player player : App.getGame().getPlayers()) {
            if (MapController.getDestinationEnergy(player, player.getHouseCoordinate()) <= player.getEnergy() &&
                MapController.getDestinationEnergy(player, player.getHouseCoordinate()) != -1) {
                player.setCoordinate(player.getHouseCoordinate());
                player.setLibGdxPositionFromCoordinate();
                player.setEnergy(200);
            } else {
                player.setCoordinate(MapController.getDestination(player, player.getHouseCoordinate()));
                player.setEnergy(150);
            }
        }
    }

    private static void goToNextDay () {
        DateAndTime t = App.getGame().getCurrentTime();
        t.setDay(t.getDay() + 1);
        t.setHour(9);
    }

    public static void saveThor(Coordinate Coordinate) {
        thorCoordinate = Coordinate;
    }

    private static List<Tile> getRandomTilesFromFarm(int farm, int count) {
        Set<Tile> selected = new HashSet<>();
        Random rand = new Random();

        int minX = 0;
        int minY = 0;
        int maxX = 65 - 1;
        int maxY = 80 - 1;

        switch (farm) {
            case 2:
                minY = 80 + 130;
                maxY = 160 + 130 - 1;
                break;
            case 3:
                minX = 65 + 110;
                minY = 80 + 130;
                maxX = 130 + 110 - 1;
                maxY = 160 + 130 - 1;
                break;
            case 4:
                minX = 65 + 110;
                maxX = 130 + 110 - 1;
                break;
        }

        while (selected.size() < count) {
            int x = rand.nextInt(maxX - minX + 1) + minX;
            int y = rand.nextInt(maxY - minY + 1) + minY;
            if (App.getGame().getTile(new Coordinate(x, y)).getType().equals(TileType.Ground))
                selected.add(App.getGame().getTile(new Coordinate(x, y)));
        }

        return new ArrayList<>(selected);
    }

    private static void calculateFriendshipAnimal() {
        for (Player player: App.getGame().getPlayers()) {
            for (Animal animal: player.getMyAnimals()) {
                if (!animal.isPetted())
                    animal.addFriendship(-10);
                if (!animal.isFeeded())
                    animal.addFriendship(-20);
                if (animal.getCoordinate() !=  null)
                    animal.addFriendship(-20);
            }
        }
    }

    private static void resetAnimals() {
        for (Player player: App.getGame().getPlayers()) {
            for (Animal animal: player.getMyAnimals()) {
                setAnimalProduct(animal);
                animal.setFeeded(false);
                animal.setPetted(false);
            }
        }
    }

    private static void setAnimalProduct(Animal animal) {
        //TODO:  یببینننن هر چند روز یه بار میزاد رو هندل نکردم تو داکم نبود هر شب میزاد این
        if (!animal.isFeeded()) {
            animal.setProduct(null);
            return;
        }

        ArrayList<AnimalProductType> productTypes = animal.getType().getProducts();
        AnimalProductType selectedProductType;

        if (productTypes.size() == 1 || animal.getFriendship() < 100) {
            selectedProductType = productTypes.get(0);
        } else {
            double random = 0.5 + Math.random();
            double chance = (animal.getFriendship() + (150 * random)) / 1500.0;

            if (chance >= 1) {
                selectedProductType = productTypes.get(1);
            } else {
                selectedProductType = productTypes.get(0);
            }
        }

        animal.setProduct(new AnimalProduct(selectedProductType, animal.getFriendship()));
    }

    private static void plantController() {
        plantControllerForEachFarm(new Coordinate(0, 0), new Coordinate(65, 80));
        if (App.getGame().getPlayers().size() >= 2) {
            plantControllerForEachFarm(new Coordinate(0, 210), new Coordinate(65, 290));
        }
        if (App.getGame().getPlayers().size() >= 3) {
            plantControllerForEachFarm(new Coordinate(175, 210), new Coordinate(240, 290));
        }
        if (App.getGame().getPlayers().size() >= 4) {
            plantControllerForEachFarm(new Coordinate(175, 0), new Coordinate(240, 80));
        }
    }

    private static void plantControllerForEachFarm(Coordinate c1, Coordinate c2) {
        // TODO: Aynaz ab? khoshk nemikone
        for (int x = c1.getX(); x < c2.getX(); x++) {
            for (int y = c1.getY(); y < c2.getY(); y++) {
                Tile tile = App.getGame().getTile(new Coordinate(x, y));
                if (tile.getItem() == null || !(tile.getItem() instanceof Plant)) {
                    tile.setWatered(false);
                    continue;
                }
                if (App.getGame().getTomorrowWeather() == Weather.Rain) {
                    tile.setWatered(true);
                    continue;
                }
                if (tile.getFertilize() == 1) {
                    tile.setWatered(true);
                    continue;
                }
                if (tile.getLastTimeWatered() == null) {
                    tile.setItem(null);
                    tile.setWatered(false);
                }
                else if (!tile.isWatered()) {
                    tile.setItem(null);
                }
                else if (App.getGame().getCurrentTime().getDay() - tile.getLastTimeWatered().getDay() >= 1) {
                    tile.setWatered(false);
                }
            }
        }
    }

    private static void shopStockReset () {
        for (Shop shop : App.getGame().getShops()) {
            shop.resetStock();
        }
    }

    private static void crowControl () {
        crowControlForEachFarm(new Coordinate(0, 0), new Coordinate(65, 80));
        if (App.getGame().getPlayers().size() >= 2) {
            crowControlForEachFarm(new Coordinate(0, 210), new Coordinate(65, 290));
        }
        if (App.getGame().getPlayers().size() >= 3) {
            crowControlForEachFarm(new Coordinate(175, 210), new Coordinate(240, 290));
        }
        if (App.getGame().getPlayers().size() >= 4) {
            crowControlForEachFarm(new Coordinate(175, 0), new Coordinate(240, 80));
        }
    }

    private static void crowControlForEachFarm (Coordinate c1, Coordinate c2) {
        // TODO: Aynaz darsad biar payin
        int numberOfPlants = 0;
        // Phase 1:
        //int[][] scared = new int[90][120];
        int[][] scared = new int[240][290];


        for (int x = c1.getX(); x < c2.getX(); x++) {
            for (int y = c1.getY(); y < c2.getY(); y++) {
                if (App.getGame().getTile(new Coordinate(x, y)).getItem() != null &&
                        App.getGame().getTile(new Coordinate(x, y)).getType() == TileType.Ground) {

                    if (App.getGame().getTile(new Coordinate(x, y)).getItem() instanceof Plant) {
                        numberOfPlants++;
                    }
                    else if (App.getGame().getTile(new Coordinate(x, y)).getItem() instanceof Craft craft) {
                        if (craft.getType() == CraftType.Scarecrow) {
                            for (int i = Math.max(0, x - 8); i < Math.min(240 - 1, x + 8); i++) {
                                for (int j = Math.max(0, y - 8); i < Math.min(290 - 1, y + 8); i++) {
                                    scared[i][j] = 1;
                                }
                            }
                        }
                        else if (craft.getType() == CraftType.DeluxeScarecrow) {
                            for (int i = Math.max(0, x - 12); i < Math.min(240 - 1, x + 12); i++) {
                                for (int j = Math.max(0, y - 12); i < Math.min(290 - 1, y + 12); i++) {
                                    scared[i][j] = 1;
                                }
                            }
                        }
                    }

                }
            }
        }


        for (int x = c1.getX(); x < c2.getX(); x++) {
            for (int y = c1.getY(); y < c2.getY(); y++) {
                if (App.getGame().getTile(new Coordinate(x, y)).getItem() != null &&
                        App.getGame().getTile(new Coordinate(x, y)).getType() == TileType.Ground &&
                        App.getGame().getTile(new Coordinate(x, y)).getItem() instanceof Plant plant){
                    if (scared[x][y] != 1) {
                        if (rand.nextInt(100) < 4) {
                            if (plant instanceof Tree tree) {
                                tree.setLastTimeHarvested(App.getGame().getCurrentTime());
                            } else if (plant instanceof Crop crop) {
                                if (crop.isOneTime()) {
                                    App.getGame().getTile(new Coordinate(x, y)).setItem(null);
                                } else {
                                    crop.setLastTimeHarvested(App.getGame().getCurrentTime());
                                }
                            } else {
                                App.getGame().getTile(new Coordinate(x, y)).setItem(null);
                            }
                        }
                    }
                }
            }
        }

    }

    private static void shippingBinControl() {
        for (Player player: App.getGame().getPlayers()) {
            player.addCount(player.getShippingBin().getPrices());
            player.getShippingBin().clear();
        }
    }

    private static void resetFriends() {
        for (Player player : App.getGame().getPlayers()) {
            for (Friend friend : player.getFriends()) {
                if (!friend.isGiftedToday() && !friend.isHuggedToday() && !friend.isTalkedToday()) {
                    friend.addXP(-10);
                }
                friend.setHuggedToday(false);
                friend.setTalkedToday(false);
                friend.setGiftedToday(false);
            }
        }
    }
}
