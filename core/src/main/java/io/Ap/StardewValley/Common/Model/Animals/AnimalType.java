package io.Ap.StardewValley.Common.Model.Animals;

import io.Ap.StardewValley.Common.Model.Map.FarmBuildingType;

import java.util.ArrayList;
import java.util.List;

public enum AnimalType {
    Chicken("Chicken", "Well cared-for chickens lay eggs every day. Lives in the coop." , FarmBuildingType.Coop, new ArrayList<>(List.of(AnimalProductType.Egg, AnimalProductType.BigEgg)), 800),
    Duck("Duck", "Happy lay duck eggs every other day. Lives in the coop." , FarmBuildingType.BigCoop, new ArrayList<>(List.of(AnimalProductType.DuckEgg, AnimalProductType.DuckFeather)), 1200),
    Rabbit("Rabbit", "These are wooly rabbits! They shed precious wool every few days. Lives in the coop." ,FarmBuildingType.DeluxeCoop, new ArrayList<>(List.of(AnimalProductType.RabbitFur, AnimalProductType.RabbitFoot)), 8000),
    Dinosaur("Dinosaur", "The Dinosaur is a farm animal that lives in a Big Coop" , FarmBuildingType.BigCoop, new ArrayList<>(List.of(AnimalProductType.DinosaurEgg)), 14000),
    Cow("Cow", "Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn." ,FarmBuildingType.Barn, new ArrayList<>(List.of(AnimalProductType.CowMilk, AnimalProductType.BigCowMilk)), 1500),
    Goat("Goat", "Happy provide goat milk every other day. A milk pail is required to harvest the milk. Lives in the barn." ,FarmBuildingType.BigBarn, new ArrayList<>(List.of(AnimalProductType.GoatMilk, AnimalProductType.BigGoatMilk)), 4000),
    Sheep("Sheep", "Can be shorn for wool. A pair of shears is required to harvest the wool. Lives in the barn." ,FarmBuildingType.DeluxeBarn, new ArrayList<>(List.of(AnimalProductType.SheepWool)), 8000),
    Pig("Pig", "These pigs are trained to find truffles! Lives in the barn." , FarmBuildingType.DeluxeBarn, new ArrayList<>(List.of(AnimalProductType.Truffle)), 16000);

    private final String name;
    private final String description;
    private final FarmBuildingType buildingType;
    private final ArrayList<AnimalProductType> products;
    private final int price;

    AnimalType(String name, String description, FarmBuildingType buildingType, ArrayList<AnimalProductType> products, int price) {
        this.name = name;
        this.description = description;
        this.buildingType = buildingType;
        this.products = products;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public FarmBuildingType getFarmBuildingType() {
        return buildingType;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<AnimalProductType> getProducts() {
        return products;
    }
}
