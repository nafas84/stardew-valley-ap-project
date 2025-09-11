package io.Ap.StardewValley.Client.Screen.MapScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;

import io.Ap.StardewValley.Common.Model.Animals.AnimalProductType;
import io.Ap.StardewValley.Common.Model.Animals.FishType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.FoodType;
import io.Ap.StardewValley.Common.Model.Cooking.IngredientType;
import io.Ap.StardewValley.Common.Model.Crafting.Craft;
import io.Ap.StardewValley.Common.Model.Crafting.CraftType;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Map.TileType;
import io.Ap.StardewValley.Common.Model.Plants.*;
import io.Ap.StardewValley.Common.Model.Player.GiftType;
import io.Ap.StardewValley.StardewValley;

public class DynamicMapLayerRender {
    // Buildings:
    private final Map<BuildingType, TextureRegion[][]> buildings = new EnumMap<>(BuildingType.class);

    // Map Item:
    private final Map<String, TextureRegion> additional = new HashMap<>();
    private final Map<ForagingCropType, TextureRegion> foragingCrops = new EnumMap<>(ForagingCropType.class);
    private final Map<ForagingMineralType, TextureRegion> foragingMinerals = new EnumMap<>(ForagingMineralType.class);
    private final Map<CraftType, TextureRegion> crafts = new EnumMap<>(CraftType.class);
    private final Map<AnimalProductType, TextureRegion> animalProducts = new EnumMap<>(AnimalProductType.class);
    private final Map<FishType, TextureRegion> fishes = new EnumMap<>(FishType.class);
    private final Map<FoodType, TextureRegion> foods = new EnumMap<>(FoodType.class);
    private final Map<GiftType, TextureRegion> gifts = new EnumMap<>(GiftType.class);
    private final Map<SeedType, TextureRegion> seeds = new EnumMap<>(SeedType.class);
    private final Map<SaplingType, TextureRegion> saplings = new EnumMap<>(SaplingType.class);
    private final Map<IngredientType, TextureRegion> ingredients = new EnumMap<>(IngredientType.class);

    private final Map<TreeType, List<TextureRegion>> treeStages = new EnumMap<>(TreeType.class);
    private final Map<CropType, List<TextureRegion>> cropStages = new EnumMap<>(CropType.class);

    // building:
    {
        TextureRegion[][] buildGreenHouse = TextureRegion.split(new Texture("map/buildings/BuildGreenHouse.png"), 16, 16);
        TextureRegion[][] barn = TextureRegion.split(new Texture("map/buildings/Barn.png"), 16, 16);
        TextureRegion[][] coop = TextureRegion.split(new Texture("map/buildings/Coop.png"), 16, 16);
        TextureRegion[][] well = TextureRegion.split(new Texture("map/buildings/Well.png"), 16, 16);
        TextureRegion[][] ShippingBin = TextureRegion.split(new Texture("map/buildings/ShippingBin.png"), 16, 16);

        buildings.put(BuildingType.GreenHouseBuild, buildGreenHouse);
        buildings.put(BuildingType.Coop, coop);
        buildings.put(BuildingType.Well, well);
        buildings.put(BuildingType.ShippingBin, ShippingBin);
        buildings.put(BuildingType.Barn, barn);
    }
    // tree stages:
    {
        TextureRegion[][] treeSheet1 = TextureRegion.split(new Texture("map/items/TreeStages.png"), 48, 5 * 16);
        TextureRegion[][] treeSheet2 = TextureRegion.split(new Texture("map/items/TreeStages2.png"), 48, 6 * 16);

        TreeType[] treeTypes = TreeType.values();

        for (int i = 0; i < 14; i++) {
            TreeType type = treeTypes[i];
            List<TextureRegion> stages;
            if (i < 8)
                stages = new ArrayList<>(Arrays.asList(treeSheet1[i]).subList(0, 5));
            else
                stages = new ArrayList<>(Arrays.asList(treeSheet2[i-8]).subList(0, 5));

            treeStages.put(type, stages);
        }
    }
    // crop stages:
    {
        TextureRegion[][] cropSheet = TextureRegion.split(new Texture("map/items/CropStages.png"), 16, 32);

        CropType[] cropTypes = CropType.values();

        for (int i = 0; i < cropTypes.length - 1; i++) {
            CropType type = cropTypes[i];
            List<TextureRegion> stages;
            int len = type.getStages().length + 1;

            if (i < 21)
                stages = new ArrayList<>(Arrays.asList(cropSheet[i]).subList(0, len));
            else
                stages = new ArrayList<>(Arrays.asList(cropSheet[i-21]).subList(6, 6 + len));

            cropStages.put(type, stages);
        }
    }
    // foraging crops:
    {
        TextureRegion[][] foragingSheet = TextureRegion.split(new Texture("items/ForagingCrops.png"), 16, 16);

        int rows = foragingSheet.length;
        int cols = foragingSheet[0].length;

        int index = 0;
        for (ForagingCropType type : ForagingCropType.values()) {
            int row = index / cols;
            int col = index % cols;

            if (row >= rows) break;

            foragingCrops.put(type, foragingSheet[row][col]);
            index++;
        }
    }
    // foraging minerals:
    {
        TextureRegion[][] foragingSheet = TextureRegion.split(new Texture("items/ForagingMinerals.png"), 16, 16);

        int rows = foragingSheet.length;
        int cols = foragingSheet[0].length;

        int index = 0;
        for (ForagingMineralType type : ForagingMineralType.values()) {
            int row = index / cols;
            int col = index % cols;

            if (row >= rows) break;

            foragingMinerals.put(type, foragingSheet[row][col]);
            index++;
        }
    }
    // crafts:
    {
        TextureRegion[][] craftSheet = TextureRegion.split(new Texture("items/Crafts.png"), 16, 32);

        int rows = craftSheet.length;
        int cols = craftSheet[0].length;

        int index = 0;
        for (CraftType type : CraftType.values()) {
            int row = index / cols;
            int col = index % cols;

            if (row >= rows) break;

            crafts.put(type, craftSheet[row][col]);
            index++;
        }
    }
//    // animal products:
//    {
//        TextureRegion[][] animalProductsSheet = TextureRegion.split(new Texture("items/AnimalProducts.png"), 16, 16);
//
//        int rows = animalProductsSheet.length;
//        int cols = animalProductsSheet[0].length;
//
//        int index = 0;
//        for (AnimalProductType type : AnimalProductType.values()) {
//            int row = index / cols;
//            int col = index % cols;
//
//            if (row >= rows) break;
//
//            animalProducts.put(type, animalProductsSheet[row][col]);
//            index++;
//        }
//    }
//    // fishes:
//    {
//        TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Fishes.png"), 16, 16);
//
//        int rows = sheet.length;
//        int cols = sheet[0].length;
//
//        int index = 0;
//        for (FishType type : FishType.values()) {
//            int row = index / cols;
//            int col = index % cols;
//
//            if (row >= rows) break;
//
//            fishes.put(type, sheet[row][col]);
//            index++;
//        }
//    }
//    // foods:
//    {
//        TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Foods.png"), 16, 16);
//
//        int rows = sheet.length;
//        int cols = sheet[0].length;
//
//        int index = 0;
//        for (FoodType type : FoodType.values()) {
//            int row = index / cols;
//            int col = index % cols;
//
//            if (row >= rows) break;
//
//            foods.put(type, sheet[row][col]);
//            index++;
//        }
//    }
//    // gifts:
//    {
//        TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Gifts.png"), 16, 16);
//
//        int rows = sheet.length;
//        int cols = sheet[0].length;
//
//        int index = 0;
//        for (GiftType type : GiftType.values()) {
//            int row = index / cols;
//            int col = index % cols;
//
//            if (row >= rows) break;
//
//            gifts.put(type, sheet[row][col]);
//            index++;
//        }
//    }
//    // seeds:
//    {
//        TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Seeds.png"), 16, 16);
//
//        int rows = sheet.length;
//        int cols = sheet[0].length;
//
//        int index = 0;
//        for (SeedType type : SeedType.values()) {
//            int col = index / rows;
//            int row = index % rows;
//
//            if (col >= cols) break;
//
//            seeds.put(type, sheet[row][col]);
//            index++;
//        }
//    }
//    // saplings:
//    {
//        TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Saplings.png"), 16, 16);
//
//        int rows = sheet.length;
//        int cols = sheet[0].length;
//
//        int index = 0;
//        for (SaplingType type : SaplingType.values()) {
//            int col = index / rows;
//            int row = index % rows;
//
//            if (col >= cols) break;
//
//            saplings.put(type, sheet[row][col]);
//            index++;
//        }
//    }
//    // ingredients:
//    {
//        TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Ingredients.png"), 16, 16);
//
//        int rows = sheet.length;
//        int cols = sheet[0].length;
//
//        int index = 0;
//        for (IngredientType type : IngredientType.values()) {
//            int row = index / cols;
//            int col = index % cols;
//
//            if (row >= rows) break;
//
//            ingredients.put(type, sheet[row][col]);
//            index++;
//        }
//    }
    // additional:
    {
        TextureRegion[][] tiledSheet = TextureRegion.split(new Texture("map/tiles/hoeDirt.png"), 16, 16);
        additional.put("plowed", tiledSheet[1][2]);
        additional.put("fertilized", tiledSheet[1][6]);
        additional.put("watered", tiledSheet[1][10]);

        additional.put("shadow", new TextureRegion(new Texture("etc/shadow.png")));

        TextureRegion[][] itemSheet = TextureRegion.split(new Texture("map/items/additional.png"), 16, 16);

        additional.put("stone", itemSheet[0][0]);
        additional.put("stone2", itemSheet[0][1]);
        additional.put("wood", itemSheet[0][2]);
        additional.put("wood2", itemSheet[0][3]);

    }

    public void renderGround() {
        int tileSize = 16;
        Tile[][] tiles = App.getGame().getMap().getCurrentRegion().getTiles();

        SpriteBatch batch = StardewValley.getBatch();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                Tile tile = tiles[y][x];

                float drawX = x * tileSize;
                float drawY = (tiles.length - 1 - y) * tileSize;

                // Ground:
                if (tile.isPlowed())
                    batch.draw(additional.get("plowed"), drawX, drawY);

                if (tile.getFertilize() != 0)
                    batch.draw(additional.get("fertilized"), drawX, drawY);

                if (tile.isWatered())
                    batch.draw(additional.get("watered"), drawX, drawY);

                // Building:
                if (tile.getType() == TileType.Building && tile.isBuildingOrigin()) {
                    BuildingType type = tile.getBuildingType();
                    TextureRegion[][] buildingTexture = buildings.get(type);
                    if (buildingTexture == null) continue;

                    float originDrawX = x * tileSize;
                    float originDrawY = (tiles.length - 1 - y) * tileSize;

                    for (int row = 0; row < type.getL(); row++) {
                        for (int col = 0; col < type.getW(); col++) {
                            TextureRegion region = buildingTexture[row][col];
                            if (region == null) continue;

                            float drawXBuilding = originDrawX + col * tileSize;
                            float drawYBuilding = originDrawY - row * tileSize;

                            batch.draw(region, drawXBuilding, drawYBuilding, tileSize, tileSize);
                        }
                    }
                }

                // Items:
                Item item = tile.getItem();
                if (item != null) {
                    if (item instanceof ForagingCrop foragingCrop) {
                        batch.draw(additional.get("shadow"), drawX, drawY);
                        batch.draw(foragingCrops.get(foragingCrop.getType()), drawX, drawY);
                    }
                    else if (item instanceof ForagingMineral foragingMineral) {
                        batch.draw(additional.get("shadow"), drawX, drawY);
                        batch.draw(foragingMinerals.get(foragingMineral.getType()), drawX, drawY);
                    }
                    else if (item instanceof Stone) {
                        batch.draw(additional.get("stone"), drawX, drawY);
                    }
                    else if (item instanceof Wood) {
                        batch.draw(additional.get("wood"), drawX, drawY);
                    }
                    else if (item instanceof Craft craft) {
                        batch.draw(crafts.get(craft.getType()), drawX, drawY);
                    }

                    // stage:
                    else if (item instanceof Crop crop) {
                        int stageIndex = crop.getCurrentStage() - 1;
                        List<TextureRegion> stages = cropStages.get(crop.getType());
                        batch.draw(stages.get(stageIndex), drawX, drawY);
                    }
                    else if (item instanceof Tree tree) {
                        int stageIndex = tree.getCurrentStage() - 1;
                        List<TextureRegion> stages = treeStages.get(tree.getType());
                        batch.draw(stages.get(stageIndex), drawX, drawY);
                    }

                }

            }
        }
    }
}
