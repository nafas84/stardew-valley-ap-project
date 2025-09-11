package io.Ap.StardewValley.Common.Model.Tool;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.BuildingType;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.Item.Wood;
import io.Ap.StardewValley.Common.Model.Plants.Sapling;
import io.Ap.StardewValley.Common.Model.Plants.Tree;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Time.Weather;

public class Axe implements Tool{
    ToolType type = ToolType.Axe;
    private ToolLevel level;
    int price;

    public Axe () {} //needed for json

    public Axe (ToolLevel level) {
        this.level = level;
        price = 0;
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
            return new Result(true, "axe upgraded successfully.\n" + pre + cur);
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
            return new Result(true, "axe upgraded successfully.\n" + pre + cur);
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
            return new Result(true, "axe upgraded successfully.\n" + pre + cur);
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
            return new Result(true, "axe upgraded successfully.\n" + pre + cur);
        }
        return new Result(false, "axe is already upgraded!\ncurrent level: Iridium");
    }

    @Override
    public Result use(Tile tile) {
        Player player = App.getGame().getCurrentPlayer();
        if (tile == null) {
            return new Result(false, "invalid direction!");
        }
        if (tile.getItem() == null) {
            return new Result(false, "this tile is empty!");
        }
        if (tile.getItem() instanceof Wood) {
            tile.setItem(null);
            player.addEnergy(-1 * getEnergyConsumption(true));
            player.addAbility(Skill.Foraging, 10);
            player.addItemToInventory(new Wood(), 1);
            return new Result(true, "you destroyed a piece of wood.");
        }
        if (tile.getItem() instanceof Tree) {
            boolean success = player.addItemToInventory(new Wood(), 1);
            if (!success) {
                player.addEnergy(-1 * getEnergyConsumption(false));
                return new Result(false, "can't add to inventory!");
            }
            tile.setItem(null);
            player.addEnergy(-1 * getEnergyConsumption(true));
            player.addAbility(Skill.Foraging, 10);
            if (!((Tree) tile.getItem()).isPurposelyPlanted()) {
                player.addItemToInventory(new Sapling(((Tree) tile.getItem()).getSource(), false), 1);
            }
            return new Result(true, "you destroyed a tree.");

        }
        return new Result(false, "TODO");
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
            if (4 == App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Foraging)) {
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
            if (4 == App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Foraging)) {
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
        if (App.getGame().getCurrentPlayer().isBuffed(Skill.Foraging)) {
            base = Math.max(base - 1, 0);
        }
        return base;
    }

    @Override
    public String getLevelString() {
        return level.name();
    }

    @Override
    public ToolType getType() {
        return type;
    }

    @Override
    public String getName() {
        if (level == ToolLevel.Starter) {
            return "axe";
        } if (level == ToolLevel.Copper) {
            return "copper axe";
        } if (level == ToolLevel.Steel) {
            return "steel axe";
        } if (level == ToolLevel.Gold) {
            return "gold axe";
        } if (level == ToolLevel.Iridium) {
            return "iridium axe";
        }
        return "axe";
    }

    @Override
    public int getPrice() {
        return 0;
    }

    public ToolLevel getLevel() {
        return level;
    }
}
