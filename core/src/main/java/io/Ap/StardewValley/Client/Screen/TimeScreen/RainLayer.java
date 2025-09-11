package io.Ap.StardewValley.Client.Screen.TimeScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class RainLayer extends WeatherLayer {
    private final boolean isStorm;
    private boolean flash = false;
    private final float flashDuration = 0.15f;
    private float flashTimer = 0f;

    private float stormCooldownTimer = 0f;
    private float timeUntilNextStorm = 10f + MathUtils.random(5f, 10f);
    private int flashesRemaining = 0;

    private final Texture whaiteTexture;
    private static class RainDrop {
        float x, y;
        float targetY;
        float fallSpeed;
        float time;
        boolean splashing = false;

        public RainDrop(float x, float y, float targetY, float fallSpeed) {
            this.x = x;
            this.y = y;
            this.targetY = targetY;
            this.fallSpeed = fallSpeed;
            this.time = 0;
        }
    }

    private final TextureRegion lineFrame;
    private final Animation<TextureRegion> splashAnimation;
    private final List<RainDrop> rainDrops = new ArrayList<>();

    private final int screenWidth;
    private final int screenHeight;
    private final float scale;

    private float spawnTimer = 0f;
    private final float spawnInterval = 0.03f;

    public RainLayer(float scale, boolean isStorm) {
        this.isStorm = isStorm;
        whaiteTexture = new Texture("etc/pixel.png");
        Texture rainTexture = new Texture("time/weather/rain.png");
        TextureRegion[] frames = TextureRegion.split(rainTexture, 16, 16)[1];
        this.lineFrame = frames[0];
        this.splashAnimation = new Animation<>(0.1f, frames[1], frames[2], frames[3]);
        this.scale = scale;

        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();

        setSize(screenWidth, screenHeight);
        setTouchable(null);
    }

    private RainDrop randomDrop() {
        float x = MathUtils.random(0, screenWidth + 200);
        float y = MathUtils.random(screenHeight, screenHeight + 200);
        float targetY = MathUtils.random(0, screenHeight);
        float speed = MathUtils.random(300, 700);
        return new RainDrop(x, y, targetY, speed);
    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < rainDrops.size(); i++) {
            RainDrop drop = rainDrops.get(i);
            drop.time += delta;

            if (!drop.splashing) {
                float moveDistance = drop.fallSpeed * delta;
                float moveX = moveDistance * MathUtils.cosDeg(60);
                float moveY = moveDistance * MathUtils.sinDeg(60);

                drop.x -= moveX;
                drop.y -= moveY;

                if (drop.y <= drop.targetY) {
                    drop.splashing = true;
                    drop.time = 0;
                }
            } else {
                if (splashAnimation.isAnimationFinished(drop.time)) {
                    rainDrops.remove(i);
                    i--;
                }
            }
        }

        spawnTimer += delta;
        while (spawnTimer >= spawnInterval) {
            rainDrops.add(randomDrop());
            spawnTimer -= spawnInterval;
        }

        if (isStorm) {
            stormCooldownTimer += delta;

            if (flashesRemaining == 0 && stormCooldownTimer >= timeUntilNextStorm) {
                flashesRemaining = 2;
                stormCooldownTimer = 0f;
                timeUntilNextStorm = 10f + MathUtils.random(5f, 10f);
            }

            if (!flash && flashesRemaining > 0) {
                flash = true;
                flashTimer = 0f;
                flashesRemaining--;
            }

            if (flash) {
                flashTimer += delta;
                if (flashTimer >= flashDuration) {
                    flash = false;
                    flashTimer = 0f;
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (RainDrop drop : rainDrops) {
            if (!drop.splashing) {
                batch.draw(lineFrame, drop.x, drop.y,
                        lineFrame.getRegionWidth() * scale, lineFrame.getRegionHeight() * scale);
            } else {
                TextureRegion splashFrame = splashAnimation.getKeyFrame(drop.time, false);
                batch.draw(splashFrame, drop.x, drop.y,
                        splashFrame.getRegionWidth() * scale, splashFrame.getRegionHeight() * scale);
            }
        }

        if (flash) {
            batch.setColor(1f, 1f, 1f, 0.8f);
            batch.draw(whaiteTexture, 0, 0, screenWidth, screenHeight);
            batch.setColor(1f, 1f, 1f, 1f);
        }

    }
}
