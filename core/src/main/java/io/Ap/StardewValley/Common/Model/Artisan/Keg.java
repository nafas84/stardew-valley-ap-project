package io.Ap.StardewValley.Common.Model.Artisan;

public enum Keg {
    Beer("Drink in moderation." , 50 , 24 , 200),
    Vinegar("An aged fermented liquid used in many cooking recipes." , 13 , 10 , 100),
    Coffee("It smells delicious. This is sure to give you a boost." , 75 , 2 , 150),
    Juice_Amaranth("A sweet, nutritious beverage." , 100 , 48 , 337),
    Juice_Artichoke("A sweet, nutritious beverage." , 60 , 48 , 360),
    Juice_Beet("A sweet, nutritious beverage." , 60 , 48 , 225),
    Juice_BokChoy("A sweet, nutritious beverage." , 50 , 48 , 180),
    Juice_Broccoli("A sweet, nutritious beverage." , 126 , 48 , 157),
    Juice_Carrot("A sweet, nutritious beverage." , 150 , 48 , 78),
    Juice_Cauliflower("A sweet, nutritious beverage." , 150 , 48 , 393),
    Juice_Corn("A sweet, nutritious beverage." , 50 , 48 , 112),
    Juice_Eggplant("A sweet, nutritious beverage." , 40 ,48 , 135),
    Juice_FiddleheadFern("A sweet, nutritious beverage." , 50 , 48 , 202),
    Juice_Garlic("A sweet, nutritious beverage." , 40 , 48 , 135),
    Juice_GreenBean("A sweet, nutritious beverage." , 50 , 48 , 90),
    Juice_Hops("A sweet, nutritious beverage." , 90 , 48 , 300),
    Juice_Kale("A sweet, nutritious beverage." , 100 , 48 , 247),
    Juice_Parsnip("A sweet, nutritious beverage." , 50 , 48 , 78),
    Juice_Potato("A sweet, nutritious beverage." , 50 , 48 , 180),
    Juice_Pumpkin("A sweet, nutritious beverage." , 0 , 48 , 720),
    Juice_Radish("A sweet, nutritious beverage." , 90 , 48 , 202),
    Juice_RedCabbage("A sweet, nutritious beverage." , 150 , 48 , 585),
    Juice_SummerSquash("A sweet, nutritious beverage." , 126 , 48 , 101),
    Juice_TaroRoot("A sweet, nutritious beverage." , 76 , 48 , 225),
    Juice_TeaLeaves("A sweet, nutritious beverage." , 0 , 48 , 100),
    Juice_Tomato("A sweet, nutritious beverage." , 40 , 48 , 135),
    Juice_UnmilledRice("A sweet, nutritious beverage." , 6 , 48 , 67),
    Juice_Yam("A sweet, nutritious beverage." , 90 , 48 , 360),
    Mead("A fermented beverage made from honey.\nDrink in moderation." , 100 , 10 , 300),
    PaleAle("Drink in moderation." , 50 , 72 , 300),
    Wine_AncientFruit("Drink in moderation." , 0 , 168 , 1650),
    Wine_Apple("Drink in moderation." , 66 ,168 , 300),
    Wine_Apricot("Drink in moderation." , 66 , 168 , 150),
    Wine_Banana("Drink in moderation." , 131 , 168 , 450),
    Wine_Blackberry("Drink in moderation." , 43 , 168 , 60),
    Wine_Blueberry("Drink in moderation." , 43 , 168 , 150),
    Wine_CactusFruit("Drink in moderation." , 131 , 168 , 225),
    Wine_Cherry("Drink in moderation." , 66 , 168 , 240),
    Wine_Coconut("Drink in moderation." , 0 , 168 , 300),
    Wine_Cranberries("Drink in moderation." , 66 , 168 ,225),
    Wine_CrystalFruit("Drink in moderation." , 110 , 168 , 450),
    Wine_Grape("Drink in moderation." , 66 , 168 , 240),
    Wine_HotPepper("Drink in moderation." , 22 , 168 , 120),
    Wine_Mango("Drink in moderation." , 175 , 168 , 390),
    Wine_Melon("Drink in moderation." , 197 , 168 , 750),
    Wine_Orange("Drink in moderation." , 66 , 168 , 300),
    Wine_Peach("Drink in moderation." , 66 , 168 , 420),
    Wine_Pineapple("Drink in moderation." , 241 , 168 , 900),
    Wine_Pomegranate("Drink in moderation." , 66 , 168 , 420),
    Wine_Powdermelon("Drink in moderation." , 110 , 168 , 180),
    Wine_QiFruit("Drink in moderation." , 5 , 168 , 3),
    Wine_Rhubarb("Drink in moderation." , 0 , 168 , 660),
    Wine_Salmonberry("Drink in moderation." , 43 , 168 , 15),
    Wine_SpiceBerry("Drink in moderation." , 43 , 168 , 240),
    Wine_Starfruit("Drink in moderation." , 218 , 168 , 2250),
    Wine_Strawberry("Drink in moderation." , 87 , 168 , 360),
    Wine_WildPlum("Drink in moderation." , 43 , 168 , 240);

    private final String description;
    private final int energy;
    private final int processingTime;
    private final int sellPrice;

    Keg (String description , int energy , int processingTime , int sellPrice) {
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.sellPrice = sellPrice;
    }
}
