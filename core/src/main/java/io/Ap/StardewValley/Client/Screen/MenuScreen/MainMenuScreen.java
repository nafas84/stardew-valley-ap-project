package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.MainMenuController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.StardewValley;

public class MainMenuScreen implements Screen {
    private final Stage stage;
    private final Table mainTable;

    private final TextButton profileButton, gameButton, exitButton, logoutButton;
    private final Image backgroundImage, logoImage;

    private final Array<Animation<TextureRegion>> butterflyAnimations = new Array<>();

    public MainMenuScreen() {
        Skin skin = StardewValley.getSkin();

        profileButton = new TextButton("Profile", skin, "Chicken");
        gameButton = new TextButton("Game", skin, "Strawberry");
        logoutButton = new TextButton("Logout", skin, "Earth");
        exitButton = new TextButton("Exit", skin, "Plant");

        backgroundImage = new Image(new Texture(Gdx.files.internal("etc/menu/background_chill.png")));
        logoImage = new Image(new Texture(Gdx.files.internal("etc/menu/logo.png")));

        Texture sheet = new Texture(Gdx.files.internal("etc/gogoli/companions.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, 16, 16);

        for (int i = 0; i < 4; i++) {
            TextureRegion[] frames = new TextureRegion[4];
            System.arraycopy(tmp[9], 4 * i, frames, 0, 4);
            butterflyAnimations.add(new Animation<>(0.13f, frames));
        }

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

        int numOfButterfly = 25;
        for (int i = 0; i < numOfButterfly; i++) {
            Animation<TextureRegion> randomAnimation = butterflyAnimations.random();

            float x = MathUtils.random(0, Gdx.graphics.getWidth());
            float y = MathUtils.random(0, Gdx.graphics.getHeight());

            float scale = MathUtils.random(1f, 5f);

            animationActor butterfly = new animationActor(
                    randomAnimation,
                    x,
                    y,
                    scale,
                    animationActor.MovementType.Random
            );

            stage.addActor(butterfly);
        }

        mainTable.add(logoImage).center().padBottom(50).row();

        Table buttonRow = new Table();

        buttonRow.add(profileButton).width(240).pad(10);
        buttonRow.add(gameButton).width(240).pad(10);

        buttonRow.add(getAvatarButtonGroup()).pad(10);

        buttonRow.add(logoutButton).width(240).pad(10);
        buttonRow.add(exitButton).width(240).pad(10);
        mainTable.add(buttonRow).center().row();

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new ProfileMenuScreen());
            }
        });

        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 StardewValley.getGame().setScreen(new GameMenuScreen());
            }
        });

        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenuController.handleStayLogin();

                StardewValley.getGame().setScreen(new StartMenuScreen());
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private Group getAvatarButtonGroup() {
        Skin skin = StardewValley.getSkin();

        TextButton button = new TextButton(App.getCurrentUser().getUsername(), skin, "Plant");
        float scaleButton = 1.3f;
        button.setSize(button.getWidth() * scaleButton,button.getHeight() * scaleButton);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new OnlineUsersScreen());
            }
        });
        float scaleAvatar = 1.4f;
        Texture avatarTexture = new Texture(Gdx.files.internal(App.getCurrentUser().getAvatarPath()));
        avatarTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        Image avatarImage = new Image(avatarTexture);
        avatarImage.setSize(avatarImage.getWidth() * scaleAvatar, avatarImage.getHeight() * scaleAvatar);

        avatarImage.setPosition(50, 19);

        Group group = new Group();
        group.setSize(button.getWidth(), button.getHeight());

        group.addActor(button);
        group.addActor(avatarImage);

        return group;
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