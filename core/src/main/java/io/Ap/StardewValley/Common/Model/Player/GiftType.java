package io.Ap.StardewValley.Common.Model.Player;

public enum GiftType {
    Bouquet ("Bouquet", 500),
    WeddingRing ("Wedding Ring", 5000);

    private final String name;
    private final int sellPrice;

    GiftType (String name, int sellPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
