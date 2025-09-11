package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.Arrays;

public class ForageableFactory {
    private static final ArrayList<Forageable> allForageables = new ArrayList<>();
    private static final ArrayList<Forageable> plantForageables = new ArrayList<>();
    private static final ArrayList<Forageable> springPlantForageables = new ArrayList<>();
    private static final ArrayList<Forageable> summerPlantForageables = new ArrayList<>();
    private static final ArrayList<Forageable> fallPlantForageables = new ArrayList<>();
    private static final ArrayList<Forageable> winterPlantForageables = new ArrayList<>();
    private static final ArrayList<Forageable> mineralForageables = new ArrayList<>();


    public static ArrayList<Forageable> getAllForageables() {
        allForageables.addAll(Arrays.asList(ForagingCropType.values()));
        allForageables.addAll(Arrays.asList(SeedType.values()));
        for (SaplingType type : SaplingType.values()) {
            if (type.isForageable()) {
                allForageables.add(type);
            }
        }
        allForageables.addAll(Arrays.asList(ForagingMineralType.values()));
        return allForageables;
    }

    public static ArrayList<Forageable> getPlantForageables() {
        plantForageables.addAll(Arrays.asList(ForagingCropType.values()));
        plantForageables.addAll(Arrays.asList(SeedType.values()));
//        for (SaplingType type : SaplingType.values()) {
//            if (type.isForageable()) {
//                plantForageables.add(type);
//            }
//        }
        return plantForageables;
    }

    public static ArrayList<Forageable> getSpringPlantForageables() {
        for (ForagingCropType type : ForagingCropType.values()) {
            if (type.getSeasons().contains(Season.Spring)) {
                springPlantForageables.add(type);
            }
        }
        for (SeedType type : SeedType.values()) {
            if (type.getSeasons().contains(Season.Spring)) {
                springPlantForageables.add(type);
            }

        }
//        for (SaplingType type : SaplingType.values()) {
//            if (type.isForageable() && type.getSeasons().contains(Season.Spring)) {
//                springPlantForageables.add(type);
//            }
//        }
        return springPlantForageables;
    }

    public static ArrayList<Forageable> getSummerPlantForageables() {
        for (ForagingCropType type : ForagingCropType.values()) {
            if (type.getSeasons().contains(Season.Summer)) {
                summerPlantForageables.add(type);
            }

        }
        for (SeedType type : SeedType.values()) {
            if (type.getSeasons().contains(Season.Summer)) {
                summerPlantForageables.add(type);
            }

        }
//        for (SaplingType type : SaplingType.values()) {
//            if (type.isForageable() && type.getSeasons().contains(Season.Summer)) {
//                summerPlantForageables.add(type);
//            }
//        }
        return summerPlantForageables;
    }

    public static ArrayList<Forageable> getFallPlantForageables() {
        for (ForagingCropType type : ForagingCropType.values()) {
            if (type.getSeasons().contains(Season.Fall)) {
                fallPlantForageables.add(type);
            }
        }
        for (SeedType type : SeedType.values()) {
            if (type.getSeasons().contains(Season.Fall)) {
                fallPlantForageables.add(type);
            }
        }
//        for (SaplingType type : SaplingType.values()) {
//            if (type.isForageable() && type.getSeasons().contains(Season.Fall)) {
//                fallPlantForageables.add(type);
//            }
//        }
        return fallPlantForageables;
    }

    public static ArrayList<Forageable> getWinterPlantForageables() {
        for (ForagingCropType type : ForagingCropType.values()) {
            if (type.getSeasons().contains(Season.Winter)) {
                winterPlantForageables.add(type);
            }
        }
        for (SeedType type : SeedType.values()) {
            if (type.getSeasons().contains(Season.Winter)) {
                winterPlantForageables.add(type);
            }
        }
//        for (SaplingType type : SaplingType.values()) {
//            if (type.isForageable() && type.getSeasons().contains(Season.Winter)) {
//                winterPlantForageables.add(type);
//            }
//        }
        return winterPlantForageables;
    }

    public static ArrayList<Forageable> getMineralForageables() {
        mineralForageables.addAll(Arrays.asList(ForagingMineralType.values()));
        return mineralForageables;
    }

    public static ArrayList<Forageable> getSeasonPlantForageables(Season s) {
        if (s == Season.Spring) {
            return getSpringPlantForageables();
        }
        if (s == Season.Summer) {
            return getSummerPlantForageables();
        }
        if (s == Season.Fall) {
            return getFallPlantForageables();
        }
        return getWinterPlantForageables();
    }
}
