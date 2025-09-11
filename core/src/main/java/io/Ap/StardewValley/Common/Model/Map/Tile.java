package io.Ap.StardewValley.Common.Model.Map;

import io.Ap.StardewValley.Common.Model.Animals.Animal;
import io.Ap.StardewValley.Common.Model.Animals.AnimalProduct;
import io.Ap.StardewValley.Common.Model.Animals.Fish;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Cooking.Food;
import io.Ap.StardewValley.Common.Model.Crafting.Craft;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.NPC.NPC;
import io.Ap.StardewValley.Common.Model.Plants.*;
import io.Ap.StardewValley.Common.Model.Plants.*;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;


public class Tile {
    private TileType type;

    private BuildingType buildingType = null;
    private boolean BuildingOrigin = false;

    private Item item = null;
    private Animal animal = null;
    private NPC npc = null; // TODO: npc nadarim felan dosrost shod add mikonam

    private boolean isPavement = false;
    private boolean isPlowed = false;
    private boolean isWatered = false;

    private DateAndTime lastTimeWatered = null;
    private int fertilize = 0;


    public Tile(TileType type) { // For Water, Mountain, Mine
        this.type = type;
    }

    public Tile(TileType type, BuildingType buildingType) { // For Building
        this.type = type;
        this.buildingType = buildingType;
    }

    public Tile(TileType type, boolean isPavement, Item item, NPC npc){ // For Ground -> Wood, Rail
        this.type = type;
        this.isPavement = isPavement;
        this.item = item;
        this.npc = npc;
    }

    public String getSymbol() {
        switch (this.type) {
            case Building:
                switch (this.buildingType) {
                    case GreenHouse:
                        return Symbols.GreenHouse.getColoredSymbol();
                    case GreenHouseBuild:
                        return Symbols.GreenHouserBuild.getColoredSymbol();
                    case House:
                        return Symbols.House.getColoredSymbol();
                    case Barn:
                        return Symbols.Barn.getColoredSymbol();
                    case Coop:
                        return Symbols.Coop.getColoredSymbol();
                    case Well:
                        return Symbols.Well.getColoredSymbol();
                    case ShippingBin:
                        return Symbols.ShippingBin.getColoredSymbol();
                    case Blacksmith:
                        return Symbols.Blacksmith.getColoredSymbol();
                    case JojaMart:
                        return Symbols.JojaMart.getColoredSymbol();
                    case PierresGeneralStore:
                        return Symbols.PierresGeneralStore.getColoredSymbol();
                    case CarpentersShop:
                        return Symbols.CarpentersShop.getColoredSymbol();
                    case FishShop:
                        return Symbols.FishShop.getColoredSymbol();
                    case MarniesRanch:
                        return Symbols.MarniesRanch.getColoredSymbol();
                    case TheStarDropSaloon:
                        return Symbols.TheStarDropSaloon.getColoredSymbol();
                    case Door:
                        return Symbols.Door.getColoredSymbol();
                    case DontKnow:
                        return Symbols.DontKnowBuildings.getSymbol();
                    default:
                        throw new IllegalArgumentException("Invalid tile");
                }
            case Ground:
                if (this.item instanceof Tree) {
                    return Symbols.Tree.getColoredSymbol();
                } else if (this.item instanceof Sapling) {
                    return Symbols.Sapling.getColoredSymbol();
                } else if (this.item instanceof Crop) {
                    return Symbols.Crop.getColoredSymbol();
                } else if (this.item instanceof Fruit) {
                    return Symbols.Fruit.getColoredSymbol();
                } else if (this.item instanceof Seed) {
                    return Symbols.Seed.getColoredSymbol();
                } else if (this.item instanceof ForagingCrop) {
                    return Symbols.ForagingCrop.getColoredSymbol();
                } else if (this.item instanceof ForagingMineral) {
                    return Symbols.ForagingMineral.getColoredSymbol();
                } else if (this.item instanceof Stone) {
                    return Symbols.Stone.getColoredSymbol();
                } else if (this.item instanceof Wood) {
                    return Symbols.Wood.getColoredSymbol();
                } else if (this.item instanceof Craft) {
                    return Symbols.Craft.getColoredSymbol();
                } else if (this.item instanceof Food) {
                    return Symbols.Food.getColoredSymbol();
                } else if (this.item instanceof Fish) {
                    return Symbols.Fish.getColoredSymbol();
                } else if (this.item instanceof AnimalProduct) {
                    return Symbols.AnimalProduct.getColoredSymbol();
                }

                if (this.npc != null) {
                    return Symbols.NPC.getColoredSymbol();
                }

                if (this.animal != null) {
                    return Symbols.Animal.getColoredSymbol();
                }

                if (this.isPavement) {
                    return Symbols.Pavement.getColoredSymbol();
                }

                if (this.item == null) {
                    return Symbols.Ground.getColoredSymbol();
                }
            case Water:
                return Symbols.Water.getColoredSymbol();
            case Mountain:
                return Symbols.Mountain.getColoredSymbol();
            case Mine:
                return Symbols.Mine.getColoredSymbol();
            default:
                throw new IllegalArgumentException("Invalid tile");
        }
    }

    public boolean isWalkable() {
        switch (this.type) {
            case Building:
                if (this.buildingType.equals(BuildingType.GreenHouseBuild)
                        || this.buildingType.equals(BuildingType.House))
                    return true;
                return false;
            case Ground:
                if (this.item instanceof Tree) {
                    return false;
                } else if (this.item instanceof Sapling) {
                    return false;
                } else if (this.item instanceof ForagingMineral) {
                    return false;
                } else if (this.item instanceof Stone) {
                    return false;
                } else if (this.item instanceof Wood) {
                    return false;
                } else if (this.npc != null) {
                    return false;
                } else {
                    return true;
                }
//                if (item != null) {
//                    return true;
//                } else {
//                    return false;
//                }
            case Water, Mountain:
                return false;
            case Mine:
                return true;
            default:
                throw new IllegalArgumentException("Invalid tile");
        }
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setPlowed(boolean plowed) {
        isPlowed = plowed;
    }

    public TileType getType() {
        return type;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
        if (watered) {
            lastTimeWatered = new DateAndTime(App.getGame().getCurrentTime().getHour(),
                    App.getGame().getCurrentTime().getDay(), App.getGame().getCurrentTime().getWeather());
        }
    }

    public Item getItem() {
        return item;
    }

    public NPC getNpc() {
        return npc;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public DateAndTime getLastTimeWatered() {
        return lastTimeWatered;
    }

    public void setLastTimeWatered(DateAndTime lastTimeWatered) {
        this.lastTimeWatered = lastTimeWatered;
    }

    public int getFertilize() {
        return fertilize;
    }

    public void setFertilize(int fertilize) {
        this.fertilize = fertilize;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public void setBuildingOrigin(boolean buildingOrigin) {
        BuildingOrigin = buildingOrigin;
    }

    public boolean isBuildingOrigin() {
        return BuildingOrigin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: ").append(type).append("\n");

        if (buildingType != null)
            sb.append("Building Type: ").append(buildingType.name()).append("\n");

        if (item != null)
            sb.append("Item: ").append(item.getName()).append("\n");

        if (animal != null)
            sb.append("Animal: ").append(animal).append("\n");

        if (npc != null)
            sb.append("NPC: ").append(npc).append("\n");

        sb.append("isPlowed: ").append(isPlowed).append("\n");
        sb.append("isWatered: ").append(isWatered);

        return sb.toString();
    }

}
