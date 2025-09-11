package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.LoginMenuController;
import io.Ap.StardewValley.Common.Model.Command.SecurityQuestion;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.StardewValley;

import java.util.ArrayList;
import java.util.List;

public class SecurityQuestionScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;
    private final Image backgroundImage;

    private final List<String> questions = new ArrayList<>();
    String username;

    public SecurityQuestionScreen(String username) {
        this.username = username;
        for (SecurityQuestion q : SecurityQuestion.values()) {
            questions.add(q.getQuestion());
        }

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = StardewValley.getSkin();
        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_start.png"));
//        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_night.png"));
        backgroundImage = new Image(backgroundTexture);
    }

    @Override
    public void show() {
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);
        stack.add(backgroundImage);

        Window window = new Window("", skin);
        Label titleLabel = new Label("Security Question", skin, "Bold");
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
        contentTable.top().pad(20);
        contentTable.row().padTop(50);

        int index = 1;
        for (String q : questions) {
            if (index < 7) {
                Label questionLabel = new Label(index + ". " + q, skin);
                questionLabel.setWrap(true);
                contentTable.add(questionLabel).width(800).left().padBottom(10);
                contentTable.row();
                index++;
            }
        }

        Label selectLabel = new Label("Question:", skin);
        SelectBox<Integer> questionSelect = new SelectBox<>(skin);
        Integer[] numbers = new Integer[6];
        for (int i = 0; i < 6; i++) numbers[i] = i + 1;
        questionSelect.setItems(numbers);

        Label answerLabel = new Label("Answer:", skin);
        TextField answerField = new TextField("", skin);

        Label reAnswerLabel = new Label("Re-Answer:", skin);
        TextField reAnswerField = new TextField("", skin);

        Table inputTable = new Table();
        inputTable.add(selectLabel).left().pad(5);
        inputTable.add(questionSelect).width(130).pad(5);
        inputTable.row();
        inputTable.add(answerLabel).left().pad(5);
        inputTable.add(answerField).width(300).pad(5);
        inputTable.row();
        inputTable.add(reAnswerLabel).left().pad(5);
        inputTable.add(reAnswerField).width(300).pad(5);

        TextButton submitButton = new TextButton("Submit", skin);
        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Result result;
                try {
                    result = LoginMenuController.securityQuestionThroughScreen(username, questionSelect.getSelected(),
                            answerField.getText(), reAnswerField.getText());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (result.isSuccessful()) {
//                    StardewValley.getGame().setScreen(new StartMenuScreen());
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

        Table buttonTable = new Table();
        buttonTable.add(submitButton).width(150).height(90).padTop(20);

        contentTable.row();
        contentTable.add(inputTable).colspan(1).padTop(30);
        contentTable.row();
        contentTable.add(buttonTable);

//        ScrollPane scrollPane = new ScrollPane(contentTable, skin);
//        scrollPane.setFadeScrollBars(false);
//        scrollPane.setScrollingDisabled(true, false);
//
//        window.add(scrollPane).expand().fill();
        window.add(contentTable).expand().center().row();

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

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
