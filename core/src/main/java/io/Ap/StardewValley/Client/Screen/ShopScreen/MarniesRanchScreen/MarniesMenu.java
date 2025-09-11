package io.Ap.StardewValley.Client.Screen.ShopScreen.MarniesRanchScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.GameScreenController;
import io.Ap.StardewValley.Common.Model.Shop.MarniesRanch.MarniesRanch;
import io.Ap.StardewValley.Client.Screen.ShopScreen.ShopMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.FirstMenu;
import io.Ap.StardewValley.StardewValley;

public class MarniesMenu extends Stage {

    private final Skin skin = StardewValley.getSkin();
    private ShopMenu shopMenu;
    //    private ShopMenu animalsMenu;
    private FirstMenu firstMenu;
    private TextButton shopMenuButton, animalsButton, leaveButton;
    private MarniesRanch marniesRanch = new MarniesRanch();

    boolean isShopMenuVisible = false;
    boolean isAnimalsMenuVisible = false;

    float buttonWidth = 1000;
    float buttonHeight = 100;

    String texturePath = "shop/Marnie.png";

    public MarniesMenu() {
        super(new ScreenViewport());

        makeShopButton();
        makeAnimalsButton();
        makeLeaveButton();

        firstMenu = new FirstMenu(skin, "Marnie's ranch", shopMenuButton, animalsButton, leaveButton);
        firstMenu.setPosition((getViewport().getScreenWidth() - 1050) / 2f, 100);
        firstMenu.setVisible(true);
        this.addActor(firstMenu);

        shopMenu = new ShopMenu(skin, marniesRanch, texturePath, this);
        shopMenu.setPosition(900, 540);
        shopMenu.setVisible(false);
        this.addActor(shopMenu);


    }

    public void setVisibleAll(boolean visible) {
        if (!isShopMenuVisible && !isAnimalsMenuVisible) {
            firstMenu.setVisible(visible);
        }
        if (!visible) {
            shopMenu.setVisible(false);
//            buildingsMenu.setVisible(false);
            isShopMenuVisible = false;
            isAnimalsMenuVisible = false;
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
        shopMenuButton = new TextButton("Supplies Shop", skin);
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

    private void makeAnimalsButton() {
        animalsButton = new TextButton("Purchase Animals", skin);
        animalsButton.setSize(buttonWidth, buttonHeight);

        animalsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        this.addActor(animalsButton);
    }

    private void makeLeaveButton() {
        leaveButton = new TextButton("Leave", skin);
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