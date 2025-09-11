package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.LoginMenuController;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.StardewValley;


public class LoginMenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;
    private final Image backgroundImage;

    private TextField usernameField;
    private TextField passwordField;
    private CheckBox toggleSwitch;

    boolean stayLoggedIn;

    public LoginMenuScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = StardewValley.getSkin();
        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_start.png"));
//        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_night.png"));
        backgroundImage = new Image(backgroundTexture);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        toggleSwitch = new CheckBox("", skin);
        toggleSwitch.setChecked(false);

        stayLoggedIn = false;
    }

    public LoginMenuScreen(String username, String password, boolean stayLoggedIn) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = StardewValley.getSkin();
        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_start.png"));
        backgroundImage = new Image(backgroundTexture);

        usernameField = new TextField(username, skin);
        passwordField = new TextField(password, skin);
        toggleSwitch = new CheckBox("", skin);
        toggleSwitch.setChecked(stayLoggedIn);

        this.stayLoggedIn = stayLoggedIn;
    }

    public LoginMenuScreen(String username, boolean stayLoggedIn) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = StardewValley.getSkin();
        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_start.png"));
        backgroundImage = new Image(backgroundTexture);

        usernameField = new TextField(username, skin);
        passwordField = new TextField("", skin);
        toggleSwitch = new CheckBox("", skin);
        toggleSwitch.setChecked(stayLoggedIn);

        this.stayLoggedIn = stayLoggedIn;
    }

    @Override
    public void show() {
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);
        stack.add(backgroundImage);

        Window window = new Window("", skin);
        Label titleLabel = new Label("Login", skin, "Bold");
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


        // left column
        Table leftColumn = new Table();
        leftColumn.add(new Label("Username:", skin)).left().pad(5);
        leftColumn.row();
        leftColumn.add(usernameField).width(350).pad(5,5,5,100);

        leftColumn.row();
        leftColumn.add(new Label("Password:", skin)).left().pad(5);
        leftColumn.row();
        leftColumn.add(passwordField).width(350).pad(5,5,5,100);

        leftColumn.row();




        // right column:

        toggleSwitch.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (toggleSwitch.isChecked()) {
                    stayLoggedIn = true;
                }
                else {
                    stayLoggedIn = false;
                }
            }
        });

        TextButton forgetPassButton = new TextButton("forget password", skin);

        forgetPassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;
                try {
                    result = LoginMenuController.forgetPasswordThroughScreen(usernameField.getText());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (result.isSuccessful()) {
                    StardewValley.getGame().setScreen(new ForgetPasswordScreen(usernameField.getText(), stayLoggedIn, result.toString()));
                }
                else {
                    String error = result.toString();

                    Window errorWindow = new Window("", skin, "Letter");
                    errorWindow.setMovable(false);
                    errorWindow.setResizable(false);
                    errorWindow.setSize(700, 100);
                    errorWindow.setPosition(80, stage.getHeight() - errorWindow.getHeight() - 100);

                    Label errorLabel = new Label(error, skin, "WhiteText");
                    errorLabel.setColor(new Color(0.7f, 0f, 0f, 1f));
                    errorLabel.setWrap(true);
                    errorLabel.setAlignment(Align.center);
                    errorWindow.add(errorLabel).width(660).pad(10);

                    stage.addActor(errorWindow);
                    errorWindow.toFront();

                    Timer.Task autoRemoveTask = new Timer.Task() {
                        @Override
                        public void run() {
                            errorWindow.remove();
                        }
                    };
                    Timer.schedule(autoRemoveTask, 5);

                    InputListener clickAnywhereListener = new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            errorWindow.remove();
                            autoRemoveTask.cancel();
                            stage.removeListener(this);
                            return true;
                        }
                    };
                    stage.addListener(clickAnywhereListener);
                }
            }
        });

        Table rightColumn = new Table();

        rightColumn.add(new Label("", skin)).left().pad(5);
        rightColumn.row();

        rightColumn.add(new Label("Stay logged in:", skin)).left().pad(5);
        rightColumn.add(toggleSwitch).width(10).pad(5);
        rightColumn.row();

        rightColumn.add(new Label("", skin)).left().pad(5);
        rightColumn.row();

        rightColumn.add(forgetPassButton).left().pad(13,5,5,5).height(90).width(300);
        rightColumn.row();




        contentTable.add(leftColumn).top().pad(10);
        contentTable.add(rightColumn).top().pad(10);

        // back and next buttons:
        Table buttonRow = new Table();
        TextButton backButton = new TextButton("Back", skin);
        TextButton nextButton = new TextButton("Next", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new StartMenuScreen());
            }
        });

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;
                try {
                    result = LoginMenuController.loginThroughScreen(usernameField.getText(), passwordField.getText(), stayLoggedIn);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (result.isSuccessful()) {
                    StardewValley.getGame().setScreen(new MainMenuScreen());
                }
                else {
                    String error = result.toString();

                    Window errorWindow = new Window("", skin, "Letter");
                    errorWindow.setMovable(false);
                    errorWindow.setResizable(false);
                    errorWindow.setSize(700, 100);
                    errorWindow.setPosition(80, stage.getHeight() - errorWindow.getHeight() - 100);

                    Label errorLabel = new Label(error, skin, "WhiteText");
                    errorLabel.setColor(new Color(0.7f, 0f, 0f, 1f));
                    errorLabel.setWrap(true);
                    errorLabel.setAlignment(Align.center);
                    errorWindow.add(errorLabel).width(660).pad(10);

                    stage.addActor(errorWindow);
                    errorWindow.toFront();

                    Timer.Task autoRemoveTask = new Timer.Task() {
                        @Override
                        public void run() {
                            errorWindow.remove();
                        }
                    };
                    Timer.schedule(autoRemoveTask, 5);

                    InputListener clickAnywhereListener = new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            errorWindow.remove();
                            autoRemoveTask.cancel();
                            stage.removeListener(this);
                            return true;
                        }
                    };
                    stage.addListener(clickAnywhereListener);
                }
            }
        });

        buttonRow.add(backButton).width(150).height(90).pad(10);
        buttonRow.add(nextButton).width(150).height(90).pad(10);


        window.add(contentTable).expand().center().row();
        window.add(buttonRow).padTop(20);

        stage.addActor(window);
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
        backgroundTexture.dispose();
    }


    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

}
