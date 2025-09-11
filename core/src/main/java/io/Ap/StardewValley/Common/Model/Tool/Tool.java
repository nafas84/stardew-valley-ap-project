package io.Ap.StardewValley.Common.Model.Tool;

import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Result;

public interface Tool extends Item {
    public Result upgrade();
    public Result use (Tile tile);
    public Result use (Coordinate direction);
    public int getEnergyConsumption(boolean useSuccess);
    public String getLevelString ();
    public ToolType getType();
}
