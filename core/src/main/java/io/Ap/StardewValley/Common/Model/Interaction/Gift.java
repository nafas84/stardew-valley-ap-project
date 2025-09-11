package io.Ap.StardewValley.Common.Model.Interaction;

import io.Ap.StardewValley.Common.Model.Item.Item;

import java.util.ArrayList;

public class Gift {
    private ArrayList<Item> gift;
    private String sender;
    private int count;
    private int rate;
    private int giftID;
    private int isAccepted;

    public Gift() {
    }//needed for json

    public Gift (ArrayList<Item> gift, String sender, int count, int giftID) {
        this.gift = gift;
        this.count = count;
        this.giftID = giftID;
        this.rate = -1;
        this.isAccepted = -1;
    }

    public ArrayList<Item> getGift() {
        return this.gift;
    }

    public int getCount() {
        return this.count;
    }

    public int getRate() {
        return this.rate;
    }

    public int getGiftID() {
        return this.giftID;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public String getSender() {
        return sender;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void addGift (Item item) {
        this.gift.add(item);
    }
}
