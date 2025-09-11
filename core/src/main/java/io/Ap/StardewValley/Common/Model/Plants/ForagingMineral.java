package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.List;

public class ForagingMineral implements Item, Plant {
    private final ForagingMineralType type;

    //constructor:
    public ForagingMineral(ForagingMineralType type) {
        this.type = type;
    }

    //getters:
    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getPrice() {
        return type.getSellPrice();
    }

    public ArrayList<Season> getSeasons() {
        return new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter));
    }

    public boolean isForageable () {
        return true;
    }

    public String showPlantInfo() {
        return "foraging mineral info:\nname: " + getName();
    }

    public ForagingMineralType getType() {
        return type;
    }
}
