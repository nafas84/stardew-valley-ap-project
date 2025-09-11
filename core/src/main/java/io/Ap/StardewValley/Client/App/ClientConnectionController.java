package io.Ap.StardewValley.Client.App;

import io.Ap.StardewValley.Client.Controller.NetworkControllers.ClientLobbyController;
import io.Ap.StardewValley.Client.Controller.NetworkControllers.UpdateController;
import io.Ap.StardewValley.Common.Message;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Map.Coordinate;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Server.Controller.ServerLobbyController;


public class ClientConnectionController {
    public static Message handleCommand(Message message) {
        String controllerName;

        if ((controllerName = message.getFromBody("controller")) == null) {
            return null;
        } else {
            System.out.println("controller is hereee!");
            if (controllerName.equalsIgnoreCase("LobbyController")) {
                return ClientLobbyController.handleCommand(message);
            }
        }
        return null;
    }

    public static void handleUpdate (Message message) {

        if (message.getFromBody("startGame") != null) {
            UpdateController.startGame(message);
            return;
        }
//        if (message.getFromBody("onlineUsers") != null) {
//            UpdateController.updateOnlineUsers(message);
//        }
        if (message.getFromBody("updatePlayer") != null) {
            int x = Integer.parseInt(message.getFromBody("x"));
            int y = Integer.parseInt(message.getFromBody("y"));
            int id = Integer.parseInt(message.getFromBody("id"));
            int energy = Integer.parseInt(message.getFromBody("energy"));
            int count = Integer.parseInt(message.getFromBody("count"));
            int fishing = Integer.parseInt(message.getFromBody("fishing"));
            int farming = Integer.parseInt(message.getFromBody("farming"));
            int foraging = Integer.parseInt(message.getFromBody("foraging"));
            int mining = Integer.parseInt(message.getFromBody("mining"));

            Player player = findPlayerWithId(id);
            if (player != null) {
                player.setCoordinate(new Coordinate(x, y));
                player.setEnergy(energy);
                player.setCount(count);
                player.setFarmingLevel(farming);
                player.setFishingLevel(fishing);
                player.setForagingLevel(foraging);
                player.setMiningLevel(mining);
            }
        }

    }


    public static Player findPlayerWithId (int id) {
        for (Player player : App.getGame().getPlayers()) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

}