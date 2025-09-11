package io.Ap.StardewValley.Common.Model.Item;

public enum ItemType {
    Tree("tree"), // !price, !quality
    Sapling("sapling"), // !price, !quality
    Crop("crop"), // basePrice, quality?
    Fruit("fruit"), // basePrice, quality?
    Seed("seed"), // shopPrice, !quality?
    ForagingCrop("foraging crop"), // basePrice, quality?
    ForagingMineral("foraging mineral"), // sellPrice, quality?

    Stone("stone"), // shopPrice
    Wood("wood"), // shopPrice

    Craft("craft"), // price, quality?
    Food("food"), // price, quality?

    Fish("fish"), // price, quality
    AnimalProduct("animal product"), // price, quality

    Ingredients("ingredient"), // price, quality?
    Fertilizer("fertilizer"), // price, quality?
    Gift("gift"), // price

    Tool("tool"),
    ;

    private final String name;

    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
