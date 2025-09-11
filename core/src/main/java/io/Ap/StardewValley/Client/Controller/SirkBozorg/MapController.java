package io.Ap.StardewValley.Client.Controller.SirkBozorg;

import io.Ap.StardewValley.Common.Model.Map.*;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Client.Controller.GameScreenController;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MapController {
    public static Result printAllMap() {
        return new Result(true, App.getGame().getMap().toString());
    }

    public static Result printFarm() {
        int i = 0, j = 0;
        switch (App.getGame().getCurrentPlayer().getFarm()) {
            case 2:
                j = 2;
                break;
            case 3:
                i = 2;
                j = 2;
                break;
            case 4:
                i = 2;
                break;
        }
        return new Result(true, App.getGame().getMap().getRegion(i, j).toString());
    }

    public static Result printMap(String stringX, String stringY, String stringSize) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        int size = Integer.parseInt(stringSize);

        if ((x < 0 || x >= 90) || (y < 0 || y >= 120)) {
            return new Result(false, "Mashti x,y bein (0,0) - (89, 119)");
        } else if ((x + size < 0 || x + size >= 90) || (y + size < 0 || y + size >= 120)) {
            return new Result(false, "Size is invalid. Mashti x,y bein (0,0) - (89, 119)");
        }

        StringBuilder result = new StringBuilder();
        Tile[][] fullMap = App.getGame().getMap().getFullMap();
        for (int i = x; i < x + size; i++){
            for(int j = y; j < y +size; j++) {
                result.append(" ");
                Coordinate coordinate = new Coordinate(i, j);
                boolean isPlayer = false;
                for (Player player: App.getGame().getPlayers()) {
                    if (player.getCoordinate().equals(coordinate)) {
                        isPlayer = true;
                        if (player.getId() == App.getGame().getCurrentPlayer().getId())
                            result.append(Symbols.CurrentPlayer.getColoredSymbol());
                        else
                            result.append(Symbols.Player.getColoredSymbol());
                    }
                }
                if (!isPlayer)
                    result.append(fullMap[i][j].getSymbol());
                result.append(" ");
            }
            result.append("\n");
        }
        return new Result(true, result.toString());
    }

    public static Result helpMap(){
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        StringBuilder result = new StringBuilder();
        for (Symbols s: Symbols.values()) {
            result.append(s.name()).append(" ").append(s.getColoredSymbol()).append("\n");
        }

        return new Result(true, result.toString());
    }

    public static Result tileInfo(String stringX, String stringY) {
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        Coordinate coordinate = new Coordinate(x, y);

        if ((x < 0 || x >= 90) || (y < 0 || y >= 120)) {
            return new Result(false, "Mashti x,y bein (0,0) - (89, 119)");
        }

        return new Result(true, App.getGame().getTile(coordinate).toString());
    }

    public static Result walk(String stringX, String stringY) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        Coordinate coordinate = new Coordinate(x, y);
        int energy = getDestinationEnergy(App.getGame().getCurrentPlayer(),coordinate);

        if ((x < 0 || x >= 90) || (y < 0 || y >= 120)) {
            return new Result(false, "Mashti x,y bein (0,0) - (89, 119)!");
        } else if (energy == -1) {
            return new Result(false, "You can't go there!");
        } else if (getFarmId(coordinate) != -1 && App.getGame().getCurrentPlayer().getFarm() != getFarmId(coordinate)) {   // TODO: if when married
            return new Result(false, "You can't go in others farm!");
        } else if (handleWorkingHours(coordinate) != null) {
            return handleWorkingHours(coordinate);
        }

        return new Result(true, "Required Energy: " + energy + "\nDo you want to go?");
    }

    public static Result walk(String input, String stringX, String stringY) {
        GameMenuController.moveControl();
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        Coordinate coordinate = new Coordinate(x, y);
        Player player = App.getGame().getCurrentPlayer();
        int energy = getDestinationEnergy(player, coordinate);

        if (!input.toLowerCase().contains("yes")) {
            return new Result(false, "Fekresho nemikardi na?");
        }

        Coordinate newCoordinate = getDestination(player, coordinate);
        App.getGame().getCurrentPlayer().setCoordinate(newCoordinate);
        App.getGame().getCurrentPlayer().addEnergy(-energy);
        return new Result(true, "You successfully go to (" + newCoordinate.getX() +", " + newCoordinate.getY() + ")");
    }

    public static Result buildGreenHouse() {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        Player player = App.getGame().getCurrentPlayer();
        if (player.getCount() < 1000) {
            return new Result(false, "You don't have enough money!(1000 dollars)");
        } else if (!player.getInventory().hasItemWithNumber("Stone", 500)) {
            return new Result(false, "You don't have enough stone!(500)");
        }

        player.getInventory().removeItem("stone", 500);
        player.addCount(-1000);
        // TODO: Phase 1
        int x = 7,y =27;
        switch (player.getFarm()) {
            case 2:
                y = 5 + 80;
                break;
            case 3:
                x = 4 + 60;
                y = 5 + 80;
                break;
            case 4:
                x = 4 + 60;
                break;
        }

        App.getGame().getMap().build(new Coordinate(x, y), BuildingType.GreenHouseBuild);
        return new Result(true, "Now you have Greenhouse:)");
    }

    public static Result buildFarmBuilding(String name, String stringX, String stringY) {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        int x = Integer.parseInt(stringX);
        int y = Integer.parseInt(stringY);
        Coordinate coordinate = new Coordinate(x, y);
        BuildingType type = getBuildingType(name);
        Result result = App.getGame().getShop(ShopType.CarpentersShop).buy(name, 1, "SOS");
        // TODO:  goh to in ghesmat!
        // Map error:
        if (App.getGame().getTile(App.getGame().getCurrentPlayer().getCoordinate()).getBuildingType() != BuildingType.CarpentersShop) {
            return new Result(false, "you must be in Carpenter's Shop to be able to build an farm building!");
        } else if ((x < 0 || x >= 90) || (y < 0 || y >= 120)) {
            return new Result(false, "Mashti x,y bein (0,0) - (89, 119)");
        } else if (!App.getGame().getCurrentPlayer().isMyFarm(coordinate)) {
            return new Result(false, "You can only build farm buildings on your own farm!");
        } else if (type == null) {
            return new Result(false, "Building name is invalid!");
        } else if (!hasThisBuildingType(type) && !canBuild(coordinate, type)) {
            return new Result(false, "You can't build this building here!");
        }
        // Shop error:
        else if (!result.isSuccessful()) {
            return result;
        }

        App.getGame().getMap().build(coordinate, type);
        return result;
    }

    public static Result showListFarmBuilding() {
        if (App.getGame().getCurrentPlayer().getMovesThisTurn() >= App.getGame().getCurrentPlayer().getMaxMovesInTurn()) {
            return new Result (false, "you have no more moves! enter next turn!");
        }
        GameMenuController.moveControl();
        StringBuilder result = new StringBuilder();
        result.append("My Farm Building List:\n").append("_______________________________________\n");
        for (FarmBuilding farmBuilding: App.getGame().getCurrentPlayer().getMyFarmBuildings()) {
            result.append("Name: ").append(farmBuilding.getName()).append("\n");
        }
        return new Result(true, result.toString());
    }

    public static Coordinate getDestination (Player player, Coordinate destination) {
        // Phase 1:
//        int lenx = 90;
//        int leny = 120;
        int lenx = 240;
        int leny = 290;

        int sourcex = App.getGame().getCurrentPlayer().getCoordinate().getX();
        int sourcey = App.getGame().getCurrentPlayer().getCoordinate().getY();
        int destx = destination.getX();
        int desty = destination.getY();
        int[][][] dist = new int[lenx][leny][4];
        Coordinate[][][] parent = new Coordinate[lenx][leny][4];
        for (int[][] row : dist) {
            for (int[] col : row)
                Arrays.fill(col, Integer.MAX_VALUE);
        }
        parent[sourcex][sourcey][0] = new Coordinate(-1, -1);
        parent[sourcex][sourcey][1] = new Coordinate(-1, -1);
        parent[sourcex][sourcey][2] = new Coordinate(-1, -1);
        parent[sourcex][sourcey][3] = new Coordinate(-1, -1);
        final int[] dx = {-1, 1, 0, 0};
        final int[] dy = {0, 0, -1, 1};
        PriorityQueue <int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        for (int i = 0; i < 4; i++) {
            dist[sourcex][sourcey][i] = 0;
            pq.offer(new int[]{0, sourcex, sourcey, i});
        }
        Coordinate last = new Coordinate(sourcex, sourcey);
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0];
            int x = cur[1];
            int y = cur[2];
            int dir = cur[3];
            Coordinate c = new Coordinate(x, y);
            if (!App.getGame().getTile(c).isWalkable())
                continue;
            int id = App.getGame().getCurrentPlayer().getPartnerID();
            if (getFarmId(c) != -1 && (App.getGame().getCurrentPlayer().getFarm() != getFarmId(c)
                    || (id != -1 && App.getGame().getPlayerByID(id).getFarm() != getFarmId(c))))
                continue;
            if ((cost / 20) > App.getGame().getCurrentPlayer().getEnergy())
                return new Coordinate(last.getX(), last.getY());
            last.setX(x);
            last.setY(y);
            if (x == destx && y == desty)
                break;
            for (int i = 0; i < 4; i++) {
                int newx = x + dx[i];
                int newy = y + dy[i];
                if (newx < 0 || newx >= lenx || newy < 0 || newy >= leny)
                    continue;
                int newCost = cost + 1;
                if (dir != i)
                    newCost += 10;
                if (newCost < dist[newx][newy][i]) {
                    parent[newx][newy][i] = new Coordinate(last.getX(), last.getY());
                    dist[newx][newy][i] = newCost;
                    pq.offer(new int[]{newCost, newx, newy, i});
                }
            }
        }
        if (dist[destx][desty][0] == Integer.MAX_VALUE && dist[destx][desty][1] == Integer.MAX_VALUE
                && dist[destx][desty][2] == Integer.MAX_VALUE && dist[destx][desty][3] == Integer.MAX_VALUE) {
            last.setX(-1);
            last.setY(-1);
        }
        else {
            Coordinate c = new Coordinate(destx, desty);
            while (c.getX() != sourcex || c.getY() != sourcey) {
                int minimumEnergy = Math.min(Math.min(dist[c.getX()][c.getY()][0], dist[c.getX()][c.getY()][1]),
                        Math.min(dist[c.getX()][c.getY()][2], dist[c.getX()][c.getY()][3]));
                if ((minimumEnergy + 19) / 20 <= App.getGame().getCurrentPlayer().getEnergy())
                    break;
                if (minimumEnergy == dist[c.getX()][c.getY()][0]) {
                    c = parent[c.getX()][c.getY()][0];
                }
                else if (minimumEnergy == dist[c.getX()][c.getY()][1]) {
                    c = parent[c.getX()][c.getY()][1];
                }
                else if (minimumEnergy == dist[c.getX()][c.getY()][2]) {
                    c = parent[c.getX()][c.getY()][2];
                }
                else if (minimumEnergy == dist[c.getX()][c.getY()][3]) {
                    c = parent[c.getX()][c.getY()][3];
                }
            }
            last = c;
        }
        return last;
    }

    public static int getDestinationEnergy (Player player, Coordinate destination) {
        // Phase 1:
//        int lenx = 90;
//        int leny = 120;
        int lenx = 240;
        int leny = 290;
        int sourcex = App.getGame().getCurrentPlayer().getCoordinate().getX();
        int sourcey = App.getGame().getCurrentPlayer().getCoordinate().getY();
        int destx = destination.getX();
        int desty = destination.getY();
        int[][][] dist = new int[lenx][leny][4];
        for (int[][] row : dist) {
            for (int[] col : row)
                Arrays.fill(col , Integer.MAX_VALUE);
        }
        final int[] dx = {-1, 1, 0, 0};
        final int[] dy = {0, 0, -1, 1};
        PriorityQueue <int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        for (int i = 0; i < 4; i++) {
            dist[sourcex][sourcey][i] = 0;
            pq.offer(new int[]{0, sourcex, sourcey, i});
        }
        Coordinate last = new Coordinate(sourcex, sourcey);
        int ans = 0;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0];
            int x = cur[1];
            int y = cur[2];
            int dir = cur[3];
            Coordinate c = new Coordinate(x, y);
            if (!App.getGame().getTile(c).isWalkable())
                continue;
            int id = App.getGame().getCurrentPlayer().getPartnerID();
            if (getFarmId(c) != -1 && (App.getGame().getCurrentPlayer().getFarm() != getFarmId(c)
                    || (id != -1 && App.getGame().getPlayerByID(id).getFarm() != getFarmId(c))))
                continue;
            if ((cost / 20) > App.getGame().getCurrentPlayer().getEnergy())
                return ans;
            last.setX(x);
            last.setY(y);
            ans = (cost + 19) / 20;
            if (x == destx && y == desty)
                return ans;
            for (int i = 0; i < 4; i++) {
                int newx = x + dx[i];
                int newy = y + dy[i];
                if (newx < 0 || newx >= lenx || newy < 0 || newy >= leny)
                    continue;
                int newCost = cost + 1;
                if (dir != i)
                    newCost += 10;
                if (newCost < dist[newx][newy][i]) {
                    dist[newx][newy][i] = newCost;
                    pq.offer(new int[]{newCost, newx, newy, i});
                }
            }
        }
        return -1;
    }

    private static Result handleWorkingHours(Coordinate coordinate) {
        BuildingType buildingType = App.getGame().getTile(coordinate).getBuildingType();
        int h = App.getGame().getCurrentTime().getHour();
        if (buildingType == null) return null;
        switch (buildingType) {
            case Blacksmith:
                if (ShopType.Blacksmith.getOpeningTime() <=  h && h <= ShopType.Blacksmith.getClosingTime()) {
                    return null;
                } else {
                    return new Result(false, ShopType.Blacksmith.getShopName() + " shop working hours " + ShopType.Blacksmith.getOpeningTime() +"-" + ShopType.Blacksmith.getClosingTime());
                }
            case JojaMart:
                if (ShopType.JojaMart.getOpeningTime() <=  h && h <= ShopType.JojaMart.getClosingTime()) {
                    return null;
                } else {
                    return new Result(false, ShopType.JojaMart.getShopName() + " shop working hours " + ShopType.JojaMart.getOpeningTime() +"-" + ShopType.JojaMart.getClosingTime());
                }
            case PierresGeneralStore:
                if (ShopType.PierresGeneralStore.getOpeningTime() <=  h && h <= ShopType.PierresGeneralStore.getClosingTime()) {
                    return null;
                } else {
                    return new Result(false, ShopType.PierresGeneralStore.getShopName() + " shop working hours " + ShopType.PierresGeneralStore.getOpeningTime() +"-" + ShopType.PierresGeneralStore.getClosingTime());
                }
            case CarpentersShop:
                if (ShopType.CarpentersShop.getOpeningTime() <=  h && h <= ShopType.CarpentersShop.getClosingTime()) {
                    return null;
                } else {
                    return new Result(false, ShopType.CarpentersShop.getShopName() + " shop working hours " + ShopType.CarpentersShop.getOpeningTime() +"-" + ShopType.CarpentersShop.getClosingTime());
                }
            case FishShop:
                if (ShopType.FishShop.getOpeningTime() <=  h && h <= ShopType.FishShop.getClosingTime()) {
                    return null;
                } else {
                    return new Result(false, ShopType.FishShop.getShopName() + " shop working hours " + ShopType.FishShop.getOpeningTime() +"-" + ShopType.FishShop.getClosingTime());
                }
            case MarniesRanch:
                if (ShopType.MarniesRanch.getOpeningTime() <=  h && h <= ShopType.MarniesRanch.getClosingTime()) {
                    return null;
                } else {
                    return new Result(false, ShopType.MarniesRanch.getShopName() + " shop working hours " + ShopType.MarniesRanch.getOpeningTime() +"-" + ShopType.MarniesRanch.getClosingTime());
                }
            case TheStarDropSaloon:
                if (ShopType.TheStarDropSaloon.getOpeningTime() <=  h && h <= ShopType.TheStarDropSaloon.getClosingTime()) {
                    return null;
                } else {
                    return new Result(false, ShopType.TheStarDropSaloon.getShopName() + " shop working hours " + ShopType.TheStarDropSaloon.getOpeningTime() +"-" + ShopType.TheStarDropSaloon.getClosingTime());
                }
            default:
                return null;
        }
    }

    private static int getFarmId(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        // Phase 1:
//        if (x >= 0 && x < 30 && y >= 0 && y < 40) {
//            return 1;
//        } else if (x >= 60 && x < 90 && y >= 0 && y < 40) {
//            return 4;
//        } else if (x >= 0 && x < 30 && y >= 80 && y < 120) {
//            return 2;
//        } else if (x >= 60 && x < 90 && y >= 80 && y < 120) {
//            return 3;
//        } else {
//            return -1; // isn't farm
//        }

        if (x >= 0 && x < 65 && y >= 0 && y < 80) {
            return 1;
        } else if (x >= 65 + 110 && x < 130 + 110 && y >= 0 && y < 80) {
            return 4;
        } else if (x >= 0 && x < 65 && y >= 80 + 130 && y < 160 + 130) {
            return 2;
        } else if (x >= 65 + 110 && x < 130 + 110 && y >= 80 + 130 && y < 160 + 130) {
            return 3;
        } else {
            return -1; // isn't farm
        }
    }

    private static boolean canBuild(Coordinate coordinate, BuildingType type) {
        Tile[][] fullMap = App.getGame().getMap().getFullMap();
        for (int i = coordinate.getX(); i < coordinate.getX() + type.getL(); i++) {
            for (int j = coordinate.getY(); j < coordinate.getY() + type.getW(); j++) {
                Tile tile = fullMap[i][j];
                switch (tile.getType()) {
                    case Building, Water, Mountain, Mine:
                        return false;
                    case Ground:
                        if (tile.getNpc() != null || tile.getAnimal() != null || tile.getItem() != null)
                            return false;
                }
            }
        }
        return true;
    }

    private static BuildingType getBuildingType(String name) {
        return switch (name.toLowerCase()) {
            case "barn", "big barn", "deluxe barn" -> BuildingType.Barn;
            case "coop", "big coop", "deluxe coop" -> BuildingType.Coop;
            case "well" -> BuildingType.Well;
            case "shipping bin" -> BuildingType.ShippingBin;
            default -> null;
        };
    }

    private static boolean hasThisBuildingType(BuildingType type)  {
        for (FarmBuilding farmBuilding: App.getGame().getCurrentPlayer().getMyFarmBuildings()) {
            if (farmBuilding.getType().getType().equals(type)) {
                return true;
            }
        }
        return false;
    }


    //added by aynaz:
    public static Result buildFarmBuildingThroughScreen (String name, String stringX, String stringY) {
        int x, y;
        try {
            x = Integer.parseInt(stringX);
            y = Integer.parseInt(stringY);
        } catch (Exception e) {
            return new Result (false, "enter number!");
        }
        Coordinate coordinate = new Coordinate(x, y);
        BuildingType type = getBuildingType(name);
        Result result = App.getGame().getShop(ShopType.CarpentersShop).buy(name, 1, "SOS");
        // Map error:
        if (!GameScreenController.isShopBesideMe(BuildingType.CarpentersShop)) {
            return new Result(false, "you must be in Carpenter's Shop to be able to build an farm building!");
        } else if ((x < 0 || x >= 90) || (y < 0 || y >= 120)) {
            return new Result(false, "Mashti x,y bein (0,0) - (89, 119)");
        } else if (!App.getGame().getCurrentPlayer().isMyFarm(coordinate)) {
            return new Result(false, "You can only build farm buildings on your own farm!");
        } else if (type == null) {
            return new Result(false, "Building name is invalid!");
        } else if (!hasThisBuildingType(type) && !canBuild(coordinate, type)) {
            return new Result(false, "You can't build this building here!");
        }
        // Shop error:
        else if (!result.isSuccessful()) {
            return result;
        }
        App.getGame().getMap().build(coordinate, type);
        return result;
    }
}
