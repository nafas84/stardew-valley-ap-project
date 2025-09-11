package io.Ap.StardewValley.Common.Model.Interaction;

import io.Ap.StardewValley.Common.Model.Item.Item;

public class Trade {
    private String type;
    private int senderID;
    private Item item;
    private int amount;
    private int price;
    private Item targetItem;
    private int targetItemAmount;
    private int id;
    private int isAccepted;

    public Trade() {
    }//needed for json

    public Trade(String type, int senderID, Item item, int amount, int price, Item targetItem, int targetItemAmount, int id) {
        this.type = type;
        this.senderID = senderID;
        this.item = item;
        this.amount = amount;
        this.price = price;
        this.targetItem = targetItem;
        this.targetItemAmount = targetItemAmount;
        this.id = id;
        this.isAccepted = -1;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public Item getTargetItem() {
        return targetItem;
    }

    public int getTargetItemAmount() {
        return targetItemAmount;
    }

    public int getId() {
        return id;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getIsAccepted() {
        return isAccepted;
    }
}
