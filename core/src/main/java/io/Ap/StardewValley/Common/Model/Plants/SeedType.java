package io.Ap.StardewValley.Common.Model.Plants;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlantController;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.List;

public enum SeedType implements Forageable, PlantType {
    JazzSeeds("Jazz Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.BlueJazz, "Blue Jazz"),
    CarrotSeeds("Carrot Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.Carrot, "Carrot"),
    CauliflowerSeeds("Cauliflower Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.Cauliflower, "Cauliflower"),
    CoffeeBean("Coffee Bean", new ArrayList<>(List.of(Season.Spring)), CropType.CoffeeBean, "Coffee Bean"),
    GarlicSeeds("Garlic Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.Garlic, "Garlic"),
    BeanStarter("Bean Starter", new ArrayList<>(List.of(Season.Spring)), CropType.GreenBean, "Green Bean"),
    KaleSeeds("Kale Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.Kale, "Kale"),
    ParsnipSeeds("Parsnip Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.Parsnip, "Parsnip"),
    PotatoSeeds("Potato Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.Potato, "Potato"),
    RhubarbSeeds("Rhubarb Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.Rhubarb, "Rhubarb"),
    StrawberrySeeds("Strawberry Seeds", new ArrayList<>(List.of(Season.Spring)), CropType.Strawberry, "Strawberry"),
    TulipBulb("Tulip Bulb", new ArrayList<>(List.of(Season.Spring)), CropType.Tulip, "Tulip"),
    RiceShoot("Rice Shoot", new ArrayList<>(List.of(Season.Spring)), CropType.UnmilledRice, "Unmilled Rice"),

    BlueberrySeeds("Blueberry Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Blueberry, "Blueberry"),
    CornSeeds("Corn Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Corn, "Corn"),
    HopsStarter("Hops Starter", new ArrayList<>(List.of(Season.Summer)), CropType.Hops, "Hops"),
    PepperSeeds("Pepper Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.HotPepper, "Hot Pepper"),
    MelonSeeds("Melon Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Melon, "Melon"),
    PoppySeeds("Poppy Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Poppy, "Poppy"),
    RadishSeeds("Radish Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Radish, "Radish"),
    RedCabbageSeeds("Red Cabbage Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.RedCabbage, "Red Cabbage"),
    StarfruitSeeds("Starfruit Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Starfruit, "Starfruit"),
    SpangleSeeds("Spangle Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.SummerSpangle, "Summer Spangle"),
    SummerSquashSeeds("Summer Squash Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.SummerSquash, "Summer Squash"),
    SunflowerSeeds("Sunflower Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Sunflower, "Sunflower"),
    TomatoSeeds("Tomato Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Tomato, "Tomato"),
    WheatSeeds("Wheat Seeds", new ArrayList<>(List.of(Season.Summer)), CropType.Wheat, "Wheat"),

    AmaranthSeeds("Amaranth Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.Amaranth, "Amaranth"),
    ArtichokeSeeds("Artichoke Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.Artichoke, "Artichoke"),
    BeetSeeds("Beet Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.Beet, "Beet"),
    BokChoySeeds("Bok Choy Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.BokChoy, "Bok Choy"),
    BroccoliSeeds("Broccoli Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.Broccoli, "Broccoli"),
    CranberrySeeds("Cranberry Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.Cranberries, "Cranberries"),
    EggplantSeeds("Eggplant Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.Eggplant, "Eggplant"),
    FairySeeds("Fairy Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.FairyRose, "Fairy Rose"),
    GrapeStarter("Grape Starter", new ArrayList<>(List.of(Season.Fall)), CropType.Grape, "Grape"),
    PumpkinSeeds("Pumpkin Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.Pumpkin, "Pumpkin"),
    YamSeeds("Yam Seeds", new ArrayList<>(List.of(Season.Fall)), CropType.Yam, "Yam"),
    RareSeed("Rare Seed", new ArrayList<>(List.of(Season.Fall)), CropType.SweetGemBerry, "Sweet Gem Berry"),

    PowdermelonSeeds("Powdermelon Seeds", new ArrayList<>(List.of(Season.Winter)), CropType.Powdermelon, "Powdermelon"),
    AncientSeeds("Ancient Seeds", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall)), CropType.AncientFruit, "Ancient Fruit"),
    GrassStarter("Grass Starter", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), CropType.Grass, "Grass"),
    MixedSeeds("Mixed Seeds", new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)), CropType.Mixed, "Mixed");



    private final String cropName;
    private final String name;
    private final ArrayList<Season> seasons;
    private final CropType crop;

    SeedType (String name, ArrayList<Season> seasons, CropType crop, String cropName) {
        this.cropName = cropName;
        this.name = name;
        this.seasons = seasons;
        this.crop = crop;
    }


    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public CropType getCrop() {
        return crop;
    }

    @Override
    public boolean isForageable () {
        return true;
    }

    @Override
    public String getInformation() {
        return "seed information:\nname: " + name + "\nseason: " + PlantController.SeasonsToString(seasons);
    }

    public String getCropName() {
        return cropName;
    }
}
