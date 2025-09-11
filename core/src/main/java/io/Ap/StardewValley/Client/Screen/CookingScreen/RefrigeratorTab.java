package io.Ap.StardewValley.Client.Screen.CookingScreen;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.FoodController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;
import io.Ap.StardewValley.StardewValley;

import java.util.*;
import java.util.List;

public class RefrigeratorTab extends Window {
    private final Skin skin;
    private final Table fridgeTable;
    private final Table inventoryTable;
    private final ScrollPane inventoryScrollPane;
    private final ScrollPane fridgeScrollPane; // ← NEW
    private final List<ImageTextButton> fridgeButtons = new ArrayList<>();
    private final List<ImageTextButton> inventoryButtons = new ArrayList<>();
    private final Map<ImageTextButton, String> fridgeButtonToItemName = new HashMap<>();
    private final Map<ImageTextButton, String> inventoryButtonToItemName = new HashMap<>();
    private ImageTextButton selectedFridgeButton;
    private ImageTextButton selectedInventoryButton;
    private final Stage stageForErrorDisplay;


    public RefrigeratorTab(Skin skin, Stage stageForErrorDisplay) {
        super("", skin);
        this.skin = StardewValley.getSkin();
        this.stageForErrorDisplay = stageForErrorDisplay;

        this.setSize(1050, 650);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.topLeft);
        this.defaults().pad(10);

        fridgeTable = new Table();
        fridgeTable.top().left();
        fridgeScrollPane = new ScrollPane(fridgeTable, skin, "inventory");
        fridgeScrollPane.setFadeScrollBars(false);

        inventoryTable = new Table();
        inventoryTable.top().right();
        inventoryScrollPane = new ScrollPane(inventoryTable, skin, "inventory");
        inventoryScrollPane.setFadeScrollBars(false);

        update();

        Table centerPart = new Table();

        ImageButton trashButton = new ImageButton(skin, "trash");
        trashButton.setTransform(true);
        trashButton.scaleBy(0.4f);
        trashButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedFridgeButton == null && selectedInventoryButton == null) {
                    showError("no item has been selected");
                    return;
                } else if (selectedFridgeButton != null && selectedInventoryButton != null) {
                    showError("you should select just one item");
                    return;
                } else if (selectedFridgeButton != null) {
                    if (!App.getGame().getCurrentPlayer().getRefrigerator().removeItem(getSelectedFridgeItemName(), -1)) {
                        showError("you cant remove " + getSelectedFridgeItemName());
                        return;
                    }
                } else {
                    if (!App.getGame().getCurrentPlayer().getInventory().removeItem(getSelectedInventoryItemName(), -1)) {
                        showError("you cant remove " + getSelectedFridgeItemName());
                        return;
                    }
                }
                update();
            }
        });

        ImageButton rightButton = new ImageButton(skin, "Right");
        rightButton.setTransform(true);
        rightButton.scaleBy(0.4f);
        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedFridgeButton == null) {
                    showError("select an item from refrigerator");
                    return;
                }
                Result result = FoodController.refrigeratorPickThroughScreen(getSelectedFridgeItemName());
                if (!result.isSuccessful()) {
                    showError(result.message());
                    return;
                }
                update();
            }
        });

        ImageButton leftButton = new ImageButton(skin, "Left");
        leftButton.setTransform(true);
        leftButton.scaleBy(0.4f);
        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedInventoryButton == null) {
                    showError("select an item from inventory");
                    return;
                }
                Result result = FoodController.refrigeratorPutThroughScreen(getSelectedInventoryItemName());
                if (!result.isSuccessful()) {
                    showError(result.message());
                    return;
                }
                update();
            }
        });

        centerPart.add(rightButton).size(80, 80).padBottom(20).row();
        centerPart.add(leftButton).size(80, 80).padBottom(20).row();
        centerPart.add(trashButton).size(80, 80);


        Table fridgeWrapper = new Table();
        fridgeWrapper.add(new Label("Refrigerator", skin)).padBottom(10).row();
        fridgeWrapper.add(fridgeScrollPane).width(450).height(500);

        Table inventoryWrapper = new Table();
        inventoryWrapper.add(new Label("Inventory", skin)).padBottom(10).row();
        inventoryWrapper.add(inventoryScrollPane).width(370).height(500);

        this.add(fridgeWrapper).top().pad(10);
        this.add(centerPart).width(80).top().pad(100, 10, 10, 10);
        this.add(inventoryWrapper).top().pad(10);

    //    this.setDebug(true);
    }

    private void selectFridgeButton(int index) {
        for (int i = 0; i < fridgeButtons.size(); i++) {
            fridgeButtons.get(i).setChecked(i == index);
        }
        selectedFridgeButton = fridgeButtons.get(index);
        if (selectedInventoryButton != null) {
            selectedInventoryButton.setChecked(false);
            selectedInventoryButton = null;
        }
    }

    private void selectInventoryButton(int index) {
        for (int i = 0; i < inventoryButtons.size(); i++) {
            inventoryButtons.get(i).setChecked(i == index);
        }
        selectedInventoryButton = inventoryButtons.get(index);
        if (selectedFridgeButton != null) {
            selectedFridgeButton.setChecked(false);
            selectedFridgeButton = null;
        }
    }

    public void update() {
        // مقداردهی به یخچال
        fridgeTable.clear();
        fridgeButtons.clear();
        fridgeButtonToItemName.clear();
        ArrayList<Item> fridgeItems = App.getGame().getCurrentPlayer().getRefrigerator().getItemList();
        int fridgeCapacity = App.getGame().getCurrentPlayer().getRefrigerator().getCapacity();
        int fridgeCols = 4;

        for (int i = 0; i < fridgeCapacity; i++) {
            final int index = i;
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle(
                    skin.get("default", ImageTextButton.ImageTextButtonStyle.class)
            );

            if (i < fridgeItems.size()) {
                style.imageUp = new TextureRegionDrawable(ItemTextureBank.getTexture(fridgeItems.get(i).getName()));
            }

            ImageTextButton button = new ImageTextButton("", style);
            fridgeButtons.add(button);

            if (i < fridgeItems.size()) {
                fridgeButtonToItemName.put(button, fridgeItems.get(i).getName());
            }

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectFridgeButton(index);
                }
            });

            fridgeTable.add(button).size(90, 90).pad(6);

            try {
                button.getImageCell().height(60).width(60);
            } catch (Exception ignored) {}

            if ((i + 1) % fridgeCols == 0) fridgeTable.row();
        }

        // مقداردهی به اینونتوری
        inventoryTable.clear();
        inventoryButtons.clear();
        inventoryButtonToItemName.clear();
        ArrayList<Item> inventoryItems = App.getGame().getCurrentPlayer().getInventory().getItemList();
        int inventoryCapacity = App.getGame().getCurrentPlayer().getInventory().getCapacity();
        int inventoryCols = 3;

        for (int i = 0; i < inventoryCapacity; i++) {
            final int index = i;
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle(
                    skin.get("default", ImageTextButton.ImageTextButtonStyle.class)
            );

            if (i < inventoryItems.size()) {
                style.imageUp = new TextureRegionDrawable(ItemTextureBank.getTexture(inventoryItems.get(i).getName()));
            }

            ImageTextButton button = new ImageTextButton("", style);
            inventoryButtons.add(button);

            if (i < inventoryItems.size()) {
                inventoryButtonToItemName.put(button, inventoryItems.get(i).getName());
            }

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectInventoryButton(index);
                }
            });

            inventoryTable.add(button).size(90, 90).pad(9);

            try {
                button.getImageCell().height(60).width(60);
            } catch (Exception ignored) {}

            if ((i + 1) % inventoryCols == 0) inventoryTable.row();
        }

        inventoryScrollPane.setWidget(inventoryTable);
    }

    private void showError(String msg) {
        final Window errorWindow = new Window("", skin, "Letter");
        errorWindow.setMovable(false);
        errorWindow.setKeepWithinStage(true);
        errorWindow.add(new Label(msg, skin));
        errorWindow.setSize(700, 90);
        errorWindow.setPosition(600, 170, Align.center);
        errorWindow.pack();

        stageForErrorDisplay.addActor(errorWindow);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorWindow.remove();
            }
        }, 5);
    }

    public String getSelectedFridgeItemName() {
        return fridgeButtonToItemName.get(selectedFridgeButton);
    }

    public String getSelectedInventoryItemName() {
        return inventoryButtonToItemName.get(selectedInventoryButton);
    }
}