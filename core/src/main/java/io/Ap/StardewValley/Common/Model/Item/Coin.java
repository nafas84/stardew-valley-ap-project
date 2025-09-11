package io.Ap.StardewValley.Common.Model.Item;

public class Coin implements Item {
    @Override
    public String getName() {
        return "Coin";
    }

    @Override
    public int getPrice() {
        return 1;
    }

}
