package io.Ap.StardewValley.Client.Screen.TimeScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;
import io.Ap.StardewValley.Common.Model.Time.Season;
import io.Ap.StardewValley.Common.Model.Time.Weather;
import io.Ap.StardewValley.StardewValley;

import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;


public class TimeBar {
    private final float scale  = 1.2f;
    private final Map<Weather, Image> weathersImage = new EnumMap<>(Weather.class);
    private final Map<Season, Image> seasonsImage = new EnumMap<>(Season.class);
    private final List<Image> energyBars = new ArrayList<>();

    private final Group group;

    private final Label timeLabel;
    private final Label dateLabel;
    private Image currentSeason;
    private Image currentWeather;

    {
        for (Weather weather : Weather.values()) {
            Image image = new Image(new Texture(Gdx.files.internal( "time/" + weather.name() + ".png")));
            weathersImage.put(weather, image);
        }
        
        for (Season season : Season.values()) {
            Image image = new Image(new Texture(Gdx.files.internal("time/" + season.name() + ".png")));
            seasonsImage.put(season, image);
        }
    }

    public TimeBar() {
        Skin skin = StardewValley.getSkin();
        group = new Group();

        DateAndTime time = App.getGame().getCurrentTime();

        Image background = new Image(new Texture("time/timeBar.png"));
        background.setSize(background.getWidth() * scale, background.getHeight() * scale);

        group.addActor(background);

        // time:
        timeLabel = new Label(time.getFormattedTime(), skin);
        timeLabel.setPosition(185 , 110);
        group.addActor(timeLabel);

        // date:
        dateLabel = new Label(time.getDayOfWeek().getAbbreviation() + " " + time.getDay(), skin);
        dateLabel.setPosition(185, 220);
        group.addActor(dateLabel);

        // season:
        currentSeason = seasonsImage.get(time.getSeason());
        currentSeason.setSize(currentSeason.getWidth() * scale, currentSeason.getHeight() * scale);
        currentSeason.setPosition(139, 168);
        group.addActor(currentSeason);

        // weather
        currentWeather = weathersImage.get(time.getWeather());
        currentWeather.setSize(currentWeather.getWidth() * scale, currentWeather.getHeight() * scale);
        currentWeather.setPosition(254, 168);
        group.addActor(currentWeather);

        // energy:
        for (int i = 0; i < 8; i++) {
            Image energy = new Image(new Texture(Gdx.files.internal("time/Green.png")));
            energy.setSize(energy.getWidth() * scale, energy.getHeight() * scale + 1);
            energy.setPosition(77 + i * 29, 9);
            energyBars.add(energy);
            group.addActor(energy);
        }

        group.setSize(background.getWidth(), background.getHeight());

    }

    public void updateTime() {
        DateAndTime time = App.getGame().getCurrentTime();
        timeLabel.setText(time.getFormattedTime());
        dateLabel.setText(time.getDayOfWeek().getAbbreviation() + " " + time.getDay());
    }

    public void updateEnergy() {
        int energy = App.getGame().getCurrentPlayer().getEnergy();
        int maxEnergy = App.getGame().getCurrentPlayer().getMaxEnergy();

        int visibleBars = Math.min(8, Math.max(0, energy * 8 / maxEnergy));
        for (int i = 0; i < 8; i++) {
            energyBars.get(i).setVisible(i < visibleBars);
        }
    }

    public void updateSeason(Season season) {
        currentSeason.setDrawable(seasonsImage.get(season).getDrawable());
    }

    public void updateWeather() {
        group.removeActor(currentWeather);
        Weather newWeather = App.getGame().getCurrentTime().getWeather();
        currentWeather = new Image(weathersImage.get(newWeather).getDrawable());
        currentWeather.setSize(currentWeather.getWidth() * scale, currentWeather.getHeight() * scale);
        currentWeather.setPosition(254, 168);
        group.addActor(currentWeather);
    }



    public Group getGroup() {
        return group;
    }
}

