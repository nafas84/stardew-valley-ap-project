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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.NetworkControllers.ClientLobbyController;
import io.Ap.StardewValley.Client.Controller.NetworkControllers.UpdateController;
import io.Ap.StardewValley.Client.Screen.MenuScreen.CoOpMenus.CoOpScreen;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.StardewValley;

public class OnlineUsersScreen implements Screen {
    private final Stage stage;
    private final Table mainTable;

    private final TextButton backButton;
    private final Image backgroundImage;

    private final Array<Animation<TextureRegion>> butterflyAnimations;

    private final Window window = new Window("", StardewValley.getSkin());

    private Label onlineUsersList;

    private TextButton refreshButton;

    private float stateTime = 0f;


    public OnlineUsersScreen() {
        butterflyAnimations = new Array<>();
        Skin skin = StardewValley.getSkin();
        backButton = new TextButton("Back", skin, "Plant");
        backgroundImage = new Image(new Texture(Gdx.files.internal("etc/menu/background_start.png")));
        mainTable = new Table();
        stage = new Stage(new ScreenViewport());

        onlineUsersList = UpdateController.onlineUsersLabel;
        onlineUsersList.setAlignment(Align.center);
        onlineUsersList.setWrap(true);
        onlineUsersList.setSize(400, 550);
        refreshButton =  new TextButton("Refresh", skin);

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
        buttonRow.add(backButton).width(240).padBottom(10);

        mainTable.add(buttonRow).center().row();

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new MainMenuScreen());
            }
        });

//        refreshButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                onlineUsersList.setText(ClientLobbyController.getPlayersList(lobbyName).message());
//            }
//        });

        Table lobbyListTable = new Table();
        lobbyListTable.add(onlineUsersList).width(400).height(550).top();

        Table contentTable = new Table();
//        contentTable.add(refreshButton).width(300).height(70).center().pad(40).row();

        Table windowTable = new Table();
        windowTable.add(lobbyListTable).pad(25);
        windowTable.add(contentTable).pad(25);

        Label titleLabel = new Label("Online Users", StardewValley.getSkin(), "Bold");
        titleLabel.setAlignment(Align.center);
        window.getTitleTable().clear();
        window.getTitleTable().add(titleLabel).expandX().center().padTop(5).padBottom(10);

        window.setMovable(false);
        window.setResizable(false);
        window.setSize(900, 600);
        window.setPosition(
                (stage.getWidth() - window.getWidth()) / 2,
                (stage.getHeight() - window.getHeight()) / 2 - 100
        );
        window.add(windowTable).expand();

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
    public void resize(int i, int i1) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}

    public Label getOnlineUsersList() {
        return onlineUsersList;
    }
}
