package io.Ap.StardewValley.Client.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Client.Controller.GameScreenController;
import io.Ap.StardewValley.Client.Screen.MultiplayerScreen.ScoreboardWindow;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.KeyManager;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;
import io.Ap.StardewValley.Common.Model.Time.Weather;
import io.Ap.StardewValley.Client.Screen.AnimalScreen.AnimalRender;
import io.Ap.StardewValley.Client.Screen.CookingScreen.CookingStage;
import io.Ap.StardewValley.Client.Screen.InventoryScreen.InventoryBar;
import io.Ap.StardewValley.Client.Screen.InventoryScreen.InventoryStage;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;
import io.Ap.StardewValley.Client.Screen.MapScreen.DynamicMapLayerRender;
import io.Ap.StardewValley.Client.Screen.MapScreen.SeasonTextureManager;
import io.Ap.StardewValley.Client.Screen.MapScreen.TiledMapRendererHelper;
import io.Ap.StardewValley.Client.Screen.MenuScreen.MainMenuScreen;
import io.Ap.StardewValley.Client.Screen.MiniGameScreen.MiniGameWindow;
import io.Ap.StardewValley.Client.Screen.ShopScreen.BlackSmithScreen.BlackSmithMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.CarpentersScreen.CarpentersMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.FishShopScreen.FishShopMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.JojaMartScreen.JojaMartMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.MarniesRanchScreen.MarniesMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.PierresScreen.PierresMenu;
import io.Ap.StardewValley.Client.Screen.ShopScreen.ShippingBin;
import io.Ap.StardewValley.Client.Screen.ShopScreen.StardropSaloonScreen.StardropMenu;
import io.Ap.StardewValley.Client.Screen.TimeScreen.RainLayer;
import io.Ap.StardewValley.Client.Screen.TimeScreen.SnowLayer;
import io.Ap.StardewValley.Client.Screen.TimeScreen.TimeBar;
import io.Ap.StardewValley.Client.Screen.TimeScreen.WeatherLayer;
import io.Ap.StardewValley.StardewValley;

public class GameScreen implements Screen, InputProcessor {
    // Map:
        // static
    private final TiledMapRendererHelper[][] mapRenderers = new TiledMapRendererHelper[3][3];
    private TiledMapRendererHelper currentMap;
    private final int[] farmSelections;
    private static Image fullMap;
        // dynamic
    private final DynamicMapLayerRender dynamicMapLayerRender = new DynamicMapLayerRender();

    // Animal:
    //private MyAnimalWindow animalListWindow;
    private AnimalRender cat = new AnimalRender();
    private MiniGameWindow miniGameWindow;

    // Multiplayer:
    private ScoreboardWindow scoreboardWindow;

    // Time:
    private Image nightOverlay;
    private TimeBar timeBar;
    private WeatherLayer currentWeatherLayer; // null-RainLayer(isStorm)-SnowLayer

    private Stage stage;
    private Stack stackBar;

    private final Table dialogTable = new Table();
    private final Table controllerTable = new Table();

    private OrthographicCamera camera;

    private boolean paused = false;
    private final GameScreenController controller = new GameScreenController();

    //inventory:
    private InventoryStage inventoryStage;
    private InventoryBar inventoryBar;

    //cooking:
    private CookingStage cookingStage = new CookingStage();

    //shops:
    private BlackSmithMenu blackSmithStage = new BlackSmithMenu();
    private CarpentersMenu carpentersStage = new CarpentersMenu();
    private FishShopMenu fishShopStage = new FishShopMenu();
    private JojaMartMenu jojaMartStage = new JojaMartMenu();
    private MarniesMenu marniesStage = new MarniesMenu();
    private PierresMenu pierresStage = new PierresMenu();
    private StardropMenu stardropStage = new StardropMenu();

    //shipping bin:
    private ShippingBin shippingBin = new ShippingBin();


    //errorHandling:
    Result currentResult = null;

    public GameScreen(int[] farmSelections) {
        this.farmSelections = new int[4];
        System.arraycopy(farmSelections, 0, this.farmSelections, 0, 4);
        controller.setViews(this);
    }

    public GameScreen() {
        this.farmSelections = App.getGame().getMap().farmSelections;
        controller.setViews(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        // rootStack:
        stackBar = new Stack();
        stackBar.setFillParent(true);
        dialogTable.setFillParent(true);
        dialogTable.top().left();


        // set nightOverlay:
        nightOverlay = new Image(new Texture("etc/pixel.png"));
        nightOverlay.setColor(Color.valueOf("0a111d"));
        nightOverlay.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        nightOverlay.setTouchable(Touchable.disabled);


        // set camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.2f;


        // set MapRenderers:
        mapRenderers[0][0] = new TiledMapRendererHelper("Farm" + farmSelections[0]);
        mapRenderers[0][1] = new TiledMapRendererHelper("path1");
        mapRenderers[0][2] = new TiledMapRendererHelper("Farm" + farmSelections[1]);

        mapRenderers[1][0] = new TiledMapRendererHelper("path4");
        mapRenderers[1][1] = new TiledMapRendererHelper("Town");
        mapRenderers[1][2] = new TiledMapRendererHelper("path2");

        mapRenderers[2][0] = new TiledMapRendererHelper("Farm" + farmSelections[3]);
        mapRenderers[2][1] = new TiledMapRendererHelper("path3");
        mapRenderers[2][2] = new TiledMapRendererHelper("Farm" + farmSelections[2]);

        setFullMap();

        // initial stage & bars::
        inventoryStage = new InventoryStage();
        inventoryBar = new InventoryBar();
        timeBar = new TimeBar();

        // timeBar:
        Table timeTable = new Table();
        timeTable.setFillParent(true);
        timeTable.top().right().padTop(10).padRight(10);
        timeTable.add(timeBar.getGroup());


        // add processors
        Gdx.input.setInputProcessor(new InputMultiplexer(
                inventoryStage,
                cookingStage,
                blackSmithStage,
                carpentersStage,
                fishShopStage,
                jojaMartStage,
                marniesStage,
                pierresStage,
                stardropStage,
                shippingBin,
                stage,
                this
        ));

        // inventory bar:
        Stack inventoryStack = new Stack();
        inventoryStack.setFillParent(true);
        Table mainLayout = new Table();
        mainLayout.setFillParent(true);
        ScrollPane inventoryScrollPane = inventoryBar.getInventoryScrollPane();
        mainLayout.left();
        mainLayout.add(inventoryScrollPane)
                .width(150)
                .height(800)
                .pad(0)
                .left();
        inventoryStack.add(mainLayout);


        // add to stackBar:
        stackBar.addActor(dialogTable);
        //stackBar.addActor(controllerTable);
        stackBar.addActor(timeTable);
        stackBar.addActor(inventoryStack);

        // add to stage:
        // animal:
        //animalListWindow = new MyAnimalWindow(StardewValley.getSkin());
        //stage.addActor(animalListWindow);
        //animalListWindow.setVisible(false);
        miniGameWindow = new MiniGameWindow(StardewValley.getSkin());
        stage.addActor(miniGameWindow);
        miniGameWindow.setVisible(false);

        scoreboardWindow = new ScoreboardWindow(StardewValley.getSkin());
        stage.addActor(scoreboardWindow);
        scoreboardWindow.setVisible(false);

        // weather layers:
        setWeatherLayerToStage(App.getGame().getCurrentTime().getWeather());
        stage.addActor(nightOverlay);
        stage.addActor(stackBar);
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            Coordinate cr = App.getGame().getMap().getCurrentRegionCoordinate();
            currentMap = mapRenderers[cr.getX()][cr.getY()];

            ScreenUtils.clear(0, 0, 0, 1);


            // update camera, controller table
            updateCamera();

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // render static map, player(+ dynamic layer):
            SpriteBatch batch = StardewValley.getBatch();
            currentMap.renderBeforePlayer(camera);
            currentMap.renderDynamicBelowLayer(camera);
            batch.begin();
            dynamicMapLayerRender.renderGround();
            if (cr.getX() == 0 && cr.getY() == 0) {
                cat.update(delta);
                cat.render(batch);
            }
            controller.updatePlayer();
            batch.end();
            currentMap.renderDynamicAboveLayer(camera);
            currentMap.renderAfterPlayer(camera);

            // update game: inventory, food:
            controller.updateGame();

            // update time:
            controller.updateTime(delta);
            updateNightOverlay();

            //updateControllerTable();


            // update weather:
            if (currentWeatherLayer != null)
                currentWeatherLayer.update(delta);
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void updateControllerTable() {
        Skin skin = StardewValley.getSkin();

        Coordinate cor = App.getGame().getCurrentPlayer().getCoordinate();
        Player player = App.getGame().getCurrentPlayer();
        DateAndTime time = App.getGame().getCurrentTime();

        controllerTable.clear();
        controllerTable.setFillParent(true);
        controllerTable.top().left();
        controllerTable.add(new Label("Player: (" + cor.getX() + ", " + cor.getY() + ")    " ,skin));
        controllerTable.add(new Label("PlayerLibGdx: (" + player.getXLibGdx() + ", " + player.getYLibGdx() + ")    " ,skin));
        //controllerTable.add(new Label("LibGdx: (" + player).getXLibGdx() + ", " + App.getGame().getCurrentPlayer().getYLibGdx() + ")" ,skin));
        //controllerTable.add(new Label("Zoom: " + camera.zoom + "    ",skin));
        controllerTable.add(new Label("Energy: " + player.getEnergy() + "    ", skin));
        controllerTable.add(new Label("Max Energy: " + player.getMaxEnergy() + "    ", skin));
        //controllerTable.add(new Label("Season: " + time.getSeason() + "    ", skin));
        //controllerTable.add(new Label("Weather: " + time.getWeather() + "    ", skin));
        controllerTable.row();
//        controllerTable.add(new Label("TileInfo: " + App.getGame().getTile(cor).toString() + "    ", skin));
        for (Player p : App.getGame().getPlayers())
            controllerTable.add(new Label("Player: (" + p.getCoordinate().getX() + ", " + p.getCoordinate().getY() + ")    " ,skin));

    }

    public void updateCamera() {
        float camHalfWidth = (camera.viewportWidth * camera.zoom) / 2f;
        float camHalfHeight = (camera.viewportHeight * camera.zoom) / 2f;


        float playerX = App.getGame().getCurrentPlayer().getXLibGdx();
        float playerY = App.getGame().getCurrentPlayer().getYLibGdx();

        float scale = App.getGame().getPlayerScale();
        float spriteWidth = 16 * scale;
        float spriteHeight = 32 * scale;

        float centerX = playerX + spriteWidth / 2f;
        float centerY = playerY + spriteHeight / 2f;

        int mapWidth = currentMap.getWidthPixels();
        int mapHeight = currentMap.getHeightPixels();

        centerX = MathUtils.clamp(centerX, camHalfWidth, mapWidth - camHalfWidth);
        centerY = MathUtils.clamp(centerY, camHalfHeight, mapHeight - camHalfHeight);

        camera.position.set(centerX, centerY, 0);
        camera.update();

        StardewValley.getBatch().setProjectionMatrix(camera.combined);
    }

    public void setWeatherLayerToStage(Weather weather) {
        WeatherLayer weatherLayer = getWeatherLayer(weather);

        if (currentWeatherLayer != null) currentWeatherLayer.remove();

        currentWeatherLayer = weatherLayer;

        if (currentWeatherLayer != null) stage.addActor(currentWeatherLayer);
    }

    private WeatherLayer getWeatherLayer(Weather weather) {
        return switch (weather) {
            case Snow -> new SnowLayer(2.5f);
            case Storm -> new RainLayer(4f, true);
            case Rain -> new RainLayer(4f, false);
            default -> null;
        };
    }

    public void showPauseDialog() {
        Dialog pauseDialog = new Dialog("Pause", StardewValley.getSkin()) {
            @Override
            protected void result(Object object) {
                if (object instanceof String) {
                    switch ((String) object) {
                        case "resume":
                            paused = false;
                            break;
                        case "save":
                            GameMenuController.exitGame();
                            StardewValley.getGame().setScreen(new MainMenuScreen());
                            break;
                        case "exit":
                            dispose();
                            Gdx.app.exit();
                            break;
                    }
                }
            }
        };

        Table table = getTableDialog();
        pauseDialog.getContentTable().add(table).expand().padLeft(50).padRight(50).center().row();


        Table buttonTable = pauseDialog.getButtonTable();
        buttonTable.defaults().space(10).padTop(25);

        pauseDialog.button("Resume", "resume").row();
        pauseDialog.button("Save", "save").row();
        pauseDialog.button("Exit", "exit");

        pauseDialog.setMovable(false);
        pauseDialog.setModal(true);
        pauseDialog.show(stage);
    }

    private Table getTableDialog() {
        final Label moveUp, moveDown, moveLeft, moveRight;
        final Label pauseGame, aynazCheat, nafisehCheat, animalList, miniGame, scoreBoard, inventory, refrigerator, shippingBin;
        final Label moveUpButton, moveDownButton, moveLeftButton, moveRightButton;
        final Label pauseGameButton, aynazCheatButton, nafisehCheatButton, animalListButton, miniGameButton, scoreBoardButton, inventoryButton, refrigeratorButton, shippingBinButton;

        Skin skin = StardewValley.getSkin();

        moveUp       = new Label("Up", skin, "Bold");
        moveDown     = new Label("Down", skin, "Bold");
        moveLeft     = new Label("Left", skin, "Bold");
        moveRight    = new Label("Right", skin, "Bold");

        pauseGame    = new Label("Pause", skin, "Bold");
        aynazCheat   = new Label("Aynaz Cheat", skin, "Bold");
        nafisehCheat = new Label("Nafiseh Cheat", skin, "Bold");
        //animalList   = new Label("Animal List", skin, "Bold");
        miniGame     = new Label("Mini Game", skin, "Bold");
        scoreBoard   = new Label("Scoreboard", skin, "Bold");
        inventory    = new Label("Inventory", skin, "Bold");
        refrigerator = new Label("Refrigerator", skin, "Bold");
        shippingBin  = new Label("Shipping Bin", skin, "Bold");

        moveUpButton       = new Label(Input.Keys.toString(App.getKeyManager().getMoveUp()), skin);
        moveDownButton     = new Label(Input.Keys.toString(App.getKeyManager().getMoveDown()), skin);
        moveLeftButton     = new Label(Input.Keys.toString(App.getKeyManager().getMoveLeft()), skin);
        moveRightButton    = new Label(Input.Keys.toString(App.getKeyManager().getMoveRight()), skin);

        pauseGameButton    = new Label(Input.Keys.toString(App.getKeyManager().getPauseGame()), skin);
        aynazCheatButton   = new Label(Input.Keys.toString(App.getKeyManager().getAynazCheat()), skin);
        nafisehCheatButton = new Label(Input.Keys.toString(App.getKeyManager().getNafisehCheat()), skin);
        //animalListButton   = new Label(Input.Keys.toString(App.getKeyManager().getAnimalList()), skin);
        miniGameButton     = new Label(Input.Keys.toString(App.getKeyManager().getMiniGame()), skin);
        scoreBoardButton   = new Label(Input.Keys.toString(App.getKeyManager().getScoreboard()), skin);
        inventoryButton    = new Label(Input.Keys.toString(App.getKeyManager().getOpenInventory()), skin);
        refrigeratorButton = new Label(Input.Keys.toString(App.getKeyManager().getOpenRefrigerator()), skin);
        shippingBinButton  = new Label(Input.Keys.toString(App.getKeyManager().getOpenShippingBin()), skin);

        Table table = new Table();

        table.add(moveUp);       table.add(moveUpButton).width(300).pad(10);
        table.add(pauseGame);    table.add(pauseGameButton).width(300).pad(10).row();

        table.add(moveDown);     table.add(moveDownButton).width(300).pad(10);
        table.add(inventory);    table.add(inventoryButton).width(300).pad(10).row();

        table.add(moveLeft);     table.add(moveLeftButton).width(300).pad(10);
        table.add(refrigerator); table.add(refrigeratorButton).width(300).pad(10).row();

        table.add(moveRight);    table.add(moveRightButton).width(300).pad(10);
        table.add(shippingBin);  table.add(shippingBinButton).width(300).pad(10).row();

        table.add(aynazCheat);   table.add(aynazCheatButton).width(300).pad(10);
        table.add(miniGame); table.add(miniGameButton).width(300).pad(10).row();

        //table.add(animalList);   table.add(animalListButton).width(300).pad(10);
        table.add(nafisehCheat);     table.add(nafisehCheatButton).width(300).pad(10);
        table.add(scoreBoard);   table.add(scoreBoardButton).width(300).pad(10).row();

        return table;
    }


    public void showGoodNightOverLayer(Runnable onFinished) {
        Stack overlay = new Stack();
        overlay.setFillParent(true);

        int rand = MathUtils.random(1, 3);
        Texture texture = new Texture(Gdx.files.internal("etc/goodNight/goodNight" + rand + ".png"));
        Image background = new Image(texture);

        Texture texture2 = new Texture(Gdx.files.internal("etc/goodNight/matn.png"));
        Image tashakor = new Image(texture2);

        background.setFillParent(true);

        Table table = new Table();
        table.setFillParent(true);

        overlay.add(background);
        overlay.add(table);

        tashakor.setScaling(Scaling.none);
        tashakor.setSize(texture2.getWidth(), texture2.getHeight());
        tashakor.setPosition(0, 40f);

        Container<Image> container = new Container<>(tashakor);
        container.align(Align.bottomLeft);
        container.padBottom(40f);
        container.setPosition(-tashakor.getWidth(), 40f);

        overlay.addActor(container);


        stackBar.setVisible(false);
        stage.addActor(overlay);

        overlay.getColor().a = 0f;
        overlay.addAction(Actions.sequence(
                Actions.fadeIn(2f),
                Actions.run(() -> {
                    float targetX = stage.getWidth();
                    tashakor.addAction(Actions.sequence(
                            Actions.moveTo(targetX, 40f, 7f, Interpolation.linear),
                            Actions.removeActor()
                    ));
                }),
                Actions.delay(6f),
                Actions.fadeOut(2f),
                Actions.run(() -> {
                    overlay.remove();
                    stackBar.setVisible(true);
                    texture.dispose();
                    texture2.dispose();
                    if (onFinished != null) {
                        onFinished.run();
                    }
                })
        ));
    }


    private void setFullMap() {
        Pixmap basePixmap = new Pixmap(Gdx.files.internal("etc/mapImages/Map.png"));
        Pixmap combined = new Pixmap(basePixmap.getWidth(), basePixmap.getHeight(), Pixmap.Format.RGBA8888);
        combined.drawPixmap(basePixmap, 0, 0);
        basePixmap.dispose();

        for (int i = 0; i < 4; i++) {
            Pixmap farm = new Pixmap(Gdx.files.internal("etc/mapImages/Farm" + farmSelections[i] + ".png"));
            int x = (i == 1 || i == 2) ? combined.getWidth() - farm.getWidth() : 0;
            int y = (i >= 2) ? combined.getHeight() - farm.getHeight() : 0;
            combined.drawPixmap(farm, x, y);
            farm.dispose();
        }

        Texture finalTexture = new Texture(combined);
        fullMap = new Image(finalTexture);
        combined.dispose();
    }

    public static Image getFullMap() {
        return fullMap;
    }

    public void updateSeasonMap(String season) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mapRenderers[i][j].applySeasonTileset(season);
            }
        }
    }

    public void updateNightOverlay() {
        DateAndTime time = App.getGame().getCurrentTime();
        int hour = time.getHour();
        int minute = time.getMinute();

        float alpha;
        boolean isRainy = time.getWeather().equals(Weather.Rain) || time.getWeather().equals(Weather.Storm);

        if (isRainy) {
            alpha = getAlpha(hour, minute, 9);
        } else {
            alpha = getAlpha(hour, minute, 18);
        }

        nightOverlay.getColor().a = alpha;
        nightOverlay.setColor(nightOverlay.getColor());
    }

    private float getAlpha(int hour, int minute, int start) {
        int minutesSinceStart = (hour - start) * 60 + minute;

        if (minutesSinceStart < 0) return 0f;

        int totalNightMinutes = (24 - start) * 60;
        float progress = Math.min(1f, minutesSinceStart / (float) totalNightMinutes);
        float maxAlpha = 0.7f;

        return progress * maxAlpha;
    }

    @Override
    public void resize(int width, int height) {

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
        for (TiledMapRendererHelper[] tiledMapRenderers: mapRenderers) {
            for (TiledMapRendererHelper tmp: tiledMapRenderers) {
                tmp.dispose();
            }
        }

        SeasonTextureManager.disposeAll();
        ItemTextureBank.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //controller.getPlayerController().getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //controller.getPlayerController().getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (Gdx.input.isKeyPressed(App.getKeyManager().getZoom())) {
            float zoomSpeed = 0.05f;
            camera.zoom += amountY * zoomSpeed;
            camera.zoom = MathUtils.clamp(camera.zoom, 0.05f, 0.6f);
            return true;
        }
        return false;
    }

    //getter and setters:

    public InventoryStage getInventoryStage() {
        return inventoryStage;
    }

    public InventoryBar getInventoryBar() {
        return inventoryBar;
    }

    public CookingStage getCookingStage() {
        return cookingStage;
    }

    public BlackSmithMenu getBlackSmithStage() {
        return blackSmithStage;
    }

    public CarpentersMenu getCarpentersStage() {
        return carpentersStage;
    }

    public FishShopMenu getFishShopStage() {
        return fishShopStage;
    }

    public JojaMartMenu getJojaMartStage() {
        return jojaMartStage;
    }

    public MarniesMenu getMarniesStage() {
        return marniesStage;
    }

    public PierresMenu getPierresStage() {
        return pierresStage;
    }

    public StardropMenu getStardropStage() {
        return stardropStage;
    }

    public ShippingBin getShippingBin() {
        return shippingBin;
    }

    public TimeBar getTimeBar() {
        return timeBar;
    }

    public Stage getStage() {
        return stage;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }

    private void showError(String msg) {
        final Window errorWindow = new Window("", StardewValley.getSkin(), "Letter");
        errorWindow.setMovable(false);
        errorWindow.setKeepWithinStage(true);
        errorWindow.add(new Label(msg, StardewValley.getSkin()));
        errorWindow.setSize(700, 90);
        errorWindow.setPosition(600, 170, Align.center);
        errorWindow.pack();

        stage.addActor(errorWindow);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                errorWindow.remove();
            }
        }, 5);
    }

    public Result getCurrentResult() {
        return currentResult;
    }

    public boolean setCurrentResult(Result currentResult) {
        this.currentResult = currentResult;
        if (currentResult != null && !currentResult.isSuccessful()) {
            showError(currentResult.message());
            return false;
        }
        return true;
    }

    public MiniGameWindow getMiniGameWindow() {
        return miniGameWindow;
    }

    public ScoreboardWindow getScoreboardWindow() {
        return scoreboardWindow;
    }
}