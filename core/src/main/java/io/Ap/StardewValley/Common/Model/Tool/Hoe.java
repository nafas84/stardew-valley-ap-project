package io.Ap.StardewValley.Common.Model.Tool;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Map.TileType;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Time.Weather;

public class Hoe implements Tool{
    ToolType type = ToolType.Hoe;
    private ToolLevel level;
    int price;

    public Hoe () {} //needed for json

    public Hoe (ToolLevel level) {
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
            return new Result(true, "hoe upgraded successfully.\n" + pre + cur);
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
            return new Result(true, "hoe upgraded successfully.\n" + pre + cur);
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
            return new Result(true, "hoe upgraded successfully.\n" + pre + cur);
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
            return new Result(true, "hoe upgraded successfully.\n" + pre + cur);
        }
        return new Result(false, "hoe is already upgraded!\ncurrent level: Iridium");
    }

    @Override
    public Result use(Tile tile) {
        App.getGame().getCurrentPlayer().addEnergy(-1 * getEnergyConsumption(true));
        if (tile == null) {
            return new Result(false, "invalid direction!");
        }
        if (tile.getType() != TileType.Ground) {
            return new Result(false, "the selected tile is not a ground tile!");
        }
        if (tile.getItem() != null) {
            return new Result (false, "the selected tile is not empty!");
        }
        if (tile.isPlowed()) {
            return new Result (false, "the selected tile is already plowed!");
        }
        tile.setPlowed(true);
        return new Result(true, "the selected tile is now plowed and ready to get planted.");
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
        if (App.getGame().getCurrentTime().getWeather() == Weather.Rain) {
            return (int) (base * 1.5);
        }
        else if (App.getGame().getCurrentTime().getWeather() == Weather.Snow) {
            return base * 2;
        }
        if (App.getGame().getCurrentPlayer().isBuffed(Skill.Farming)) {
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
            return "hoe";
        } if (level == ToolLevel.Copper) {
            return "copper hoe";
        } if (level == ToolLevel.Steel) {
            return "steel hoe";
        } if (level == ToolLevel.Gold) {
            return "gold hoe";
        } if (level == ToolLevel.Iridium) {
            return "iridium hoe";
        }
        return "hoe";
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
}
