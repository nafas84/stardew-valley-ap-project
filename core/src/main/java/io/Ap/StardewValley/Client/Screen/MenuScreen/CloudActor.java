package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

class CloudActor extends Image {
    private final float speed;

    public CloudActor(Texture texture, float baseSpeed, float startX, float startY, float scaleFactor) {
        super(texture);

        float width = getWidth() * scaleFactor;
        float height = getHeight() * scaleFactor;
        setSize(width, height);

        setScale(scaleFactor);

        setPosition(startX, startY);
        this.speed = baseSpeed * scaleFactor;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        moveBy(-speed * delta, 0);

        if (getX() + getWidth() + getWidth() < 0) {
            float newX = Gdx.graphics.getWidth() + getWidth();
            setX(newX);
        }
    }
}

