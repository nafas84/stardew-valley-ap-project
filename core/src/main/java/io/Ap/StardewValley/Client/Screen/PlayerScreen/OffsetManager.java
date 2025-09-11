package io.Ap.StardewValley.Client.Screen.PlayerScreen;

import io.Ap.StardewValley.Common.Model.Map.Coordinate;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class OffsetManager {
    private static final Map<OffsetType, EnumMap<StateType, EnumMap<DirectionType, List<Coordinate>>>> allOffsets = new EnumMap<>(OffsetType.class);

    static {
        // Hair
        EnumMap<StateType, EnumMap<DirectionType, List<Coordinate>>> hairOffsets = new EnumMap<>(StateType.class);

        // walk
        EnumMap<DirectionType, List<Coordinate>> hairWalk = new EnumMap<>(DirectionType.class);
        hairWalk.put(DirectionType.Right, List.of(
                new Coordinate(0, -2),
                new Coordinate(0, -2),
                new Coordinate(0, -1),
                new Coordinate(0, -2),
                new Coordinate(0, -2),
                new Coordinate(0, -1)
        ));
        hairWalk.put(DirectionType.Left, List.of(
                new Coordinate(0, -2),
                new Coordinate(0, -2),
                new Coordinate(0, -1),
                new Coordinate(0, -2),
                new Coordinate(0, -2),
                new Coordinate(0, -1)
        ));
        hairWalk.put(DirectionType.Up, List.of(
                new Coordinate(0, -2),
                new Coordinate(0, -3),
                new Coordinate(0, -2),
                new Coordinate(0, -3)
        ));
        hairWalk.put(DirectionType.Down, List.of(
                new Coordinate(0, -2),
                new Coordinate(0, -3),
                new Coordinate(0, -2),
                new Coordinate(0, -3)
        ));
        hairOffsets.put(StateType.Walk, hairWalk);

        // idle
        EnumMap<DirectionType, List<Coordinate>> hairIdle = new EnumMap<>(DirectionType.class);
        hairIdle.put(DirectionType.Right, List.of(new Coordinate(0, -1)));
        hairIdle.put(DirectionType.Left, List.of(new Coordinate(0, -1)));
        hairIdle.put(DirectionType.Up, List.of(new Coordinate(0, -1)));
        hairIdle.put(DirectionType.Down, List.of(new Coordinate(0, -1)));
        hairOffsets.put(StateType.Idle, hairIdle);

        // faint:
        EnumMap<DirectionType, List<Coordinate>> hairFaint = new EnumMap<>(DirectionType.class);
        hairFaint.put(DirectionType.Down, List.of(
                new Coordinate(0, -1),
                new Coordinate(0, -2),
                new Coordinate(0, -6),
                new Coordinate(0, -7)
        ));
        hairOffsets.put(StateType.Faint, hairFaint);


        //shear
        EnumMap<DirectionType, List<Coordinate>> hairToolShear = new EnumMap<>(DirectionType.class);
        hairToolShear.put(DirectionType.Right, List.of(
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0)
        ));
        hairToolShear.put(DirectionType.Left, List.of(
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0)
        ));
        hairToolShear.put(DirectionType.Up, List.of(
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -1)
        ));
        hairToolShear.put(DirectionType.Down, List.of(
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -1)
        ));
        hairOffsets.put(StateType.ToolShear, hairToolShear);

        //hoe
        EnumMap<DirectionType, List<Coordinate>> hairToolHoe = new EnumMap<>(DirectionType.class);
        hairToolHoe.put(DirectionType.Right, List.of(
                new Coordinate(-1, -1),
                new Coordinate(-1, 0),
                new Coordinate(-1, 1),
                new Coordinate(-1, 2),
                new Coordinate(-1, 1)
        ));
        hairToolHoe.put(DirectionType.Left, List.of(
                new Coordinate(1, -1),
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(1, 2),
                new Coordinate(1, 1)
        ));
        hairToolHoe.put(DirectionType.Up, List.of(
                new Coordinate(0, -1),
                new Coordinate(0, -1),
                new Coordinate(0, -2),
                new Coordinate(0, -3),
                new Coordinate(0, -3)
        ));
        hairToolHoe.put(DirectionType.Down, List.of(
                new Coordinate(0, -2),
                new Coordinate(0, -1),
                new Coordinate(0, 0),
                new Coordinate(0, 1),
                new Coordinate(0, 0)

        ));
        hairOffsets.put(StateType.ToolHoe, hairToolHoe);
        hairOffsets.put(StateType.ToolAxe, hairToolHoe);
        hairOffsets.put(StateType.ToolPickaxe, hairToolHoe);

        //hoe
        EnumMap<DirectionType, List<Coordinate>> hairWateringTool = new EnumMap<>(DirectionType.class);
        hairWateringTool.put(DirectionType.Right, List.of(
                new Coordinate(-1, -1),
                new Coordinate(-1, 0),
                new Coordinate(-1, 1),
                new Coordinate(-1, 1),
                new Coordinate(-1, 1)
        ));
        hairWateringTool.put(DirectionType.Left, List.of(
                new Coordinate(1, -1),
                new Coordinate(1, 0),
                new Coordinate(1, 1),
                new Coordinate(1, 1),
                new Coordinate(1, 1)
        ));
        hairWateringTool.put(DirectionType.Up, List.of(
                new Coordinate(0, -2),
                new Coordinate(0, -2),
                new Coordinate(0, -2),
                new Coordinate(0, -2),
                new Coordinate(0, -2)
        ));
        hairWateringTool.put(DirectionType.Down, List.of(
                new Coordinate(0, -1),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0),
                new Coordinate(0, 0)

        ));
        hairOffsets.put(StateType.ToolWateringCan, hairWateringTool);

        allOffsets.put(OffsetType.Hair, hairOffsets);

        // ---------------------------------------------------------------------------------------------
        // Shirt
        EnumMap<StateType, EnumMap<DirectionType, List<Coordinate>>> shirtOffsets = new EnumMap<>(StateType.class);

        // walk
        EnumMap<DirectionType, List<Coordinate>> shirtWalk = new EnumMap<>(DirectionType.class);
        shirtWalk.put(DirectionType.Right, List.of(
                new Coordinate(4, 8),
                new Coordinate(4, 8),
                new Coordinate(4, 9),
                new Coordinate(4, 8),
                new Coordinate(4, 8),
                new Coordinate(4, 9)
        ));
        shirtWalk.put(DirectionType.Left, List.of(
                new Coordinate(4, 8),
                new Coordinate(4, 8),
                new Coordinate(4, 9),
                new Coordinate(4, 8),
                new Coordinate(4, 8),
                new Coordinate(4, 9)
        ));
        shirtWalk.put(DirectionType.Up, List.of(
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9)
        ));
        shirtWalk.put(DirectionType.Down, List.of(
                new Coordinate(4, 8),
                new Coordinate(4, 7),
                new Coordinate(4, 8),
                new Coordinate(4, 7)
        ));
        shirtOffsets.put(StateType.Walk, shirtWalk);

        // idle
        EnumMap<DirectionType, List<Coordinate>> shirtIdle = new EnumMap<>(DirectionType.class);
        shirtIdle.put(DirectionType.Right, List.of(new Coordinate(4, 9)));
        shirtIdle.put(DirectionType.Left, List.of(new Coordinate(4, 9)));
        shirtIdle.put(DirectionType.Up, List.of(new Coordinate(4, 10)));
        shirtIdle.put(DirectionType.Down, List.of(new Coordinate(4, 9)));
        shirtOffsets.put(StateType.Idle, shirtIdle);

        //eat
        EnumMap<DirectionType, List<Coordinate>> shirtEat = new EnumMap<>(DirectionType.class);
        shirtEat.put(DirectionType.Right, List.of(
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9)
        ));
        shirtEat.put(DirectionType.Left, List.of(
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9)
        ));
        shirtEat.put(DirectionType.Up, List.of(
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 10)
        ));
        shirtEat.put(DirectionType.Down, List.of(
                new Coordinate(4, 11),
                new Coordinate(4, 11),
                new Coordinate(4, 11),
                new Coordinate(4, 11),
                new Coordinate(4, 11),
                new Coordinate(4, 11)
        ));
        shirtOffsets.put(StateType.Eat, shirtEat);

        //faint
        EnumMap<DirectionType, List<Coordinate>> shirtFaint = new EnumMap<>(DirectionType.class);
        shirtFaint.put(DirectionType.Down, List.of(
                new Coordinate(4, 10),
                new Coordinate(4, 8),
                new Coordinate(4, 5),
                new Coordinate(4, 4)
        ));
        shirtOffsets.put(StateType.Faint, shirtFaint);

        //shear
        EnumMap<DirectionType, List<Coordinate>> shirtToolShear = new EnumMap<>(DirectionType.class);
        shirtToolShear.put(DirectionType.Right, List.of(
                new Coordinate(3, 9),
                new Coordinate(3, 9),
                new Coordinate(3, 9),
                new Coordinate(3, 9),
                new Coordinate(3, 9),
                new Coordinate(3, 9)
        ));
        shirtToolShear.put(DirectionType.Left, List.of(
                new Coordinate(5, 9),
                new Coordinate(5, 9),
                new Coordinate(5, 9),
                new Coordinate(5, 9),
                new Coordinate(5, 9),
                new Coordinate(5, 9)
        ));
        shirtToolShear.put(DirectionType.Up, List.of(
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 10)
        ));
        shirtToolShear.put(DirectionType.Down, List.of(
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9)
        ));
        shirtOffsets.put(StateType.ToolShear, shirtToolShear);

        //hoe
        EnumMap<DirectionType, List<Coordinate>> shirtToolHoe = new EnumMap<>(DirectionType.class);
        shirtToolHoe.put(DirectionType.Right, List.of(
                new Coordinate(3, 9),
                new Coordinate(3, 9),
                new Coordinate(3, 10),
                new Coordinate(3, 10),
                new Coordinate(3, 10)
        ));
        shirtToolHoe.put(DirectionType.Left, List.of(
                new Coordinate(5, 9),
                new Coordinate(5, 9),
                new Coordinate(5, 10),
                new Coordinate(5, 10),
                new Coordinate(5, 10)
        ));
        shirtToolHoe.put(DirectionType.Up, List.of(
                new Coordinate(4, 10),
                new Coordinate(4, 10),
                new Coordinate(4, 9),
                new Coordinate(4, 8),
                new Coordinate(4, 8)
        ));
        shirtToolHoe.put(DirectionType.Down, List.of(
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 10),
                new Coordinate(4, 11),
                new Coordinate(4, 9)

        ));
        shirtOffsets.put(StateType.ToolHoe, shirtToolHoe);
        shirtOffsets.put(StateType.ToolAxe, shirtToolHoe);
        shirtOffsets.put(StateType.ToolPickaxe, shirtToolHoe);

        EnumMap<DirectionType, List<Coordinate>> shirtWateringTool = new EnumMap<>(DirectionType.class);
        shirtWateringTool.put(DirectionType.Right, List.of(
                new Coordinate(3, 9),
                new Coordinate(3, 9),
                new Coordinate(3, 10),
                new Coordinate(3, 10),
                new Coordinate(3, 10)
        ));
        shirtWateringTool.put(DirectionType.Left, List.of(
                new Coordinate(5, 9),
                new Coordinate(5, 9),
                new Coordinate(5, 10),
                new Coordinate(5, 10),
                new Coordinate(5, 10)
        ));
        shirtWateringTool.put(DirectionType.Up, List.of(
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 9)
        ));
        shirtWateringTool.put(DirectionType.Down, List.of(
                new Coordinate(4, 9),
                new Coordinate(4, 9),
                new Coordinate(4, 10),
                new Coordinate(4, 11),
                new Coordinate(4, 9)

        ));
        shirtOffsets.put(StateType.ToolWateringCan, shirtWateringTool);


        allOffsets.put(OffsetType.Shirt, shirtOffsets);
    }

    public static Coordinate getOffset(OffsetType type, StateType state, DirectionType direction, int frameIndex) {
        EnumMap<StateType, EnumMap<DirectionType, List<Coordinate>>> stateMap = allOffsets.get(type);
        if (stateMap == null) return new Coordinate(0, 0);

        EnumMap<DirectionType, List<Coordinate>> dirMap = stateMap.get(state);
        if (dirMap == null) return new Coordinate(0, 0);

        List<Coordinate> offsets = dirMap.get(direction);
        if (offsets == null || frameIndex < 0 || frameIndex >= offsets.size()) return new Coordinate(0, 0);

        return offsets.get(frameIndex);
    }
}

enum OffsetType {
    Hair,
    Shirt;
}