package io.Ap.StardewValley.Client.Screen.MultiplayerScreen;

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
import io.Ap.StardewValley.Client.Screen.ItemScreen.ItemTextureBank;
import io.Ap.StardewValley.Common.Model.Animals.Fish;
import io.Ap.StardewValley.Common.Model.Animals.FishType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ScoreboardWindow extends Window {

    private Table table;
    private SelectBox<String> sortSelectBox;

    public ScoreboardWindow(Skin skin) {
        super("Scoreboard", skin);

        setSize(384 * 3, 256 * 3);
        setMovable(false);
        setResizable(false);
        setModal(false);
        setKeepWithinStage(true);
        float windowX = (Gdx.graphics.getWidth() - getWidth()) / 2f;
        float windowY = (Gdx.graphics.getHeight() - getHeight()) / 2f;
        setPosition(windowX, windowY);

        sortSelectBox = new SelectBox<>(skin);
        sortSelectBox.setItems("Count", "Energy", "Skills");
        sortSelectBox.addListener(event -> {
            updateTable();
            return false;
        });

        add(sortSelectBox).colspan(6).pad(50).expandX().fillX();
        row();

        table = new Table();
        table.top();
        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        add(scrollPane).expand().fill().colspan(6);

        updateTable();
    }

    private void updateTable() {
        table.clear();

        Skin skin = getSkin();

        table.add(new Label("Rank", skin)).pad(5).expandX();
        table.add(new Label("Name", skin)).pad(5).expandX();
        table.add(new Label("Count", skin)).pad(5).expandX();
        table.add(new Label("Energy", skin)).pad(5).expandX();
        table.add(new Label("Skills", skin)).pad(5).expandX();
        table.row();

        ArrayList<Player> players = App.getGame().getPlayers();

        String criteria = sortSelectBox.getSelected();
        players.sort(getComparator(criteria));

        int rank = 1;
        for (Player player : players) {
            table.add(new Label(String.valueOf(rank), skin)).pad(5);
            table.add(new Label(player.getUsername(), skin)).pad(5);
            table.add(new Label(String.valueOf(player.getCount()), skin)).pad(5);
            table.add(new Label(String.valueOf(player.getEnergy()), skin)).pad(5);
            table.add(new Label(String.valueOf(player.getTotalSkills()), skin)).pad(5);
            table.row();
            rank++;
        }
    }


    private Comparator<Player> getComparator(String criteria) {
        switch (criteria) {
            case "Count":
                return (a, b) -> Integer.compare(b.getCount(), a.getCount());
            case "Energy":
                return (a, b) -> Integer.compare(b.getEnergy(), a.getEnergy());
            case "Skills":
                return (a, b) -> Integer.compare(b.getTotalSkills(), a.getTotalSkills());
            default:
                return (a, b) -> 0;
        }
    }
}
