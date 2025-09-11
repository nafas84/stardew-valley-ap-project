package io.Ap.StardewValley.Common.Model.Animals;

import io.Ap.StardewValley.Common.Model.Time.Season;

public enum FishType {
    Salmon("Salmon", 75 , Season.Fall, false),
    Sardine("Sardine", 40 , Season.Fall, false),
    Shad("Shad", 60 , Season.Fall, false),
    BlueDiscus("Blue Discus", 120 , Season.Fall, false),

    MidnightCarp("Midnight Carp", 150 , Season.Winter, false),
    Squid("Squid", 80 , Season.Winter, false),
    Tuna("Tuna", 100 , Season.Winter, false),
    Perch("Perch", 55 , Season.Winter, false),

    Flounder("Flounder", 100 , Season.Spring, false),
    Lionfish("Lionfish", 100 , Season.Spring, false),
    Herring("Herring", 30 , Season.Spring, false),
    Ghostfish("Ghostfish", 45 , Season.Spring, false),

    Tilapia("Tilapia", 75 , Season.Summer, false),
    Dorado("Dorado", 100 , Season.Summer, false),
    Sunfish("Sunfish", 30 , Season.Summer, false),
    RainbowTrout("Rainbow Trout", 65 , Season.Summer, false),

    Legend("Legend", 5000 , Season.Spring, true),
    GlacierFish("Glacierfish", 1000 , Season.Winter, true),
    Angler("Angler", 900 , Season.Fall, true),
    CrimsonFish("Crimsonfish", 1500 , Season.Summer, true);

    private final String name;
    private final int basePrice;
    private final Season season;
    private final boolean isLegendary;

    FishType(String name, int basePrice, Season season, boolean isLegendary) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.isLegendary = isLegendary;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public Season getSeason() {
        return season;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public String getName() {
        return name;
    }
}
