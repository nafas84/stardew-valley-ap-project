package io.Ap.StardewValley.Common.Model.Tool;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Crafting.Craft;
import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Item.Stone;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Plants.ForagingMineral;
import io.Ap.StardewValley.Common.Model.Plants.ForagingMineralType;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Time.Weather;

import java.util.ArrayList;
import java.util.Collections;

public class Pickaxe implements Tool {
    ToolType type = ToolType.Pickaxe;
    private ToolLevel level;
    int price;

    public Pickaxe() {
    }//needed for json

    public Pickaxe (ToolLevel level) {
        this.level = level;
    }

    @Override
    public Result upgrade() {
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() != BuildingType.Blacksmith) {
            return new Result(false, "you are not in black smith building!");
        }
        String pre = "previous level: ";
        String cur = "\ncurrent level: ";
        if (level == ToolLevel.Starter) {
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("copper", 25)) {
                return new Result(false, "you don't have enough copper ores!\n25 copper ores are needed.");
            }
            else if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("coal", 5)) {
                return new Result(false, "you don't have enough coal!\n5 pieces are needed.");
            }
            else if (App.getGame().getCurrentPlayer().getCount() < 2000) {
                return new Result(false, "you don't have enough money!\ncost: 2000g.");
            }
            App.getGame().getCurrentPlayer().addCount(-2000);
            App.getGame().getCurrentPlayer().removeItemFromInventory("copper", 25);
            App.getGame().getCurrentPlayer().removeItemFromInventory("coal", 5);
            price = 2000;
            level = ToolLevel.Copper;
            pre = pre + "Starter";
            cur = cur + "Copper";
            return new Result(true, "pickaxe upgraded successfully.\n" + pre + cur);
        }
        else if (level == ToolLevel.Copper) {
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("iron", 25)) {
                return new Result(false, "you don't have enough iron ores!\n25 iron ores are needed.");
            }
            else if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("coal", 5)) {
                return new Result(false, "you don't have enough coal!\n5 pieces are needed.");
            }
            else if (App.getGame().getCurrentPlayer().getCount() < 5000) {
                return new Result(false, "you don't have enough money!\ncost: 5000g.");
            }
            App.getGame().getCurrentPlayer().addCount(-5000);
            App.getGame().getCurrentPlayer().removeItemFromInventory("iron", 25);
            App.getGame().getCurrentPlayer().removeItemFromInventory("coal", 5);
            price = 5000;
            level = ToolLevel.Steel;
            pre = pre + "Copper";
            cur = cur + "Steel";
            return new Result(true, "pickaxe upgraded successfully.\n" + pre + cur);
        }
        else if (level == ToolLevel.Steel) {
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("gold", 25)) {
                return new Result(false, "you don't have enough gold ores!\n25 gold ores are needed.");
            }
            else if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("coal", 5)) {
                return new Result(false, "you don't have enough coal!\n5 pieces are needed.");
            }
            else if (App.getGame().getCurrentPlayer().getCount() < 10000) {
                return new Result(false, "you don't have enough money!\ncost: 10000g.");
            }
            App.getGame().getCurrentPlayer().addCount(-10000);
            App.getGame().getCurrentPlayer().removeItemFromInventory("gold", 25);
            App.getGame().getCurrentPlayer().removeItemFromInventory("coal", 5);
            price = 10000;
            level = ToolLevel.Gold;
            pre = pre + "Steel";
            cur = cur + "Gold";
            return new Result(true, "pickaxe upgraded successfully.\n" + pre + cur);
        }
        else if (level == ToolLevel.Gold) {
            if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("iridium", 25)) {
                return new Result(false, "you don't have enough iridium ores!\n25 iridium ores are needed.");
            }
            else if (!App.getGame().getCurrentPlayer().getInventory().hasItemWithNumber("coal", 5)) {
                return new Result(false, "you don't have enough coal!\n5 pieces are needed.");
            }
            else if (App.getGame().getCurrentPlayer().getCount() < 25000) {
                return new Result(false, "you don't have enough money!\ncost: 25000g.");
            }
            App.getGame().getCurrentPlayer().addCount(-25000);
            App.getGame().getCurrentPlayer().removeItemFromInventory("iridium", 25);
            App.getGame().getCurrentPlayer().removeItemFromInventory("coal", 5);
            price = 25000;
            level = ToolLevel.Iridium;
            pre = pre + "Gold";
            cur = cur + "Iridium";
            return new Result(true, "pickaxe upgraded successfully.\n" + pre + cur);
        }
        return new Result(false, "pickaxe is already upgraded!\ncurrent level: Iridium");
    }

    @Override
    public Result use(Tile tile) {
        Player player = App.getGame().getCurrentPlayer();
        if (tile == null) {
            return new Result(false, "invalid direction!");
        }
        if (tile.isPlowed()) {
            tile.setItem(null);
            tile.setPlowed(false);
            player.addEnergy(-1 * getEnergyConsumption(true));
            return new Result(true, "the selected tile is no longer plowed.");
        }
        if (tile.getItem() != null && tile.getItem() instanceof Stone) {
            if (player.getAbilityLevel(Skill.Mining) >= 2) {
                if (!player.addItemToInventory(new Stone(), 2)) {
                    player.addEnergy(-1 * getEnergyConsumption(false));
                    return new Result(false, "can't add the stone to inventory!");
                }
                player.addEnergy(-1 * getEnergyConsumption(false));
                player.addAbility(Skill.Mining, 10);
                player.addAbility(Skill.Foraging, 10);
                tile.setItem(null);
                return new Result(true, "you destroyed a stone.\ndue to your mining ability level, 2 stones are added to inventory.");
            }

            if (!player.addItemToInventory(new Stone(), 1)) {
                player.addEnergy(-1 * getEnergyConsumption(false));
                return new Result(false, "can't add the stone to inventory!");
            }
            player.addEnergy(-1 * getEnergyConsumption(false));
            player.addAbility(Skill.Mining, 10);
            player.addAbility(Skill.Foraging, 10);
            tile.setItem(null);
            return new Result(true, "you destroyed a stone.");
        }
        if (tile.getItem() != null && tile.getItem() instanceof ForagingMineral) {
            if (!allowedMinerals().contains(((ForagingMineral) tile.getItem()).getType())) {
                player.addEnergy(-1 * getEnergyConsumption(false));
                return new Result(false, "your current pickaxe is not able to break this mineral!");
            }

            tile.setItem(null);
            player.addEnergy(-1 * getEnergyConsumption(true));
            player.addAbility(Skill.Mining, 10);
            player.addAbility(Skill.Foraging, 10);
            if (player.getAbilityLevel(Skill.Mining) >= 2) {
                player.addItemToInventory(new Stone(), 1);
                return new Result(true, "you destroyed a " + tile.getItem().getName() +
                        "\ndue to your mining skill level, 2 " + tile.getItem().getName() + "s are added to inventory.");
            }
            player.addItemToInventory(new Stone(), 1);
            return new Result(true, "you destroyed a " + tile.getItem().getName());
        }
        if (tile.getItem() != null && tile.getItem() instanceof Craft craft) {
            if (!player.addItemToInventory(craft, 1)) {
                return new Result(false, "your inventory doesn't have enough capacity!");
            }
            player.addEnergy(-1 * getEnergyConsumption(true));
            tile.setItem(null);
            return new Result(true, craft.getName() + " added to inventory.");
        }
        if (tile.getItem() != null) {
            player.addEnergy(-1 * getEnergyConsumption(true));
            tile.setItem(null);
            return new Result(true, "the item is no longer on the tile.");
        }
        player.addEnergy(-1 * getEnergyConsumption(false));
        return new Result(false, "nothing can be done on the selected tile!");
    }

    @Override
    public Result use(Coordinate c) {
        if (c == null) {
            return new Result(false, "invalid coordinate!");
        }
        Tile t = App.getGame().getTile(c);
        return use(t);
    }

    @Override
    public int getEnergyConsumption(boolean useSuccess) {
        int base = 0;
        if (useSuccess) {
            if (4 == App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Mining)) {
                if (level == ToolLevel.Starter) {
                    base = 4;
                }
                else if (level == ToolLevel.Copper) {
                    base = 3;
                }
                else if (level == ToolLevel.Steel) {
                    base = 2;
                }
                else if (level == ToolLevel.Gold) {
                    base = 1;
                }
                else {
                    base = 0;
                }
            }
            else {
                if (level == ToolLevel.Starter) {
                    base = 5;
                }
                else if (level == ToolLevel.Copper) {
                    base = 4;
                }
                else if (level == ToolLevel.Steel) {
                    base = 3;
                }
                else if (level == ToolLevel.Gold) {
                    base = 2;
                }
                else {
                    base = 1;
                }
            }
        }
        else {
            if (4 == App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Mining)) {
                if (level == ToolLevel.Starter) {
                    base = 3;
                }
                else if (level == ToolLevel.Copper) {
                    base = 2;
                }
                else if (level == ToolLevel.Steel) {
                    base = 1;
                }
                else if (level == ToolLevel.Gold) {
                    base = 0;
                }
                else {
                    base = 0;
                }
            }
            else {
                if (level == ToolLevel.Starter) {
                    base = 4;
                }
                else if (level == ToolLevel.Copper) {
                    base = 3;
                }
                else if (level == ToolLevel.Steel) {
                    base = 2;
                }
                else if (level == ToolLevel.Gold) {
                    base = 1;
                }
                else {
                    base = 0;
                }
            }
        }
        if (App.getGame().getCurrentTime().getWeather() == Weather.Rain) {
            return (int) (base * 1.5);
        }
        else if (App.getGame().getCurrentTime().getWeather() == Weather.Snow) {
            return base * 2;
        }

        if (App.getGame().getCurrentPlayer().isBuffed(Skill.Mining)) {
            base = Math.max(base - 1, 0);
        }
        return base;
    }

    @Override
    public String getLevelString() {
        return level.name();
    }

    @Override
    public String getName() {
        if (level == ToolLevel.Starter) {
            return "pickaxe";
        } if (level == ToolLevel.Copper) {
            return "copper pickaxe";
        } if (level == ToolLevel.Steel) {
            return "steel pickaxe";
        } if (level == ToolLevel.Gold) {
            return "gold pickaxe";
        } if (level == ToolLevel.Iridium) {
            return "iridium pickaxe";
        }
        return "pickaxe";
    }

    @Override
    public int getPrice() {
        return 0;
    }

    public ToolLevel getLevel() {
        return level;
    }

    @Override
    public ToolType getType() {
        return type;
    }

    private ArrayList<ForagingMineralType> allowedMinerals () {
        ArrayList<ForagingMineralType> minerals = new ArrayList<>();
        if (level == ToolLevel.Starter) {
            minerals.add(ForagingMineralType.Copper);
            minerals.add(ForagingMineralType.Coal);
        }
        else if (level == ToolLevel.Copper) {
            minerals.add(ForagingMineralType.Copper);
            minerals.add(ForagingMineralType.Coal);
            minerals.add(ForagingMineralType.Iron);
        }
        else if (level == ToolLevel.Steel) {
            for (ForagingMineralType m : ForagingMineralType.values()) {
                if (m != ForagingMineralType.Iriduim) {
                    minerals.add(m);
                }
            }
        }
        else {
            Collections.addAll(minerals, ForagingMineralType.values());
        }
        return minerals;
    }
}
