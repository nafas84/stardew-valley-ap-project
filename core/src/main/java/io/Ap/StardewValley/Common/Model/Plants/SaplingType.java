package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlantController;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.List;

public enum SaplingType implements Forageable, PlantType{
    ApricotSapling("Apricot Sapling", new ArrayList<>(List.of(Season.Spring)), TreeType.Apricot, false),
    CherrySapling("Cherry Sapling", new ArrayList<>(List.of(Season.Spring)), TreeType.Cherry, false),
    BananaSapling("Banana Sapling", new ArrayList<>(List.of(Season.Summer)), TreeType.Banana, false),
    MangoSapling("Mango Sapling", new ArrayList<>(List.of(Season.Summer)), TreeType.Mango, false),
    OrangeSapling("Orange Sapling", new ArrayList<>(List.of(Season.Summer)), TreeType.Orange, false),
    PeachSapling("Peach Sapling", new ArrayList<>(List.of(Season.Summer)), TreeType.Peach, false),
    AppleSapling("Apple Sapling", new ArrayList<>(List.of(Season.Fall)), TreeType.Apple, false),
    PomegranateSapling("Pomegranate Sapling", new ArrayList<>(List.of(Season.Fall)), TreeType.Pomegranate, false),

    Acorns("Acorns", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), TreeType.Oak, true),
    MapleSeeds("Maple Seeds", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), TreeType.Maple, true),
    PineCones("Pine Cones", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), TreeType.Pine, true),
    MahoganySeeds("Mahogany Seeds", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), TreeType.Mahogany, true),
    MushroomTreeSeeds("Mushroom Tree Seeds", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), TreeType.Mushroom, true),
    MysticTreeSeeds("Mystic Tree Seeds", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), TreeType.Mystic, false);



    private final String name;
    private final ArrayList<Season> seasons;
    private final TreeType treeType;
    private final boolean isForageable;

    SaplingType (String name, ArrayList<Season> seasons, TreeType treeType, boolean isForageable) {
        this.name = name;
        this.seasons = seasons;
        this.treeType = treeType;
        this.isForageable = isForageable;
    }


    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public TreeType getTreeType() {
        return treeType;
    }

    @Override
    public boolean isForageable() {
        return isForageable;
    }

    @Override
    public String getInformation () {
        return "sapling information: \nname: " + name + "\nseason: " + PlantController.SeasonsToString(seasons) +
                "\nis forageable: " + isForageable;
    }
}
