package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.Item.Item;

public class Fertilizer implements Item {
    private final FertilizerType fertilizerType;

    public Fertilizer (FertilizerType type) {
        fertilizerType = type;
    }

    public FertilizerType getFertilizerType() {
        return fertilizerType;
    }

    @Override
    public String getName() {
        return fertilizerType.getName();
    }

    @Override
    public int getPrice() {
        return fertilizerType.getSellPrice();
    }

    public int getTypeNumber() {
        return fertilizerType.getType();
    }
}
