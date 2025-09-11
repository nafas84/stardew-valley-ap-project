package io.Ap.StardewValley.Client.Screen.TimeScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FlashLayer extends Actor {

    private float alpha = 0f;

    public FlashLayer() {
        setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setTouchable(null);
    }

    public void flash(float intensity) {
        alpha = intensity; // مثلا 0.8f برای برق
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (alpha > 0) {
            alpha -= delta * 2.5f; // سرعت محو شدن (مثلا ظرف 0.3 ثانیه)
            if (alpha < 0) alpha = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (alpha > 0) {
            Color original = batch.getColor();
            batch.setColor(1f, 1f, 1f, alpha * parentAlpha);
            batch.draw(getWhiteTexture(), 0, 0, getWidth(), getHeight());
            batch.setColor(original);
        }
    }

    private static Texture whiteTexture;
    private Texture getWhiteTexture() {
        if (whiteTexture == null) {
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(1, 1, 1, 1);
            pixmap.fill();
            whiteTexture = new Texture(pixmap);
            pixmap.dispose();
        }
        return whiteTexture;
    }
}
