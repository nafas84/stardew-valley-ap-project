package io.Ap.StardewValley.Client.Screen.ShopScreen.JojaMartScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.GameScreenController;
import io.Ap.StardewValley.Common.Model.Shop.JojaMart.JojaMart;
import io.Ap.StardewValley.Client.Screen.ShopScreen.ShopMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.FirstMenu;
import io.Ap.StardewValley.StardewValley;

public class JojaMartMenu extends Stage {

    private final Skin skin = StardewValley.getSkin();
    private ShopMenu shopMenu;
    private FirstMenu firstMenu;
    private TextButton shopMenuButton, leaveButton;
    private JojaMart jojaMart = new JojaMart();

    boolean isShopMenuVisible = false;
    boolean isBuildingsMenuVisible = false;

    float buttonWidth = 1000;
    float buttonHeight = 100;

    String texturePath = "shop/Morris.png";

    public JojaMartMenu() {
        super(new ScreenViewport());

        makeShopButton();
        makeLeaveButton();

        firstMenu = new FirstMenu(skin, "Joja Mart", shopMenuButton, leaveButton);
        firstMenu.setPosition((getViewport().getScreenWidth() - 1050) / 2f, 100);
        firstMenu.setVisible(true);
        this.addActor(firstMenu);

        shopMenu = new ShopMenu(skin, jojaMart, texturePath, this);
        shopMenu.setPosition(900, 540);
        shopMenu.setVisible(false);
        this.addActor(shopMenu);


    }

    public void setVisibleAll(boolean visible) {
        if (!isShopMenuVisible && !isBuildingsMenuVisible) {
            firstMenu.setVisible(visible);
        }
        if (!visible) {
            shopMenu.setVisible(false);
//            buildingsMenu.setVisible(false);
            isShopMenuVisible = false;
            isBuildingsMenuVisible = false;
        }
        if (visible) {
            this.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            this.draw();
        }
    }

    public void update() {
        shopMenu.updateShop();
    }

    private void makeShopButton() {
        shopMenuButton = new TextButton("shop", skin);
        shopMenuButton.setSize(buttonWidth, buttonHeight);

        shopMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                firstMenu.setVisible(false);
                shopMenu.setVisible(true);
                shopMenu.toFront();
                isShopMenuVisible = true;
            }
        });
        this.addActor(shopMenuButton);
    }

    private void makeLeaveButton() {
        leaveButton = new TextButton("leave", skin);
        leaveButton.setSize(buttonWidth, buttonHeight);

        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreenController.setVisibleShop(null);
            }
        });
        this.addActor(leaveButton);
    }

}
