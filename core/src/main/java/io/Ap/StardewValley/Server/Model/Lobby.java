package io.Ap.StardewValley.Server.Model;

import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Game;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {
    private User host;
    private ArrayList<User> users = new ArrayList<>();
    private Player hostPlayer = null;
    private ArrayList<Player> players = new ArrayList<>();
    private String name;
    private int ID;
    private boolean isVisible;
    private boolean isPrivate;
    private String password;
    private long time;
    private int[] farmSelections = new int[4];


    public Lobby () {} //needed for jason

    public Lobby(User host, String name, int ID, boolean isVisible, boolean isPrivate, String password) {
        this.host = host;
        this.name = name;
        this.ID = ID;
        this.isVisible = isVisible;
        this.isPrivate = isPrivate;
        this.password = password;
        this.time = System.currentTimeMillis();
        users.add(host);

    }

    public User getHost() {
        return host;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTime() {
        return time;
    }

    public Player getHostPlayer() {
        return hostPlayer;
    }

    public void setHostPlayer(Player hostPlayer) {
        this.hostPlayer = hostPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int[] getFarmSelections() {
        return farmSelections;
    }

    public void setFarmSelections(int index, int selection) {
        System.out.println("bbbb");
        farmSelections[index] = selection;
    }

    public boolean isExpired() {
        return (users.size() == 1) && ((System.currentTimeMillis() - time) > 5 * 60 * 1000);
    }

    public void setHost(User host) {
        this.host = host;
    }


    public LobbyData getLobbyData () {

        System.out.println("tkh1");
        HashMap<String, Object> hostInfo = new HashMap<>();
        System.out.println("tkh2");
        hostInfo.put("hairColor", hostPlayer.getHairColor());
        System.out.println("tkhh1");
        hostInfo.put("pantColor", hostPlayer.getPantColor());
        System.out.println("tkhh1");
        hostInfo.put("pantIndex", "" + hostPlayer.getPantIndex());
        System.out.println("tkhh1");
        hostInfo.put("shirtIndex", "" + hostPlayer.getShirtIndex());
        System.out.println("tkhh1");
        hostInfo.put("hairIndex", "" + hostPlayer.getHairIndex());
        System.out.println("tkhh1");
        hostInfo.put("id", "" + hostPlayer.getId());
        System.out.println("tkhh1");
        hostInfo.put("farm", "1");
        System.out.println("tkh3");

        ArrayList<HashMap<String, Object>> playerInfo = new ArrayList<>();
        System.out.println("tkh4");
        for (int i = 1; i < players.size(); i++) {
            System.out.println("tkh5");
            HashMap<String, Object> info = new HashMap<>();
            System.out.println("tkh6");
            info.put("hairColor", players.get(i).getHairColor());
            info.put("pantColor", players.get(i).getPantColor());
            info.put("pantIndex", "" + players.get(i).getPantIndex());
            info.put("shirtIndex", "" + players.get(i).getShirtIndex());
            info.put("hairIndex", "" + players.get(i).getHairIndex());
            info.put("id", "" + players.get(i).getId());
            info.put("farm", "" + (i + 1));
            System.out.println("tkh7");
            playerInfo.add(info);
            System.out.println("tkh8");
        }
        System.out.println("tkh9");
        return new LobbyData(players.size(), hostInfo, playerInfo, farmSelections);
    }
}
