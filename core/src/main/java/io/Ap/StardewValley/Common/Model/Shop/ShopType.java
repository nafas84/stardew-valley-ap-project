package io.Ap.StardewValley.Common.Model.Shop;

public enum ShopType {
    Blacksmith("Clint" , "Blacksmith" , 9 , 16),
    JojaMart("Morris" , "JojaMart" , 9 , 23),
    PierresGeneralStore("Pierre" , "Pierre's General Store" , 9 , 17),
    CarpentersShop("Robin" , "Carpenter's Shop" , 9 , 20),
    FishShop("Willy" , "Fish Shop" , 9 , 17),
    MarniesRanch("Marnie" , "Marnie's Ranch" , 9 , 16),
    TheStarDropSaloon("Gus" , "The StarDrop Saloon" , 12 , 24);

    private final String owner;
    private final String name;
    private final int openingTime;
    private final int closingTime;

    ShopType(String owner, String name, int openingTime, int closingTime) {
        this.owner = owner;
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getOwnerName() {
        return owner;
    }

    public String getShopName() {
        return name;
    }

    public int getOpeningTime() {
        return openingTime;
    }

    public int getClosingTime() {
        return closingTime;
    }
}
