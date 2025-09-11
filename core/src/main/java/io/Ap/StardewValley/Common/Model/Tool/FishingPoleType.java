package io.Ap.StardewValley.Common.Model.Tool;

public enum FishingPoleType {
    Training ("Training Rod", 25, 8, 0, 0.1),
    Bamboo ("Bamboo Pole", 500, 8, 0, 0.5),
    Fiberglass ("Fiberglass Rod", 1800, 6, 2, 0.9),
    Iridium ("Iridium Rod", 7500, 4, 4, 1.2);

    private final String name;
    private final int price;
    private final int energyConsumption;
    private final int fishingSkillLevel;
    private final double fishingFactor;

    FishingPoleType (String name, int price, int energyConsumption, int fishingSkillLevel, double fishingFactor) {
        this.name = name;
        this.price = price;
        this.energyConsumption = energyConsumption;
        this.fishingSkillLevel = fishingSkillLevel;
        this.fishingFactor = fishingFactor;
    }

    public int getPrice() {
        return price;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public int getFishingSkillLevel() {
        return fishingSkillLevel;
    }

    public String getName() {
        return name;
    }

    public double getFishingFactor() {
        return fishingFactor;
    }
}
