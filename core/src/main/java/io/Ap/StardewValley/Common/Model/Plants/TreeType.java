package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlantController;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.List;

public enum TreeType implements PlantType{
    Apricot ("Apricot Tree", SaplingType.ApricotSapling, new int[] {7, 7, 7, 7}, 28,
            FruitType.Apricot, 1, new ArrayList<>(List.of(Season.Spring))),
    Cherry("Cherry Tree", SaplingType.CherrySapling, new int[] {7, 7, 7, 7}, 28,
            FruitType.Cherry, 1, new ArrayList<>(List.of(Season.Spring))),
    Banana("Banana Tree", SaplingType.BananaSapling, new int[] {7, 7, 7, 7}, 28,
            FruitType.Banana, 1, new ArrayList<>(List.of(Season.Summer))),
    Mango("Mango Tree", SaplingType.MangoSapling, new int[] {7, 7, 7, 7}, 28,
            FruitType.Mango, 1, new ArrayList<>(List.of(Season.Summer))),
    Orange("Orange Tree", SaplingType.OrangeSapling, new int[] {7, 7, 7, 7}, 28,
            FruitType.Orange, 1, new ArrayList<>(List.of(Season.Summer))),
    Peach("Peach Tree", SaplingType.PeachSapling, new int[] {7, 7, 7, 7}, 28,
            FruitType.Peach, 1, new ArrayList<>(List.of(Season.Summer))),
    Apple("Apple Tree", SaplingType.AppleSapling, new int[] {7, 7, 7, 7}, 28,
            FruitType.Apple, 1, new ArrayList<>(List.of(Season.Fall))),
    Pomegranate("Pomegranate Tree", SaplingType.PomegranateSapling, new int[] {7, 7, 7, 7}, 28,
            FruitType.Pomegranate, 1, new ArrayList<>(List.of(Season.Fall))),


    Oak("Oak Tree", SaplingType.Acorns, new int[] {7, 7, 7, 7}, 28,
            FruitType.OakResin, 7, new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter))),
    Maple("Maple Tree", SaplingType.MapleSeeds, new int[] {7, 7, 7, 7}, 28,
            FruitType.MapleSyrup, 9, new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter))),
    Pine("Pine Tree", SaplingType.PineCones, new int[] {7, 7, 7, 7}, 28,
            FruitType.PineTar, 5, new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter))),
    Mahogany("Mahogany Tree", SaplingType.MahoganySeeds, new int[] {7, 7, 7, 7}, 28,
            FruitType.Sap, 1, new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter))),
    Mushroom("Mushroom Tree", SaplingType.MushroomTreeSeeds, new int[] {7, 7, 7, 7}, 28,
            FruitType.CommonMushroom, 1, new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter))),
    Mystic("Mystic Tree", SaplingType.MysticTreeSeeds, new int[] {7, 7, 7, 7}, 28,
            FruitType.MysticSyrup, 7, new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)));


    private final String name;
    private final SaplingType source;
    private final int[] stages;
    private final int totalHarvestTime;
    private final FruitType fruitType;
    private final int fruitHarvestCycle;
    private final ArrayList<Season> seasons;

    TreeType (String name, SaplingType source, int[] stages, int totalHarvestTime,
              FruitType fruitType, int fruitHarvestCycle, ArrayList<Season> seasons) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.fruitType = fruitType;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.seasons = seasons;

    }

    public String getName() {
        return name;
    }

    public SaplingType getSource() {
        return source;
    }

    public int[] getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public String getInformation () {
        return "tree information: \nname: " + name + "\nsource: " + source.getName() + "\nstages: " +
                PlantController.stagesToString(stages) + "\ntotal harvest time: " + totalHarvestTime +
                totalHarvestTime + "\nfruit: " + fruitType.name() + "\nfruit harvest cycle: " +
                fruitHarvestCycle + "\nseason: " + PlantController.SeasonsToString(seasons);
    }
}
