package io.Ap.StardewValley.Client.Screen.MenuScreen.CoOpMenus;

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
import io.Ap.StardewValley.Client.Screen.MenuScreen.GameMenuScreen;
import io.Ap.StardewValley.Client.Screen.MenuScreen.animationActor;
import io.Ap.StardewValley.StardewValley;

public class CoOpScreen implements Screen {

    private final Stage stage;
    private final Table mainTable;

    private final TextButton hostButton, joinButton, backButton;
//    private final Label ipAddressLabel;
    private final Image backgroundImage, logoImage;

    private final Array<Animation<TextureRegion>> butterflyAnimations;

    public CoOpScreen() {
        butterflyAnimations = new Array<>();
        Skin skin = StardewValley.getSkin();
        hostButton = new TextButton("Host", skin , "Chicken");
        joinButton = new TextButton("Join", skin, "Strawberry");
        backButton = new TextButton("Back", skin, "Plant");

//        ipAddressLabel = new Label("Your IP Address: " + StardewValleyServers.getIPv4(), skin);
        backgroundImage = new Image(new Texture(Gdx.files.internal("etc/menu/background_start.png")));
        logoImage = new Image(new Texture(Gdx.files.internal("etc/menu/logo.png")));
        mainTable = new Table();
        stage = new Stage(new ScreenViewport());

        Texture sheet = new Texture(Gdx.files.internal("etc/gogoli/companions.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, 16, 16);

        for (int i = 0; i < 4; i++) {
            TextureRegion[] frames = new TextureRegion[4];
            System.arraycopy(tmp[0], 4 * i, frames, 0, 4);
            butterflyAnimations.add(new Animation<>(0.13f, frames));
        }
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
            float scale = MathUtils.random(2f, 5.5f);

            animationActor butterfly = new animationActor(
                    finalAnimation,
                    x,
                    y,
                    scale,
                    animationActor.MovementType.Random
            );

            stage.addActor(butterfly);
        }

        mainTable.add(logoImage).center().row();

        Table buttonRow = new Table();

        buttonRow.add(hostButton).width(240).pad(10);
        buttonRow.add(joinButton).width(240).pad(10);
        buttonRow.add(backButton).width(240).pad(10);

        mainTable.add(buttonRow).center().padTop(50).row();

        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new CoOpHostScreen());
            }
        });

        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new CoOpJoinScreen());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new GameMenuScreen());
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
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
