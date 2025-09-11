package io.Ap.StardewValley.Common.Model.Map;

public enum Symbols {
    /*
    30 black
    31 red
    32 green
    33 yellow
    34 blue
    35 purple
    36 cyan
    37 gray
     */
    Player("@", "\u001B[35m"),
    CurrentPlayer("~", "\u001B[35m"),

    Mountain("M", "\u001B[37m"),
    Water("L", "\u001B[34m"),
    Mine("X", "\u001B[35m"),

    Pavement("*", "\u001B[36m"),
    Ground("G", "\u001B[32m"),

    Tree("T", "\u001B[90m"),
    Sapling("1", "\u001B[90m"),
    Crop("2", "\u001B[90m"),
    Fruit("3", "\u001B[90m"),
    Seed("4", "\u001B[90m"),
    ForagingCrop("^", "\u001B[90m"),
    ForagingMineral("<", "\u001B[90m"),
    Stone("%", "\u001B[90m"),
    Wood("W", "\u001B[90m"),
    Craft("/", "\u001B[90m"),
    Food("U", "\u001B[90m"),
    Fish("Y", "\u001B[90m"),
    AnimalProduct("{", "\u001B[90m"),

    NPC("#", "\u001B[35m"),
    Animal("$", "\u001B[35m"),

    House("H", "\u001B[36m"),
    GreenHouse("9", "\u001B[36m"),
    GreenHouserBuild("%", "\u001B[36m"),

    Barn("&", "\u001B[37m"),
    BigBarn("=", "\u001B[37m"),
    DeluxeBarn("_", "\u001B[37m"),
    Coop(")", "\u001B[37m"),
    BigCoop("(", "\u001B[37m"),
    DeluxeCoop("!", "\u001B[37m"),
    Well("?", "\u001B[37m"),
    ShippingBin("0", "\u001B[37m"),

    FishShop("F", "\u001B[33m"),
    JojaMart("J", "\u001B[33m"),
    Blacksmith("B", "\u001B[33m"),
    PierresGeneralStore("I", "\u001B[33m"),
    MarniesRanch("R", "\u001B[33m"),
    CarpentersShop("C", "\u001B[33m"),
    TheStarDropSaloon("K", "\u001B[33m"),

    Door("D", "\u001B[30m"),
    DontKnowBuildings("?", "\u001B[30m"),
    ;

    private final String symbol;
    private final String colorCode;

    Symbols(String symbol, String colorCode) {
        this.symbol = symbol;
        this.colorCode = colorCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColoredSymbol() {
        return colorCode + symbol + "\u001B[0m";
    }
}
