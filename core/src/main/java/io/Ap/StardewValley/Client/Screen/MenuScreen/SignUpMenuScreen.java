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


public class SignUpMenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;
    private final Image backgroundImage;

    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPassField;
    private TextField nicknameField;
    private TextField emailField;
    boolean isPassRandom = false;

    public SignUpMenuScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = StardewValley.getSkin();
        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_start.png"));
//        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_night.png"));
        backgroundImage = new Image(backgroundTexture);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        confirmPassField = new TextField("", skin);
        nicknameField = new TextField("", skin);
        emailField = new TextField("", skin);
    }

    @Override
    public void show() {
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);
        stack.add(backgroundImage);

        Window window = new Window("", skin);
        Label titleLabel = new Label("Register", skin, "Bold");
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
//        passwordField.setPasswordCharacter('*');
//        passwordField.setPasswordMode(true);
        leftColumn.add(passwordField).width(350).pad(5,5,5,100);

        leftColumn.row();
        leftColumn.add(new Label("Confirm Pass:", skin)).left().pad(5);
        leftColumn.row();
        leftColumn.add(confirmPassField).width(350).pad(5,5,5,100);

        leftColumn.row();
        leftColumn.add(new Label("Gender:", skin)).left().pad(5);
        leftColumn.row();


        SelectBox<String> genderBox = new SelectBox<>(skin);
        genderBox.setItems("female", "male", "prefer not to answer");
        leftColumn.add(genderBox).width(350).pad(5,5,5,100);


        // right column:
        Table rightColumn = new Table();
        rightColumn.add(new Label("Nickname:", skin)).left().pad(5);
        rightColumn.row();
        rightColumn.add(nicknameField).width(350).pad(5);
        rightColumn.row();

        rightColumn.add(new Label(" ", skin)).pad(5);
        rightColumn.row();


        // new table for dice and switch:
        Table diceRow = new Table();

        ImageButton diceButton = new ImageButton(skin);
        diceButton.setTransform(true);
        diceButton.scaleBy(1f);

        diceButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LoginMenuController.setRandomPass(SignUpMenuScreen.this);

            }
        });

        diceRow.add(diceButton).width(50).height(50).padRight(300).padTop(35);

        CheckBox toggleSwitch = new CheckBox(" ", skin);
        toggleSwitch.setChecked(false);

        toggleSwitch.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (toggleSwitch.isChecked()) {
                    LoginMenuController.setRandomPass(SignUpMenuScreen.this);
                    isPassRandom = true;
                }
                else {
                    if (isPassRandom) {
                        passwordField.setMessageText("");
                        confirmPassField.setMessageText("");
                        isPassRandom = false;
                    }
                }
            }
        });

//        diceRow.add(toggleSwitch).width(100).height(50);


//        passwordField.setTextFieldListener((textField, c) -> {
//            if (isPassRandom) {
//                isPassRandom = false;
//                toggleSwitch.setChecked(false);
//            }
//        });

        rightColumn.add(diceRow).pad(0);
        rightColumn.row();


        rightColumn.add(new Label("Email:", skin)).left().pad(0,5,5,5);
        rightColumn.row();
        rightColumn.add(emailField).width(350).pad(5);


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
                    result = LoginMenuController.registerThroughScreen(usernameField.getText(), passwordField.getText(),
                            confirmPassField.getText(), nicknameField.getText(), emailField.getText(), genderBox.getSelected());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (result.isSuccessful()) {
                    StardewValley.getGame().setScreen(new SecurityQuestionScreen(usernameField.getText()));

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
        buttonRow.add(nextButton).width(150).height(90).width(150).pad(10);


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

    public TextField getConfirmPassField() {
        return confirmPassField;
    }

    public TextField getNicknameField() {
        return nicknameField;
    }

    public TextField getEmailField() {
        return emailField;
    }
}
