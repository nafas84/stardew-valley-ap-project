package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlantController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;
import io.Ap.StardewValley.Common.Model.Time.Season;
import io.Ap.StardewValley.Common.Model.Time.Weather;

import java.util.ArrayList;

public class Tree implements Item, Plant {

    private final TreeType type;
    private boolean isBurned;
    private final DateAndTime plantingDate;
    private DateAndTime lastTimeHarvested;
    private final boolean purposelyPlanted;
    private int totalHarvestTime;

    //constructor:
    public Tree (TreeType type, DateAndTime t, boolean purposelyPlanted) {
        this.type = type;
        this.plantingDate = new DateAndTime(t.getHour(), t.getDay(), t.getWeather());
        isBurned = false;
        lastTimeHarvested = null;
        this.purposelyPlanted = purposelyPlanted;
        totalHarvestTime = type.getTotalHarvestTime();
    }
    public Tree (TreeType type, boolean purposelyPlanted) {
        this.type = type;
        this.plantingDate = new DateAndTime(9, 1, Weather.Sunny);
        isBurned = false;
        lastTimeHarvested = null;
        this.purposelyPlanted = purposelyPlanted;
        totalHarvestTime = type.getTotalHarvestTime();
    }
    public Tree (TreeType type) {
        this.type = type;
        this.plantingDate = new DateAndTime(9, 1, Weather.Sunny);
        isBurned = false;
        lastTimeHarvested = null;
        this.purposelyPlanted = true;
        totalHarvestTime = type.getTotalHarvestTime();
    }

    //getters:
    public TreeType getType() {
        return type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getPrice() {
        //TODO: nemidoonammmmmmmmmmmmmmm
        return 0;
    }

    public SaplingType getSource() {
        return type.getSource();
    }

    public int[] getStages() {
        return type.getStages();
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public FruitType getFruitType() {
        return type.getFruitType();
    }

    public int getFruitHarvestCycle() {
        return type.getFruitHarvestCycle();
    }

    public ArrayList<Season> getSeasons() {
        return type.getSeasons();
    }

    public boolean isBurned() {
        return isBurned;
    }

    public DateAndTime getPlantingDate() {
        return plantingDate;
    }

    public DateAndTime getLastTimeHarvested() {
        return lastTimeHarvested;
    }

    public int getCurrentStage () {
        int daysSincePlanted = App.getGame().getCurrentTime().getDay() - plantingDate.getDay();
        if (daysSincePlanted < 7) {
            return 1;
        }
        else if (daysSincePlanted < 14) {
            return 2;
        }
        else if (daysSincePlanted < 21) {
            return 3;
        }
        else if (daysSincePlanted < 28) {
            return 4;
        }
        return 5;
    }

    public boolean hasFruit () {
        if (lastTimeHarvested != null) {
            if (App.getGame().getCurrentTime().getDay() - lastTimeHarvested.getDay() >= getFruitHarvestCycle()) {
                return true;
            }
        }
        if (App.getGame().getCurrentTime().getDay() - plantingDate.getDay() >= totalHarvestTime) {
            return true;
        }
        return false;
    }

    //setters:
    public void burn() {
        isBurned = true;
    }

//    public void setPlantingDate(DateAndTime plantingDate) {
//        this.plantingDate = plantingDate;
//    } //TODO: in bashe? ya final konim?

    public void setLastTimeHarvested(DateAndTime lastTimeHarvested) {
        this.lastTimeHarvested = lastTimeHarvested;
    }

    @Override
    public String showPlantInfo() {
        return "crop info:\nname: " + getName() + "\ntotal harvest time: " + totalHarvestTime + "\ndate of planting: " +
                plantingDate.getSeason().name() + " " + plantingDate.getDay() + "th\nstages: " +
                PlantController.stagesToString(getStages()) + "\ncurrent stage: " + getCurrentStage() +
                "\nhas been burned " + isBurned;
    }

    public boolean isPurposelyPlanted() {
        return purposelyPlanted;
    }

    public void setTotalHarvestTime(int totalHarvestTime) {
        this.totalHarvestTime = totalHarvestTime;
    }
}
