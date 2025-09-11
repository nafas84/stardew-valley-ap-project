package io.Ap.StardewValley.Client.Screen.PlayerScreen;

import io.Ap.StardewValley.Common.Model.Map.Coordinate;

public class ToolRenderTransform {
    private final Coordinate coordinate;
    private final float rotation;
    private final Coordinate origin;

    public ToolRenderTransform(Coordinate coordinate, float rotation, Coordinate origin) {
        this.coordinate = coordinate;
        this.rotation = rotation;
        this.origin = origin;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public float getRotation() {
        return rotation;
    }

    public int getX() {
        return coordinate.getX();
    }

    public int getY() {
        return coordinate.getY();
    }

    public Coordinate getOrigin() {
        return origin;
    }

    public int getOriginX() {
        return origin.getX();
    }

    public int getOriginY() {
        return origin.getY();
    }
}
