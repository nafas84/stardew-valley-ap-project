package io.Ap.StardewValley.Common.Model.Map;

public enum FarmBuildingType {
    Barn (BuildingType.Barn, 4, "Barn", 1),
    BigBarn (BuildingType.Barn, 8, "Big Barn", 2),
    DeluxeBarn (BuildingType.Barn, 12, "Deluxe Barn", 3),
    Coop (BuildingType.Coop, 4, "Coop", 1),
    BigCoop (BuildingType.Coop, 8, "Big Coop", 2),
    DeluxeCoop (BuildingType.Coop, 12, "Deluxe Coop", 3),
    Well (BuildingType.Well, 0, "Well", 1),
    ShippingBin (BuildingType.ShippingBin, 0, "Shipping Bin", 1);

    private final BuildingType type;
    private final int capacity;
    private final String name;
    private final int level;

    FarmBuildingType (BuildingType type, int capacity, String name, int level) {
        this.capacity = capacity;
        this.type = type;
        this.name = name;
        this.level = level;
    }

    public BuildingType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getW() {
        return type.getW();
    }

    public int getL() {
        return type.getL();
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
}
