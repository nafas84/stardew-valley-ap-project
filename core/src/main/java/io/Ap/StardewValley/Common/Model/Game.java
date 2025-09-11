package io.Ap.StardewValley.Common.Model;

import io.Ap.StardewValley.Client.Controller.SirkBozorg.NightController;
import io.Ap.StardewValley.Common.Model.Interaction.Friend;
import io.Ap.StardewValley.Common.Model.Interaction.Talk;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Map.GameMap;
import io.Ap.StardewValley.Common.Model.Map.Tile;
import io.Ap.StardewValley.Common.Model.NPC.NPC;
import io.Ap.StardewValley.Common.Model.NPC.NPCType;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Shop.Shop;
import io.Ap.StardewValley.Common.Model.Shop.ShopType;
import io.Ap.StardewValley.Common.Model.Time.Weather;
import io.Ap.StardewValley.Common.Model.Time.DateAndTime;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final ArrayList<Player> players;
    private Player mainPlayer;
    private Player currentPlayer;

    private GameMap map;
    //private Region currentRegion = null;


    private DateAndTime currentTime = new DateAndTime (9, 1, Weather.Sunny);
    private Weather tomorrowWeather;


    private ArrayList<Shop> shops = new ArrayList<>();

    private ArrayList<NPC> npcs = new ArrayList<>();

    private ArrayList<Talk> talks = new ArrayList<>();

    private int tradeAmount;

    public Game(ArrayList<Player> players, Player currentPlayer, Player mainPlayer) {
        this.players = players;
        this.mainPlayer = mainPlayer;
        this.currentPlayer = currentPlayer;

        this.tomorrowWeather = Weather.Sunny;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public void setTomorrowWeather(Weather tomorrowWeather) {
        this.tomorrowWeather = tomorrowWeather;
    }

    public void setTradeAmount(int tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Weather getTomorrowWeather() {
        return tomorrowWeather;
    }

    public DateAndTime getCurrentTime() {
        return this.currentTime;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameMap getMap() {
        return map;
    }

    public Tile getTile(Coordinate coordinate) {
        Tile[][] tiles = this.getMap().getFullMap();
        try {
            return tiles[coordinate.getX()][coordinate.getY()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Shop getShop(ShopType type) {
        for (Shop s : shops) {
            if (s.getType() == type) {
                return s;
            }
        }
        return shops.get(0);
    }

    public ArrayList<Shop> getShops () {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = new ArrayList<>(shops);
    }

    public void setNPCs() {
        for (NPCType type : NPCType.values()) {
            int x;
            int y;
            NPC npc;
            while (true) {
                // Phase 1:
//                x = NightController.rand.nextInt(30, 60);
//                y = NightController.rand.nextInt(40, 80);
                x = NightController.rand.nextInt(65, 175);
                y = NightController.rand.nextInt(80, 210);
                Coordinate c = new Coordinate(x, y);
                if (App.getGame().getTile(c).isWalkable()) {
                    npc = new NPC(type, c);
                    App.getGame().getTile(c).setNpc(npc);
                    break;
                }
            }
            npcs.add(npc);
        }
    }

    public void setFriends() {
        for (Player player : this.players) {
            for (Player player2 : this.players) {
                if (player.getId() == player2.getId())
                    continue;
                player.getFriends().add(new Friend(player2.getId()));
            }
        }
    }

    public void addTalk(Talk talk) {
        this.talks.add(talk);
    }

    public ArrayList<Talk> getTalks() {
        return this.talks;
    }

    public Player getPlayerByID(int id) {
        for (Player player : this.players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    public int getTradeAmount() {
        return tradeAmount;
    }

    public ArrayList<NPC> getNPCs() {
        return npcs;
    }

    // TODO: CONSTANTS:
    public int getPlayerSpeed() {
        return 2;
    }

    public float getPlayerScale() {
        return 1f;
    }
}
