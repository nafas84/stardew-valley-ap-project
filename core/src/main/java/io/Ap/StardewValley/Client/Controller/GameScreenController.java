package io.Ap.StardewValley.Client.Controller;

import com.badlogic.gdx.Gdx;
import io.Ap.StardewValley.Client.Controller.NetworkControllers.UpdateController;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.*;
import io.Ap.StardewValley.Common.Model.Plants.*;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProduct;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProductType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.Food;
import io.Ap.StardewValley.Common.Model.Cooking.FoodType;
import io.Ap.StardewValley.Common.Model.Crafting.Craft;
import io.Ap.StardewValley.Common.Model.Crafting.CraftType;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Map.TileType;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;
import io.Ap.StardewValley.Common.Model.Time.Season;
import io.Ap.StardewValley.Common.Model.Tool.Shear;
import io.Ap.StardewValley.Common.Model.Tool.Tool;
import io.Ap.StardewValley.Common.Model.Tool.ToolType;
import io.Ap.StardewValley.Client.Screen.GameScreen;
import io.Ap.StardewValley.Client.Screen.InventoryScreen.MapTab;
import io.Ap.StardewValley.Client.Screen.MapScreen.RegionTransition;
import io.Ap.StardewValley.Client.Screen.PlayerScreen.DirectionType;
import io.Ap.StardewValley.Client.Screen.PlayerScreen.PlayerRender;
import io.Ap.StardewValley.Client.Screen.PlayerScreen.StateType;

public class GameScreenController {
    private final PlayerRender playerRender = new PlayerRender();
    private GameScreen view;

    // animal:
//    private static boolean isAnimalListVisible = false;
//    private static boolean animalListNeedsUpdate = false;
    private static boolean isMiniGameVisible = false;

    // multiplayer:
    private static boolean isScoreboardVisible = false;

    //inventory:
    private static boolean isInventoryStageVisible = false;
    private static boolean inventoryStageNeedsUpdate = false;

    //cooking:
    private static boolean isCookingStageVisible = false;
    private static boolean cookingStageNeedsUpdate = false;

    //tools:
    private boolean isToolActionInProgress = false;
    private float toolActionStartTime = 0f;
    private float toolActionDuration = 0.6f;
    boolean callToolUse = false;

    //shops:
    private static ShopType visibleShop = null;

    //shipping bin:
    private static boolean isShippingBinVisible = false;
    private static boolean shippingBinNeedsUpdate = false;

    //food:
    private boolean isFoodActionInProgress = false;
    private float foodActionStartTime = 0f;
    private float foodActionDuration = 1.44f;
    boolean callFoodUse = false;

    private boolean isAnotherVisible() {
        return isMiniGameVisible || isScoreboardVisible ||
                isInventoryStageVisible || isCookingStageVisible || isShippingBinVisible;
    }
    public void setViews(GameScreen view) {
        this.view = view;
    }

    public void updateTime(float delta) {
        DateAndTime time = App.getGame().getCurrentTime();
        int oldHour = time.getHour();

        time.update(delta);

        int newHour = time.getHour();

        if (oldHour <= 24 && newHour >= 24)
            goToNextDay();

        view.getTimeBar().updateTime();
        view.getTimeBar().updateEnergy();
    }

    public void goToNextDay() {
        view.setPaused(true);
        view.showNightOverlay(() -> {
            Season oldSeason = App.getGame().getCurrentTime().getSeason();
            NightController.nightControl();
            Season newSeason = App.getGame().getCurrentTime().getSeason();
            if (oldSeason != newSeason) {
                view.updateSeasonMap(newSeason.name().toLowerCase());
                view.getTimeBar().updateSeason(newSeason);
            }
            // time bar:
            view.setWeatherLayerToStage(App.getGame().getCurrentTime().getWeather());
            view.getTimeBar().updateWeather();
            view.setPaused(false);
        });
        view.getTimeBar().updateTime();
    }

    public void  updateGame() {
        handleInputKey();
        //inventory bar:
        view.getInventoryBar().updateInventoryBar();

        //inventory stage:
        view.getInventoryStage().setVisibleAll(isInventoryStageVisible);
        if (inventoryStageNeedsUpdate) {
            view.getInventoryStage().update();
            inventoryStageNeedsUpdate = false;
        }

        //cooking stage
        view.getCookingStage().setVisibleAll(isCookingStageVisible);
        if (cookingStageNeedsUpdate) {
            view.getCookingStage().update();
            cookingStageNeedsUpdate = false;
        }

        //todo: سنگین میشه یا نه؟
        ((MapTab) view.getInventoryStage().getInfoWindows().get(3)).updatePlayerPosition();
        updateToolAction(Gdx.graphics.getDeltaTime());
        updateFoodAction(Gdx.graphics.getDeltaTime());

        // animal:
//        view.getAnimalListWindow().setVisible(isAnimalListVisible);
//        if (animalListNeedsUpdate) {
//            view.getAnimalListWindow().update();
//            animalListNeedsUpdate = false;
//        }

        // miniGame:
        view.getMiniGameWindow().setVisible(isMiniGameVisible);

        // multiplayer:
        view.getScoreboardWindow().setVisible(isScoreboardVisible);


        //shops:
        updateShops();

        //shipping bin:
        //inventory stage:
        view.getShippingBin().setVisibleAll(isShippingBinVisible);
        if (shippingBinNeedsUpdate) {
            view.getShippingBin().update();
            shippingBinNeedsUpdate = false;
        }
    }

    private void handleInputKey(){
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getPauseGame()) && !view.isPaused()){
            view.setPaused(true);
            view.showPauseDialog();
        }

        // cheats:
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getAynazCheat())) {
            App.getGame().getCurrentPlayer().addAbility(Skill.Farming, 10);
            App.getGame().getCurrentPlayer().setInventoryCapacity(24);
            App.getGame().getCurrentPlayer().getInventory().addItem(new AnimalProduct(AnimalProductType.Egg), 10);
            App.getGame().getCurrentPlayer().getInventory().addItem(new Shear(), 1);
            App.getGame().getCurrentPlayer().getInventory().addItem(new Seed(SeedType.JazzSeeds));
            App.getGame().getCurrentPlayer().getInventory().addItem(new Craft(CraftType.BeeHouse));
            App.getGame().getCurrentPlayer().getInventory().addItem(new Craft(CraftType.Scarecrow));
            App.getGame().getCurrentPlayer().getInventory().addItem(new Craft(CraftType.DeluxeScarecrow));
            App.getGame().getCurrentPlayer().getInventory().addItem(new Food(FoodType.BakedFish));
            App.getGame().getCurrentPlayer().getInventory().addItem(new Crop(CropType.Wheat));
            App.getGame().getCurrentPlayer().getInventory().addItem(new Fruit(FruitType.Apricot));
            App.getGame().getCurrentPlayer().getInventory().addItem(new ForagingMineral(ForagingMineralType.Coal), 100);
            App.getGame().getCurrentPlayer().getInventory().addItem(new ForagingMineral(ForagingMineralType.Copper), 100);
            App.getGame().getCurrentPlayer().getInventory().addItem(new Stone(), 1000);
            App.getGame().getCurrentPlayer().getInventory().addItem(new Wood(), 1000);
            App.getGame().getCurrentPlayer().getInventory().addItem(new Sapling(SaplingType.ApricotSapling));
            inventoryStageNeedsUpdate = true;
            cookingStageNeedsUpdate = true;
            if (visibleShop == null) {
                visibleShop = ShopType.CarpentersShop;
            } else {
                visibleShop = null;
            }
            App.getGame().getCurrentPlayer().addCount(1000);

        }

        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getNafisehCheat())){
           Player player = App.getGame().getCurrentPlayer();
//           player.addAnimal(new Animal(AnimalType.Pig, "nafiseh"));
//           player.addAnimal(new Animal(AnimalType.Goat, "goat"));
//           player.addAnimal(new Animal(AnimalType.Dinosaur, "kir"));
//           player.addAnimal(new Animal(AnimalType.Sheep, "sheep"));
            //App.getGame().getCurrentPlayer().setEnergy(-1);
            //App.getGame().getCurrentTime().setHour(23);
            //App.getGame().getCurrentTime().setMinute(59);
            //App.getGame().getMap().build(new Coordinate(7, 27), BuildingType.GreenHouseBuild);
            view.setCurrentResult(MapController.buildGreenHouse());
            //App.getGame().getCurrentTime().addDay(27);
            //goToNextDay();
            //App.getGame().getMap().getFullMap()[App.getGame().getCurrentPlayer().getCoordinate().getX()][App.getGame().getCurrentPlayer().getCoordinate().getY()].setFertilize(1);

//            App.getGame().getMap().getFullMap()
//                    [App.getGame().getCurrentPlayer().getCoordinate().getX()][App.getGame().getCurrentPlayer().getCoordinate().getY()]
//                    .setPlowed(true);

//            App.getGame().getMap().getFullMap()
//                    [App.getGame().getCurrentPlayer().getCoordinate().getX() + 1][App.getGame().getCurrentPlayer().getCoordinate().getY() + 1]
//                    .setItem(new Crop(CropType.Potato));
        }

        // animal:
//        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getAnimalList())) {
//            isAnimalListVisible = !isAnimalListVisible;
//            if (isAnimalListVisible) {
//                animalListNeedsUpdate = true;
//                // بقیه UI ها رو ببند
//                isInventoryStageVisible = false;
//                isCookingStageVisible = false;
//                isShippingBinVisible = false;
//                visibleShop = null;
//            }
//        }

        // miniGame:
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getMiniGame())) {
            isMiniGameVisible = true;
            isInventoryStageVisible = false;
            isCookingStageVisible = false;
            isShippingBinVisible = false;
            visibleShop = null;
        }

        // multiplayer:
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getScoreboard())) {
            isScoreboardVisible = !isScoreboardVisible;
            isMiniGameVisible = false;
            isInventoryStageVisible = false;
            isCookingStageVisible = false;
            isShippingBinVisible = false;
            visibleShop = null;
        }

        //inventory:
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getOpenInventory())){
            isInventoryStageVisible = !isInventoryStageVisible;
            if (isInventoryStageVisible) {
                inventoryStageNeedsUpdate = true;
                isCookingStageVisible = false;
                isShippingBinVisible = false;
                visibleShop = null;
            }
        }

        //cooking:
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getOpenRefrigerator())){
            isCookingStageVisible = !isCookingStageVisible;
            if (isCookingStageVisible) {
                cookingStageNeedsUpdate = true;
                isInventoryStageVisible = false;
                isShippingBinVisible = false;
                visibleShop = null;
            }
        }

        //shipping bin:
        if (Gdx.input.isKeyJustPressed(App.getKeyManager().getOpenShippingBin())) {
            if (isShopBesideMe(BuildingType.ShippingBin)) {
                isShippingBinVisible = !isShippingBinVisible;
                if (isShippingBinVisible) {
                    shippingBinNeedsUpdate = true;
                    isCookingStageVisible = false;
                    isInventoryStageVisible = false;
                    visibleShop = null;
                }
            }
        }
    }

    public void updatePlayer() {
        // handle energy:
        Player player = App.getGame().getCurrentPlayer();
        if (player.getEnergy() > 0) {
            if (!isAnotherVisible())
                handlePlayerInputKey();
        } else {
            player.setState(StateType.Faint);
            player.setDirection(DirectionType.Down);
        }

        playerRender.render();
    }

    private void handlePlayerInputKey() {
        // player inputs: walk, eat, use tool:
        Player player = App.getGame().getCurrentPlayer();
        boolean isMoving = false;
        boolean isUsingTool = isToolActionActive();
        boolean isEating = isFoodActionActive();

        float newX = player.getXLibGdx();
        float newY = player.getYLibGdx();

        // Player move:
        int speed = App.getGame().getPlayerSpeed();
        if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveUp())){
            newY += speed;
            isMoving = true;
            player.setDirection(DirectionType.Up);
        } else if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveDown())){
            newY -= speed;
            isMoving = true;
            player.setDirection(DirectionType.Down);
        } else if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveRight())){
            newX += speed;
            isMoving = true;
            player.setDirection(DirectionType.Right);
        } else if (Gdx.input.isKeyPressed(App.getKeyManager().getMoveLeft())){
            newX -= speed;
            isMoving = true;
            player.setDirection(DirectionType.Left);
        } else if (Gdx.input.isButtonJustPressed(App.getKeyManager().getLeftClick())){
            try {
                if (isShopBesideMe(BuildingType.Blacksmith) && visibleShop != ShopType.Blacksmith) {
                    visibleShop = ShopType.Blacksmith;
                } else if (isShopBesideMe(BuildingType.CarpentersShop) && visibleShop != ShopType.CarpentersShop) {
                    visibleShop = ShopType.CarpentersShop;
                } else if (isShopBesideMe (BuildingType.FishShop) && visibleShop != ShopType.FishShop) {
                    visibleShop = ShopType.FishShop;
                } else if (isShopBesideMe(BuildingType.JojaMart)) {
                    visibleShop = ShopType.JojaMart;
                } else if (isShopBesideMe(BuildingType.MarniesRanch)) {
                    visibleShop = ShopType.MarniesRanch;
                } else if (isShopBesideMe(BuildingType.PierresGeneralStore)) {
                    visibleShop = ShopType.PierresGeneralStore;
                } else if (isShopBesideMe(BuildingType.TheStarDropSaloon)) {
                    visibleShop = ShopType.TheStarDropSaloon;
                } else if (isShopBesideMe(BuildingType.ShippingBin)) {
//                    isShippingBinVisible = !isShippingBinVisible;
//                    if (isShippingBinVisible) {
//                        shippingBinNeedsUpdate = true;
//                        isCookingStageVisible = false;
//                        isInventoryStageVisible = false;
//                        visibleShop = null;
//                    }
                }
                else if (view.getInventoryBar().getSelectedItem() instanceof Tool && player.getCurrentTool() != null) {
                    isUsingTool = true;
                    startToolAction();
                    callToolUse = true;
                }
                else if (view.getInventoryBar().getSelectedItem() instanceof Seed seed) {
                    view.setCurrentResult(PlantController.plantThroughScreen(seed.getName(), ToolController.directionTypeToString(App.getGame().getCurrentPlayer().getDirection())));
                }
                else if (view.getInventoryBar().getSelectedItem() instanceof Sapling sapling) {
                    view.setCurrentResult(PlantController.plantThroughScreen(sapling.getName(), ToolController.directionTypeToString(App.getGame().getCurrentPlayer().getDirection())));
                }
                else if (view.getInventoryBar().getSelectedItem() instanceof Craft craft) {
                    view.setCurrentResult(CraftController.placeCraftThroughScreen(craft.getName(), ToolController.directionTypeToString(App.getGame().getCurrentPlayer().getDirection())));
                }
                else if (view.getInventoryBar().getSelectedItem() instanceof Food food) {
                    if (view.setCurrentResult(FoodController.eatThroughScreen(food.getName()))) {
                        isEating = true;
                        startFoodAction();
                    }
                } else if (view.getInventoryBar().getSelectedItem() instanceof Crop food) {
                    if (view.setCurrentResult(FoodController.eatThroughScreen(food.getName()))) {
                        isEating = true;
                        startFoodAction();
                    }
                } else if (view.getInventoryBar().getSelectedItem() instanceof Fruit food) {
                    if (view.setCurrentResult(FoodController.eatThroughScreen(food.getName()))) {
                        isEating = true;
                        startFoodAction();
                    }
                }
            } catch (Exception e) {}

        }

        Tool tool = player.getCurrentTool();
        // status
        if (isMoving) {
            player.setState(StateType.Walk);
            //player.addEnergy(-1);
        } else if (isEating) {
            player.setState(StateType.Eat);
        } else if (isUsingTool && player.getCurrentTool() == null) {
            player.setState(StateType.Idle);
        } else if (isUsingTool && player.getCurrentTool().getType() == ToolType.Shear) {
            if (callToolUse) {
                if (view.setCurrentResult(ToolController.useToolThoughScreen(ToolController.directionTypeToString(player.getDirection())))) {
                    player.setState(StateType.ToolShear);
                }
                callToolUse = false;
            }
        } else if (isUsingTool && player.getCurrentTool().getType() == ToolType.MilkPail) {
            if (callToolUse) {
                if (view.setCurrentResult(ToolController.useToolThoughScreen(ToolController.directionTypeToString(player.getDirection())))) {
//                    player.setState(StateType.ToolMilkPail);
                }
                callToolUse = false;
            }
        } else if (isUsingTool && player.getCurrentTool().getType() == ToolType.Hoe) {
            if (callToolUse) {
                if (view.setCurrentResult(ToolController.useToolThoughScreen(ToolController.directionTypeToString(player.getDirection())))) {
                    player.setState(StateType.ToolHoe);
                }
                callToolUse = false;
            }
        } else if (isUsingTool && player.getCurrentTool().getType() == ToolType.Axe) {
            if (callToolUse) {
                if (view.setCurrentResult(ToolController.useToolThoughScreen(ToolController.directionTypeToString(player.getDirection())))) {
                    player.setState(StateType.ToolAxe);
                }
                callToolUse = false;
            }
        } else if (isUsingTool && player.getCurrentTool().getType() == ToolType.Pickaxe) {
            if (callToolUse) {
                if (view.setCurrentResult(ToolController.useToolThoughScreen(ToolController.directionTypeToString(player.getDirection())))) {
                    player.setState(StateType.ToolPickaxe);
                }
                callToolUse = false;
            }
        } else if (isUsingTool && player.getCurrentTool().getType() == ToolType.Scythe) {
            if (callToolUse) {
                if (view.setCurrentResult(ToolController.useToolThoughScreen(ToolController.directionTypeToString(player.getDirection())))) {
//                    player.setState(StateType.ToolScythe);
                }
                callToolUse = false;
            }
        } else if (isUsingTool && player.getCurrentTool().getType() == ToolType.WateringCan) {
            if (callToolUse) {
                if (view.setCurrentResult(ToolController.useToolThoughScreen(ToolController.directionTypeToString(player.getDirection())))) {
                    player.setState(StateType.ToolWateringCan);
                }
                callToolUse = false;
            }
        } else {
            player.setState(StateType.Idle);
        }

        // change region:
        Coordinate oldRegion = App.getGame().getMap().getCurrentRegionCoordinate();
        Coordinate newCoordinate = getPlayerCoordinate(newX, newY);

        // set new coordinate:
        if (App.getGame().getTile(newCoordinate) != null && App.getGame().getTile(newCoordinate).isWalkable()) {
            // calculate newRegion:
            player.setXLibGdx(newX);
            player.setYLibGdx(newY);
            player.setCoordinate(newCoordinate);

            Coordinate newRegion = App.getGame().getMap().getCurrentRegionCoordinate(newCoordinate);
            if (!oldRegion.equals(newRegion)) {
                RegionTransition rt = RegionTransition.get(oldRegion, newRegion);
                if (rt != null) {
                    player.setXLibGdx(rt.getLibGdxX(16));
                    player.setYLibGdx(rt.getLibGdxY(16));
                    player.setCoordinate(rt.getDestinationCoordinate());
                }
            }
        }
    }

    public Coordinate getPlayerCoordinate(float xLibGdx, float yLibGdx) {
        final int tileSize = 16;

        // TODO: curren region:
        Coordinate cor = App.getGame().getMap().getCurrentRegionCoordinate();
        int mapHeightInTiles = App.getGame().getMap().getCurrentRegion().getTiles().length;

        int[] rowOffsets = App.getGame().getMap().getRowOffsets();
        int[] colOffsets = App.getGame().getMap().getColOffsets();


        int localX = mapHeightInTiles - 1 - (int)(yLibGdx / tileSize);
        int localY = (int)((xLibGdx + tileSize / 2f) / tileSize);

        int globalX = rowOffsets[cor.getX()] + localX;
        int globalY = colOffsets[cor.getY()] + localY;

        return new Coordinate(globalX, globalY);
    }


    public static void setInventoryStageNeedsUpdate(boolean inventoryStageNeedsUpdate) {
        GameScreenController.inventoryStageNeedsUpdate = inventoryStageNeedsUpdate;
    }

    public static void setCookingStageNeedsUpdate(boolean cookingStageNeedsUpdate) {
        GameScreenController.cookingStageNeedsUpdate = cookingStageNeedsUpdate;
    }


    public void startToolAction() {
        isToolActionInProgress = true;
        toolActionStartTime = 0f;
    }

    public void updateToolAction(float deltaTime) {
        if (isToolActionInProgress) {
            toolActionStartTime += deltaTime;
            if (toolActionStartTime >= toolActionDuration) {
                isToolActionInProgress = false;
            }
        }
    }

    public boolean isToolActionActive() {
        return isToolActionInProgress;
    }

    public void updateShops () {
        view.getBlackSmithStage().setVisibleAll((visibleShop == ShopType.Blacksmith));
        view.getCarpentersStage().setVisibleAll((visibleShop == ShopType.CarpentersShop));
        view.getFishShopStage().setVisibleAll((visibleShop == ShopType.FishShop));
        view.getJojaMartStage().setVisibleAll((visibleShop == ShopType.JojaMart));
        view.getMarniesStage().setVisibleAll((visibleShop == ShopType.MarniesRanch));
        view.getPierresStage().setVisibleAll((visibleShop == ShopType.PierresGeneralStore));
        view.getStardropStage().setVisibleAll((visibleShop == ShopType.TheStarDropSaloon));

        if (visibleShop == ShopType.Blacksmith) {
            view.getBlackSmithStage().update();
        } else if (visibleShop == ShopType.CarpentersShop) {
            view.getCarpentersStage().update();
        } else if (visibleShop == ShopType.FishShop) {
            view.getFishShopStage().update();
        } else if (visibleShop == ShopType.JojaMart) {
            view.getJojaMartStage().update();
        } else if (visibleShop == ShopType.MarniesRanch) {
            view.getMarniesStage().update();
        } else if (visibleShop == ShopType.PierresGeneralStore) {
            view.getPierresStage().update();
        } else if (visibleShop == ShopType.TheStarDropSaloon) {
            view.getStardropStage().update();
        }
    }

    public static boolean isShopBesideMe(BuildingType buildingType) {
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
            if (tile != null && tile.getType().equals(TileType.Building)) {
                if (tile.getBuildingType().equals(buildingType))
                    return true;
            }
        }
        return false;
    }

    public static void setVisibleShop(ShopType visibleShop) {
        GameScreenController.visibleShop = visibleShop;
    }

    public boolean isFoodActionActive() {
        return isFoodActionInProgress;
    }

    public void updateFoodAction(float deltaTime) {
        if (isFoodActionInProgress) {
            foodActionStartTime += deltaTime;
            if (foodActionStartTime >= foodActionDuration) {
                isFoodActionInProgress = false;
            }
        }
    }

    public void startFoodAction() {
        isFoodActionInProgress = true;
        foodActionStartTime = 0f;
    }

    public static void setIsMiniGameVisible(boolean isMiniGameVisible) {
        GameScreenController.isMiniGameVisible = isMiniGameVisible;
    }
}
