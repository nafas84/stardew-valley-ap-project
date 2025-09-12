package io.Ap.StardewValley.Client.Screen.InventoryScreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import io.Ap.StardewValley.Common.Model.Player.Player;

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

    private Image bodyImage, handImage, pantImage, hairImage, shirtImage;

    {
        // player image:
        int pantIndex = App.getGame().getCurrentPlayer().getPantIndex();
        int shirtIndex = App.getGame().getCurrentPlayer().getShirtIndex();
        int hairIndex = App.getGame().getCurrentPlayer().getHairIndex();

        Texture bodySheetTexture = new Texture("player/body_boy.png");
        TextureRegion bodyRegion = new TextureRegion(bodySheetTexture, 0, 0, 16, 32);
        Texture handSheetTexture = new Texture("player/hand_01.png");
        TextureRegion handRegion = new TextureRegion(handSheetTexture, 0, 0, 16, 32);

        bodyImage = new Image(bodyRegion);
        handImage = new Image(handRegion);

        TextureRegion[][] shirtSheet = TextureRegion.split(new Texture("player/clothes/shirts.png"), 8, 8);
        TextureRegion[][] hairSheet = TextureRegion.split(new Texture("player/clothes/hairstyles.png"), 16, 32);
        TextureRegion[][] pantSheet = TextureRegion.split(new Texture("player/pants/pant_" + pantIndex + ".png"), 16, 32);


        pantImage = new Image(pantSheet[0][0]);
        shirtImage = new Image(shirtSheet[(shirtIndex / 18) * 4][shirtIndex % 16]);
        hairImage = new Image(hairSheet[(hairIndex / 8) * 3][hairIndex % 8]);
    }

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
        scrollPane = new ScrollPane(leftPart, skin, "Dark");
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

        Image characterBackground = new Image(new Texture("etc/menu/daybg.png"));
        characterBackground.setScaling(Scaling.fit);
        Group characterGroup = getCharacterGroup();

        Stack characterStack = new Stack();
        characterStack.add(characterBackground);
        characterStack.add(characterGroup);


        rightPart.add(characterStack).size(
                characterBackground.getWidth() * 1.7f,
                characterBackground.getHeight() * 1.7f
        ).padBottom(30);

        rightPart.row();

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

        scrollPane.setWidget(leftPart);

        label2.setText("count: " + App.getGame().getCurrentPlayer().getCount());
    }

    private String getSelectedItemName () {
        return buttonToItemName.get(selectedButton);
    }

    private Group getCharacterGroup() {
        Group characterGroup = new Group();
        Player player = App.getGame().getCurrentPlayer();

        float scale = 8f;

        int x = 45;
        int y = 49;

        bodyImage.setSize(16 * scale, 32 * scale);
        bodyImage.setPosition(x, y);
        characterGroup.addActor(bodyImage);

        // selected:
        pantImage.setSize(16 * scale, 32 * scale);
        pantImage.setPosition(x, y);
        pantImage.setColor(App.getColor(player.getPantColor()));
        characterGroup.addActor(pantImage);

        shirtImage.setSize(8 * scale, 8 * scale);
        shirtImage.setPosition(x + 4 * scale, y + 9 * scale);
        characterGroup.addActor(shirtImage);

        int longHair = (player.getHairIndex() < 16) ? 0 : -1;
        hairImage.setSize(16 * scale, 32 * scale);
        hairImage.setPosition(x, y - (1 + longHair) * scale);
        hairImage.setColor(App.getColor(player.getHairColor()));
        characterGroup.addActor(hairImage);

        // hand
        handImage.setSize(16 * scale, 32 * scale);
        handImage.setPosition(x, y);
        characterGroup.addActor(handImage);

        return characterGroup;
    }
}