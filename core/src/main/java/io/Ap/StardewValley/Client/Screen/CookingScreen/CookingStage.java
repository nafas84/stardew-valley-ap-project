package io.Ap.StardewValley.Client.Screen.CookingScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.StardewValley;

import java.util.ArrayList;
import java.util.List;

public class CookingStage extends Stage{

    private final Skin skin = StardewValley.getSkin();
    private final List<Window> infoWindows = new ArrayList<>();
    private final Window refrigeratorTab = new RefrigeratorTab(skin, this);
    private final Window cookingTab = new CookingTab(skin, this);
    private final List<ImageTextButton> buttons = new ArrayList<>();
    private ImageTextButton refrigeratorButton, cookingButton;
    private ImageTextButton activeButton;

    float windowWidth = 1050;
    float windowHeight = 650;
    float windowX = (getViewport().getScreenWidth() - windowWidth) / 2f;
    float windowY = (getViewport().getScreenHeight() - windowHeight) / 2f;

    float buttonWidth = 180;
    float buttonHeight = 90;

    public CookingStage() {
        super(new ScreenViewport());

        makeRefrigeratorButton();
        makeCookingButton();

        buttons.get(0).setChecked(true);
        infoWindows.get(0).setVisible(true);
        infoWindows.get(0).toFront();
        activeButton = buttons.get(0);
    }

    @Override
    public Actor hit(float stageX, float stageY, boolean touchable) {
        Actor hit = super.hit(stageX, stageY, touchable);

        for (Window win : infoWindows) {
            if (hit == win) return null;
        }
        return hit;
    }

    public void setVisibleAll(boolean visible) {
        for (ImageTextButton btn : buttons)
            btn.setVisible(visible);
        for (Window win : infoWindows)
            win.setVisible(visible);

        if (visible) {
            this.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            this.draw();
        }
    }

    public void update() {
        try {
            ((RefrigeratorTab) refrigeratorTab).update();
        } catch (Exception e) {
        }
        try {
            ((CookingTab) cookingTab).update();
        } catch (Exception e) {
        }
    }

    private void makeRefrigeratorButton() {

        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle(
                skin.get("inventoryButton", ImageTextButton.ImageTextButtonStyle.class)
        );
        Texture texture = new Texture(Gdx.files.internal("inventory/cooking button 0.png"));
        TextureRegionDrawable icon = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageUp = icon;

        refrigeratorButton = new ImageTextButton("", style);

        refrigeratorButton.getImageCell().size(64, 64);

        refrigeratorButton.setSize(buttonWidth, buttonHeight);
        float x = windowX + 20;
        float y = windowY + windowHeight;
        refrigeratorButton.setPosition(x, y);


        refrigeratorTab.setPosition(windowX, windowY);
        infoWindows.add(refrigeratorTab);
        refrigeratorTab.setVisible(false);
        this.addActor(refrigeratorTab);
        TooltipManager.getInstance().instant();

        refrigeratorButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (int j = 0; j < buttons.size(); j++) {
                    buttons.get(j).setChecked(false);
                    infoWindows.get(j).setVisible(false);
                }

                refrigeratorButton.setChecked(true);
                infoWindows.get(0).setVisible(true);
                infoWindows.get(0).toFront();
                activeButton = refrigeratorButton;
            }
        });

        buttons.add(refrigeratorButton);
        this.addActor(refrigeratorButton);

    }

    private void makeCookingButton() {

        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle(
                skin.get("inventoryButton", ImageTextButton.ImageTextButtonStyle.class)
        );
        Texture texture = new Texture(Gdx.files.internal("inventory/cooking button 1.png"));
        TextureRegionDrawable icon = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageUp = icon;

        cookingButton = new ImageTextButton("", style);

        cookingButton.getImageCell().size(64, 64);

        cookingButton.setSize(buttonWidth, buttonHeight);
        float x = windowX + buttonWidth + 20;
        float y = windowY + windowHeight;
        cookingButton.setPosition(x, y);


        cookingTab.setPosition(windowX, windowY);
        infoWindows.add(cookingTab);
        cookingTab.setVisible(false);
        this.addActor(cookingTab);
        TooltipManager.getInstance().instant();

        cookingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (int j = 0; j < buttons.size(); j++) {
                    buttons.get(j).setChecked(false);
                    infoWindows.get(j).setVisible(false);
                }

                cookingButton.setChecked(true);
                infoWindows.get(1).setVisible(true);
                infoWindows.get(1).toFront();
                activeButton = cookingButton;
            }
        });

        buttons.add(cookingButton);
        this.addActor(cookingButton);

    }

}