package io.Ap.StardewValley.Client.Screen.MapScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class SeasonTextureManager {
    private static final Map<String, Texture> sharedTextures = new HashMap<>();

    public static Texture getSeasonTexture(String originalPath, String season) {
        String fileName = new FileHandle(originalPath).name();
        String newFileName = fileName.replaceFirst("^(spring|summer|fall|winter)_", season + "_");
        String newPath = originalPath.replace(fileName, newFileName);

        if (sharedTextures.containsKey(newPath)) {
            return sharedTextures.get(newPath);
        }

        Texture t = new Texture(Gdx.files.internal(newPath));
        sharedTextures.put(newPath, t);
        return t;
    }

    public static void disposeAll() {
        for (Texture t : sharedTextures.values()) {
            t.dispose();
        }
        sharedTextures.clear();
    }
}
