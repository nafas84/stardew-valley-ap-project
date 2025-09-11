package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;

public class Seed implements Item, Plant {
    //TODO: mixed seeds must be controlled in controller. no specific operation has been considered yet.

    private final SeedType seedType;
    private final boolean purposelyPlanted;

    public Seed (SeedType seedType) {
        this.seedType = seedType;
        this.purposelyPlanted = true;
    }

    public Seed (SeedType seedType, boolean purposelyPlanted) {
        this.seedType = seedType;
        this.purposelyPlanted = purposelyPlanted;
    }

    public SeedType getSeedType() {
        return seedType;
    }

    @Override
    public String getName() {
        return seedType.getName();
    }

    public ArrayList<Season> getSeasons() {
        return seedType.getSeasons();
    }

    public CropType getCrop() {
        return seedType.getCrop();
    }

    public boolean isForageable () {
        return true;
    }

    @Override
    public int getPrice() {
        //TODO: check shop shit
        return 10;
    }

    @Override
    public String showPlantInfo() {
        return "seed info:\nname: " + getName() + "\nis forageable: " + !purposelyPlanted;
    }

    public boolean isPurposelyPlanted() {
        return purposelyPlanted;
    }
}
