package io.Ap.StardewValley.Server.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyData {
    private int numberOfPlayers;
    private HashMap<String, Object> hostInfo;
    private ArrayList<HashMap<String, Object>> playerInfo;

    public int[] getFarmSelections() {
        return farmSelections;
    }

    public void setFarmSelections(int[] farmSelections) {
        this.farmSelections = farmSelections;
    }

    private int[] farmSelections = new int[4];

    public LobbyData(int numberOfPlayers, HashMap<String, Object> hostInfo, ArrayList<HashMap<String, Object>> playerInfo, int[] farmSelections) {
        this.numberOfPlayers = numberOfPlayers;
        this.hostInfo = hostInfo;
        this.playerInfo = playerInfo;
        this.farmSelections = farmSelections;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public HashMap<String, Object> getHostInfo() {
        return hostInfo;
    }

    public void setHostInfo(HashMap<String, Object> hostInfo) {
        this.hostInfo = hostInfo;
    }

    public ArrayList<HashMap<String, Object>> getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(ArrayList<HashMap<String, Object>> playerInfo) {
        this.playerInfo = playerInfo;
    }
}
