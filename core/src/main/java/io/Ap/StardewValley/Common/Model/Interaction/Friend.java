package io.Ap.StardewValley.Common.Model.Interaction;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Player.Player;

import java.util.ArrayList;

public class Friend {
    private int friendId;
    private int xp;
    private int level;
    private ArrayList<Gift> sentGifts;
    private ArrayList<Trade> trades;
    private boolean talkedToday;
    private boolean giftedToday;
    private boolean huggedToday;

    public Friend() {
    }//needed for json

    public Friend(int friendId) {
        this.friendId = friendId;
        this.xp = 0;
        this.level = 0;
        this.sentGifts = new ArrayList<>();
        this.trades = new ArrayList<>();
        this.talkedToday = false;
        this.giftedToday = false;
        this.huggedToday = false;
    }

    public void setTalkedToday(boolean talkedToday) {
        this.talkedToday = talkedToday;
    }

    public void setGiftedToday(boolean giftedToday) {
        this.giftedToday = giftedToday;
    }

    public void setHuggedToday(boolean huggedToday) {
        this.huggedToday = huggedToday;
    }

    public void nextLevel() {
        if ((this.level + 1) * 100 > this.xp)
            return;
        this.level++;
        this.xp = 0;
    }

    public void updateLevel() {
        if (this.level < 2)
            nextLevel();
    }

    public void setLevel(int level) {
        this.level = level;
        this.xp = 0;
    }

    public void addXP(int xp) {
        this.xp += xp;
        if (this.xp < 0) {
            this.level--;
            this.xp += ((this.level + 1) * 100);
            this.level = Math.max(0, this.level);
        }
        else {
            this.xp = Math.min(this.xp, (this.level + 1) * 100);
        }
    }

    public int getXP() {
        return this.xp;
    }

    public int getLevel() {
        return this.level;
    }

    public int getFriendId() {
        return this.friendId;
    }

    public int getXp() {
        return this.xp;
    }

    public boolean isTalkedToday() {
        return talkedToday;
    }

    public boolean isGiftedToday() {
        return giftedToday;
    }

    public boolean isHuggedToday() {
        return huggedToday;
    }

    public ArrayList<Gift> getSentGifts() {
        return this.sentGifts;
    }

    public ArrayList<Trade> getTrades() {
        return this.trades;
    }

    public String getFriendName() {
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == this.friendId)
                return player.getUsername();
        }
        return null;
    }

    public void addGift(Gift gift) {
        this.sentGifts.add(gift);
    }

    public void addTrade(Trade trade) {
        this.trades.add(trade);
    }

    public void rateGift (int rate) {
        this.addXP(rate * 30 - 75);
    }
}
