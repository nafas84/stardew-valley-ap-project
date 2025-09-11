package io.Ap.StardewValley.Client.Screen.AnimalScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimalRender {
    private Texture catSheet;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime;
    private float x, y;
    private float speed = 50f;
    private boolean movingRight = true;

    private float startX;
    private float rightLimit;
    private float leftLimit;

    public AnimalRender() {
        this.catSheet = new Texture(Gdx.files.internal("animal/cat3.png"));

        TextureRegion[][] tmp = TextureRegion.split(catSheet, 32, 32);
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < tmp[0].length; i++) {
            frames.add(tmp[1][i]);
        }

        walkAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        stateTime = 0f;

        setLibGdxPosition();
        this.rightLimit = startX + 10 * 32;
        this.leftLimit = startX;
    }

    public void setLibGdxPosition() {
        final int tileSize = 16;
        int mapHeightInTiles = 65;

        this.x = 60 * tileSize;
        this.startX = 60 * tileSize;
        this.y = (mapHeightInTiles - 1 - 17) * tileSize;
    }

    public void update(float delta) {
        stateTime += delta;

        if (movingRight) {
            x += speed * delta;
            if (x >= rightLimit) {
                movingRight = false;
            }
        } else {
            x -= speed * delta;
            if (x <= leftLimit) {
                movingRight = true;
            }
        }
    }

    public void render(Batch batch) {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        if (movingRight && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        } else if (!movingRight && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }

        batch.draw(currentFrame, x, y);
    }

    public void dispose() {
        catSheet.dispose();
    }
}
