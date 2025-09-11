package io.Ap.StardewValley.Common.Model.Player;

import io.Ap.StardewValley.Common.Model.Item.Item;

public class GiftItem implements Item {
    private final GiftType type;

    public GiftItem(GiftType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getPrice() {
        return type.getSellPrice();
    }

    public GiftType getType() {
        return type;
    }
}
