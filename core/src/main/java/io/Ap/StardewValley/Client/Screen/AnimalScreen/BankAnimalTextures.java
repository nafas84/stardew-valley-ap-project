package io.Ap.StardewValley.Client.Screen.AnimalScreen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.Ap.StardewValley.Common.Model.Animals.AnimalType;

import java.util.EnumMap;
import java.util.Map;

public class BankAnimalTextures {
    private final static Map<AnimalType, TextureRegion> animalAvatars = new EnumMap<>(AnimalType.class);
    private final static Map<AnimalType, Animation<TextureRegion>> walkAnimations = new EnumMap<>(AnimalType.class);

    static {

    }

    public static TextureRegion getAvatar(AnimalType type) {
        return animalAvatars.get(type);
    }
}
