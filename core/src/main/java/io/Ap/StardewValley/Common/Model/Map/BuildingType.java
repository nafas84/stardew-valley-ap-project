package io.Ap.StardewValley.Common.Model.Map;


public enum BuildingType {
    Door(1,2), // add door in isWalkable

    GreenHouse(5, 7),
    House(4, 5),

    GreenHouseBuild(7, 10),
    Barn(7, 7),
    Coop(6 , 7),
    Well( 3 , 5),
    ShippingBin(2, 2),

    Blacksmith(4 , 6),
    JojaMart(3 , 7),
    PierresGeneralStore(5 , 8),
    CarpentersShop(4 , 6),
    FishShop(4 , 5),
    MarniesRanch(3 , 5),
    TheStarDropSaloon(4, 7),

    DontKnow(4 , 7),

    ;

    private int w;
    private int l;

    BuildingType(int w, int l) {
        this.w = w;
        this.l = l;
    }

    public int getW() {
        return w;
    }

    public int getL() {
        return l;
    }
}
