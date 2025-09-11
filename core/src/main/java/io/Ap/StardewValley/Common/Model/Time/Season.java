package io.Ap.StardewValley.Common.Model.Time;

import java.util.List;

public enum Season {
    Spring(List.of(Weather.Sunny, Weather.Rain, Weather.Storm)),
    Summer(List.of(Weather.Sunny, Weather.Rain, Weather.Storm)),
    Fall(List.of(Weather.Sunny, Weather.Rain, Weather.Storm)),
    Winter(List.of(Weather.Snow));

    private final List<Weather> weathers;

    Season(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }
}
