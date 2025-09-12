package io.Ap.StardewValley.Client.Screen.ItemScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProductType;
import io.Ap.StardewValley.Common.Model.Animals.FishType;
import io.Ap.StardewValley.Common.Model.Cooking.FoodType;
import io.Ap.StardewValley.Common.Model.Cooking.IngredientType;
import io.Ap.StardewValley.Common.Model.Crafting.CraftType;
import io.Ap.StardewValley.Common.Model.Plants.*;
import io.Ap.StardewValley.Common.Model.Player.GiftType;

import java.util.HashMap;
import java.util.Map;

public class ItemTextureBank {
    private final static Map<String, Texture> inventoryButtons = new HashMap<>();

    private final static Map<String, TextureRegion> items = new HashMap<>();

    private final static Map<String, TextureRegion> tools = new HashMap<>();


    static {
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

                items.put(type.getName().toLowerCase(), foragingSheet[row][col]);
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

                items.put(type.getName().toLowerCase(), foragingSheet[row][col]);
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

                items.put(type.getName().toLowerCase(), craftSheet[row][col]);
                index++;
            }
        }
        // animal products:
        {
            TextureRegion[][] animalProductsSheet = TextureRegion.split(new Texture("items/AnimalProducts.png"), 16, 16);

            int rows = animalProductsSheet.length;
            int cols = animalProductsSheet[0].length;

            int index = 0;
            for (AnimalProductType type : AnimalProductType.values()) {
                int row = index / cols;
                int col = index % cols;

                if (row >= rows) break;

                items.put(type.getName().toLowerCase(), animalProductsSheet[row][col]);
                index++;
            }
        }
        // fishes:
        {
            TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Fishes.png"), 16, 16);

            int rows = sheet.length;
            int cols = sheet[0].length;

            int index = 0;
            for (FishType type : FishType.values()) {
                int row = index / cols;
                int col = index % cols;

                if (row >= rows) break;

                items.put(type.getName().toLowerCase(), sheet[row][col]);
                index++;
            }
        }
        // foods:
        {
            TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Foods.png"), 16, 16);

            int rows = sheet.length;
            int cols = sheet[0].length;

            int index = 0;
            for (FoodType type : FoodType.values()) {
                int row = index / cols;
                int col = index % cols;

                if (row >= rows) break;

                items.put(type.getName().toLowerCase(), sheet[row][col]);
                index++;
            }
        }
        // gifts:
        {
            TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Gifts.png"), 16, 16);

            int rows = sheet.length;
            int cols = sheet[0].length;

            int index = 0;
            for (GiftType type : GiftType.values()) {
                int row = index / cols;
                int col = index % cols;

                if (row >= rows) break;

                items.put(type.getName().toLowerCase(), sheet[row][col]);
                index++;
            }
        }
        // seeds:
        {
            TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Seeds.png"), 16, 16);

            int rows = sheet.length;
            int cols = sheet[0].length;

            int index = 0;
            for (SeedType type : SeedType.values()) {
                int col = index / rows;
                int row = index % rows;

                if (col >= cols) break;

                items.put(type.getName().toLowerCase(), sheet[row][col]);
                index++;
            }
        }
        // saplings:
        {
            TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Saplings.png"), 16, 16);

            int rows = sheet.length;
            int cols = sheet[0].length;

            int index = 0;
            for (SaplingType type : SaplingType.values()) {
                int col = index / rows;
                int row = index % rows;

                if (col >= cols) break;

                items.put(type.getName().toLowerCase(), sheet[row][col]);
                index++;
            }
        }
        // ingredients:
        {
            TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Ingredients.png"), 16, 16);

            int rows = sheet.length;
            int cols = sheet[0].length;

            int index = 0;
            for (IngredientType type : IngredientType.values()) {
                int row = index / cols;
                int col = index % cols;

                if (row >= rows) break;

                items.put(type.getName().toLowerCase(), sheet[row][col]);
                index++;
            }
        }
        // crops:
        {
            TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Crops.png"), 16, 16);

            int rows = sheet.length;
            int cols = sheet[0].length;

            int index = 0;
            for (CropType type : CropType.values()) {
                int col = index / rows;
                int row = index % rows;

                if (col >= cols) break;

                items.put(type.getName().toLowerCase(), sheet[row][col]);
                index++;
            }
        }
        // fruits:
        {
            TextureRegion[][] sheet = TextureRegion.split(new Texture("items/Fruits.png"), 16, 16);

            int rows = sheet.length;
            int cols = sheet[0].length;

            int index = 0;
            for (FruitType type : FruitType.values()) {
                int col = index / rows;
                int row = index % rows;

                if (col >= cols) break;

                items.put(type.getName().toLowerCase(), sheet[row][col]);
                index++;
            }
        }
        // additional:
        {
            TextureRegion[][] itemSheet = TextureRegion.split(new Texture("map/items/additional.png"), 16, 16);

            items.put("stone", itemSheet[0][0]);
            items.put("wood", itemSheet[0][2]);
        }
    }

    static {
        FileHandle dir = Gdx.files.internal("assets/inventory");
        for (FileHandle file : dir.list()) {
            if (!file.isDirectory() && file.extension().equals("png")) {
                String itemName = file.nameWithoutExtension();
                Texture texture = new Texture(file);
                inventoryButtons.put(itemName, texture);
            }
        }
    }

    static {
        FileHandle dir = Gdx.files.internal("assets/tools");
        for (FileHandle file : dir.list()) {
            if (!file.isDirectory() && file.extension().equals("png")) {
                String itemName = file.nameWithoutExtension();
                Texture texture = new Texture(file);
                inventoryButtons.put(itemName, texture);
            }
        }
    }

    public static TextureRegion getTexture (String itemName) {
        if (items.get(itemName.toLowerCase()) != null) {
            return items.get(itemName.toLowerCase());
        } else if (tools.get(itemName) != null) {
            return new TextureRegion(tools.get(itemName.toLowerCase()));
        } else if (inventoryButtons.get(itemName.toLowerCase()) != null) {
            return new TextureRegion(inventoryButtons.get(itemName.toLowerCase()));
        }

        return new TextureRegion(inventoryButtons.get("unknown"));
    }

    public static void dispose() {
        for (Texture texture : inventoryButtons.values()) {
            texture.dispose();
        }
        inventoryButtons.clear();
    }
}
