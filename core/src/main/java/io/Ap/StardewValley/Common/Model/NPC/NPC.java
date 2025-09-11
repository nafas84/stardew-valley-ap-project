package io.Ap.StardewValley.Common.Model.NPC;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Game;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Item.Item;
import io.Ap.StardewValley.Common.Model.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC {
    private NPCType type;
    private String name;
    private String job;
    private Coordinate coordinate;
    private ArrayList<Item> favorites;
    private ArrayList<Item> receivedItems;
    private ArrayList<Quest> quests;
    private HashMap<Player, Integer> friendXp;
    private HashMap<Player, Boolean> talkedToday;

    public NPC(NPCType type, Coordinate coordinate) {
        this.type = type;
        this.name = type.getName();
        this.job = type.getJob();
        this.coordinate = coordinate;
        this.favorites = type.getFavorites();
        this.receivedItems = new ArrayList<>();
        this.quests = type.getQuests();
        this.friendXp = new HashMap<>();
        this.talkedToday = new HashMap<>();
        for (Player player : App.getGame().getPlayers()) {
            this.friendXp.put(player, 0);
            this.talkedToday.put(player, false);
        }
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void addReceivedItems(Item item) {
        this.receivedItems.add(item);
    }

    public void setFriendXp(Player player, int xp) {
        this.friendXp.put(player, xp);
    }

    public void setTalkedToday(Player player, boolean talkedToday) {
        this.talkedToday.put(player, talkedToday);
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public ArrayList<Item> getReceivedItems() {
        return receivedItems;
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public int getFriendXp(Player player) {
        int mn = friendXp.get(player);
        mn = Math.min(mn, 799);
        friendXp.put(player, mn);
        return friendXp.get(player);
    }

    public int getLevel (Player player) {
        int mn = friendXp.get(player);
        mn = Math.min(mn, 799);
        friendXp.put(player, mn);
        return mn / 200;
    }

    public boolean getTalkedToday(Player player) {
        return talkedToday.get(player);
    }

    public void addXp (int xp, Player player) {
        int number = friendXp.get(player);
        number += xp;
        number = Math.min(number, 799);
        number = Math.max(number, 0);
        friendXp.put(player, number);
    }

    public String getContext(Game game){
        StringBuilder builder = new StringBuilder();
        builder.append("your name is ").append(name).append("\n");
        builder.append("job is ").append(job).append("\n");
        builder.append("day is ").append(game.getCurrentTime().getDay()).append("\n");
        builder.append("season is ").append(game.getCurrentTime().getSeason()).append("\n");
        builder.append("time is ").append(game.getCurrentTime().getHour()).append("\n");

        return builder.toString();
    }
}
