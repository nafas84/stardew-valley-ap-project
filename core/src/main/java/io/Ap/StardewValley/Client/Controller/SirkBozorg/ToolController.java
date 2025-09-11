package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Tool.Tool;
import io.Ap.StardewValley.Client.Screen.PlayerScreen.DirectionType;

import java.util.List;
import java.util.stream.Collectors;

public class ToolController {
    public static Result equip (String toolName) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        List<Tool> tools = extractTools(App.getGame().getCurrentPlayer().getInventory().getItemList());
        Tool tool = findToolByName(tools, toolName);
        if (tool == null) {
            return new Result(false, "you don't have this tool in your inventory!");
        }
        else {
            App.getGame().getCurrentPlayer().setCurrentTool(tool);
            return new Result(true, "you're now equipped with a " + toolName.toLowerCase());
        }
    }

    public static Result showCurrentTool () {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        if (App.getGame().getCurrentPlayer().getCurrentTool() == null) {
            return new Result(false, "you are equipped with no tools right now!\nuse tools equip command.");
        }
        return new Result(true, "your current tool is " + App.getGame().getCurrentPlayer().
                getCurrentTool().getName());
    }

    public static Result showAvailableTools () {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        List<Tool> tools = extractTools(App.getGame().getCurrentPlayer().getInventory().getItemList());
        String result = "your available tools are:";
        for (Tool tool : tools) {
            result = result + "\n" + tool.getName();
        }
        return new Result (true, result);
    }

    public static Result upgradeTool (String toolName) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        List<Tool> tools = extractTools(App.getGame().getCurrentPlayer().getInventory().getItemList());
        Tool tool = findToolByName(tools, toolName);
        if (tool == null) {
            return new Result(false, "you don't have this tool in your inventory!");
        }
        else {
            return tool.upgrade();
        }
    }

    public static Result useTool (String direction) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        if (App.getGame().getCurrentPlayer().getCurrentTool() == null) {
            return new Result (false, "you are not equipped with any tool yet!");
        }
        return App.getGame().getCurrentPlayer().getCurrentTool().
                use(GameMenuController.getCoordinateByDirection(direction));
    }



    public static List<Tool> extractTools(List<Item> items) {
        return items.stream()
                .filter(item -> item instanceof Tool)
                .map(item -> (Tool) item)
                .collect(Collectors.toList());
    }

    public static Tool findToolByName(List<Tool> tools, String inputName) {
        if (tools == null) {
            return null;
        }
        return tools.stream()
                .filter(tool -> tool.getName().equalsIgnoreCase(inputName))
                .findFirst()
                .orElse(null);
    }


    public static Result equipThroughScreen (String toolName) {
        List<Tool> tools = extractTools(App.getGame().getCurrentPlayer().getInventory().getItemList());
        Tool tool = findToolByName(tools, toolName);
        if (tool == null) {
            return new Result(false, "you don't have this tool in your inventory!");
        }
        else {
            App.getGame().getCurrentPlayer().setCurrentTool(tool);
            return new Result(true, "you're now equipped with a " + toolName.toLowerCase());
        }
    }

    public static String directionTypeToString (DirectionType directionType) {
        switch (directionType) {
            case Up -> {
                return "n";
            }
            case Down -> {
                return "s";
            }
            case Right -> {
                return "e";
            }
            case Left -> {
                return "w";
            }
        }
        return "n";
    }

    public static Result useToolThoughScreen (String direction) {
        if (App.getGame().getCurrentPlayer().getCurrentTool() == null) {
            return new Result (false, "you are not equipped with any tool yet!");
        }
        return App.getGame().getCurrentPlayer().getCurrentTool().
                use(GameMenuController.getCoordinateByDirection(direction));
    }
}
