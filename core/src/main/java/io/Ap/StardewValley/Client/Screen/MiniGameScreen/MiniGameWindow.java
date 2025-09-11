package io.Ap.StardewValley.Client.Screen.MiniGameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import io.Ap.StardewValley.Client.Controller.GameScreenController;
import io.Ap.StardewValley.Common.Model.Animals.Fish;
import io.Ap.StardewValley.Common.Model.Animals.FishType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;

import java.util.Random;

public class MiniGameWindow extends Window {
    private FishType fishType;
    private TextureRegion fishImage;
    private final int maxPoolWidth = 930;
    private final int minPoolWidth = 384;
    private final int maxPoolHeight = 197 + 246 - 36;
    private final int minPoolHeight = 197;

    private boolean isPrefect = true;
    private float timeInGreen = 0f;

    private Group buttonsGroup;

    public MiniGameWindow(Skin skin) {
        super("", skin, "MiniGame");

        setSize(384 * 3, 256 * 3);
        setMovable(false);
        setResizable(false);
        setModal(false);
        setKeepWithinStage(true);
        float windowX = (Gdx.graphics.getWidth() - getWidth()) / 2f;
        float windowY = (Gdx.graphics.getHeight() - getHeight()) / 2f;
        setPosition(windowX, windowY);


        setFishType();

        buttonsGroup = new Group();
        addActor(buttonsGroup);

        Image greenBar = new Image(new Texture(Gdx.files.internal("animal/GreenMiniGame.png")));
        greenBar.setPosition(minPoolWidth, minPoolHeight);
        buttonsGroup.addActor(greenBar);

        ImageButton right = new ImageButton(skin, "Right");
        right.setPosition(1035, 264);
        right.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float newX = greenBar.getX() + 10;
                if (newX + greenBar.getWidth() <= maxPoolWidth) {
                    greenBar.setX(newX);
                } else {
                    greenBar.setX(maxPoolWidth - greenBar.getWidth());
                }            }
        });
        buttonsGroup.addActor(right);

        ImageButton left = new ImageButton(skin, "Left");
        left.setPosition(264, 264);
        left.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float newX = greenBar.getX() - 10;
                if (newX >= minPoolWidth) {
                    greenBar.setX(newX);
                } else {
                    greenBar.setX(minPoolWidth);
                }            }
        });
        buttonsGroup.addActor(left);

        CheckBox myCheckBox = new CheckBox(" Snoar Bobber", skin);
        myCheckBox.setPosition(370, 500);
        buttonsGroup.addActor(myCheckBox);

        TextureRegion unknownImage = ItemTextureBank.getTexture("unknown");
        Image myImage = new Image(unknownImage);
        myImage.setPosition(370 + myCheckBox.getWidth() + 5, 500);
        buttonsGroup.addActor(myImage);

        myCheckBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (myCheckBox.isChecked()) {
                    myImage.setDrawable(new TextureRegionDrawable(fishImage));
                } else {
                    myImage.setDrawable(new TextureRegionDrawable(unknownImage));
                }
            }
        });

        TextButton exitButton = new TextButton("exit", skin);
        exitButton.setPosition(560, 60);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreenController.setIsMiniGameVisible(false);
            }
        });
        buttonsGroup.addActor(exitButton);

        Label statusLabel = new Label("Perfect: true, Time: 0.0", skin, "WhiteText");
        statusLabel.setColor(new Color(0x00345f));
        statusLabel.setPosition(410, 500 + 120);
        buttonsGroup.addActor(statusLabel);

        // add fish and its move:
        Image fish = new Image(new Texture(Gdx.files.internal("animal/GameFish.png")));
        fish.setScale(3f);
        buttonsGroup.addActor(fish);

        Random random = new Random();
        float fishX = minPoolWidth + random.nextFloat() * (maxPoolWidth - minPoolWidth - fish.getWidth());
        float fishY = minPoolHeight + random.nextFloat() * (maxPoolHeight - minPoolHeight - fish.getHeight());
        fish.setPosition(fishX, fishY);

        moveType currentMove = moveType.values()[random.nextInt(moveType.values().length)];

        Gdx.app.postRunnable(() -> {
            Timer.schedule(new Timer.Task() {
                float vx = 4 + random.nextFloat() * 3;
                float vy = 4 + random.nextFloat() * 3;
                boolean dirX = random.nextBoolean();
                boolean dirY = random.nextBoolean();

                @Override
                public void run() {
                    float x = fish.getX();
                    float y = fish.getY();

                    switch (currentMove) {
                        case Mixed:
                            x += dirX ? vx : -vx;
                            y += dirY ? vy : -vy;
                            break;
                        case Smooth:
                            x += dirX ? 4 : -4;
                            break;
                        case Sinker:
                            x += dirX ? vx : -vx;
                            y -= 4;
                            break;
                        case Floater:
                            x += dirX ? vx : -vx;
                            y += 4;
                            break;
                        case Dart:
                            x += dirX ? 5 : -5;
                            y += dirY ? 5 : -5;
                            break;
                    }

                    if (x < minPoolWidth) { x = minPoolWidth; dirX = true; }
                    if (x + fish.getWidth() > maxPoolWidth) { x = maxPoolWidth - fish.getWidth(); dirX = false; }
                    if (y < minPoolHeight) { y = minPoolHeight; dirY = true; }
                    if (y + fish.getHeight() > maxPoolHeight) { y = maxPoolHeight - fish.getHeight(); dirY = false; }

                    fish.setPosition(x, y);

                    boolean inGreen = x + fish.getWidth()/2 >= greenBar.getX() &&
                            x + fish.getWidth()/2 <= greenBar.getX() + greenBar.getWidth();
                    if (!inGreen) {
                        isPrefect = false;
                        timeInGreen = Math.max(timeInGreen - 0.1f, 0f);
                    } else {
                        timeInGreen += 0.1f;
                        if (timeInGreen >= 30f) {
                            win();
                        }
                    }
                    statusLabel.setText("Perfect: " + isPrefect + ", Time: " + String.format("%.1f", timeInGreen));

                }
            }, 0, 0.1f);
        });


    }

    private void win() {
        Player player = App.getGame().getCurrentPlayer();
        double quality = (this.isPrefect) ? 0.9d : 0.5d;
        int skill = (this.isPrefect) ? 10 : 5;
        player.getInventory().addItem(new Fish(fishType, quality));
        player.addAbility(Skill.Fishing, skill);
        setFishType();
        timeInGreen = 0f;
        this.isPrefect = true;
    }

    private void setFishType() {
        FishType[] values = FishType.values();
        Random random = new Random();
        fishType = values[random.nextInt(values.length)];

        fishImage = ItemTextureBank.getTexture(fishType.getName());
    }
}
enum moveType {
    Mixed,
    Smooth,
    Sinker,
    Floater,
    Dart
}