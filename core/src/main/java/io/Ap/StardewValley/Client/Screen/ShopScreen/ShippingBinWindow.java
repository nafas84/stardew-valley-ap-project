package io.Ap.StardewValley.Client.Screen.ShopScreen;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.ShopController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShippingBinWindow extends Window {
    private final Skin skin;
    private final List<ImageTextButton> inventoryButtons;
    private ImageTextButton selectedButton;
    private Table leftPart;
    private ScrollPane scrollPane;
    private Map<ImageTextButton, String> buttonToItemName = new HashMap<>();

    private Label label1;
    private Label label2;
    private Stage stage;


    public ShippingBinWindow(Skin skin, Stage stage) {
        super("Shipping bin", skin);
        this.skin = skin;
        this.inventoryButtons = new ArrayList<>();
        label1 = new Label(App.getCurrentUser().getNickname() , skin);
        label2 = new Label("count: " + App.getGame().getCurrentPlayer().getCount(), skin);
        this.stage = stage;

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

        ImageButton sellButton = new ImageButton(skin, "trash");
        sellButton.setTransform(true);
        sellButton.scaleBy(0.4f);
        sellButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("11111111");
                if (selectedButton == null || getSelectedItemName() == null) {
                    System.out.println("2222");
                    showError("choose an item to sell!");
                    return;
                }
                Result result = ShopController.sellThroughScreen(getSelectedItemName());
                if (!result.isSuccessful()) {
                    System.out.println("3333");
                    showError(result.message());
                    return;
                }
                System.out.println("44");
                updateInventory();

            }
        });
        centerPart.add(sellButton).size(100, 100).left().pad(10, 0, 0, 30);


        Table rightPart = new Table();
        rightPart.top();

        Image topImage = new Image(new Texture("etc/menu/daybg.png"));
        topImage.setScaling(Scaling.fit);
        rightPart.add(topImage).width(200).height(350).center().row();

        label1 = new Label(App.getCurrentUser().getNickname() , skin);
        label2 = new Label("count: " + App.getGame().getCurrentPlayer().getCount(), skin);
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

    private void showError(String msg) {
        final Window errorWindow = new Window("", skin, "Letter");
        errorWindow.setMovable(false);
        errorWindow.setKeepWithinStage(true);
        errorWindow.add(new Label(msg, skin));
        errorWindow.setSize(700, 90);
        errorWindow.setPosition(600, 170, Align.center);
        errorWindow.pack();

        stage.addActor(errorWindow);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorWindow.remove();
            }
        }, 5);
    }


    private String getSelectedItemName () {
        return buttonToItemName.get(selectedButton);
    }
}