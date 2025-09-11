package io.Ap.StardewValley.Client.Screen.PlayerScreen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.EnumMap;
import java.util.Map;

public class BankPlayerAnimationFrames {
    private final Map<DirectionType, Animation<TextureRegion>> walkAnimations = new EnumMap<>(DirectionType.class);

    private final Map<DirectionType, TextureRegion> idleFrames = new EnumMap<>(DirectionType.class);

    private final Animation<TextureRegion> faintAnimation;
    private final Animation<TextureRegion> eatAnimation;

    private final Map<DirectionType, Animation<TextureRegion>> toolMilkPailAnimations = new EnumMap<>(DirectionType.class);
    private final Map<DirectionType, Animation<TextureRegion>> toolFishingPoleAnimations = new EnumMap<>(DirectionType.class);
    private final Map<DirectionType, Animation<TextureRegion>> toolScytheAnimations = new EnumMap<>(DirectionType.class);
    private final Map<DirectionType, Animation<TextureRegion>> toolShearAnimations = new EnumMap<>(DirectionType.class);
    private final Map<DirectionType, Animation<TextureRegion>> toolHoeAnimations = new EnumMap<>(DirectionType.class);
    private final Map<DirectionType, Animation<TextureRegion>> toolWateringCanAnimations = new EnumMap<>(DirectionType.class);


    public BankPlayerAnimationFrames(TextureRegion[][] sheet) {
        float frameDuration = 0.12f;

        faintAnimation = new Animation<>(frameDuration + 1.88f, sheet[0][0], sheet[2][4], sheet[0][4], sheet[0][5]);
        eatAnimation = new Animation<>(frameDuration * 2, sheet[14][2], sheet[14][3], sheet[14][4], sheet[14][3], sheet[14][4], sheet[14][3]);

        //DOWN
        walkAnimations.put(DirectionType.Down,
                new Animation<>(frameDuration, sheet[0][1], sheet[3][0], sheet[0][2], sheet[3][1]));
        idleFrames.put(DirectionType.Down, sheet[0][0]);
        toolShearAnimations.put(DirectionType.Down,
                new Animation<>(frameDuration, sheet[13][0], sheet[13][1], sheet[13][0], sheet[13][1], sheet[13][0], sheet[13][1]));
        toolHoeAnimations.put(DirectionType.Down,
                new Animation<>(frameDuration, sheet[11][0], sheet[11][1], sheet[11][2], sheet[11][3], sheet[11][4]));
        toolWateringCanAnimations.put(DirectionType.Down,
                new Animation<>(frameDuration, sheet[4][2], sheet[4][1], sheet[4][0], sheet[4][0], sheet[4][0]));


        //UP
        walkAnimations.put(DirectionType.Up,
                new Animation<>(frameDuration, sheet[2][1], sheet[3][4], sheet[2][2], sheet[3][5]));
        idleFrames.put(DirectionType.Up, sheet[2][0]);
        toolShearAnimations.put(DirectionType.Up,
                new Animation<>(frameDuration, sheet[13][4], sheet[13][5], sheet[13][4], sheet[13][5], sheet[13][4], sheet[13][5]));
        toolHoeAnimations.put(DirectionType.Up,
                new Animation<>(frameDuration, sheet[10][4], sheet[10][4], sheet[10][3], sheet[10][2], sheet[10][2]));
        toolWateringCanAnimations.put(DirectionType.Up,
                new Animation<>(frameDuration, sheet[8][5], sheet[8][5], sheet[8][5], sheet[8][5], sheet[8][5]));

        // RIGHT
        TextureRegion[] rightFrames = new TextureRegion[] {
                sheet[3][3], sheet[2][5], sheet[1][0], sheet[3][2] , sheet[1][5], sheet[1][0]
        };
        TextureRegion[] rightShearFrames = new TextureRegion[] {
                sheet[13][2], sheet[13][3], sheet[13][2], sheet[13][3], sheet[13][2], sheet[13][3]
        };
        TextureRegion[] rightHoeFrames = new TextureRegion[] {
                sheet[8][0], sheet[8][1], sheet[8][2], sheet[8][3], sheet[8][4]
        };
        TextureRegion[] rightWateringCanFrames = new TextureRegion[] {
                sheet[9][5], sheet[9][5], sheet[8][2], sheet[8][2], sheet[8][2]
        };

        walkAnimations.put(DirectionType.Right, new Animation<>(frameDuration, rightFrames));
        idleFrames.put(DirectionType.Right, sheet[1][0]);
        toolShearAnimations.put(DirectionType.Right, new Animation<>(frameDuration, rightShearFrames));
        toolHoeAnimations.put(DirectionType.Right, new Animation<>(frameDuration, rightHoeFrames));
        toolWateringCanAnimations.put(DirectionType.Right, new Animation<>(frameDuration, rightWateringCanFrames));


        // LEFT = flipped RIGHT
        TextureRegion[] leftFrames = new TextureRegion[rightFrames.length];
        for (int i = 0; i < rightFrames.length; i++) {
            TextureRegion flipped = new TextureRegion(rightFrames[i]);
            flipped.flip(true, false);  // Flip horizontally
            leftFrames[i] = flipped;
        }

        TextureRegion idleLeft = new TextureRegion(sheet[1][0]);
        idleLeft.flip(true, false);

        TextureRegion[] leftShearFrames = new TextureRegion[rightShearFrames.length];
        for (int i = 0; i < rightShearFrames.length; i++) {
            TextureRegion flipped = new TextureRegion(rightShearFrames[i]);
            flipped.flip(true, false);  // Flip horizontally
            leftShearFrames[i] = flipped;
        }

        TextureRegion[] leftHoeFrames = new TextureRegion[rightHoeFrames.length];
        for (int i = 0; i < rightHoeFrames.length; i++) {
            TextureRegion flipped = new TextureRegion(rightHoeFrames[i]);
            flipped.flip(true, false);  // Flip horizontally
            leftHoeFrames[i] = flipped;
        }

        TextureRegion[] leftWateringCanFrames = new TextureRegion[rightWateringCanFrames.length];
        for (int i = 0; i < rightWateringCanFrames.length; i++) {
            TextureRegion flipped = new TextureRegion(rightWateringCanFrames[i]);
            flipped.flip(true, false);  // Flip horizontally
            leftWateringCanFrames[i] = flipped;
        }


        walkAnimations.put(DirectionType.Left, new Animation<>(frameDuration, leftFrames));
        idleFrames.put(DirectionType.Left, idleLeft);
        toolShearAnimations.put(DirectionType.Left, new Animation<>(frameDuration, leftShearFrames));
        toolHoeAnimations.put(DirectionType.Left, new Animation<>(frameDuration, leftHoeFrames));
        toolWateringCanAnimations.put(DirectionType.Left, new Animation<>(frameDuration, leftWateringCanFrames));
    }

    public Animation<TextureRegion> getAnimation(StateType type, DirectionType state) {
        return switch (type) {
            case Walk -> walkAnimations.get(state);
            case Eat -> eatAnimation;
            case Faint -> faintAnimation;
            case ToolShear -> toolShearAnimations.get(state);
            case ToolHoe, ToolAxe, ToolPickaxe -> toolHoeAnimations.get(state);
            case ToolWateringCan -> toolWateringCanAnimations.get(state);


            default -> null;
        };
    }

    public TextureRegion getIdleFrame(DirectionType state) {
        return idleFrames.get(state);
    }
}
