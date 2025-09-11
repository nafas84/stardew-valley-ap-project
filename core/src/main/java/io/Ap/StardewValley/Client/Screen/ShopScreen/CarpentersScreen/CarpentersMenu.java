package io.Ap.StardewValley.Client.Screen.ShopScreen.CarpentersScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.GameScreenController;
import io.Ap.StardewValley.Common.Model.Shop.CarpentersShop.CarpentersShop;
import io.Ap.StardewValley.Client.Screen.ShopScreen.ShopMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.FirstMenu;
import io.Ap.StardewValley.StardewValley;

public class CarpentersMenu extends Stage {

    private final Skin skin = StardewValley.getSkin();
    private ShopMenu shopMenu;
    private BuildingShopMenu buildingsMenu;
    private FirstMenu firstMenu;
    private TextButton shopMenuButton, buildingsButton, leaveButton;
    private CarpentersShop carpentersShop = new CarpentersShop();

    boolean isShopMenuVisible = false;
    boolean isBuildingsMenuVisible = false;

    float buttonWidth = 1000;
    float buttonHeight = 100;

    String texturePath = "shop/Robin.png";

    public CarpentersMenu() {
        super(new ScreenViewport());

        makeShopButton();
        makeBuildingButton();
        makeLeaveButton();

        firstMenu = new FirstMenu(skin, "Carpenter's shop", shopMenuButton, buildingsButton, leaveButton);
        firstMenu.setPosition((getViewport().getScreenWidth() - 1050) / 2f, 100);
        firstMenu.setVisible(true);
        this.addActor(firstMenu);

        shopMenu = new ShopMenu(skin, carpentersShop, texturePath, this);
        shopMenu.setPosition(900, 540);
        shopMenu.setVisible(false);
        this.addActor(shopMenu);

        buildingsMenu = new BuildingShopMenu(skin, carpentersShop, texturePath, this);
        buildingsMenu.setPosition(900, 540);
        buildingsMenu.setVisible(false);
        this.addActor(buildingsMenu);

        shopMenu.getProductInformation().setShopStockNeedsUpdate(true);
        update();

    }

    public void setVisibleAll(boolean visible) {
        if (!isShopMenuVisible && !isBuildingsMenuVisible) {
            firstMenu.setVisible(visible);
        }
        if (!visible) {
            shopMenu.setVisible(false);
            buildingsMenu.setVisible(false);
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
        buildingsMenu.updateShop();
    }

    private void makeShopButton() {
        shopMenuButton = new TextButton("Shop", skin);
        shopMenuButton.setSize(buttonWidth, buttonHeight);

        shopMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                firstMenu.setVisible(false);
                buildingsMenu.setVisible(false);
                shopMenu.setVisible(true);
                shopMenu.toFront();
                isShopMenuVisible = true;
                isBuildingsMenuVisible = false;
            }
        });
        this.addActor(shopMenuButton);
    }

    private void makeBuildingButton() {
        buildingsButton = new TextButton("Construct Farm Buildings", skin);
        buildingsButton.setSize(buttonWidth, buttonHeight);

        buildingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                firstMenu.setVisible(false);
                shopMenu.setVisible(false);
                buildingsMenu.setVisible(true);
                buildingsMenu.toFront();
                isBuildingsMenuVisible = true;
                isShopMenuVisible = false;
            }
        });
        this.addActor(buildingsButton);
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