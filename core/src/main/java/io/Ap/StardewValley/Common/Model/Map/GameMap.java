package io.Ap.StardewValley.Common.Model.Map;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Player.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import io.Ap.StardewValley.Gson.ItemAdapter;

public class GameMap {
    public final int[] farmSelections;
    private final Region[][] region = new Region[3][3];
    private transient Tile[][] fullMap;

    private final int[] rowHeights = new int[3];
    private final int[] colWidths = new int[3];

    private final int[] rowOffsets = new int[3];
    private final int[] colOffsets = new int[3];

    public GameMap(int[] farmSelection) {
        this.farmSelections = farmSelection;
        // Phase 1:
//        region[0][0] = loadRegionJson("Farming"  + farmSelection[0]);
//        region[0][1] = loadRegionJson("Path1");
//        region[0][2] = loadRegionJson("Farming" + farmSelection[1]);
//
//        region[1][0] = loadRegionJson("Path4");
//        region[1][1] = loadRegionJson("NPC");
//        region[1][2] = loadRegionJson("Path2");
//
//        region[2][0] = loadRegionJson("Farming" + farmSelection[3]);
//        region[2][1] = loadRegionJson("Path3");
//        region[2][2] = loadRegionJson("Farming" + farmSelection[2]);


        region[0][0] = loadRegionJson("Farm"  + farmSelection[0]);
        region[0][1] = loadRegionJson("path1");
        region[0][2] = loadRegionJson("Farm" + farmSelection[1]);

        region[1][0] = loadRegionJson("path4");
        region[1][1] = loadRegionJson("Town");
        region[1][2] = loadRegionJson("path2");

        region[2][0] = loadRegionJson("Farm" + farmSelection[3]);
        region[2][1] = loadRegionJson("path3");
        region[2][2] = loadRegionJson("Farm" + farmSelection[2]);
    }

    private Region loadRegionJson(String name) {
        // Phase 1:
        //File file = new File("core/src/main/resources/Maps/" + name + ".json");
        File file = new File("core/src/main/resources/TiledMaps/" + name + ".json");

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(Item.class, new ItemAdapter()) // ثبت TypeAdapter
                .create();
            return gson.fromJson(reader, Region.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setFulMap() {
        // Phase 1:
        // total rows = 3 * 30, total cols = 3 * 40;

        int rows = 3;
        int cols = 3;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Region r = region[i][j];
                int h = r.getTiles().length;
                int w = r.getTiles()[0].length;

                rowHeights[i] = h;
                colWidths[j] = w;
            }
        }

        int totalRows = 0;
        int totalCols = 0;
        for (int h : rowHeights) totalRows += h;
        for (int w : colWidths) totalCols += w;

        fullMap = new Tile[totalRows][totalCols];

        int rowOffset = 0;
        for (int i = 0; i < rows; i++) {
            int colOffset = 0;
            for (int j = 0; j < cols; j++) {
                Region r = region[i][j];
                Tile[][] tiles = r.getTiles();
                int h = tiles.length;
                int w = tiles[0].length;

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        fullMap[rowOffset + y][colOffset + x] = tiles[y][x];
                    }
                }

                colOffset += colWidths[j];
            }
            rowOffset += rowHeights[i];
        }

        calculateOffsets();
    }

    private void calculateOffsets() {
        rowOffsets[0] = 0;
        colOffsets[0] = 0;
        for (int i = 1; i < 3; i++) {
            rowOffsets[i] = rowOffsets[i - 1] + rowHeights[i - 1];
            colOffsets[i] = colOffsets[i - 1] + colWidths[i - 1];
        }
    }

    public int[] getRowOffsets() {
        return rowOffsets;
    }

    public int[] getColOffsets() {
        return colOffsets;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        int height = fullMap.length;
        int width = fullMap[0].length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
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

        return result.toString();
    }

    public Tile[][] getFullMap() {
        return fullMap;
    }

    public Region getRegion(int i, int j) {
        return region[i][j];
    }

    public Coordinate getCurrentRegionCoordinate() {
        int x = App.getGame().getCurrentPlayer().getCoordinate().getX();
        int y = App.getGame().getCurrentPlayer().getCoordinate().getY();

        int row = 0, col = 0;

        int accumulated = 0;
        for (int i = 0; i < 3; i++) {
            accumulated += rowHeights[i];
            if (x < accumulated) {
                row = i;
                break;
            }
        }

        accumulated = 0;
        for (int j = 0; j < 3; j++) {
            accumulated += colWidths[j];
            if (y < accumulated) {
                col = j;
                break;
            }
        }

        return new Coordinate(row, col);
    }

    public Coordinate getCurrentRegionCoordinate(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();

        int row = 0, col = 0;

        int accumulated = 0;
        for (int i = 0; i < 3; i++) {
            accumulated += rowHeights[i];
            if (x < accumulated) {
                row = i;
                break;
            }
        }

        accumulated = 0;
        for (int j = 0; j < 3; j++) {
            accumulated += colWidths[j];
            if (y < accumulated) {
                col = j;
                break;
            }
        }

        return new Coordinate(row, col);
    }

    public Region getCurrentRegion() {
        Coordinate c = getCurrentRegionCoordinate();
        return region[c.getX()][c.getY()];
    }

    public int[] getFarmSelections() {
        return farmSelections;
    }

    public void build(Coordinate coordinate, BuildingType type) {
        fullMap[coordinate.getX()][coordinate.getY()].setBuildingOrigin(true);

        for (int i = coordinate.getX(); i < type.getL() + coordinate.getX(); i++) {
            for (int j = coordinate.getY(); j < type.getW() + coordinate.getY(); j++) {
                fullMap[i][j].setItem(null);
                fullMap[i][j].setType(TileType.Building);
                fullMap[i][j].setBuildingType(type);
            }
        }
    }
}
