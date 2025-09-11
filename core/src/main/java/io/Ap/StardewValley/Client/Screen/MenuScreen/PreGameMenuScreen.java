package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.NetworkControllers.ClientLobbyController;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Client.Screen.GameScreen;
import io.Ap.StardewValley.Client.Screen.MenuScreen.CoOpMenus.HostLobbyScreen;
import io.Ap.StardewValley.Client.Screen.MenuScreen.CoOpMenus.LobbyScreen;
import io.Ap.StardewValley.StardewValley;


public class PreGameMenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final TextButton backButton, startButton;
    private final Image backgroundImage, characterBackground;
    private final TextureRegion[][] shirtSheet, hairSheet, pantSheet;

    private String hairColor, pantColor;
    private int pantIndex, shirtIndex, hairIndex;

    private int farmId;
    private ImageButton rightButton;
    private ImageButton leftButton;
    private final Image farmImageBackGround;
    private Image farmIconImage;
    private Image farmImage;

    private final Image bodyImage, handImage;
    private Stack characterStack;

    private final boolean isHost;
    private final String lobbyName;

    public PreGameMenuScreen(boolean isHost, String lobbyName) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = StardewValley.getSkin();

        this.isHost = isHost;
        this.lobbyName = lobbyName;

        backgroundImage = new Image(new Texture(Gdx.files.internal("etc/menu/background_start.png")));
        characterBackground = new Image(new Texture(Gdx.files.internal("etc/menu/daybg.png")));

        Texture bodySheetTexture = new Texture("player/body_boy.png");
        TextureRegion bodyRegion = new TextureRegion(bodySheetTexture, 0, 0, 16, 32);
        Texture handSheetTexture = new Texture("player/hand_01.png");
        TextureRegion handRegion = new TextureRegion(handSheetTexture, 0, 0, 16, 32);

        bodyImage = new Image(bodyRegion);
        handImage = new Image(handRegion);

        shirtSheet = TextureRegion.split(new Texture("player/clothes/shirts.png"), 8, 8);
        hairSheet = TextureRegion.split(new Texture("player/clothes/hairstyles.png"), 16, 32);
        pantSheet = TextureRegion.split(new Texture("player/clothes/pants.png"), 16, 32);

        backButton = new TextButton("Back", skin);
        startButton = new TextButton("Start", skin);

        hairColor = "Black";
        pantColor = "Black";
        pantIndex = 0;
        shirtIndex = 0;
        hairIndex = 0;

        farmId = 1;

        rightButton = new ImageButton(skin, "Right");
        leftButton = new ImageButton(skin, "Left");
        farmIconImage = new Image(new Texture(Gdx.files.internal("etc/mapImages/Farm" + farmId + "_Icon.png")));
        farmImage = new Image(new Texture(Gdx.files.internal("etc/mapImages/Farm" + farmId + "_pixel.png")));
        farmImageBackGround = new Image(new Texture(Gdx.files.internal("etc/mapImages/farmBackground.png")));

    }

    @Override
    public void show() {
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);
        stack.add(backgroundImage);

        Window window = new Window("", skin);
        Label titleLabel = new Label("Pre Game", skin, "Bold");
        titleLabel.setAlignment(Align.center);
        window.getTitleTable().clear();
        window.getTitleTable().add(titleLabel).expandX().center().padTop(5).padBottom(10);

        window.setMovable(false);
        window.setResizable(false);
        window.setSize(1200, 800);
        window.setPosition(
                (stage.getWidth() - window.getWidth()) / 2,
                (stage.getHeight() - window.getHeight()) / 2
        );

        Table contentTable = new Table();
        //avatar row
        Table avatarRow = new Table();
        avatarRow.add(leftButton).pad(20, 0, 0, 0).height(60).width(120);
        avatarRow.add(farmIconImage).pad(5).size(100, 100);
        avatarRow.add(rightButton).pad(20, 0, 0, 0).height(60).width(120);

        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                farmId--;
                if (farmId < 1) farmId = 5; // wrap around
                updateFarmImages();
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                farmId++;
                if (farmId > 5) farmId = 1; // wrap around
                updateFarmImages();
            }
        });

        // rightRight column
        Table rightRightColumn = new Table();

        rightRightColumn.add(avatarRow).center().pad(15, 0, 0, 0);
        rightRightColumn.row();

        farmImage.setAlign(Align.center);
        farmImageBackGround.setAlign(Align.center);

        Stack farmImageStack = new Stack();
        farmImageStack.add(farmImage);
        farmImageStack.add(farmImageBackGround);


        rightRightColumn.add(farmImageStack).pad(70, 0, 0, 0).size(farmImageBackGround.getWidth(), farmImageBackGround.getHeight());
        rightRightColumn.row();

        //  left column(character):
        Table leftColumn = new Table();
        leftColumn.pad(20);
        Group characterGroup = getCharacterGroup();

        characterStack = new Stack();
        characterStack.add(characterBackground);
        characterStack.add(characterGroup);

        leftColumn.add(characterStack).size(
                characterBackground.getWidth() * 1.7f,
                characterBackground.getHeight() * 1.7f
        ).padBottom(30);

        leftColumn.row();

        //  right column (button Hair, Shirt, Pant)
        Table rightColumn = new Table();
        rightColumn.pad(20);

        SelectorGroup hairSelector = new SelectorGroup("Hair");
        SelectorGroup shirtSelector = new SelectorGroup("Shirt");
        SelectorGroup pantSelector = new SelectorGroup("Pant");

        rightColumn.add(hairSelector.table).pad(10).row();
        rightColumn.add(shirtSelector.table).pad(10).row();
        rightColumn.add(pantSelector.table).pad(10).row();

        Table characterWithOptions = new Table();
        characterWithOptions.add(leftColumn).top().padRight(20);
        characterWithOptions.add(rightColumn).top().padTop(-30);

        contentTable.add(characterWithOptions).colspan(2).pad(10);
        contentTable.add(rightRightColumn).top().pad(10, 10, 10, 10);

        String[] colors = {
                "Black", "Brown", "Blonde", "Red", "Blue", "Cyan", "Green", "Magenta",
                "Orange", "Pink", "Yellow", "Gray", "White"
        };

        SelectBox<String> hairColorSelectBox = new SelectBox<>(skin);
        hairColorSelectBox.setItems(colors);

        SelectBox<String> pantColorSelectBox = new SelectBox<>(skin);
        pantColorSelectBox.setItems(colors);

        Table colorSelectTable = new Table();
        colorSelectTable.align(Align.center);

        colorSelectTable.add(new Label("Hair Color:", skin)).padRight(10);
        colorSelectTable.add(hairColorSelectBox).width(200).row();
        colorSelectTable.add(new Label("Pant Color:", skin)).padRight(10).padTop(40);
        colorSelectTable.add(pantColorSelectBox).width(200).padTop(40);

        contentTable.row();
        contentTable.add(colorSelectTable).padBottom(20).row();

        Table buttonRow = new Table();
        buttonRow.add(backButton).width(200).height(90).pad(10);
        buttonRow.add(startButton).width(200).height(90).pad(10);


        window.add(contentTable).expand().center().row();
        window.add(buttonRow).padTop(20);
        stage.addActor(window);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isHost && (lobbyName == null)) {
                    StardewValley.getGame().setScreen(new GameMenuScreen());
                }

            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isHost && (lobbyName == null)) {
                    GameMenuController.newGameOffline(hairColor, pantColor, pantIndex / 12, shirtIndex, hairIndex, farmId);
                    StardewValley.getGame().setScreen(new GameScreen(GameMenuController.farmSelections));
                }
                else if (isHost && (lobbyName != null)) {
                    //TODO show error
                    Result result = ClientLobbyController.setHostPlayer(lobbyName, hairColor, pantColor, pantIndex / 12, shirtIndex, hairIndex, farmId);
                    if (result.isSuccessful()) {
                        StardewValley.getGame().setScreen(new HostLobbyScreen(lobbyName));
                    }
                }
                else if (!isHost && (lobbyName != null)) {
                    //TODO show error
                    Result result = ClientLobbyController.addPlayer(lobbyName, hairColor, pantColor, pantIndex / 12, shirtIndex, hairIndex, farmId);
                    if (result.isSuccessful()) {
                        StardewValley.getGame().setScreen(new LobbyScreen(lobbyName));
                    }
                }
            }
        });

        hairColorSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hairColor = hairColorSelectBox.getSelected();
                refreshCharacter();
            }
        });

        pantColorSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pantColor = pantColorSelectBox.getSelected();
                refreshCharacter();
            }
        });

        hairSelector.leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hairIndex--;
                if (hairIndex < 0) hairIndex = 6 * 8 + 7;
                refreshCharacter();
            }
        });

        hairSelector.rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hairIndex++;
                if (hairIndex > 6 * 8 + 7) hairIndex = 0;
                refreshCharacter();
            }
        });

        shirtSelector.leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shirtIndex--;
                if (shirtIndex < 0) shirtIndex = 17 * 18 + 9;
                refreshCharacter();
            }
        });

        shirtSelector.rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shirtIndex++;
                if (shirtIndex > 17 * 18 + 9) shirtIndex = 0;
                refreshCharacter();
            }
        });

        pantSelector.leftBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pantIndex -= 12;
                if (pantIndex < 0) pantIndex = pantSheet[0].length - 12;
                refreshCharacter();
            }
        });

        pantSelector.rightBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pantIndex += 12;
                if (pantIndex > pantSheet[0].length - 12) pantIndex = 0;
                refreshCharacter();
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

    private Group getCharacterGroup() {
        Group characterGroup = new Group();

        float scale = 8f;

        int x = 45;
        int y = 49;

        bodyImage.setSize(16 * scale, 32 * scale);
        bodyImage.setPosition(x, y);
        characterGroup.addActor(bodyImage);

        // selected:
        Image pantImage = new Image(pantSheet[0][pantIndex]);
        pantImage.setSize(16 * scale, 32 * scale);
        pantImage.setPosition(x, y);
        pantImage.setColor(App.getColor(pantColor));
        characterGroup.addActor(pantImage);

        Image shirtImage = new Image(shirtSheet[(shirtIndex / 18) * 4][shirtIndex % 16]);
        shirtImage.setSize(8 * scale, 8 * scale);
        shirtImage.setPosition(x + 4 * scale, y + 9 * scale);
        characterGroup.addActor(shirtImage);

        int longHair = (hairIndex < 16) ? 0 : -1;
        Image hairImage = new Image(hairSheet[(hairIndex / 8) * 3][hairIndex % 8]);
        hairImage.setSize(16 * scale, 32 * scale);
        hairImage.setPosition(x, y - (1 + longHair) * scale);
        hairImage.setColor(App.getColor(hairColor));
        characterGroup.addActor(hairImage);

        // hand
        handImage.setSize(16 * scale, 32 * scale);
        handImage.setPosition(x, y);
        characterGroup.addActor(handImage);

        return characterGroup;
    }

    private void refreshCharacter() {
        characterStack.clear();
        characterStack.add(characterBackground);
        characterStack.add(getCharacterGroup());
    }

    private void updateFarmImages() {
        // به‌روزرسانی آیکون فارم
        farmIconImage.setDrawable(new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal("etc/mapImages/Farm" + farmId + "_Icon.png")))
        ));

        // به‌روزرسانی تصویر فارم
        farmImage.setDrawable(new TextureRegionDrawable(
                new TextureRegion(new Texture(Gdx.files.internal("etc/mapImages/Farm" + farmId + "_pixel.png")))
        ));
    }

}

class SelectorGroup {
    public Table table;
    public ImageButton leftBtn, rightBtn;
    public Label label;

    public SelectorGroup(String labelText) {
        table = new Table();
        Skin skin = StardewValley.getSkin();

        leftBtn = new ImageButton(skin, "Left");
        rightBtn = new ImageButton(skin, "Right");
        label = new Label(labelText, skin);
        label.setAlignment(Align.center);

        table.add(leftBtn).size(60, 60).padRight(10);
        table.add(label).width(100).center().padRight(10);
        table.add(rightBtn).size(60, 60);
    }
}
