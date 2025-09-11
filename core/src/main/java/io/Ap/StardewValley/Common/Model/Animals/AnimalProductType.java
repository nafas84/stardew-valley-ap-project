package io.Ap.StardewValley.Common.Model.Animals;

public enum AnimalProductType {
    Egg("Egg", 50),
    BigEgg("Big egg", 95),

    DuckEgg("Duck egg", 95),
    DuckFeather("Duck feather", 250),

    RabbitFur("Rabbit fur", 340),
    RabbitFoot("Rabbit foot", 565),

    DinosaurEgg("Dinosaur egg", 350),

    CowMilk("Cow milk", 125),
    BigCowMilk("Big cow milk", 190),
    GoatMilk("Goat milk", 225),
    BigGoatMilk("Big goat milk", 345),

    SheepWool("Sheep wool", 340),
    Truffle("Truffle", 625)
    ;

    private final String name;
    private final int price;

    AnimalProductType(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
