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
import io.Ap.StardewValley.Client.Controller.NetworkControllers.ClientLobbyController;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Client.Screen.MenuScreen.PreGameMenuScreen;
import io.Ap.StardewValley.Client.Screen.MenuScreen.animationActor;
import io.Ap.StardewValley.StardewValley;

public class CoOpHostScreen implements Screen {

    private final Stage stage;
    private final Table mainTable;

    private final TextButton startButton, backButton;
    private final Image backgroundImage;

    private final Label lobbyName;
    private final Label isPrivate;
    private final Label isVisible;
    private final Label lobbyPassword;

    private TextField name;
    private TextField password;
    private final CheckBox visibilityCheckbox;
    private final CheckBox privacyCheckbox;

    private final Window window = new Window("", StardewValley.getSkin());

    private final Array<Animation<TextureRegion>> butterflyAnimations;

    private float stateTime = 0;

    public CoOpHostScreen() {
        butterflyAnimations = new Array<>();
        Skin skin = StardewValley.getSkin();

        visibilityCheckbox = new CheckBox("" , skin);
        visibilityCheckbox.setChecked(true);
        privacyCheckbox = new CheckBox("" , skin);
        privacyCheckbox.setChecked(true);
        name = new TextField("", skin);
        password = new TextField("", skin);

        lobbyName = new Label("name:", skin);
        lobbyPassword = new Label("password:", skin);
        isVisible = new Label("visible:", skin);
        isPrivate = new Label("private:", skin);

        startButton = new TextButton("Start", skin , "Chicken");
        backButton = new TextButton("Back", skin, "Plant");
        backgroundImage = new Image(new Texture(Gdx.files.internal("etc/menu/background_start.png")));
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

        Table buttonRow = new Table();
        buttonRow.add(startButton).width(240).pad(10);
        buttonRow.add(backButton).width(240).row();
        buttonRow.add(visibilityCheckbox).width(100);

        mainTable.add(buttonRow).center().row();

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new CoOpScreen());
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO
                Result result = ClientLobbyController.makeLobby(name.getText(), password.getText(), visibilityCheckbox.isChecked(), privacyCheckbox.isChecked());
                if (result.isSuccessful()) {
                    StardewValley.getGame().setScreen(new PreGameMenuScreen(true, name.getText()));
                }
            }
        });

        Table contentTable = new Table();
        contentTable.add(lobbyName).left().padBottom(30);
        contentTable.add(name).right().padBottom(30).row();
        contentTable.add(isVisible).left().padBottom(30);
        contentTable.add(visibilityCheckbox).right().padBottom(30).row();
        contentTable.add(isPrivate).left().padBottom(30);
        contentTable.add(privacyCheckbox).right().padBottom(30).row();
        contentTable.add(lobbyPassword).left();
        contentTable.add(password).right().padLeft(20);

        window.setMovable(false);
        window.setResizable(false);
        window.setSize(900, 600);
        window.add(contentTable).center();
        window.setPosition(
                (stage.getWidth() - window.getWidth()) / 2,
                (stage.getHeight() - window.getHeight()) / 2 - 100
        );

        stage.addActor(window);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
        stateTime += delta;
        if (stateTime >= 2) {
            stateTime = 0;
        }
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
