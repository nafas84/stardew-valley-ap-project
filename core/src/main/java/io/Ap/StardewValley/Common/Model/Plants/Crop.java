package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlantController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;
import io.Ap.StardewValley.Common.Model.Time.Season;
import io.Ap.StardewValley.Common.Model.Time.Weather;

import java.util.ArrayList;

public class Crop implements Item, Plant {
    private DateAndTime plantingDate;
    private DateAndTime lastTimeHarvested;
    private final CropType type;
    private final boolean purposelyPlanted;
    private int totalHarvestTime;
    private double qualityConst = 1;
    //TODO: isGiant?


    //constructor:
    public Crop(DateAndTime t, CropType type, boolean purposelyPlanted) {
        this.plantingDate = new DateAndTime(t.getHour(), t.getDay(), t.getWeather());
        this.type = type;
        this.purposelyPlanted = purposelyPlanted;
        totalHarvestTime = type.getHarvestTime();
    }
    public Crop(CropType type, boolean purposelyPlanted) {
//        this.plantingDate = new DateAndTime(App.getCurrentGame().getCurrentTime().getHour(),
//                App.getCurrentGame().getCurrentTime().getDay(), App.getCurrentGame().getCurrentTime().getWeather());
        this.plantingDate = new DateAndTime(9, 1, Weather.Sunny);
        this.type = type;
        this.purposelyPlanted = purposelyPlanted;
        totalHarvestTime = type.getHarvestTime();
    }
    public Crop(CropType type) {
//        this.plantingDate = new DateAndTime(App.getCurrentGame().getCurrentTime().getHour(),
//                App.getCurrentGame().getCurrentTime().getDay(), App.getCurrentGame().getCurrentTime().getWeather());
        this.plantingDate = new DateAndTime(9, 1, Weather.Sunny);
        this.type = type;
        this.purposelyPlanted = true;
        totalHarvestTime = type.getHarvestTime();
    }

    public Crop(CropType type, double qualityConst) {
        this.plantingDate = new DateAndTime(App.getGame().getCurrentTime().getHour(),
                App.getGame().getCurrentTime().getDay(), App.getGame().getCurrentTime().getWeather());
        this.type = type;
        this.purposelyPlanted = true;
        totalHarvestTime = type.getHarvestTime();
        this.qualityConst = qualityConst;
    }


    //getters:
    @Override
    public String getName() {
        return type.getName();
    }

    public SeedType getSource() {
        return type.getSource();
    }

    public int[] getStages() {
        return type.getStages();
    }

    public int getHarvestTime() {
        return totalHarvestTime;
    }

    public boolean isOneTime() {
        return type.isOneTime();
    }

    public int getRegrowthTime() {
        return type.getRegrowthTime();
    }

    @Override
    public int getPrice() {
        return (int) (type.getBaseSellPrice() * qualityConst);
    }

    public boolean isEdible() {
        return type.isEdible();
    }

    public int getEnergy() {
        return type.getEnergy();
    }

    public ArrayList<Season> getSeasons() {
        return type.getSeasons();
    }

    public boolean canBecomeGiant() {
        return type.canBecomeGiant();
    }

    public boolean isMixed() {
        return type.isMixed();
    }

    public DateAndTime getPlantingDate() {
        return plantingDate;
    }

    public DateAndTime getLastTimeHarvested() {
        return lastTimeHarvested;
    }

    public int getCurrentStage () {
        int stageTime = getHarvestTime();
        int currentStage = getStages().length + 1;
        int daysSincePlanted = App.getGame().getCurrentTime().getDay() - plantingDate.getDay();//TODO: in moshkel dare
        if (lastTimeHarvested == null) {
            if (daysSincePlanted >= stageTime) {
                return currentStage;
            }
            for (int i : getStages()) {
                currentStage--;
                stageTime -= i;
                if (daysSincePlanted >= stageTime) {
                    return currentStage;
                }
            }
        }
        else {
            if (App.getGame().getCurrentTime().getDay() - lastTimeHarvested.getDay() >= getRegrowthTime()) {
                return getStages().length;
            }
            else {
                return 1;
            }
        }
        return 1;
    }

    public CropType getType() {
        return type;
    }

    //setters:
    public void setPlantingDate(DateAndTime t) {
        this.plantingDate = new DateAndTime(t.getHour(), t.getDay(), t.getWeather());
    } //TODO: in bashe ya na?

    public void setLastTimeHarvested(DateAndTime t) {
        if (t != null) {
            this.lastTimeHarvested = new DateAndTime(t.getHour(), t.getDay(), t.getWeather());
        }
        else {
            this.lastTimeHarvested = null;
        }
    }

    public void setTotalHarvestTime (int t) {
        this.totalHarvestTime = t;
    }

    public boolean isHarvestable () {
//        if (isOneTime()) {
//            return true;
//        }
        if (lastTimeHarvested == null) {
            if (App.getGame().getCurrentTime().getDay() - plantingDate.getDay() >= totalHarvestTime) {
                return true;
            }
            return false;
        }
        if (lastTimeHarvested.getDay() - plantingDate.getDay() >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public String showPlantInfo() {
        if (getName().equalsIgnoreCase("mixed seeds")) {
            return "crop info:\nname: mixed";
        }
        return "crop info:\nname: " + getName() + "\ntotal harvest time: " + totalHarvestTime + "\ndate of planting: " +
                plantingDate.getSeason().name() + " " + plantingDate.getDay() + "th\nstages: " +
                PlantController.stagesToString(getStages()) + "\ncurrent stage: " + getCurrentStage() + "\nis foraging: " +
                !purposelyPlanted;
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

}
