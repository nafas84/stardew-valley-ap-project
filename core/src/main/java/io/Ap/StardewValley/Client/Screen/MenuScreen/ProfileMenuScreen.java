package io.Ap.StardewValley.Client.Screen.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.Ap.StardewValley.Client.Controller.ProfileMenuController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.StardewValley;

import java.io.IOException;

public class ProfileMenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;
    private final Image backgroundImage;

    private TextButton changeUsername;
    private TextButton changeNickname;
    private TextButton changeEmail;
    private TextButton changePassword;
    private ImageButton rightButton;
    private ImageButton leftButton;
    private String avatarPath;
    Image avatarImage;

    private final String toRemove1 = "etc/avatar/";
    private final String toRemove2 = ".png";


    public ProfileMenuScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = StardewValley.getSkin();
        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_start.png"));
//        backgroundTexture = new Texture(Gdx.files.internal("etc/menu/background_night.png"));
        backgroundImage = new Image(backgroundTexture);

        changeUsername = new TextButton("change username", skin);
        changeNickname = new TextButton("change nickname", skin);
        changeEmail = new TextButton("change email", skin);
        changePassword = new TextButton("change password", skin);
        rightButton = new ImageButton(skin, "Right");
        leftButton = new ImageButton(skin, "Left");
        avatarPath = App.getCurrentUser().getAvatarPath();
        avatarImage = new Image(new Texture(Gdx.files.internal(avatarPath)));
    }


    @Override
    public void show() {
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);
        stack.add(backgroundImage);



/*


        //TODO: inventory bar


        // ساخت جدول اصلی که سمت چپ اینونتوری و وسط محتوای پنجره رو بچینه
        Table mainLayout = new Table();
        mainLayout.setFillParent(true);


        InventoryBar inventoryBar = new InventoryBar(); // فرض می‌کنیم کلاس Inventory رو طبق راهنمای قبلی ساختی

        ScrollPane inventoryScrollPane = inventoryBar.getInventoryScrollPane(); // تابع getInventoryScrollPane رو اضافه می‌کنی به کلاس Inventory

        mainLayout.add(inventoryScrollPane).width(130).height(800).pad(50, 100, 50, 0); // سمت چپ نوار
        mainLayout.add().expand(); // جای خالی برای window وسط

        inventoryBar.setSlotImage(0,new Texture("inventory/axe.png"));

        stack.add(mainLayout);



*/




        Window window = new Window("", skin);
        Label titleLabel = new Label("Profile", skin, "Bold");
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
        avatarRow.add(avatarImage).pad(5).size(100, 100);
        avatarRow.add(rightButton).pad(20, 0, 0, 0).height(60).width(120);

        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int index;
                try {
                    index = Integer.parseInt(avatarPath.replace(toRemove1, "").replace(toRemove2, ""));
                    index --;
                    index = ((index % 9) + 9) % 9;

                } catch (Exception e) {
                    index = 0;
                }
//                avatarPath = toRemove1 + index + toRemove2;
//                App.getCurrentUser().setAvatarPath(avatarPath);
//                avatarImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(avatarPath)))));

                try {
                    ProfileMenuController.changeAvatar(index, ProfileMenuScreen.this);
                } catch (IOException e) {}
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int index;
                try {
                    index = Integer.parseInt(avatarPath.replace(toRemove1, "").replace(toRemove2, ""));
                    index ++;
                    index = ((index % 9) + 9) % 9;
                } catch (Exception e) {
                    index = 0;
                }
                try {
                    ProfileMenuController.changeAvatar(index, ProfileMenuScreen.this);
                } catch (IOException e) {}
            }
        });


        // left column
        Table leftColumn = new Table();

        leftColumn.add(avatarRow).center().pad(15, 0, 0, 0);
        leftColumn.row();
        leftColumn.add(new Label("Avatar", skin)).center().pad(5, 0, 30, 0);
        leftColumn.row();
        leftColumn.add(new Label("Username: " + App.getCurrentUser().getUsername(), skin)).center().pad(5);

        leftColumn.row();
        leftColumn.add(new Label("NickName: " + App.getCurrentUser().getNickname(), skin)).center().pad(5);
        leftColumn.row();
        leftColumn.add(new Label("Most coins earned: " + App.getCurrentUser().getMostCoinsEarned(), skin)).center().pad(5);

        leftColumn.row();
        leftColumn.add(new Label("Number of games: " + App.getCurrentUser().getGames(), skin)).center().pad(5);
        leftColumn.row();
        leftColumn.row();




        // right column:
        changeUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new ChangeUsernameScreen());
            }
        });
        changeNickname.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new ChangeNickNameScreen());
            }
        });
        changeEmail.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new ChangeEmailScreen());
            }
        });
        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new ChangePasswordScreen());
            }
        });

        Table rightColumn = new Table();


        rightColumn.add(changeUsername).center().pad(13,100,5,5).height(90).width(300);
        rightColumn.row();
        rightColumn.add(changeNickname).center().pad(13,100,5,5).height(90).width(300);
        rightColumn.row();
        rightColumn.add(changeEmail).center().pad(13,100,5,5).height(90).width(300);
        rightColumn.row();
        rightColumn.add(changePassword).center().pad(13,100,5,5).height(90).width(300);
        rightColumn.row();



        contentTable.add(leftColumn).top().pad(10);
        contentTable.add(rightColumn).top().pad(10);

        // back and next buttons:
        Table buttonRow = new Table();
        TextButton backButton = new TextButton("Back", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StardewValley.getGame().setScreen(new MainMenuScreen());
            }
        });

        buttonRow.add(backButton).width(150).height(90).pad(10);


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



    public TextButton getChangeUsername() {
        return changeUsername;
    }

    public TextButton getChangeNickname() {
        return changeNickname;
    }

    public TextButton getChangeEmail() {
        return changeEmail;
    }

    public TextButton getChangePassword() {
        return changePassword;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Image getAvatarImage() {
        return avatarImage;
    }
}
