package io.Ap.StardewValley;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.Ap.StardewValley.Common.Model.App;

import java.io.FileNotFoundException;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class StardewValley extends Game {
    private static StardewValley game;
    private static SpriteBatch batch;
    private static Skin skin;

    @Override
    public void create() {
        game = this;
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/NzSkin.json"));

        // setCursor
        setCustomCursor();

        //Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        //Gdx.graphics.setFullscreenMode(displayMode);

        // loadApp(stayLoggedIn)
        try {
            App.loadAppScreen();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //getGame().setScreen(new TiledMapRenderHelper());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static StardewValley getGame() {
        return game;
    }

    public static void setGame(StardewValley game) {
        StardewValley.game = game;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        StardewValley.batch = batch;
    }

    public static Skin getSkin() {
        return skin;
    }

    private void setCustomCursor() {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("etc/cursor.png"));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);
        pixmap.dispose();
    }
}
