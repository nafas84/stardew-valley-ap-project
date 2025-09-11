package io.Ap.StardewValley.Common.Model.Artisan;

public enum Dehydrator {
    DriedMushrooms_Chanterelle("A package of gourmet mushrooms." , 50 , 0 , 1225),
    DriedMushrooms_CommonMushroom("A package of gourmet mushrooms." , 50 , 0 , 325),
    DriedMushrooms_MagmaCap("A package of gourmet mushrooms." , 50 , 0 , 3025),
    DriedMushrooms_Morel("A package of gourmet mushrooms." , 50 , 0 , 1150),
    DriedMushrooms_PurpleMushroom("A package of gourmet mushrooms." , 50 , 0 , 1900),
    DriedFruit_AncientFruit("Chewy pieces of dried fruit." , 50 , 0 , 4150),
    DriedFruit_Apple("Chewy pieces of dried fruit." , 50 ,0 , 775),
    DriedFruit_Apricot("Chewy pieces of dried fruit." , 50 , 0 , 400),
    DriedFruit_Banana("Chewy pieces of dried fruit." , 50 , 0 , 1150),
    DriedFruit_Blackberry("Chewy pieces of dried fruit." , 50 , 0 , 175),
    DriedFruit_Blueberry("Chewy pieces of dried fruit." , 50 , 0 , 400),
    DriedFruit_CactusFruit("Chewy pieces of dried fruit." , 50 , 0 , 587),
    DriedFruit_Cherry("Chewy pieces of dried fruit." , 50 , 0 , 625),
    DriedFruit_Coconut("Chewy pieces of dried fruit." , 50 , 0 , 775),
    DriedFruit_Cranberries("Chewy pieces of dried fruit." , 50 , 0 ,587),
    DriedFruit_CrystalFruit("Chewy pieces of dried fruit." , 50 , 0 , 1150),
    DriedFruit_Grape("Chewy pieces of dried fruit." , 50 , 0 , 600),
    DriedFruit_HotPepper("Chewy pieces of dried fruit." , 50 , 0 , 325),
    DriedFruit_Mango("Chewy pieces of dried fruit." , 50 , 0 , 1000),
    DriedFruit_Melon("Chewy pieces of dried fruit." , 50 , 0 , 1900),
    DriedFruit_Orange("Chewy pieces of dried fruit." ,  50 , 0 , 775),
    DriedFruit_Peach("Chewy pieces of dried fruit." , 50 , 0 , 1075),
    DriedFruit_Pineapple("Chewy pieces of dried fruit." , 50 , 0 , 2275),
    DriedFruit_Pomegranate("Chewy pieces of dried fruit." , 50 , 0 , 1075),
    DriedFruit_Powdermelon("Chewy pieces of dried fruit." , 50 , 0 , 475),
    DriedFruit_QiFruit("Chewy pieces of dried fruit." , 50 , 0 , 32),
    DriedFruit_Rhubarb("Chewy pieces of dried fruit." , 50 , 0 , 1675),
    DriedFruit_Salmonberry("Chewy pieces of dried fruit." , 50 , 0 , 62),
    DriedFruit_SpiceBerry("Chewy pieces of dried fruit." , 50 , 0 , 625),
    DriedFruit_Starfruit("Chewy pieces of dried fruit." , 50 , 0 , 5650),
    DriedFruit_Strawberry("Chewy pieces of dried fruit." , 50 , 0 , 925),
    DriedFruit_WildPlum("Chewy pieces of dried fruit." , 50 , 0 , 625),
    Raisins("It's said to be the Junimos' favorite food." , 125 , 0 , 600);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    Dehydrator(String description , int energy , int processingTime , int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
