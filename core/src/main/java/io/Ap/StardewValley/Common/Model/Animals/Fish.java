package io.Ap.StardewValley.Common.Model.Animals;

import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Time.Season;

public class Fish implements Item {
    private final FishType type;
    private final double quality;

    public Fish(FishType type, double quality) {
        this.type = type;
        this.quality = quality;
    }

    public Fish(FishType type) {
        this.type = type;
        this.quality = 1;
    }

    @Override
    public int getPrice() {
        double coefficient = 1;
        if (this.quality < 0.7) {
            coefficient = 1.25;
        } else if (this.quality < 0.9) {
            coefficient = 1.5;
        } else if (this.quality >= 0.9) {
            coefficient = 2;
        }
        return (int) (type.getBasePrice() * coefficient);
    }

    public double getQuality() {
        return quality;
    }

    public String getQualityString() {
        if (this.quality < 0.5) {
            return "Regular";
        } else if (this.quality < 0.7) {
            return "Steel";
        } else if (this.quality < 0.9) {
            return "Gold";
        } else if (this.quality >= 0.9) {
            return "Iridium";
        } else {
            return "Invalid quality!";
        }
    }
    public Season getSeason() {
        return type.getSeason();
    }

    public boolean isLegendary() {
        return type.isLegendary();
    }

    @Override
    public String getName() {
        return type.getName();
    }
}
