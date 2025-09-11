package io.Ap.StardewValley.Common.Model.Tool;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.AnimalController;
import io.Ap.StardewValley.Common.Model.Animals.Animal;
import io.Ap.StardewValley.Common.Model.Animals.AnimalType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Time.Weather;

public class Shear implements Tool {
    ToolType type = ToolType.Shear;

    public Shear() {
    }//needed for json

    @Override
    public Result upgrade() {
        return new Result(false, "shear can't get upgraded!");
    }

    @Override
    public Result use(Tile tile) {
        return new Result(false, "TODO");
    }

    @Override
    public Result use(Coordinate c) {
        if (c == null) {
            return new Result(false, "invalid coordinate!");
        }
        for (Animal a : App.getGame().getCurrentPlayer().getMyAnimals()) {
            if (c.equals(a.getCoordinate()) && a.getType() == AnimalType.Sheep) {
                return AnimalController.collectAnimalProduce(a.getName());
            }
        }
        return new Result(false, "there's no sheep in this tile!");
    }

    @Override
    public int getEnergyConsumption(boolean useSuccess) {
        if (App.getGame().getCurrentTime().getWeather() == Weather.Rain) {
            return 6;
        }
        else if (App.getGame().getCurrentTime().getWeather() == Weather.Snow) {
            return 8;
        }
        return 4;
    }

    @Override
    public String getLevelString() {
        return "Shears";
    }

    @Override
    public String getName() {
        return "shears";
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public ToolType getType() {
        return type;
    }
}
