package io.Ap.StardewValley.Client.Screen.ShopScreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class SellerPicture extends Window {
    public SellerPicture(Skin skin, Texture texture, String name) {
        super(name, skin);
        Image img = new Image(texture);
        add(img).size(256,256).pad(8);
        this.setSize(350, 350);
    }
}