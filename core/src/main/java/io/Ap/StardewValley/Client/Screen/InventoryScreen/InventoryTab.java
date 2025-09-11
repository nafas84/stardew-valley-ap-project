package io.Ap.StardewValley.Client.Screen.InventoryScreen;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlayerController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InventoryTab extends Window {
    private final Skin skin;
    private final List<ImageTextButton> inventoryButtons;
    private ImageTextButton selectedButton;
    private Table leftPart;
    private ScrollPane scrollPane;
    private Map<ImageTextButton, String> buttonToItemName = new HashMap<>();

    private Label label1;
    private Label label2;

    public InventoryTab(Skin skin) {
        super("", skin);
        this.skin = skin;
        this.inventoryButtons = new ArrayList<>();
        label1 = new Label(App.getGame().getCurrentPlayer().getUsername() , skin);
        label2 = new Label("count: " + App.getGame().getCurrentPlayer().getCount(), skin);

        this.setSize(1050, 650);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.topLeft);
        this.defaults().pad(10);


        leftPart = new Table();
        scrollPane = new ScrollPane(leftPart, skin, "inventory");
        scrollPane.setFadeScrollBars(false);
        leftPart.top().left();

        updateInventory();


        Table centerPart = new Table();
        ImageButton orderButton = new ImageButton(skin, "order");
        orderButton.setTransform(true);
        orderButton.scaleBy(0.4f);
        orderButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //todo
            }
        });
        centerPart.add(orderButton).size(100, 100).left().pad(30, 0, 0, 30);
        centerPart.row();

        ImageButton trashButton = new ImageButton(skin, "trash");
        trashButton.setTransform(true);
        trashButton.scaleBy(0.4f);
        trashButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //todo
                PlayerController.inventoryTrashWithoutNumber(getSelectedItemName());
                updateInventory();

            }
        });
        centerPart.add(trashButton).size(100, 100).left().pad(10, 0, 0, 30);


        Table rightPart = new Table();
        rightPart.top();

        Image topImage = new Image(new Texture("etc/menu/daybg.png"));
        topImage.setScaling(Scaling.fit);
        rightPart.add(topImage).width(200).height(350).center().row();

        rightPart.add(label1).center().padTop(10).row();
        rightPart.add(label2).center().padTop(5).row();

        this.add(rightPart).width(350).top();
        this.add(centerPart).width(140).top();
        this.add(scrollPane).width(450).top();
    }

    private void selectButton(int index) {
        for (int i = 0; i < inventoryButtons.size(); i++) {
            ImageTextButton b = inventoryButtons.get(i);
            b.setChecked(i == index);
        }
        selectedButton = inventoryButtons.get(index);
    }

    public ImageTextButton getSelectedButton() {
        return selectedButton;
    }

    public void updateInventory() {
        ArrayList<Item> items = App.getGame().getCurrentPlayer().getInventory().getItemList();
        int capacity = App.getGame().getCurrentPlayer().getInventory().getCapacity();
        int columns = 3;

        leftPart.clear();
        inventoryButtons.clear();

        for (int i = 0; i < capacity; i++) {
            final int index = i;

            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle(
                    skin.get("default", ImageTextButton.ImageTextButtonStyle.class)
            );

            if (i < items.size()) {
                TextureRegionDrawable icon = new TextureRegionDrawable(ItemTextureBank.getTexture(items.get(i).getName()));
                style.imageUp = icon;

            }

            ImageTextButton button = new ImageTextButton("", style);
            inventoryButtons.add(button);

            if (i < items.size()) {
                buttonToItemName.put(button, items.get(index).getName());
            }

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectButton(index);
                }
            });

            leftPart.add(button).size(120, 120).pad(9);

            try {
                button.getImageCell().height(75).width(75);
            } catch (Exception e) {}

            if ((i + 1) % columns == 0) leftPart.row();
        }

        scrollPane.setWidget(leftPart);  // اطمینان از بروزرسانی محتوا

        label2.setText("count: " + App.getGame().getCurrentPlayer().getCount());
    }

    private String getSelectedItemName () {
        return buttonToItemName.get(selectedButton);
    }
}