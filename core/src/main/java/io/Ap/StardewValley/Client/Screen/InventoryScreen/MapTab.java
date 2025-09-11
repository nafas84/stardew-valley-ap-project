package io.Ap.StardewValley.Client.Screen.InventoryScreen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Client.Screen.GameScreen;
import io.Ap.StardewValley.Client.Screen.PlayerScreen.PlayerRender;

public class MapTab extends Window {
    private final float scale = 0.15f;
    private final Skin skin;
    private final Image map;
    private final Image playerImage;

    private final Group mapGroup;

    public MapTab(Skin skin) {
        super("", skin);
        this.skin = skin;
        this.map = GameScreen.getFullMap();
        this.playerImage = PlayerRender.getHeadImage();

        this.setSize(1050, 650);
        this.setMovable(false);
        this.setResizable(false);
        this.align(Align.center);
        this.defaults().pad(10);

        map.setSize(map.getWidth() * scale, map.getHeight() * scale);
        playerImage.setSize(playerImage.getWidth() * 3.8f, playerImage.getHeight() * 3.8f);

        mapGroup = new Group();
        mapGroup.setSize(map.getWidth(), map.getHeight());
        mapGroup.addActor(map);
        mapGroup.addActor(playerImage);

        updatePlayerPosition();
        this.add(mapGroup).size(map.getWidth(), map.getHeight());
    }

    // TODO:  playerssss??
    public void updatePlayerPosition() {
        Coordinate cor = App.getGame().getCurrentPlayer().getCoordinate();
        float playerPixelX = cor.getY() * 16;
        float playerPixelY = (map.getHeight() / scale) - (cor.getX() + 1) * 16;
        float playerX = playerPixelX * scale;
        float playerY = playerPixelY * scale;
        playerImage.setPosition(playerX, playerY);
    }
}


