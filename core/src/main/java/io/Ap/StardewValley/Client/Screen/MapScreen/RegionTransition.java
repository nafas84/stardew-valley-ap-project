package io.Ap.StardewValley.Client.Screen.MapScreen;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;

public enum RegionTransition {
    // Row 0
    FROM_00_TO_01(new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(58, 80)),
    FROM_01_TO_00(new Coordinate(0, 1), new Coordinate(0, 0), new Coordinate(17, 79)),

    FROM_01_TO_02(new Coordinate(0, 1), new Coordinate(0, 2), new Coordinate(39, 7 + 210)),
    FROM_02_TO_01(new Coordinate(0, 2), new Coordinate(0, 1), new Coordinate(15, 209)),

    FROM_00_TO_10(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(65, 37)),
    FROM_10_TO_00(new Coordinate(1, 0), new Coordinate(0, 0), new Coordinate(64, 40)),

    FROM_01_TO_11(new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(65, 161)),
    FROM_11_TO_01(new Coordinate(1, 1), new Coordinate(0, 1), new Coordinate(64, 161)),

    FROM_02_TO_12(new Coordinate(0, 2), new Coordinate(1, 2), new Coordinate(65, 248)),
    FROM_12_TO_02(new Coordinate(1, 2), new Coordinate(0, 2), new Coordinate(64, 250)),

    // Row 1
    FROM_10_TO_11(new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(119, 80)),
    FROM_11_TO_10(new Coordinate(1, 1), new Coordinate(1, 0), new Coordinate(85, 79)),

    //FROM_11_TO_12(new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(5, 0)),
    //FROM_12_TO_11(new Coordinate(1, 2), new Coordinate(1, 1), new Coordinate(5, 39)),

    FROM_10_TO_20(new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(175, 40)),
    FROM_20_TO_10(new Coordinate(2, 0), new Coordinate(1, 0), new Coordinate(174, 67)),

    FROM_11_TO_21(new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(175, 124)),
    FROM_21_TO_11(new Coordinate(2, 1), new Coordinate(1, 1), new Coordinate(174, 134)),

    FROM_12_TO_22(new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(176, 250)),
    FROM_22_TO_12(new Coordinate(2, 2), new Coordinate(1, 2), new Coordinate(174, 245)),

    // Row 2
    FROM_20_TO_21(new Coordinate(2, 0), new Coordinate(2, 1), new Coordinate(190, 80)),
    FROM_21_TO_20(new Coordinate(2, 1), new Coordinate(2, 0), new Coordinate(192, 79)),

    FROM_21_TO_22(new Coordinate(2, 1), new Coordinate(2, 2), new Coordinate(224, 210)),
    FROM_22_TO_21(new Coordinate(2, 2), new Coordinate(2, 1), new Coordinate(224, 209));

    // -----------------------

    private final Coordinate fromRegion;
    private final Coordinate toRegion;
    private final Coordinate destinationCoordinate;

    RegionTransition(Coordinate fromRegion, Coordinate toRegion, Coordinate destinationCoordinate) {
        this.fromRegion = fromRegion;
        this.toRegion = toRegion;
        this.destinationCoordinate = destinationCoordinate;
    }

    public boolean matches(Coordinate from, Coordinate to) {
        return from.equals(fromRegion) && to.equals(toRegion);
    }

    public static RegionTransition get(Coordinate from, Coordinate to) {
        for (RegionTransition rt : values()) {
            if (rt.matches(from, to))
                return rt;
        }
        return null;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public float getLibGdxX(int tileSize) {
        int[] colOffsets = App.getGame().getMap().getColOffsets();
        int localY = destinationCoordinate.getY() - colOffsets[toRegion.getY()];
        return localY * tileSize;
    }

    public float getLibGdxY(int tileSize) {
        int[] rowOffsets = App.getGame().getMap().getRowOffsets();
        int regionHeight = App.getGame().getMap()
                .getRegion(toRegion.getX(), toRegion.getY()).getTiles().length;
        int localX = destinationCoordinate.getX() - rowOffsets[toRegion.getX()];
        return (regionHeight - 1 - localX) * tileSize;
    }
}
