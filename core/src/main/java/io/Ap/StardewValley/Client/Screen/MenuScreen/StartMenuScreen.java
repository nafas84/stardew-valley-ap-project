package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.StardewValley;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import java.util.Random;

public class StartMenuScreen implements Screen {
    private final Stage stage;
    private final Table mainTable;

    private final TextButton registerButton, loginButton, exitButton;
    private final Image backgroundImage, logoImage;

    private final Array<Texture> cloudTextures = new Array<>();
    private final Array<CloudActor> cloudActors = new Array<>();

    private final Animation<TextureRegion> birdAnimation;

    public StartMenuScreen() {
        Skin skin = StardewValley.getSkin();

        registerButton = new TextButton("Register", skin, "Chicken");
        loginButton = new TextButton("Login", skin, "Strawberry");
        exitButton = new TextButton("Exit", skin, "Plant");

        backgroundImage = new Image(new Texture(Gdx.files.internal("etc/menu/background_start.png")));
        logoImage = new Image(new Texture(Gdx.files.internal("etc/menu/logo.png")));
        cloudTextures.add(new Texture(Gdx.files.internal("etc/menu/cloud_1.png")));
        cloudTextures.add(new Texture(Gdx.files.internal("etc/menu/cloud_2.png")));
        cloudTextures.add(new Texture(Gdx.files.internal("etc/menu/cloud_3.png")));

        Texture birdSheet = new Texture(Gdx.files.internal("etc/menu/bird.png"));
        TextureRegion[][] tmp = TextureRegion.split(birdSheet, 26, 18);
        TextureRegion[] birdFrames = new TextureRegion[4];
        System.arraycopy(tmp[0], 0, birdFrames, 0, 4);

        birdAnimation = new Animation<>(0.13f, birdFrames);

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

        Group cloudLayer = new Group();
        stack.add(cloudLayer);

        stack.add(mainTable);

        mainTable.setFillParent(true);
        mainTable.center().top().padTop(100);

        animationActor bird1 = new animationActor(birdAnimation, 1920, 375, 3f, animationActor.MovementType.Liner);
        animationActor bird2 = new animationActor(birdAnimation, 1800, 320, 4f, animationActor.MovementType.Liner);
        animationActor bird3 = new animationActor(birdAnimation, 1600, 350, 5f, animationActor.MovementType.Liner);
        stage.addActor(bird1);
        stage.addActor(bird2);
        stage.addActor(bird3);

        mainTable.add(logoImage).center().padBottom(50).row();

        Table buttonRow = new Table();
        buttonRow.add(registerButton).width(240).pad(10);
        buttonRow.add(loginButton).width(240).pad(10);
        buttonRow.add(exitButton).width(240).pad(10);
        mainTable.add(buttonRow).center().row();

        Random random = new Random();
        int numberOfClouds = 8;
        for (int i = 0; i < numberOfClouds; i++) {
            Texture cloudTex = cloudTextures.random();

            float startX = random.nextFloat() * Gdx.graphics.getWidth();
            float startY = 360 + random.nextFloat() * Gdx.graphics.getHeight();
            float speed = 5 + random.nextFloat() * 15;
            float scaleFactor = 1.5f + random.nextFloat() * 0.8f;

            CloudActor cloud = new CloudActor(cloudTex, speed, startX, startY, scaleFactor);
            cloudActors.add(cloud);
            cloudLayer.addActor(cloud);
        }

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new SignUpMenuScreen());

            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 StardewValley.getGame().setScreen(new LoginMenuScreen());
//                StardewValley.getGame().setScreen(new SecurityQuestionScreen("Aynaz"));
//                StardewValley.getGame().setScreen(new MainMenuScreen());

            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
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
        for (Texture tex : cloudTextures) tex.dispose();
    }
}

