package io.Ap.StardewValley.Server.App;

import io.Ap.StardewValley.Common.Message;
import io.Ap.StardewValley.Server.Controller.ServerLobbyController;

import java.util.HashMap;

public class ServerConnectionController {
    public static Message handleCommand(Message message) {
        String controllerName;

        if ((controllerName = message.getFromBody("controller")) == null) {
            return null;
        } else {
            System.out.println("controller is hereee!");
            if (controllerName.equalsIgnoreCase("LobbyController")) {
                return ServerLobbyController.handleCommand(message);
            }
        }
        return null; //TODO
    }

    public static void handleUpdate (Message command) {
        synchronized (ServerApp.getUpdateBody()) {
            HashMap<String, Object> news = command.getBody();
            for (String key : news.keySet())
                ServerApp.getUpdateBody().put(key, news.get(key));
        }
    }
}
