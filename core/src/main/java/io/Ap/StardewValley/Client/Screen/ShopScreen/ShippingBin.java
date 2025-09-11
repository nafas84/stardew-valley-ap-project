package io.Ap.StardewValley.Client.Screen.ShopScreen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.StardewValley;

public class ShippingBin extends Stage {
    private final Skin skin = StardewValley.getSkin();
    private final Window shippingBinWindow = new ShippingBinWindow(skin, this);

    float windowWidth = 1050;
    float windowHeight = 650;
    float windowX = (getViewport().getScreenWidth() - windowWidth) / 2f;
    float windowY = (getViewport().getScreenHeight() - windowHeight) / 2f;

    public ShippingBin() {
        super(new ScreenViewport());

        shippingBinWindow.setPosition(windowX, windowY);
        shippingBinWindow.setVisible(false);
        this.addActor(shippingBinWindow);
        TooltipManager.getInstance().instant();

        shippingBinWindow.setVisible(true);
        shippingBinWindow.toFront();
    }

//    @Override
//    public Actor hit(float stageX, float stageY, boolean touchable) {
//        Actor hit = super.hit(stageX, stageY, touchable);
//
////        if (hit == shippingBinWindow) return null;
//
//        return hit;
//    }

    public void setVisibleAll(boolean visible) {
        shippingBinWindow.setVisible(visible);

        if (visible) {
            this.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            this.draw();
        }
    }

    public void update() {
        try {
            ((ShippingBinWindow) shippingBinWindow).updateInventory();
        } catch (Exception e) {
        }
    }
}