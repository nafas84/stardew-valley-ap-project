package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.ProfileMenuController;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.StardewValley;

public class ChangePasswordScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;
    private final Image backgroundImage;

    private TextField oldPassField;
    private TextField newPassField;

    public ChangePasswordScreen () {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = StardewValley.getSkin();
        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_start.png"));
//        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_night.png"));
        backgroundImage = new Image(backgroundTexture);


        oldPassField = new TextField("", skin);
        newPassField = new TextField("", skin);
    }

    @Override
    public void show() {
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);
        stack.add(backgroundImage);

        Window window = new Window("", skin);
        Label titleLabel = new Label("Change Password", skin, "Bold");
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

        contentTable.add(new Label("old password:", skin)).right().pad(15, 5, 15, 55);
        contentTable.add(oldPassField).width(350).left().pad(15, 45, 15, 5);
        contentTable.row();
        contentTable.add(new Label("new password:", skin)).right().pad(15, 5, 15, 55);
        contentTable.add(newPassField).width(350).left().pad(15, 45, 15, 5);
        contentTable.row();



        ImageButton diceButton = new ImageButton(skin);
        diceButton.setTransform(true);
        diceButton.scaleBy(0.9f);

        diceButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ProfileMenuController.setRandomPass(ChangePasswordScreen.this);
            }
        });

        contentTable.add(new Label("", skin)).left().pad(50, 5,5,5);
        contentTable.add(diceButton).pad(50, 0,5,5);




        // back and next buttons:
        Table buttonRow = new Table();
        TextButton backButton = new TextButton("Back", skin);
        TextButton submitButton = new TextButton("Submit", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new ProfileMenuScreen());
            }
        });

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result;
                try {
                    result = ProfileMenuController.changePasswordThroughScreen(newPassField.getText(), oldPassField.getText());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (result.isSuccessful()) {
                    StardewValley.getGame().setScreen(new ProfileMenuScreen());
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
        buttonRow.add(submitButton).width(150).height(90).pad(10);


        window.add(contentTable).expand().center().row();
        window.add(buttonRow).padTop(20);

//        contentTable.setDebug(true);
//        buttonRow.setDebug(true);


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

    public TextField getNewPassField() {
        return newPassField;
    }
}

