package io.Ap.StardewValley.Common.Model.Player;

import io.Ap.StardewValley.Common.Model.Item.Item;

import java.util.HashMap;
import java.util.Map;

public class ShippingBin {

    public ShippingBin() {}//needed for json
    private final HashMap<Item, Integer> items = new HashMap<>();

    public void add(Item item, int count) {
        items.put(item, count);
    }

    public int getPrices() {
        int totalPrice = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item item = entry.getKey();
            int count = entry.getValue();
            totalPrice += item.getPrice() * count;
        }
        return totalPrice;
    }


    public void clear() {
        items.clear();
    }
}
