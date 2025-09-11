package io.Ap.StardewValley.Common.Model.Artisan;

public enum PreservesJar {
    Juice_Amaranth("A jar of your home-made pickles." , 100 , 6 , 350),
    Juice_Artichoke("A jar of your home-made pickles." , 60 , 6 , 370),
    Juice_Beet("A jar of your home-made pickles." , 60 , 6 , 250),
    Juice_BokChoy("A jar of your home-made pickles." , 50 , 6 , 210),
    Juice_Broccoli("A jar of your home-made pickles." , 126 , 6 , 190),
    Juice_Carrot("A jar of your home-made pickles." , 150 , 6 , 120),
    Juice_Cauliflower("A jar of your home-made pickles." , 150 , 6 , 400),
    Juice_Corn("A jar of your home-made pickles." , 50 , 6 , 150),
    Juice_Eggplant("A jar of your home-made pickles." , 40 ,6 , 170),
    Juice_FiddleheadFern("A jar of your home-made pickles." , 50 , 6 , 230),
    Juice_Garlic("A jar of your home-made pickles." , 40 , 6 , 170),
    Juice_GreenBean("A jar of your home-made pickles." , 50 , 6 , 130),
    Juice_Hops("A jar of your home-made pickles." , 90 , 6 , 100),
    Juice_Kale("A jar of your home-made pickles." , 100 , 6 , 270),
    Juice_Parsnip("A jar of your home-made pickles." , 50 , 6 , 120),
    Juice_Potato("A jar of your home-made pickles." , 50 , 6 , 210),
    Juice_Pumpkin("A jar of your home-made pickles." , 0 , 6 , 690),
    Juice_Radish("A jar of your home-made pickles." , 90 , 6 , 230),
    Juice_RedCabbage("A jar of your home-made pickles." , 150 , 6 , 570),
    Juice_SummerSquash("A jar of your home-made pickles." , 126 , 6 , 140),
    Juice_TaroRoot("A jar of your home-made pickles." , 76 , 6 , 250),
    Juice_TeaLeaves("A jar of your home-made pickles." , 0 , 6 , 150),
    Juice_Tomato("A jar of your home-made pickles." , 40 , 6 , 170),
    Juice_UnmilledRice("A jar of your home-made pickles." , 6 , 6 , 110),
    Juice_Wheat("A jar of your home-made pickles." , 0 , 6 , 100),
    Juice_Yam("A jar of your home-made pickles." , 90 , 6 , 370),
    Jelly("Gooey." , 0 , 72 , 0);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    PreservesJar(String description, int energy, int processingTime, int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
