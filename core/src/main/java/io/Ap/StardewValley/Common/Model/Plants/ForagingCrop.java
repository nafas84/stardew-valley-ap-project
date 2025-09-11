package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;
import io.Ap.StardewValley.Common.Model.Time.Season;
import io.Ap.StardewValley.Common.Model.Time.Weather;

import java.util.ArrayList;

public class ForagingCrop implements Item, Plant {
    private final ForagingCropType type;
    private final boolean purposelyPlanted;
    private final DateAndTime plantingDate;
    private double qualityConst = 1;

    public ForagingCrop (ForagingCropType type, boolean purposelyPlanted) {
        this.type = type;
        this.purposelyPlanted = purposelyPlanted;
        plantingDate = new DateAndTime(App.getGame().getCurrentTime().getHour(),
                App.getGame().getCurrentTime().getDay(), App.getGame().getCurrentTime().getWeather());
    }

    public ForagingCrop (DateAndTime t, ForagingCropType type, boolean purposelyPlanted) {
        this.type = type;
        this.purposelyPlanted = purposelyPlanted;
        plantingDate = new DateAndTime(t.getHour(), t.getDay(), t.getWeather());
    }

    public ForagingCrop (ForagingCropType type) {
        this.type = type;
        this.purposelyPlanted = false;
//        plantingDate = new DateAndTime(App.getCurrentGame().getCurrentTime().getHour(),
//                App.getCurrentGame().getCurrentTime().getDay(), App.getCurrentGame().getCurrentTime().getWeather());
        this.plantingDate = new DateAndTime(9, 1, Weather.Sunny);
    }

    @Override
    public String getName() {
        return type.getName();
    }

    public ArrayList<Season> getSeasons() {
        return type.getSeasons();
    }

    @Override
    public int getPrice() {
        return (int) (qualityConst * type.getBaseSellPrice());
    }

    public int getEnergy() {
        return type.getEnergy();
    }

    public boolean isForageable () {
        return true;
    }

    @Override
    public String showPlantInfo() {
        return "crop info:\nname: " + getName() + "\nis forageable: " + !purposelyPlanted;
    }

    public boolean isPurposelyPlanted() {
        return purposelyPlanted;
    }

    public double getQualityConst() {
        return qualityConst;
    }

    public void setQualityConst(double qualityConst) {
        this.qualityConst = qualityConst;
    }

    public DateAndTime getPlantingDate() {
        return plantingDate;
    }

    public ForagingCropType getType() {
        return type;
    }
}
