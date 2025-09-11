package io.Ap.StardewValley.Common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.Ap.StardewValley.Server.Model.Lobby;
import io.Ap.StardewValley.Server.Model.LobbyData;

public class JSONUtils {
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static final Gson gson;

    static {
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public synchronized static String toJson(Message message) {
        return gson.toJson(message);
    }

    public synchronized static Message fromJson(String json) {
        return gson.fromJson(json, Message.class);
    }

    public synchronized static String toJson(LobbyData lobbyData) {
        return gson.toJson(lobbyData);
    }

    public synchronized static LobbyData lobbyDataFromJson(String json) {
        return gson.fromJson(json, LobbyData.class);
    }
}