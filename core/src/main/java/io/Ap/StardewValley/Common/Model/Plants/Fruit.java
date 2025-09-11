package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Common.Model.Item.Item;

public class Fruit implements Item, Plant {
    private final FruitType type;
    private final boolean purposelyPlanted;
    private double qualityConst = 1;

    //constructor:
    public Fruit (FruitType type, boolean purposelyPlanted) {
        this.type = type;
        this.purposelyPlanted = purposelyPlanted;
    }

    public Fruit (FruitType type) {
        this.type = type;
        this.purposelyPlanted = true;
    }
    public Fruit (FruitType type, double qualityConst) {
        this.type = type;
        this.purposelyPlanted = true;
        this.qualityConst = qualityConst;
    }

    //getters:
    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getPrice() {
        return (int) (type.getBaseSellPrice() * qualityConst);
    }

    public boolean isEdible() {
        return type.isEdible();
    }

    public int getEnergy() {
        return type.getEnergy();
    }

    public TreeType getTreeType() {
        return type.getTreeType();
    }

    @Override
    public String showPlantInfo() {
        return "fruit info:\nname: " + getName() + "\nis forageable: " + !purposelyPlanted;
    }

    public boolean isPurposelyPlanted() {
        return purposelyPlanted;
    }


    public double getQualityConst() {
        return qualityConst;
    }

    public void setQualityConst(double qualityConst) {
        this.qualityConst = qualityConst;
    }
}
