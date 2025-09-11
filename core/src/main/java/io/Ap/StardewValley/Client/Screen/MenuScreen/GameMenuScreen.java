package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Client.Screen.GameScreen;
import io.Ap.StardewValley.Client.Screen.MenuScreen.CoOpMenus.CoOpScreen;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.StardewValley;

public class GameMenuScreen implements Screen {
    private final Stage stage;
    private final Table mainTable;

    private final TextButton newButton, loadButton, coOpButton, backButton;
    private final Image backgroundImage, logoImage;

    private final Array<Animation<TextureRegion>> butterflyAnimations = new Array<>();

    public GameMenuScreen() {
        Skin skin = StardewValley.getSkin();

        newButton = new TextButton("New", skin, "Chicken");
        loadButton = new TextButton("Load", skin, "Strawberry");
        coOpButton = new TextButton("Co-op", skin, "Earth");
        backButton = new TextButton("Back", skin, "Plant");

        //backgroundImage = new Image(new Texture(Gdx.files.internal("etc/menu/background_night.png")));
        backgroundImage = new Image(new Texture(Gdx.files.internal("etc/menu/background_start.png")));

        logoImage = new Image(new Texture(Gdx.files.internal("etc/menu/logo.png")));

//        Texture sheet = new Texture(Gdx.files.internal("etc/gogoli/Bat.png"));
//        TextureRegion[][] tmp = TextureRegion.split(sheet, 64, 64);
//        Texture sheet = new Texture(Gdx.files.internal("etc/gogoli/companions.png"));
//        TextureRegion[][] tmp = TextureRegion.split(sheet, 16, 16);

        for (int i = 0; i < 4; i++) {
            TextureRegion[] frames = new TextureRegion[]{new TextureRegion(new Texture("etc/sherekVane.png"))};
            //System.arraycopy(tmp[0], 4 * i, frames, 0, 4);
            butterflyAnimations.add(new Animation<>(0.13f, frames));
        }

//        butterflyAnimations.add(new Animation<>(0.13f, tmp[0]));

        mainTable = new Table();
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        stack.add(backgroundImage);
        stack.add(mainTable);

        mainTable.setFillParent(true);
        mainTable.center().top().padTop(100);

        int numOfButterfly = 10;
        for (int i = 0; i < numOfButterfly; i++) {
            Animation<TextureRegion> baseAnimation = butterflyAnimations.random();
            TextureRegion[] originalFrames = baseAnimation.getKeyFrames();
            TextureRegion[] flippedFrames = new TextureRegion[originalFrames.length];

            boolean shouldFlip = MathUtils.randomBoolean(0.5f);

            for (int j = 0; j < originalFrames.length; j++) {
                flippedFrames[j] = new TextureRegion(originalFrames[j]);
                if (shouldFlip) {
                    flippedFrames[j].flip(true, false);
                }
            }

            Animation<TextureRegion> finalAnimation = new Animation<>(baseAnimation.getFrameDuration(), flippedFrames);

            float x = MathUtils.random(0, Gdx.graphics.getWidth());
            float y = MathUtils.random(0, Gdx.graphics.getHeight());
            float scale = MathUtils.random(1f, 2f);

            animationActor butterfly = new animationActor(
                    finalAnimation,
                    x,
                    y,
                    scale,
                    animationActor.MovementType.Random
            );

            stage.addActor(butterfly);
        }


        mainTable.add(logoImage).center().padBottom(50).row();

        Table buttonRow = new Table();
        buttonRow.add(newButton).width(240).pad(10);
        buttonRow.add(loadButton).width(240).pad(10);
        buttonRow.add(coOpButton).width(240).pad(10);
        buttonRow.add(backButton).width(240).pad(10);

        mainTable.add(buttonRow).center().row();

        newButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new PreGameMenuScreen(false, null));
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = GameMenuController.loadGame();
                if (result.isSuccessful())
                    StardewValley.getGame().setScreen(new GameScreen());
                else
                    System.out.println(result.message());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new MainMenuScreen());
            }
        });

        coOpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new CoOpScreen());
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}