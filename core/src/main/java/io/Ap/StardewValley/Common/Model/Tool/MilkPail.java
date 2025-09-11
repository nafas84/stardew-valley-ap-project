package io.Ap.StardewValley.Common.Model.Tool;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.AnimalController;
import io.Ap.StardewValley.Common.Model.Animals.Animal;
import io.Ap.StardewValley.Common.Model.Animals.AnimalType;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Time.Weather;

public class MilkPail implements Tool{
    ToolType type = ToolType.MilkPail;

    public MilkPail() {
    } //needed for json

    @Override
    public Result upgrade() {
        return new Result(false, "milk pail can't get upgraded!");
    }

    @Override
    public Result use(Tile tile) {

        return new Result(false, "TODO"); //TODO
    }

    @Override
    public Result use(Coordinate c) {
        if (c == null) {
            return new Result(false, "invalid coordinate!");
        }
        for (Animal a : App.getGame().getCurrentPlayer().getMyAnimals()) {
            if (c.equals(a.getCoordinate()) && a.getType() == AnimalType.Cow) {
                return AnimalController.collectAnimalProduce(a.getName());
            }
        }
        return new Result(false, "there's no cow in this tile!");

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

    public static int getEnergyForAnimals() {
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
        return "milk pail";
    }

    @Override
    public String getName() {
        return "Milk pail";
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
