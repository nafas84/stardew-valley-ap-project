package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class animationActor extends Actor {
    private final Animation<TextureRegion> animation;
    private float time = 0f;

    public enum MovementType {
        Liner,
        Random
    }

    private final MovementType movementType;

    private float directionAngle;
    private float speed = 40f;
    private float directionChangeTimer = 0f;

    public animationActor(Animation<TextureRegion> animation, float x, float y, float scale, MovementType movementType) {
        this.animation = animation;
        this.movementType = movementType;
        setPosition(x, y);

        TextureRegion firstFrame = animation.getKeyFrame(0f);
        float originalWidth = firstFrame.getRegionWidth();
        float originalHeight = firstFrame.getRegionHeight();
        setSize(originalWidth * scale, originalHeight * scale);

        if (movementType == MovementType.Random) {
            directionAngle = MathUtils.random(0f, MathUtils.PI2);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;

        if (movementType == MovementType.Random) {
            directionChangeTimer += delta;
            if (directionChangeTimer >= 0.1f) {
                directionAngle += MathUtils.random(-0.3f, 0.3f);
                directionChangeTimer = 0f;
            }

            float dx = MathUtils.cos(directionAngle) * speed * delta;
            float dy = MathUtils.sin(directionAngle) * speed * delta;

            float newX = getX() + dx;
            float newY = getY() + dy;

            if (newX > Gdx.graphics.getWidth()) newX = -getWidth();
            if (newX + getWidth() < 0) newX = Gdx.graphics.getWidth();

            if (newY > Gdx.graphics.getHeight()) newY = -getHeight();
            if (newY + getHeight() < 0) newY = Gdx.graphics.getHeight();

            setPosition(newX, newY);
        } else {
            float newX = getX() - speed * delta;
            if (newX + getWidth() < 0) {
                newX = Gdx.graphics.getWidth();
            }
            setX(newX);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = animation.getKeyFrame(time, true);
        batch.draw(frame, getX(), getY(), getWidth(), getHeight());
    }
}
