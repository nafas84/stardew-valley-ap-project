package io.Ap.StardewValley.Common.Model.Map;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Player.Player;

public class Region {
    private String name;
    private final Tile[][] tiles;

    public Region(String name, Tile[][] tiles) {
        this.name = name;
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int rows = tiles.length;
        int cols = tiles[0].length;

        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
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
                    result.append(tiles[i][j].getSymbol());
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
