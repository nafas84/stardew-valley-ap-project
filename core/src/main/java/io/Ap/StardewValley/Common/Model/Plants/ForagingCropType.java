package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlantController;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.List;

public enum ForagingCropType implements Forageable, PlantType{
    CommonMushroom ("Common Mushroom", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), 40, 38),

    Daffodil("Daffodil", new ArrayList<>(List.of(Season.Spring)), 30, 0),

    Dandelion("Dandelion", new ArrayList<>(List.of(Season.Spring)), 40, 25),

    Leek("Leek", new ArrayList<>(List.of(Season.Spring)), 60, 40),

    Morel("Morel", new ArrayList<>(List.of(Season.Spring)), 150, 20),

    Salmonberry("Salmonberry", new ArrayList<>(List.of(Season.Spring)), 5, 25),

    SpringOnion("Spring Onion", new ArrayList<>(List.of(Season.Spring)), 8, 13),

    WildHorseradish("Wild Horseradish", new ArrayList<>(List.of(Season.Spring)), 50, 13),

    FiddleheadFern("Fiddlehead Fern", new ArrayList<>(List.of(Season.Summer)), 90, 25),

    Grape("Grape", new ArrayList<>(List.of(Season.Summer)), 80, 38),

    RedMushroom("Red Mushroom", new ArrayList<>(List.of(Season.Summer)), 75, -50),

    SpiceBerry("Spice Berry", new ArrayList<>(List.of(Season.Summer)), 80, 25),

    SweetPea("Sweet Pea", new ArrayList<>(List.of(Season.Summer)), 50, 0),

    Blackberry("Blackberry", new ArrayList<>(List.of(Season.Fall)), 25, 25),

    Chanterelle("Chanterelle", new ArrayList<>(List.of(Season.Fall)), 160, 75),

    Hazelnut("Hazelnut", new ArrayList<>(List.of(Season.Fall)), 40, 38),

    PurpleMushroom("Purple Mushroom", new ArrayList<>(List.of(Season.Fall)), 90, 30),

    WildPlum("Wild Plum", new ArrayList<>(List.of(Season.Fall)), 80, 25),

    Crocus("Crocus", new ArrayList<>(List.of(Season.Winter)), 60, 0),

    CrystalFruit("Crystal Fruit", new ArrayList<>(List.of(Season.Winter)), 150, 63),

    Holly("Holly", new ArrayList<>(List.of(Season.Winter)), 80, -37),

    SnowYam("Snow Yam", new ArrayList<>(List.of(Season.Winter)), 100, 30),

    WinterRoot("Winter Root", new ArrayList<>(List.of(Season.Winter)), 70, 25),

    Grass("Grass", new ArrayList<>(List.of(Season.Spring)), 70, 25);

    private final String name;
    private final ArrayList<Season> seasons;
    private final int baseSellPrice;
    private final int energy;


    ForagingCropType (String name, ArrayList<Season> seasons, int baseSellPrice, int energy) {
        this.name = name;
        this.seasons = seasons;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
    }


    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public boolean isForageable () {
        return true;
    }

    @Override
    public String getInformation () {
        return "foraging crop type:\nname: " + name + "\nseason: " + PlantController.SeasonsToString(seasons) +
                "\nbase sell price: " + baseSellPrice + "\nenergy: " + energy;
    }
}
