package io.Ap.StardewValley.Client.Screen.InventoryScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.ToolController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Tool.Tool;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;
import io.Ap.StardewValley.StardewValley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryBar extends Stage {
    private final Skin skin = StardewValley.getSkin();
    private final ScrollPane scrollPane;
    private final Table inventoryTable;
    private final List<ImageTextButton> slotButtons = new ArrayList<>();
    private final Map<Integer, Item> indexToItem = new HashMap<>();

    private int selectedIndex = -1;
    private boolean noProblem = false;
    private int lastKnownCapacity = -1;
    private Item selectedItem = null;

    public InventoryBar() {
        super(new ScreenViewport());

        Table root = new Table();
        root.setFillParent(true);
        root.top().left();

        inventoryTable = new Table();

        // set ScrollPane:
        scrollPane = new ScrollPane(inventoryTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setOverscroll(false, false);

        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();
        scrollPane.setStyle(style);

        // add to page:
        root.pad(0);
        root.defaults().pad(0);


        this.addActor(root);
        initializeSlots();
        loadInitialItems();
    }

    private void initializeSlots() {
        double boxNumbers;
        try {
            boxNumbers = App.getGame().getCurrentPlayer().getInventoryCapacity();
            noProblem = true;
        } catch (Exception e) {
            boxNumbers = 12;
        }

        if (boxNumbers > 30) boxNumbers = 200;
        lastKnownCapacity = (int) boxNumbers;

        for (int i = 0; i < lastKnownCapacity; i++) {
            addSlotButton(i);
        }
    }

    private void addSlotButton(int index) {
        ImageTextButton.ImageTextButtonStyle newStyle = new ImageTextButton.ImageTextButtonStyle(
                skin.get(ImageTextButton.ImageTextButtonStyle.class)
        );
        ImageTextButton slot = new ImageTextButton("", newStyle);
        final int finalIndex = index;

        slot.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setSelectedIndex(finalIndex);
            }
        });

        inventoryTable.add(slot).size(90f).pad(0).row();
        slotButtons.add(slot);
    }

    private void loadInitialItems() {
        if (!noProblem) return;

        int i = 0;
        for (Item item : App.getGame().getCurrentPlayer().getInventory().getItemList()) {
            String itemName = item.getName();
            setSlotImage(i, ItemTextureBank.getTexture(itemName));
            int quantity = App.getGame().getCurrentPlayer().getInventory().getItemQuantity(item);
            if (quantity > 1) {
                setSlotText(i, Integer.toString(quantity));
            }
            i++;
        }
    }

    private void refreshCapacityIfNeeded() {
        int actualCapacity;
        try {
            actualCapacity = App.getGame().getCurrentPlayer().getInventory().getCapacity();
        } catch (Exception e) {
            actualCapacity = 12;
        }

        if (actualCapacity > 30) actualCapacity = 200;

        if (actualCapacity == lastKnownCapacity) return;

        lastKnownCapacity = actualCapacity;

        inventoryTable.clear();
        slotButtons.clear();
        indexToItem.clear();

        for (int i = 0; i < actualCapacity; i++) {
            addSlotButton(i);
        }
    }

    public void updateInventoryBar() {
        if (!noProblem) return;

        refreshCapacityIfNeeded();

        List<Item> items = App.getGame().getCurrentPlayer().getInventory().getItemList();

        for (int i = 0; i < slotButtons.size(); i++) {
            if (i < items.size()) {
                Item item = items.get(i);
                String itemName = item.getName();
                setSlotImage(i, ItemTextureBank.getTexture(itemName));

                int quantity = App.getGame().getCurrentPlayer().getInventory().getItemQuantity(item);
                if (quantity > 1) {
                    setSlotText(i, Integer.toString(quantity));
                } else {
                    setSlotText(i, "");
                }
                indexToItem.put(i, item);
            } else {
                clearSlot(i);
                indexToItem.put(i, null);
            }
        }
    }

    public void setSlotText(int index, String text) {
        if (index >= 0 && index < slotButtons.size()) {
            slotButtons.get(index).setText(text != null ? text : "");
        }
    }

    public void setSlotImage(int index, TextureRegion texture) {
        int cellSize = 84;

        if (index >= 0 && index < slotButtons.size()) {
            if (texture != null) {

                int w = texture.getRegionWidth();
                int h = texture.getRegionHeight();

                //float scale = Math.min((float) cellSize / w, (float) cellSize / h);
                float scale = (h < 32) ? 3.2f : 2.6f;

                int drawW = (w < 48) ? Math.round(w * scale) : w;
                int drawH = (h < 48) ? Math.round(h * scale) : h;

                TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
                drawable.setMinWidth(drawW);
                drawable.setMinHeight(drawH);

                ImageTextButton slot = slotButtons.get(index);
                ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle(slot.getStyle());
                style.imageUp = drawable;
                slot.setStyle(style);
            } else {
                slotButtons.get(index).getStyle().imageUp = null;
            }
        }
    }



    public void clearSlot(int index) {
        setSlotText(index, "");
        setSlotImage(index, null);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int index) {
        if (index < 0 || index >= slotButtons.size()) return;

        Drawable image = slotButtons.get(index).getStyle().imageUp;
        if (image == null) {
            slotButtons.get(index).setChecked(false);
            return;
        }

        for (ImageTextButton button : slotButtons) {
            button.setChecked(false);
        }

        slotButtons.get(index).setChecked(true);
        selectedIndex = index;

        try {
            Item item = getSelectedItem(index);
            selectedItem = item;
            if (item instanceof Tool tool) {
                ToolController.equipThroughScreen(tool.getName());
            }

            //TODO: تکمیل شوددددد. هر چیزی که انتخاب میشه اضافه کن.

        } catch (Exception ignored) {}
    }

    public ImageTextButton getSlotButton(int index) {
        if (index >= 0 && index < slotButtons.size()) {
            return slotButtons.get(index);
        }
        return null;
    }

    public int getSlotCount() {
        return slotButtons.size();
    }

    public ScrollPane getInventoryScrollPane() {
        return scrollPane;
    }

    public Item getSelectedItem(int index) {
        try {
            return indexToItem.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        this.screenToStageCoordinates(mousePos);

        if (scrollPane.hit(mousePos.x, mousePos.y, true) != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean handled = super.touchDown(screenX, screenY, pointer, button);
        return handled;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        boolean handled = super.touchDragged(screenX, screenY, pointer);
        return handled;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean handled = super.touchUp(screenX, screenY, pointer, button);
        return handled;
    }
    @Override
    public boolean keyDown(int keyCode) {
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

}
