package io.Ap.StardewValley.Client.Screen.PlayerScreen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.Ap.StardewValley.Common.Model.Tool.ToolLevel;
import io.Ap.StardewValley.Common.Model.Tool.ToolType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class BankToolAnimation {

    private final Map<DirectionType, Animation<TextureRegion>> toolMilkPailAnimations = new EnumMap<>(DirectionType.class);
    private final Map<DirectionType, Animation<TextureRegion>> toolFishingPoleAnimations = new EnumMap<>(DirectionType.class);

    private final List<Map<DirectionType, Animation<TextureRegion>>> hoeAnimations = new ArrayList<>();
    private final List<Map<DirectionType, Animation<TextureRegion>>> axeAnimations = new ArrayList<>();
    private final List<Map<DirectionType, Animation<TextureRegion>>> pickaxeAnimations = new ArrayList<>();
    private final List<Map<DirectionType, Animation<TextureRegion>>> wateringCanAnimations = new ArrayList<>();

    public BankToolAnimation(TextureRegion[][] sheet) {
        float frameDuration = 0.12f;

        for (int i = 0; i < 5; i++) {
            hoeAnimations.add(new EnumMap<>(DirectionType.class));
            axeAnimations.add(new EnumMap<>(DirectionType.class));
            pickaxeAnimations.add(new EnumMap<>(DirectionType.class));
            wateringCanAnimations.add(new EnumMap<>(DirectionType.class));
        }


        //DOWN
        for (int i = 0; i < 5; i++) {
            hoeAnimations.get(i).put(DirectionType.Down,
                    new Animation<>(frameDuration, sheet[i][0], sheet[i][0], sheet[i][1], sheet[i][1], sheet[i][1]));
            pickaxeAnimations.get(i).put(DirectionType.Down,
                    new Animation<>(frameDuration, sheet[5 + i][0], sheet[5 + i][0], sheet[5 + i][1], sheet[5 + i][1], sheet[5 + i][1]));
            axeAnimations.get(i).put(DirectionType.Down,
                    new Animation<>(frameDuration, sheet[10 + i][0], sheet[10 + i][0], sheet[10 + i][1], sheet[10 + i][1], sheet[10 + i][1]));
            wateringCanAnimations.get(i).put(DirectionType.Down,
                    new Animation<>(frameDuration, sheet[15 + i][1], sheet[15 + i][1], sheet[15 + i][1], sheet[15 + i][1], sheet[15 + i][1]));

        }


        //UP
        for (int i = 0; i < 5; i++) {
            hoeAnimations.get(i).put(DirectionType.Up,
                    new Animation<>(frameDuration, sheet[i][3], sheet[i][3], sheet[i][4], sheet[i][4], sheet[i][4]));
            pickaxeAnimations.get(i).put(DirectionType.Up,
                    new Animation<>(frameDuration, sheet[5 + i][3], sheet[5 + i][3], sheet[5 + i][4], sheet[5 + i][4], sheet[5 + i][4]));
            axeAnimations.get(i).put(DirectionType.Up,
                    new Animation<>(frameDuration, sheet[10 + i][3], sheet[10 + i][3], sheet[10 + i][4], sheet[10 + i][4], sheet[10 + i][4]));
            wateringCanAnimations.get(i).put(DirectionType.Up,
                    new Animation<>(frameDuration, sheet[15 + i][4], sheet[15 + i][4], sheet[15 + i][4], sheet[15 + i][4], sheet[15 + i][4]));

        }


        // RIGHT
        TextureRegion[][] rightHoeFrames = new TextureRegion[5][];
        TextureRegion[][] rightAxeFrames = new TextureRegion[5][];
        TextureRegion[][] rightPickaxeFrames = new TextureRegion[5][];
        TextureRegion[][] rightWateringCanFrames = new TextureRegion[5][];

        for (int i = 0; i < 5; i++) {
            rightHoeFrames[i] = new TextureRegion[] {sheet[i][2], sheet[i][2], sheet[i][2], sheet[i][2], sheet[i][2]};
            rightPickaxeFrames[i] = new TextureRegion[] {sheet[i + 5][2], sheet[i + 5][2], sheet[i + 5][2], sheet[i + 5][2], sheet[i + 5][2]};
            rightAxeFrames[i] = new TextureRegion[] {sheet[i + 10][2], sheet[i + 10][2], sheet[i + 10][2], sheet[i + 10][2], sheet[i + 10][2]};
            rightWateringCanFrames[i] = new TextureRegion[] {sheet[i + 15][2], sheet[i + 15][2], sheet[i + 15][3], sheet[i + 15][3], sheet[i + 15][3]};
        }

        for (int i = 0; i < 5; i++) {
            hoeAnimations.get(i).put(DirectionType.Right, new Animation<>(frameDuration, rightHoeFrames[i]));
            axeAnimations.get(i).put(DirectionType.Right, new Animation<>(frameDuration, rightAxeFrames[i]));
            pickaxeAnimations.get(i).put(DirectionType.Right, new Animation<>(frameDuration, rightPickaxeFrames[i]));
            wateringCanAnimations.get(i).put(DirectionType.Right, new Animation<>(frameDuration, rightWateringCanFrames[i]));
        }


        // LEFT = flipped RIGHT
        TextureRegion[][] leftHoeFrames = new TextureRegion[5][];
        TextureRegion[][] leftAxeFrames = new TextureRegion[5][];
        TextureRegion[][] leftPickaxeFrames = new TextureRegion[5][];
        TextureRegion[][] leftWateringCanFrames = new TextureRegion[5][];

        for (int i = 0; i < 5; i++) {
            leftHoeFrames[i] = new TextureRegion[5];
            leftAxeFrames[i] = new TextureRegion[5];
            leftPickaxeFrames[i] = new TextureRegion[5];
            leftWateringCanFrames[i] = new TextureRegion[5];

            for (int j = 0; j < 5; j++) {
                TextureRegion flippedHoe = new TextureRegion(rightHoeFrames[i][j]);
                flippedHoe.flip(true, false);
                leftHoeFrames[i][j] = flippedHoe;

                TextureRegion flippedAxe = new TextureRegion(rightAxeFrames[i][j]);
                flippedAxe.flip(true, false);
                leftAxeFrames[i][j] = flippedAxe;

                TextureRegion flippedPickaxe = new TextureRegion(rightPickaxeFrames[i][j]);
                flippedPickaxe.flip(true, false);
                leftPickaxeFrames[i][j] = flippedPickaxe;

                TextureRegion flippedWateringCan = new TextureRegion(rightWateringCanFrames[i][j]);
                flippedWateringCan.flip(true, false);
                leftWateringCanFrames[i][j] = flippedWateringCan;
            }
        }


        for (int i = 0; i < 5; i++) {
            hoeAnimations.get(i).put(DirectionType.Left, new Animation<>(frameDuration, leftHoeFrames[i]));
            axeAnimations.get(i).put(DirectionType.Left, new Animation<>(frameDuration, leftAxeFrames[i]));
            pickaxeAnimations.get(i).put(DirectionType.Left, new Animation<>(frameDuration, leftPickaxeFrames[i]));
            wateringCanAnimations.get(i).put(DirectionType.Left, new Animation<>(frameDuration, leftWateringCanFrames[i]));
        }
    }

    public Animation<TextureRegion> getAnimation(ToolType type, ToolLevel level, DirectionType state) {
        return switch (type) {
            case Hoe -> hoeAnimations.get(level.getLevel() - 1).get(state);
            case Axe -> axeAnimations.get(level.getLevel() - 1).get(state);
            case Pickaxe -> pickaxeAnimations.get(level.getLevel() - 1).get(state);
            case WateringCan -> wateringCanAnimations.get(level.getLevel() - 1).get(state);

            default -> null;
        };
    }
}