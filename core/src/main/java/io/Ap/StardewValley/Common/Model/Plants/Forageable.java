package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;

public interface Forageable {
    boolean isForageable();
    ArrayList<Season> getSeasons();
}
