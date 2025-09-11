package io.Ap.StardewValley.Client.Screen.TimeScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class SnowLayer extends WeatherLayer {
    private static class Snowflake {
        Sprite sprite;
        float speed;
        float angle; // زاویه حرکت (درجه)
    }

    private final List<Snowflake> flakes = new ArrayList<>();
    private final int screenWidth = Gdx.graphics.getWidth();
    private final int screenHeight = Gdx.graphics.getHeight();

    private final Texture snowTexture;
    private final float scale;

    private final int numFlakes = 800;

    public SnowLayer(float scale) {
        this.scale = scale;
        this.snowTexture = new Texture("time/weather/snow.png"); // مثلا دونه برف 16x16

        int count = (int) (numFlakes * scale);
        for (int i = 0; i < count; i++) {
            Snowflake flake = new Snowflake();
            flake.sprite = new Sprite(snowTexture);
            flake.sprite.setSize(16 * scale, 16 * scale);
            flake.sprite.setPosition(MathUtils.random(0, screenWidth), MathUtils.random(0, screenHeight));
            flake.speed = MathUtils.random(30f, 90f);
            flake.angle = MathUtils.randomBoolean() ? -45f : -135f;

            flakes.add(flake);
        }
    }

    @Override
    public void update(float delta) {
        for (Snowflake flake : flakes) {
            float dx = flake.speed * delta * MathUtils.cosDeg(flake.angle);
            float dy = flake.speed * delta * MathUtils.sinDeg(flake.angle);

            flake.sprite.translate(dx, dy);

            // اگه از صفحه خارج شد، برگرد بالا
            if (flake.sprite.getY() < -flake.sprite.getHeight()) {
                flake.sprite.setY(screenHeight);
                flake.sprite.setX(MathUtils.random(0, screenWidth));
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Snowflake flake : flakes) {
            flake.sprite.draw(batch);
        }
    }
}
