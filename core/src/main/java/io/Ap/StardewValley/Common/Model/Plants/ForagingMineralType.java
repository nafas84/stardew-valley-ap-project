package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.List;

public enum ForagingMineralType implements Forageable, PlantType {
    Quartz ("Quartz", 25),
    EarthCrystal ("Earth Crystal", 50),
    FrozenTear ("Frozen Tear", 75),
    FireQuartz ("Fire Quartz", 100),

    Emerald ("Emerald", 250),
    Aquamarine ("Aquamarine", 180),
    Ruby ("Ruby", 250),
    Amethyst ("Amethyst", 100),
    Topaz ("Topaz", 80),
    Jade ("Jade", 200),
    Diamond ("Diamond", 750),
    PrismaticShard ("Prismatic Shard", 2000),

    Copper ("Copper", 5),
    Iron ("Iron", 10),
    Gold ("Gold", 25),
    Iriduim ("Iriduim", 100),
    Coal ("Coal", 15);


    private final String name;
    private final int sellPrice;

    ForagingMineralType(String name, int sellPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    @Override
    public ArrayList<Season> getSeasons() {
        return new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter));
    }

    @Override
    public boolean isForageable () {
        return true;
    }

    @Override
    public String getInformation () {
        return "foraging mineral information:\nname: " + name + "\nsell price: " + sellPrice;
    }
}
