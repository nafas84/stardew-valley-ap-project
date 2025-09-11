package io.Ap.StardewValley.Client.Controller.NetworkControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.Ap.StardewValley.Client.App.ClientApp;
import io.Ap.StardewValley.Client.Controller.GameMenuController;
import io.Ap.StardewValley.Client.Screen.GameScreen;
import io.Ap.StardewValley.Client.Screen.MenuScreen.OnlineUsersScreen;
import io.Ap.StardewValley.Common.JSONUtils;
import io.Ap.StardewValley.Common.Message;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Game;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Player.Skill;
import io.Ap.StardewValley.Server.Model.LobbyData;
import io.Ap.StardewValley.StardewValley;


import java.util.ArrayList;
import java.util.HashMap;

public class UpdateController {
    public static Label onlineUsersLabel = new Label("Online Users:", StardewValley.getSkin());

    public static void startGame(Message message) {
        try {
            Player currentPlayer = null;
            Player mainPlayer;
            ArrayList<Player> players = new ArrayList<>();
            String jsonLobby = message.getFromBody("startGame");
            System.out.println("JSON Received: " + jsonLobby);

            LobbyData lobbyData = JSONUtils.lobbyDataFromJson(message.getFromBody("startGame"));

            if (lobbyData == null) return;
            for (int i = 0; i < lobbyData.getNumberOfPlayers(); i++) {
                GameMenuController.farmSelections[i] = lobbyData.getFarmSelections()[i];
            }

            mainPlayer = getPlayer(lobbyData.getHostInfo());
            players.add(mainPlayer);

            if (mainPlayer.getId() == App.getCurrentUser().getId()) {
                currentPlayer = mainPlayer;
            } else {
                for (HashMap<String, Object> info : lobbyData.getPlayerInfo()) {
                    if ((getPlayer(info).getId()) == App.getCurrentUser().getId()) {
                        currentPlayer = getPlayer(info);
                    }
//                    players.add(getPlayer(info));
                }
            }
            for (HashMap<String, Object> info : lobbyData.getPlayerInfo()) {
                if (mainPlayer.getId() != getPlayer(info).getId()) {
                    players.add(getPlayer(info));
                }
            }

            if (currentPlayer == null) return;

            App.setGame(new Game(players, currentPlayer, mainPlayer));
            System.arraycopy(lobbyData.getFarmSelections(), 0, GameMenuController.farmSelections, 0, 4);
            GameMenuController.loadNewGame();

            Gdx.app.postRunnable(() -> {
                StardewValley.getGame().setScreen(new GameScreen(GameMenuController.farmSelections));
            });

        } catch (Exception e) {
        }
    }

    public static Player getPlayer(HashMap<String, Object> info) {
        String hairColor = (String) info.get("hairColor");
        String pantColor = (String) info.get("pantColor");
        int pantIndex = Integer.parseInt((String) info.get("pantIndex"));
        int shirtIndex = Integer.parseInt((String) info.get("shirtIndex"));
        int hairIndex = Integer.parseInt((String) info.get("hairIndex"));
        int id = Integer.parseInt((String) info.get("id"));
        int farm = Integer.parseInt((String) info.get("farm"));

        return new Player(hairColor, pantColor, pantIndex, shirtIndex, hairIndex, id, farm);
    }

//    public static void updateOnlineUsers(Message message) {
//        String onlineUsersList = message.getFromBody("onlineUsers");
//        onlineUsersLabel.setText(onlineUsersList);
//    }

    public static void updatePlayer() {
        HashMap<String, Object> body = new HashMap<>();
        body.put("updatePlayer", "hmmm");
        body.put("x", "" + App.getGame().getCurrentPlayer().getCoordinate().getX());
        body.put("y", "" + App.getGame().getCurrentPlayer().getCoordinate().getY());
        body.put("id", "" + App.getCurrentUser().getId());
        body.put("energy", "" + App.getGame().getCurrentPlayer().getEnergy());
        body.put("count", "" + App.getGame().getCurrentPlayer().getCount());
        body.put("farming", "" + App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Farming));
        body.put("foraging", "" + App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Foraging));
        body.put("fishing", "" + App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Fishing));
        body.put("mining", "" + App.getGame().getCurrentPlayer().getAbilityLevel(Skill.Mining));

        ClientApp.getServerConnectionThread().sendMessage(new Message(body, Message.Type.update));
    }

}
