package io.Ap.StardewValley.Client.Screen.PlayerScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Tool.*;
import io.Ap.StardewValley.StardewValley;

import java.util.EnumMap;
import java.util.Map;

public class PlayerRender {
    private TextureRegion[][] hairSheet;
    private TextureRegion[][] shirtSheet;
    private TextureRegion[][] pantSheet;

    private float stateTime = 0f;
    private final Texture shadow = new Texture("etc/shadow.png");

    private BankPlayerAnimationFrames bodyAnimations;
    private BankPlayerAnimationFrames hand01Animations;
    private BankPlayerAnimationFrames hand02Animations;
    private BankPlayerAnimationFrames pantAnimations;

    private BankToolAnimation toolAnimations;

    private final Map<DirectionType, TextureRegion> hairFrames = new EnumMap<>(DirectionType.class);
    private final Map<DirectionType, TextureRegion> shirtFrames = new EnumMap<>(DirectionType.class);

    private static Image headImage;

    public PlayerRender() {
        int pantIndex = App.getGame().getCurrentPlayer().getPantIndex();
        int shirtIndex = App.getGame().getCurrentPlayer().getShirtIndex();
        int hairIndex = App.getGame().getCurrentPlayer().getHairIndex();

        // HairStyle
        hairSheet = TextureRegion.split(new Texture("player/clothes/hairstyles.png"), 16, 32);
        hairFrames.put(DirectionType.Down, hairSheet[(hairIndex / 8) * 3][hairIndex % 8]);
        hairFrames.put(DirectionType.Up, hairSheet[(hairIndex / 8) * 3 + 2][hairIndex % 8]);
        hairFrames.put(DirectionType.Right, hairSheet[(hairIndex / 8) * 3 + 1][hairIndex % 8]);
        TextureRegion leftHair = new TextureRegion(hairSheet[(hairIndex / 8) * 3 + 1][hairIndex % 8]);
        leftHair.flip(true, false);
        hairFrames.put(DirectionType.Left, leftHair);

        // Shirt
        shirtSheet = TextureRegion.split(new Texture("player/clothes/shirts.png"), 8, 8);
        shirtFrames.put(DirectionType.Down, shirtSheet[(shirtIndex / 18) * 4][shirtIndex % 16]);
        shirtFrames.put(DirectionType.Right, shirtSheet[(shirtIndex / 18) * 4 + 1][shirtIndex % 16]);
        shirtFrames.put(DirectionType.Left, shirtSheet[(shirtIndex / 18) * 4 + 2][shirtIndex % 16]);
        shirtFrames.put(DirectionType.Up, shirtSheet[(shirtIndex / 18) * 4 + 3][shirtIndex % 16]);

        // Body, Hand, Pant
        TextureRegion[][] bodySheet = TextureRegion.split(new Texture("player/body_boy.png"), 16, 32);
        this.bodyAnimations = new BankPlayerAnimationFrames(bodySheet);
        TextureRegion[][] hand01Sheet = TextureRegion.split(new Texture("player/hand_01.png"), 16, 32);
        this.hand01Animations = new BankPlayerAnimationFrames(hand01Sheet);
        TextureRegion[][] hand02Sheet = TextureRegion.split(new Texture("player/hand_02.png"), 16, 32);
        this.hand02Animations = new BankPlayerAnimationFrames(hand02Sheet);

        pantSheet = TextureRegion.split(new Texture("player/pants/pant_" + pantIndex + ".png"), 16, 32);
        this.pantAnimations = new BankPlayerAnimationFrames(pantSheet);

        // make head:
        setHeadImage(bodySheet[0][1], hairSheet[(hairIndex / 8) * 3][hairIndex % 8],
                App.getColor(App.getGame().getCurrentPlayer().getHairColor()), hairIndex);

        //tool
        TextureRegion[][] toolsSheet = TextureRegion.split(new Texture("player/tools/tools.png"), 16, 24);
        this.toolAnimations = new BankToolAnimation(toolsSheet);
    }

    public void setHeadImage(TextureRegion fullHeadFrame, TextureRegion fullHairFrame, Color hairColor, int hairIndex) {
        int longHair = (hairIndex < 16) ? 1 : 0;
        int width = 16, height = 16;

        Pixmap headPixmapFull = getPixmapFromTexture(fullHeadFrame.getTexture());
        Pixmap hairPixmapFull = getPixmapFromTexture(fullHairFrame.getTexture());

        Pixmap headPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        Pixmap hairPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        Pixmap combined = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        headPixmap.drawPixmap(headPixmapFull, 0, 0, fullHeadFrame.getRegionX(), fullHeadFrame.getRegionY(), width, height);
        hairPixmap.drawPixmap(hairPixmapFull, 0, 0, fullHairFrame.getRegionX(), fullHairFrame.getRegionY(), width, height);

        combined.drawPixmap(headPixmap, 0, 0);

        // color:
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = hairPixmap.getPixel(x, y);

                int a = pixel & 0xff;
                if (a == 0) continue;

                float r = ((pixel >> 24) & 0xff) / 255f;
                float g = ((pixel >> 16) & 0xff) / 255f;
                float b = ((pixel >> 8) & 0xff) / 255f;
                float alpha = a / 255f;

                combined.setColor(r * hairColor.r, g * hairColor.g, b * hairColor.b, alpha);
                combined.drawPixel(x, y + 1 + longHair);
            }
        }

        Texture finalTexture = new Texture(combined);
        headImage = new Image(finalTexture);
        headImage.setSize(width, height);

        headPixmap.dispose();
        hairPixmap.dispose();
        combined.dispose();
    }

    private Pixmap getPixmapFromTexture(Texture texture) {
        TextureData textureData = texture.getTextureData();
        if (!textureData.isPrepared()) textureData.prepare();
        return textureData.consumePixmap();
    }



    public static Image getHeadImage() {
        return headImage;
    }

    public void render() {
        SpriteBatch batch = StardewValley.getBatch();
        stateTime += Gdx.graphics.getDeltaTime();

        StateType state = App.getGame().getCurrentPlayer().getState();
        DirectionType direction = App.getGame().getCurrentPlayer().getDirection();

        float x = App.getGame().getCurrentPlayer().getXLibGdx();
        float y = App.getGame().getCurrentPlayer().getYLibGdx();

        TextureRegion bodyFrame, handFrame, pantFrame, hairFrame, shirtFrame;
        TextureRegion toolFrame = null;
        int frameIndex = 0;
        if (!state.equals(StateType.Idle)) {
            Animation<TextureRegion> bodyAnim = bodyAnimations.getAnimation(state, direction);

            boolean looping = !(state.equals(StateType.Faint));
            if (looping)
                bodyAnim.setPlayMode(Animation.PlayMode.LOOP);

            frameIndex = bodyAnim.getKeyFrameIndex(stateTime);

            Animation<TextureRegion> handAnim = hand01Animations.getAnimation(state, direction);
            Animation<TextureRegion> pantAnim = pantAnimations.getAnimation(state, direction);

            Tool tool = App.getGame().getCurrentPlayer().getCurrentTool();
            if (tool != null) {
                Animation<TextureRegion> toolAnim = null;
                if (tool instanceof Hoe hoe) {
                    toolAnim = toolAnimations.getAnimation(tool.getType(), hoe.getLevel(), direction);
                } else if (tool instanceof Axe axe) {
                    toolAnim = toolAnimations.getAnimation(tool.getType(), axe.getLevel(), direction);
                } else if (tool instanceof Pickaxe pickaxe) {
                    toolAnim = toolAnimations.getAnimation(tool.getType(), pickaxe.getLevel(), direction);
                } else if (tool instanceof WateringCan wateringCan) {
                    toolAnim = toolAnimations.getAnimation(tool.getType(), wateringCan.getLevel(), direction);
                }
                if (toolAnim != null) {
                    toolFrame = toolAnim.getKeyFrame(stateTime, true);
                }
            }

            bodyFrame = bodyAnim.getKeyFrame(stateTime, looping);
            handFrame = handAnim.getKeyFrame(stateTime, looping);
            pantFrame = pantAnim.getKeyFrame(stateTime, looping);
        } else {
            bodyFrame = bodyAnimations.getIdleFrame(direction);
            handFrame = hand01Animations.getIdleFrame(direction);
            pantFrame = pantAnimations.getIdleFrame(direction);
        }

        hairFrame = hairFrames.get(direction);
        shirtFrame = shirtFrames.get(direction);

        Coordinate hairOffset = OffsetManager.getOffset(OffsetType.Hair, state, direction, frameIndex);
        Coordinate shirtOffset = OffsetManager.getOffset(OffsetType.Shirt, state, direction, frameIndex);

        ToolRenderTransform toolTransform = ToolOffsetManager.getTransform(state, direction, frameIndex);

        int longHair = (App.getGame().getCurrentPlayer().getHairIndex() < 16) ? 0 : 1;

        float scale = App.getGame().getPlayerScale();
        batch.draw(shadow, x + (bodyFrame.getRegionWidth() * scale - 12 * scale) / 2f , y - (shadow.getHeight() * scale) * 0.25f, shadow.getWidth() * scale, shadow.getHeight() * scale);


        if (toolFrame != null && direction == DirectionType.Up && (state == StateType.ToolHoe || state == StateType.ToolAxe || state == StateType.ToolPickaxe || state == StateType.ToolWateringCan)) {
            batch.draw(toolFrame,
                    x + toolTransform.getX(), y + toolTransform.getY(),
                    toolTransform.getOriginX(), toolTransform.getOriginY(), // origin for rotation
                    toolFrame.getRegionWidth(), toolFrame.getRegionHeight(),
                    1.1f, 1.1f, // scale
                    toolTransform.getRotation()
            );
        }

        batch.draw(bodyFrame, x, y, bodyFrame.getRegionWidth() * scale, bodyFrame.getRegionHeight() * scale);

        batch.draw(shirtFrame, x + shirtOffset.getX() * scale, y + shirtOffset.getY() * scale, shirtFrame.getRegionWidth() * scale, shirtFrame.getRegionHeight() * scale);

        batch.setColor(App.getColor(App.getGame().getCurrentPlayer().getPantColor()));
        batch.draw(pantFrame, x, y, pantFrame.getRegionWidth() * scale, pantFrame.getRegionHeight() * scale);
        batch.setColor(Color.WHITE);

        batch.setColor(App.getColor(App.getGame().getCurrentPlayer().getHairColor()));
        batch.draw(hairFrame, x + hairOffset.getX() * scale, y + (hairOffset.getY() + longHair) * scale, hairFrame.getRegionWidth() * scale, hairFrame.getRegionHeight() * scale);
        batch.setColor(Color.WHITE);

        renderOtherPlayers();

        if (toolFrame != null && direction != DirectionType.Up && direction != DirectionType.Down && (state == StateType.ToolHoe || state == StateType.ToolAxe || state == StateType.ToolPickaxe || state == StateType.ToolWateringCan)) {
            batch.draw(toolFrame,
                    x + toolTransform.getX(), y + toolTransform.getY(),
                    toolTransform.getOriginX(), toolTransform.getOriginY(), // origin for rotation
                    toolFrame.getRegionWidth(), toolFrame.getRegionHeight(),
                    0.95f, 0.95f, // scale
                    toolTransform.getRotation()
            );
        }

        batch.draw(handFrame, x, y, handFrame.getRegionWidth() * scale, handFrame.getRegionHeight() * scale);

        if (toolFrame != null && direction == DirectionType.Down && (state == StateType.ToolHoe || state == StateType.ToolAxe || state == StateType.ToolPickaxe || state == StateType.ToolWateringCan)) {
            batch.draw(toolFrame,
                    x + toolTransform.getX(), y + toolTransform.getY(),
                    toolTransform.getOriginX(), toolTransform.getOriginY(), // origin for rotation
                    toolFrame.getRegionWidth(), toolFrame.getRegionHeight(),
                    0.95f, 0.95f, // scale
                    toolTransform.getRotation()
            );
        }
    }

    public void renderOtherPlayers() {
        SpriteBatch batch = StardewValley.getBatch();
        float scale = App.getGame().getPlayerScale();

        for (Player other : App.getGame().getPlayers()) {
            if (other.getId() == App.getGame().getCurrentPlayer().getId()) continue;
            if (!isInTheSameRegion(other.getCoordinate(), App.getGame().getCurrentPlayer().getCoordinate())) continue;

            DirectionType direction = other.getDirection();

            TextureRegion bodyFrame = bodyAnimations.getIdleFrame(direction);
            TextureRegion handFrame = hand01Animations.getIdleFrame(direction);

            // TODO: یتیمه شلوار خودشو نشون میده
            TextureRegion pantFrame = pantAnimations.getIdleFrame(direction);

            TextureRegion hairFrame = getHairFrameFor(other, direction);
            TextureRegion shirtFrame = getShirtFrameFor(other, direction);

            Coordinate hairOffset = OffsetManager.getOffset(OffsetType.Hair, StateType.Idle, direction, 0);
            Coordinate shirtOffset = OffsetManager.getOffset(OffsetType.Shirt, StateType.Idle, direction, 0);

            int longHair = (other.getHairIndex() < 16) ? 0 : 1;

            float x = other.getXLibGdx();
            float y = other.getYLibGdx();

            batch.draw(shadow, x + (bodyFrame.getRegionWidth() * scale - 12 * scale) / 2f,
                    y - (shadow.getHeight() * scale) * 0.25f,
                    shadow.getWidth() * scale, shadow.getHeight() * scale);

            batch.draw(bodyFrame, x, y, bodyFrame.getRegionWidth() * scale, bodyFrame.getRegionHeight() * scale);

            batch.draw(shirtFrame, x + shirtOffset.getX() * scale, y + shirtOffset.getY() * scale,
                    shirtFrame.getRegionWidth() * scale, shirtFrame.getRegionHeight() * scale);

            batch.setColor(App.getColor(other.getPantColor()));
            batch.draw(pantFrame, x, y, pantFrame.getRegionWidth() * scale, pantFrame.getRegionHeight() * scale);
            batch.setColor(Color.WHITE);

            batch.setColor(App.getColor(other.getHairColor()));
            batch.draw(hairFrame, x + hairOffset.getX() * scale, y + (hairOffset.getY() + longHair) * scale,
                    hairFrame.getRegionWidth() * scale, hairFrame.getRegionHeight() * scale);
            batch.setColor(Color.WHITE);

            batch.draw(handFrame, x, y, handFrame.getRegionWidth() * scale, handFrame.getRegionHeight() * scale);

        }
    }

    private TextureRegion getHairFrameFor(Player p, DirectionType direction) {
        int hairIndex = p.getHairIndex();

        TextureRegion frame;
        switch (direction) {
            case Down -> frame = hairSheet[(hairIndex / 8) * 3][hairIndex % 8];
            case Up -> frame = hairSheet[(hairIndex / 8) * 3 + 2][hairIndex % 8];
            case Right -> frame = hairSheet[(hairIndex / 8) * 3 + 1][hairIndex % 8];
            case Left -> {
                frame = new TextureRegion(hairSheet[(hairIndex / 8) * 3 + 1][hairIndex % 8]);
                frame.flip(true, false);
            }
            default -> throw new IllegalStateException();
        }
        return frame;
    }

    private TextureRegion getShirtFrameFor(Player p, DirectionType direction) {
        int shirtIndex = p.getShirtIndex();

        TextureRegion frame;
        switch (direction) {
            case Down -> frame = shirtSheet[(shirtIndex / 18) * 4][shirtIndex % 16];
            case Right -> frame = shirtSheet[(shirtIndex / 18) * 4 + 1][shirtIndex % 16];
            case Left -> frame = shirtSheet[(shirtIndex / 18) * 4 + 2][shirtIndex % 16];
            case Up -> frame = shirtSheet[(shirtIndex / 18) * 4 + 3][shirtIndex % 16];
            default -> throw new IllegalStateException();
        }
        return frame;
    }

    public boolean isInTheSameRegion(Coordinate cor1, Coordinate cor2) {
        Coordinate region1 = App.getGame().getMap().getCurrentRegionCoordinate(cor1);
        Coordinate region2 = App.getGame().getMap().getCurrentRegionCoordinate(cor2);
        return region1.getX() == region2.getX() && region1.getY() == region2.getY();
    }

}
