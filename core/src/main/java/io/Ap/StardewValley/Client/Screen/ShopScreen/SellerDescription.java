package io.Ap.StardewValley.Client.Screen.ShopScreen;

import com.badlogic.gdx.scenes.scene2d.ui.*;

public class SellerDescription extends Window {
    public SellerDescription(Skin skin, String description) {
        super("", skin);
        Label label = new Label(description, skin);
        label.setWrap(true);
        add(label).width(240).pad(8);
        pack();
    }
}
