package io.Ap.StardewValley.Client.Screen.InventoryScreen;

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


public class InventoryStage extends Stage {
    private final Skin skin = StardewValley.getSkin();
    private final List<Window> infoWindows = new ArrayList<>();
    private final List<ImageTextButton> buttons = new ArrayList<>();
    private ImageTextButton activeButton;

    public InventoryStage() {
        super(new ScreenViewport());

        float windowWidth = 1050;
        float windowHeight = 650;
        float windowX = (getViewport().getScreenWidth() - windowWidth) / 2f;
        float windowY = (getViewport().getScreenHeight() - windowHeight) / 2f;

        float buttonWidth = 160;
        float buttonHeight = 80;

        for (int i = 0; i < 6; i++) {
            final int index = i;

            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle(
                    skin.get("inventoryButton", ImageTextButton.ImageTextButtonStyle.class)
            );
            Texture texture = new Texture(Gdx.files.internal("inventory/inventory button " + i + ".png"));
            TextureRegionDrawable icon = new TextureRegionDrawable(new TextureRegion(texture));
            style.imageUp = icon;

            final ImageTextButton btn = new ImageTextButton("", style);

            btn.getImageCell().size(64, 64);

            btn.setSize(buttonWidth, buttonHeight);
            float x = windowX + i * (buttonWidth) + 20;
            float y = windowY + windowHeight;
            btn.setPosition(x, y);


            Window win = createWindow(i);
            win.setPosition(windowX, windowY);
            infoWindows.add(win);
            win.setVisible(false);
            this.addActor(win);
            TooltipManager.getInstance().instant();

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for (int j = 0; j < buttons.size(); j++) {
                        buttons.get(j).setChecked(false);
                        infoWindows.get(j).setVisible(false);
                    }

                    btn.setChecked(true);
                    infoWindows.get(index).setVisible(true);
                    infoWindows.get(index).toFront();
                    activeButton = btn;
                }
            });

            buttons.add(btn);
            this.addActor(btn);
        }

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

    private Window createWindow(int index) {
        Window win;
        if (index == 0) {
            win = new InventoryTab((skin));
        } else if (index == 1) {
            win = new SkillsTab(skin);
        } else if (index == 4) {
            win = new cheatTab(skin);
        } else if (index == 3) {
            win = new MapTab(skin);
        } else if (index == 5) {
            win = new CraftTab(skin, this);
        }
        else {
            win = new Window("", skin);
            win.add(new Label("Window " + (index + 1), skin)).row();
        }
        win.setMovable(false);
        win.setSize(1050, 650);
        return win;
    }

    public void update() {
        try {
            ((InventoryTab) infoWindows.get(0)).updateInventory();
        } catch (Exception e) {}
        try {
            ((SkillsTab) infoWindows.get(1)).updateInfo();
        } catch (Exception e) {}
        try {
            ((MapTab) infoWindows.get(3)).updatePlayerPosition();
        } catch (Exception e) {}
    }


    public List<Window> getInfoWindows() {
        return infoWindows;
    }
}