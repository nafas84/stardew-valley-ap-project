package io.Ap.StardewValley.Common.Model.Cooking;

import io.Ap.StardewValley.Common.Model.Player.Player;

@FunctionalInterface
public interface BuffCheck {
    void applyBuff (Player player);
}
