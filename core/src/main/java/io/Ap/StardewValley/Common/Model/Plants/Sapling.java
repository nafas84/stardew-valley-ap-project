package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;

public class Sapling implements Item, Plant {
    private final SaplingType saplingType;
    private final boolean purposelyPlanted;

    public Sapling (SaplingType saplingType, boolean purposelyPlanted) {
        this.saplingType =saplingType;
        this.purposelyPlanted = purposelyPlanted;
    }
    public Sapling (SaplingType saplingType) {
        this.saplingType =saplingType;
        this.purposelyPlanted = true;
    }

    @Override
    public String getName() {
        return saplingType.getName();
    }

    public ArrayList<Season> getSeasons() {
        return saplingType.getSeasons();
    }

    public TreeType getTreeType() {
        return saplingType.getTreeType();
    }

    public boolean isForageable() {
        return saplingType.isForageable();
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String showPlantInfo() {
        return "sapling info:\nname: " + getName() + "\ntree type: " + getTreeType() + "\nisForageable: " +
                !purposelyPlanted;
    }

    public boolean isPurposelyPlanted() {
        return purposelyPlanted;
    }
}
