package io.Ap.StardewValley.Common.Model.Plants;


import io.Ap.StardewValley.Client.Controller.SirkBozorg.PlantController;
import io.Ap.StardewValley.Common.Model.Time.Season;

import java.util.ArrayList;
import java.util.List;

public enum CropType implements PlantType{


    BlueJazz ("Blue Jazz", SeedType.JazzSeeds, new int[] {1, 2, 2, 2},
            7, true, 0, 50, true, 45,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Carrot ("Carrot", SeedType.CarrotSeeds, new int[] {1, 1, 1},
            3, true, 0, 35, true, 75,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Cauliflower ("Cauliflower", SeedType.CauliflowerSeeds, new int[] {1, 2, 4, 4, 1},
            12, true, 0, 175, true, 75,
            new ArrayList<>(List.of(Season.Spring)), true, false),

    CoffeeBean ("Coffee Bean", SeedType.CoffeeBean, new int[] {1, 2, 2, 3, 2},
            10, false, 2, 15, false, 0,
            new ArrayList<>(List.of(Season.Spring, Season.Summer)), false, false),

    Garlic ("Garlic", SeedType.GarlicSeeds, new int[] {1, 1, 1, 1},
            4, true, 0, 60, true, 20,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    GreenBean ("Green Bean", SeedType.BeanStarter, new int[] {1, 1, 1, 3, 4},
            10, false, 3, 40, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Kale ("Kale", SeedType.KaleSeeds, new int[] {1, 2, 2, 1},
            6, true, 0, 110, true, 50,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Parsnip ("Parsnip", SeedType.ParsnipSeeds, new int[] {1, 1, 1, 1},
            4, true, 0, 35, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Potato ("Potato", SeedType.PotatoSeeds, new int[] {1, 1, 1, 2, 1},
            6, true, 0, 80, true, 25,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Rhubarb ("Rhubarb", SeedType.RhubarbSeeds, new int[] {2, 2, 2, 3, 4},
            13, true, 0, 220, false, 0,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Strawberry ("Strawberry", SeedType.StrawberrySeeds, new int[] {1, 1, 2, 2, 2},
            8, false, 4, 120, true, 50,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Tulip ("Tulip", SeedType.TulipBulb, new int[] {1, 1, 2, 2},
            6, true, 0, 30, true, 45,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    UnmilledRice ("Unmilled Rice", SeedType.RiceShoot, new int[] {1, 2, 2, 3},
            8, true, 0, 30, true, 3,
            new ArrayList<>(List.of(Season.Spring)), false, false),

    Blueberry ("Blueberry", SeedType.BlueberrySeeds, new int[] {1, 3, 3, 4, 2},
            13, false, 4, 50, true, 25,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    Corn ("Corn", SeedType.CornSeeds, new int[] {2, 3, 3, 3, 3},
            14, false, 4, 50, true, 25,
            new ArrayList<>(List.of(Season.Summer, Season.Fall)), false, false),

    Hops ("Hops", SeedType.HopsStarter, new int[] {1, 1, 2, 3, 4},
            11, false, 1, 25, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    HotPepper ("Hot Pepper", SeedType.PepperSeeds, new int[] {1, 1, 1, 1, 1},
            5, false, 3, 40, true, 13,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    Melon ("Melon", SeedType.MelonSeeds, new int[] {1, 2, 3, 3, 3},
            12, true, 0, 250, true, 113,
            new ArrayList<>(List.of(Season.Summer)), true, false),

    Poppy ("Poppy", SeedType.PoppySeeds, new int[] {1, 2, 2, 2},
            7, true, 0, 140, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    Radish ("Radish", SeedType.RadishSeeds, new int[] {2, 1, 2, 1},
            6, true, 0, 90, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    RedCabbage ("Red Cabbage", SeedType.RedCabbageSeeds, new int[] {2, 1, 2, 2, 2},
            9, true, 0, 260, true, 75,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    Starfruit ("Starfruit", SeedType.StarfruitSeeds, new int[] {2, 3, 2, 3, 3},
            13, true, 0, 750, true, 125,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    SummerSpangle ("Summer Spangle", SeedType.SpangleSeeds, new int[] {1, 2, 3, 1},
            8, true, 0, 90, true, 45,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    SummerSquash ("Summer Squash", SeedType.SummerSquashSeeds, new int[] {1, 1, 1, 2, 1},
            6, false, 3, 45, true, 63,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    Sunflower ("Sunflower", SeedType.SunflowerSeeds, new int[] {1, 2, 3, 2},
            8, true, 0, 80, true, 45,
            new ArrayList<>(List.of(Season.Summer, Season.Fall)), false, false),

    Tomato ("Tomato", SeedType.TomatoSeeds, new int[] {2, 2, 2, 2, 3},
            11, false, 4, 60, true, 20,
            new ArrayList<>(List.of(Season.Summer)), false, false),

    Wheat ("Wheat", SeedType.WheatSeeds, new int[] {1, 1, 1, 1},
            4, true, 0, 25, false, 0,
            new ArrayList<>(List.of(Season.Summer, Season.Fall)), false, false),

    Amaranth ("Amaranth", SeedType.AmaranthSeeds, new int[] {1, 2, 2, 2},
            7, true, 0, 150, true, 50,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    Artichoke ("Artichoke", SeedType.ArtichokeSeeds, new int[] {2, 2, 1, 2, 1},
            8, true, 0, 160, true, 30,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    Beet ("Beet", SeedType.BeetSeeds, new int[] {1, 1, 2, 2},
            6, true, 0, 100, true, 30,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    BokChoy ("Bok Choy", SeedType.BokChoySeeds, new int[] {1, 1, 1, 1},
            4, true, 0, 80, true, 25,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    Broccoli ("Broccoli", SeedType.BroccoliSeeds, new int[] {2, 2, 2, 2},
            8, false, 4, 70, true, 63,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    Cranberries ("Cranberries", SeedType.CranberrySeeds, new int[] {1, 2, 1, 1, 2},
            7, false, 5, 75, true, 38,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    Eggplant ("Eggplant", SeedType.EggplantSeeds, new int[] {1, 1, 1, 1, 1},
            5, false, 5, 60, true, 20,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    FairyRose ("Fairy Rose", SeedType.FairySeeds, new int[] {1, 4, 4, 3},
            12, true, 0, 290, true, 45,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    Grape ("Grape", SeedType.GrapeStarter, new int[] {1, 1, 2, 3, 3},
            10, false, 3, 80, true, 38,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    Pumpkin ("Pumpkin", SeedType.PumpkinSeeds, new int[] {1, 2, 3, 4, 3},
            13, true, 0, 320, false, 0,
            new ArrayList<>(List.of(Season.Fall)), true, false),

    Yam ("Yam", SeedType.YamSeeds, new int[] {1, 3, 3, 3},
            10, true, 0, 160, true, 45,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    SweetGemBerry ("Sweet Gem Berry", SeedType.RareSeed, new int[] {2, 4, 6, 6, 6},
            24, true, 0, 3000, false, 0,
            new ArrayList<>(List.of(Season.Fall)), false, false),

    Powdermelon ("Powdermelon", SeedType.PowdermelonSeeds, new int[] {1, 2, 1, 2, 1},
            7, true, 0, 60, true, 63,
            new ArrayList<>(List.of(Season.Winter)), true, false),

    AncientFruit ("Ancient Fruit", SeedType.AncientSeeds, new int[] {2, 7, 7, 7, 5},
            28, false, 7, 550, false, 0,
            new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall)), false, false),

    Grass ("Grass", SeedType.GrassStarter, new int[] {0}, 0, true, 0,
            50, false, 0, new ArrayList<>(List.of(Season.Spring, Season.Summer, Season.Fall, Season.Winter)),
            false, false),

    Mixed (null, null, null, 0, false, 0,
                   0, false, 0, null, false, true);


    private final String name;
    private final SeedType source;
    private final int[] stages;
    private final int harvestTime;
    private final boolean oneTime;
    private final int regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final ArrayList<Season> seasons;
    private final boolean canBecomeGiant;
    private final boolean isMixed;

    CropType (String name, SeedType source, int[] stages, int harvestTime, boolean oneTime,
              int regrowthTime, int baseSellPrice, boolean isEdible, int energy,
              ArrayList<Season> seasons, boolean canBecomeGiant, boolean isMixed) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.harvestTime = harvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.seasons = seasons;
        this.canBecomeGiant = canBecomeGiant;
        this.isMixed = isMixed;
    }

    public String getName() {
        return name;
    }

    public SeedType getSource() {
        return source;
    }

    public int[] getStages() {
        return stages;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public boolean canBecomeGiant() {
        return canBecomeGiant;
    }

    public boolean isMixed() {
        return isMixed;
    }

    @Override
    public String getInformation () {
        if (name != null) {
            return "crop information:\n" + "name: " + name + "\nsource: " + source.getName() + "\nstages: " +
                    PlantController.stagesToString(stages) + "\ntotal harvest time: " + harvestTime +
                    "\n" + "one time: " + oneTime + "\nregrowth time: " + regrowthTime +
                    "\nbase sell price: " + baseSellPrice + "\nis edible: " + isEdible +
                    "\nbase energy: " + energy + "\nseason: " + PlantController.SeasonsToString(seasons) +
                    "\ncan become giant: " + canBecomeGiant;
        }
        return "\n";
    }
}
