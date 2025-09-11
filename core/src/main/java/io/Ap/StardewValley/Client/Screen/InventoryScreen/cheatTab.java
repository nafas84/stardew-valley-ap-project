package io.Ap.StardewValley.Client.Screen.InventoryScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Timer;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlantController;
import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlayerController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Map.TileType;
import io.Ap.StardewValley.Common.Model.Plants.Tree;
import io.Ap.StardewValley.Common.Model.Result;

public class cheatTab extends Window {

    private final TextField stormField;
    private final TextField energyField;
    private final TextField craftInfo;
    private final TextField addItem;
    private final ImageButton timeButton;
    private final CheckBox infinityEnergy;

    public cheatTab(Skin skin) {
        super("", skin);

        this.setSize(1050, 650);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.topLeft);
        this.defaults().pad(10);

        Table mainTable = new Table();
        mainTable.defaults().pad(5).top();

        // left: time, thunder, infinity energy, energy, craftInfo
        Table leftTable = new Table();
        leftTable.defaults().pad(5).left();

        leftTable.add(new Label("Time:", skin)).left();
        timeButton = new ImageButton(skin, "Right");
        leftTable.add(timeButton).width(200).row();

        leftTable.add(new Label("Storm:", skin)).left();
        stormField = new TextField("(x, y)", skin);
        leftTable.add(stormField).width(200).row();

        leftTable.add(new Label("Unlimited Energy:", skin)).left();
        infinityEnergy = new CheckBox("", skin);
        leftTable.add(infinityEnergy).width(200).row();

        leftTable.add(new Label("Energy:", skin)).left();
        energyField = new TextField("", skin);
        leftTable.add(energyField).width(200).row();

        leftTable.add(new Label("Craft Info:", skin)).left();
        craftInfo = new TextField("craftName", skin);
        leftTable.add(craftInfo).width(200).row();

        // right: add item
        Table rightTable = new Table();
        rightTable.defaults().pad(5).left();

        rightTable.add(new Label("Add Item:", skin)).left();
        addItem = new TextField("type-name-count", skin);
        rightTable.add(addItem).width(200).row();

        /*
         * ---------- Add Tables to Main ----------
         */
        TextButton submit = new TextButton("Submit", skin);


        mainTable.add(leftTable).expand().top();
        mainTable.add(rightTable).expand().top();
        mainTable.row();
        mainTable.add(submit).colspan(2).center().height(90).width(300);


        // listener:
        timeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.getGame().getCurrentTime().setHour(23);
                App.getGame().getCurrentTime().setMinute(59);
            }
        });

        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = validateInputs();

                if (result.isSuccessful()) {
                    String message = result.message();

                    Window successWindow = new Window("", skin, "Letter");
                    successWindow.setMovable(false);
                    successWindow.setResizable(false);
                    successWindow.setSize(700, 700);

                    Stage stage = getStage();
                    if (stage == null) return;

                    successWindow.setPosition(80, stage.getHeight() - successWindow.getHeight() - 100);

                    Label successLabel = new Label(message, skin, "WhiteText");
                    successLabel.setColor(new Color(0f, 0.7f, 0f, 1f));
                    successLabel.setWrap(true);
                    successLabel.setAlignment(Align.center);
                    successWindow.add(successLabel).width(660).pad(10);

                    stage.addActor(successWindow);
                    successWindow.toFront();

                    Timer.Task autoRemoveTask = new Timer.Task() {
                        @Override
                        public void run() {
                            successWindow.remove();
                        }
                    };
                    Timer.schedule(autoRemoveTask, 5);

                    InputListener clickAnywhereListener = new InputListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            successWindow.remove();
                            autoRemoveTask.cancel();
                            stage.removeListener(this);
                            return true;
                        }
                    };
                    stage.addListener(clickAnywhereListener);
                } else {
                    String error = result.message();

                    Window errorWindow = new Window("", skin, "Letter");
                    errorWindow.setMovable(false);
                    errorWindow.setResizable(false);
                    errorWindow.setSize(700, 100);

                    Stage stage = getStage();
                    if (stage == null) return;

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

        this.add(mainTable).expand().fill();
    }

    private Result validateInputs() {
        String coordPattern = "^\\(\\s*\\d+\\s*,\\s*\\d+\\s*\\)$";

        Coordinate stormCoordinate = null;
        String craftName = null, energy = null;
        String type = null, name = null, count = null;

        boolean infinityEnergyValue = infinityEnergy.isChecked();

        // Storm
        String stormText = stormField.getText();
        if (!stormText.equals("(x, y)")) {
            if (!stormText.matches(coordPattern)) {
                return new Result(false, "Storm field must be in format (x, y) with numbers");
            }
            String[] parts = stormText.replaceAll("[()\\s]", "").split(",");
            stormCoordinate = new Coordinate(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }

        // Craft Info
        String craftText = craftInfo.getText();
        if (!craftText.equals("craftName")) {
            craftName = craftInfo.getText();
        }

        // Energy
        String energyText = energyField.getText();
        if (!energyText.isEmpty()) {
            if (!energyText.matches("^\\d+$")) {
                return new Result(false, "Energy field must be a number");
            }
            energy = energyText;
        }

        // Add Item
        String addItemText = addItem.getText();
        if (!addItemText.equals("type-name-count")) {
            String[] parts = addItemText.split("-");
            if (parts.length != 3) {
                return new Result(false, "Add Item field must be in format type-name-count");
            }
            type = parts[0];
            name = parts[1];
            count = parts[2];

            if (!count.matches("^\\d+$")) {
                return new Result(false, "count must be a number");
            }
        }

        // check:
        thorEffect(stormCoordinate);
        if (infinityEnergyValue)
            PlayerController.unlimitedEnergy();
        if (energy != null)
            PlayerController.cheatEnergy(energy);


        if (craftName != null) {
            resetFields();
            return PlantController.craftInfo(craftName);
        } else if (type != null) {
            resetFields();
            return PlayerController.cheatItem(type, name, count);
        } else {
            resetFields();
            return new Result(true, "DONE");
        }

    }

    private void thorEffect(Coordinate thorCoordinate) {
        if (thorCoordinate != null) {
            Tile tile = App.getGame().getTile(thorCoordinate);
            if (tile.getType().equals(TileType.Ground)) {
                if (tile.getItem() instanceof Tree tree) {
                    tree.burn();
                } else {
                    tile.setItem(null);
                }
            }
        }
    }

    private void resetFields() {
        stormField.setText("(x, y)");
        craftInfo.setText("craftName");
        energyField.setText("");
        addItem.setText("type-name-count");
    }
}
