package io.Ap.StardewValley.Common.Model.Animals;

import io.Ap.StardewValley.Common.Model.Item.Item;

import java.util.Random;

public class AnimalProduct implements Item {
    private final AnimalProductType type;
    private final double quality;

    public AnimalProduct(AnimalProductType type, int friendship) {
        this.type = type;

        Random random = new Random();
        double R = random.nextDouble();
        this.quality = (friendship / 1000.0) * (0.5 + 0.5 * R);
    }

    public AnimalProduct(AnimalProductType type) {
        this.type = type;
        this.quality = 0;
    }


    public AnimalProductType getType() {
        return type;
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

    @Override
    public String getName() {
        return type.getName();
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
        return (int) (type.getPrice() * coefficient);
    }
}
