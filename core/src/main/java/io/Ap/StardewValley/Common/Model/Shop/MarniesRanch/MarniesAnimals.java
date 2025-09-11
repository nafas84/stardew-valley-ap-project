package io.Ap.StardewValley.Common.Model.Shop.MarniesRanch;

import io.Ap.StardewValley.Common.Model.Animals.AnimalType;
import io.Ap.StardewValley.Common.Model.Map.FarmBuildingType;

public enum MarniesAnimals {
    Chicken(AnimalType.Chicken, "Chicken", "Well cared-for chickens lay eggs every day. Lives in the coop." , 800 , FarmBuildingType.Coop , 2),
    Cow(AnimalType.Cow, "Cow", "Can be milked daily. A milk pail is required to harvest the milk. Lives in the barn." , 1500 , FarmBuildingType.Barn , 2),
    Goat(AnimalType.Goat, "Goat", "Happy provide goat milk every other day. A milk pail is required to harvest the milk. Lives in the barn." , 4000 , FarmBuildingType.BigBarn , 2),
    Duck(AnimalType.Duck, "Duck", "Happy lay duck eggs every other day. Lives in the coop." , 1200 , FarmBuildingType.BigCoop , 2),
    Sheep(AnimalType.Sheep, "Sheep", "Can be shorn for wool. A pair of shears is required to harvest the wool. Lives in the barn." , 8000 , FarmBuildingType.DeluxeBarn , 2),
    Rabbit(AnimalType.Rabbit, "Rabbit", "These are wooly rabbits! They shed precious wool every few days. Lives in the coop." , 8000 , FarmBuildingType.DeluxeCoop , 2),
    Dinosaur(AnimalType.Dinosaur, "Dinosaur", "The Dinosaur is a farm animal that lives in a Big Coop" , 14000 , FarmBuildingType.BigCoop , 2),
    Pig(AnimalType.Pig, "Pig", "These pigs are trained to find truffles! Lives in the barn." , 16000 , FarmBuildingType.DeluxeBarn , 2);


    private final AnimalType type;
    private final String name;
    private final String description;
    private final int price;
    private final FarmBuildingType buildingRequired;
    private final int dailyLimit;

    MarniesAnimals(AnimalType type, String name, String description, int price, FarmBuildingType buildingRequired, int dailyLimit) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.buildingRequired = buildingRequired;
        this.dailyLimit = dailyLimit;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getBuildingRequired() {
        return buildingRequired.getName();
    }

    public FarmBuildingType getBuildingType () {
        return buildingRequired;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public String getName() {
        return name;
    }

    public AnimalType getType() {
        return type;
    }
}
