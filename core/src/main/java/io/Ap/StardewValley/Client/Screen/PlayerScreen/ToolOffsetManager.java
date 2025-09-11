package io.Ap.StardewValley.Client.Screen.PlayerScreen;

import io.Ap.StardewValley.Common.Model.Map.Coordinate;

import java.util.EnumMap;
import java.util.List;

public class ToolOffsetManager {
    private static final EnumMap<StateType, EnumMap<DirectionType, List<ToolRenderTransform>>> allToolOffsets = new EnumMap<>(StateType.class);

    static {
        //hoe
        EnumMap<DirectionType, List<ToolRenderTransform>> hoeOffsets = new EnumMap<>(DirectionType.class);

        hoeOffsets.put(DirectionType.Right, List.of(
                new ToolRenderTransform(new Coordinate(-1, 15), 20, new Coordinate(2, 10)),
                new ToolRenderTransform(new Coordinate(6, 14), -15, new Coordinate(2, 9)),
                new ToolRenderTransform(new Coordinate(8, 14), -45, new Coordinate(2, 9)),
                new ToolRenderTransform(new Coordinate(10, 13), -75, new Coordinate(2, 8)),
                new ToolRenderTransform(new Coordinate(12, 13), -105, new Coordinate(2, 8))
        ));

        hoeOffsets.put(DirectionType.Left, List.of(
                new ToolRenderTransform (new Coordinate(6, 14), -20, new Coordinate(3, 10)),
                new ToolRenderTransform (new Coordinate(3, 11), 15, new Coordinate(3, 9)),
                new ToolRenderTransform (new Coordinate(0, 9), 45, new Coordinate(3, 9)),
                new ToolRenderTransform (new Coordinate(-2, 5), 75, new Coordinate(3, 8)),
                new ToolRenderTransform (new Coordinate(-3, 1), 105, new Coordinate(3, 8))
        ));

        hoeOffsets.put(DirectionType.Up, List.of(
                new ToolRenderTransform (new Coordinate(1, 13), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 12), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 9), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 8), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 8), 0, new Coordinate(4, 10))
        ));

        hoeOffsets.put(DirectionType.Down, List.of(
                new ToolRenderTransform (new Coordinate(0, 10), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(0, 9), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 1), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 0), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 1), 0, new Coordinate(2, 7))
        ));

        allToolOffsets.put(StateType.ToolHoe, hoeOffsets);


        //axe
        EnumMap<DirectionType, List<ToolRenderTransform>> axeOffsets = new EnumMap<>(DirectionType.class);

        axeOffsets.put(DirectionType.Right, List.of(
                new ToolRenderTransform(new Coordinate(-1, 15), 20, new Coordinate(2, 10)),
                new ToolRenderTransform(new Coordinate(6, 14), -15, new Coordinate(2, 9)),
                new ToolRenderTransform(new Coordinate(8, 14), -45, new Coordinate(2, 9)),
                new ToolRenderTransform(new Coordinate(10, 13), -75, new Coordinate(2, 8)),
                new ToolRenderTransform(new Coordinate(12, 13), -105, new Coordinate(2, 8))
        ));

        axeOffsets.put(DirectionType.Left, List.of(
                new ToolRenderTransform (new Coordinate(6, 14), -20, new Coordinate(3, 10)),
                new ToolRenderTransform (new Coordinate(3, 11), 15, new Coordinate(3, 9)),
                new ToolRenderTransform (new Coordinate(0, 9), 45, new Coordinate(3, 9)),
                new ToolRenderTransform (new Coordinate(-2, 5), 75, new Coordinate(3, 8)),
                new ToolRenderTransform (new Coordinate(-3, 1), 105, new Coordinate(3, 8))
        ));

        axeOffsets.put(DirectionType.Up, List.of(
                new ToolRenderTransform (new Coordinate(1, 13), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 12), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 9), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 8), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 8), 0, new Coordinate(4, 10))
        ));

        axeOffsets.put(DirectionType.Down, List.of(
                new ToolRenderTransform (new Coordinate(0, 10), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(0, 9), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 1), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 0), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 1), 0, new Coordinate(2, 7))
        ));

        allToolOffsets.put(StateType.ToolAxe, axeOffsets);


        //pickaxe
        EnumMap<DirectionType, List<ToolRenderTransform>> pickaxeOffsets = new EnumMap<>(DirectionType.class);

        pickaxeOffsets.put(DirectionType.Right, List.of(
                new ToolRenderTransform(new Coordinate(-1, 15), 20, new Coordinate(2, 10)),
                new ToolRenderTransform(new Coordinate(7, 14), -15, new Coordinate(2, 9)),
                new ToolRenderTransform(new Coordinate(9, 14), -45, new Coordinate(2, 9)),
                new ToolRenderTransform(new Coordinate(11, 13), -75, new Coordinate(2, 8)),
                new ToolRenderTransform(new Coordinate(13, 13), -105, new Coordinate(2, 8))
        ));

        pickaxeOffsets.put(DirectionType.Left, List.of(
                new ToolRenderTransform (new Coordinate(6, 14), -20, new Coordinate(3, 10)),
                new ToolRenderTransform (new Coordinate(3, 11), 15, new Coordinate(3, 9)),
                new ToolRenderTransform (new Coordinate(0, 9), 45, new Coordinate(3, 9)),
                new ToolRenderTransform (new Coordinate(-2, 5), 75, new Coordinate(3, 8)),
                new ToolRenderTransform (new Coordinate(-3, 1), 105, new Coordinate(3, 8))
        ));

        pickaxeOffsets.put(DirectionType.Up, List.of(
                new ToolRenderTransform (new Coordinate(1, 13), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 12), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 9), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 8), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(1, 8), 0, new Coordinate(4, 10))
        ));

        pickaxeOffsets.put(DirectionType.Down, List.of(
                new ToolRenderTransform (new Coordinate(0, 10), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(0, 9), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 1), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 0), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(1, 1), 0, new Coordinate(2, 7))
        ));

        allToolOffsets.put(StateType.ToolPickaxe, pickaxeOffsets);


        //watering can
        EnumMap<DirectionType, List<ToolRenderTransform>> wateringCanOffsets = new EnumMap<>(DirectionType.class);

        wateringCanOffsets.put(DirectionType.Right, List.of(
                new ToolRenderTransform(new Coordinate(10, 8), 0, new Coordinate(2, 10)),
                new ToolRenderTransform(new Coordinate(10, 8), 0, new Coordinate(2, 9)),
                new ToolRenderTransform(new Coordinate(10, 9), 0, new Coordinate(2, 9)),
                new ToolRenderTransform(new Coordinate(10, 9), 0, new Coordinate(2, 8)),
                new ToolRenderTransform(new Coordinate(10, 9), 0, new Coordinate(2, 8))
        ));

        wateringCanOffsets.put(DirectionType.Left, List.of(
                new ToolRenderTransform (new Coordinate(-9, 8), 0, new Coordinate(3, 10)),
                new ToolRenderTransform (new Coordinate(-9, 8), 0, new Coordinate(3, 9)),
                new ToolRenderTransform (new Coordinate(-9, 9), 0, new Coordinate(3, 9)),
                new ToolRenderTransform (new Coordinate(-9, 9), 0, new Coordinate(3, 8)),
                new ToolRenderTransform (new Coordinate(-9, 9), 0, new Coordinate(3, 8))
        ));

        wateringCanOffsets.put(DirectionType.Up, List.of(
                new ToolRenderTransform (new Coordinate(0, 13), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(0, 15), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(0, 18), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(0, 18), 0, new Coordinate(4, 10)),
                new ToolRenderTransform (new Coordinate(0, 18), 0, new Coordinate(4, 10))
        ));

        wateringCanOffsets.put(DirectionType.Down, List.of(
                new ToolRenderTransform (new Coordinate(0, 1), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(0, 2), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(0, 3), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(0, 3), 0, new Coordinate(2, 7)),
                new ToolRenderTransform (new Coordinate(0, 3), 0, new Coordinate(2, 7))
        ));

        allToolOffsets.put(StateType.ToolWateringCan, wateringCanOffsets);
    }

    public static ToolRenderTransform getTransform(StateType state, DirectionType direction, int frameIndex) {
        EnumMap<DirectionType, List<ToolRenderTransform>> dirMap = allToolOffsets.get(state);
        if (dirMap == null) return new ToolRenderTransform(new Coordinate(0, 0), 0, new Coordinate(0, 0));

        List<ToolRenderTransform> transforms = dirMap.get(direction);
        if (transforms == null || frameIndex < 0 || frameIndex >= transforms.size())
            return new ToolRenderTransform(new Coordinate(0, 0), 0, new Coordinate(0, 0));

        return transforms.get(frameIndex);
    }
}