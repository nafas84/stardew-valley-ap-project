package io.Ap.StardewValley.Server.Controller;

import io.Ap.StardewValley.Common.JSONUtils;
import io.Ap.StardewValley.Common.Message;
import io.Ap.StardewValley.Common.Model.App;
import io.Ap.StardewValley.Common.Model.Player.Player;
import io.Ap.StardewValley.Common.Model.Result;
import io.Ap.StardewValley.Common.Model.User;
import io.Ap.StardewValley.Server.App.ServerApp;
import io.Ap.StardewValley.Server.App.UpdateThread;
import io.Ap.StardewValley.Server.Model.Lobby;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ServerLobbyController {

    public static Message handleCommand (Message command) {
        String request;
        Result result = null;

        if ((request = command.getFromBody("request")) == null) {
            return null;
        }
        else {
            if (request.equalsIgnoreCase("getLobbyList")) {
                result = getLobbyList();
            } else if (request.equalsIgnoreCase("makeLobby")) {
                result = makeLobby(command);
            } else if (request.equalsIgnoreCase("joinLobby")) {
                result = joinLobby(command);
            } else if (request.equalsIgnoreCase("setHostPlayer")) {
                result = setHostPlayer(command);
            } else if (request.equalsIgnoreCase("addPlayer")) {
                result = addPlayer(command);
            } else if (request.equalsIgnoreCase("getPlayersList")) {
                result = getPlayersList(command);
            } else if (request.equalsIgnoreCase("leaveLobby")) {
                result = leaveLobby(command);
            } else if (request.equalsIgnoreCase("hostLeaveLobby")) {
                result = hostLeaveLobby(command);
            } else if (request.equalsIgnoreCase("startGame")) {
                result = startGame(command);
            }
        }
        if (result == null) return null;

        HashMap<String, Object> body = new HashMap<>();
        body.put("message", result.message());
        body.put("success", result.isSuccessful());

        return new Message(body, Message.Type.response);
    }

    public static Result getLobbyList () {
        ArrayList<Lobby> expiredLobbies = new ArrayList<>();

        String result = "Lobby List: \n\n";
        int i = 0;
        for (Lobby lobby : App.getLobbies()) {
            if (lobby.isExpired()) {
                expiredLobbies.add(lobby);
                continue;
            }
            if (lobby.isVisible() && !lobby.isExpired() && (lobby.getPlayers().size() < 4) && (!lobby.getPlayers().isEmpty())){
                result = result + "+name: " + lobby.getName() + ", host: " + lobby.getHost().getNickname() +
                        ", size: " + lobby.getPlayers().size() + "\n";
                i++;
            }
        }

        for (Lobby lobby : expiredLobbies) {
            App.getLobbies().remove(lobby);
        }

        if (i == 0) {
            result = result + "there is no visible lobby :(";
        }
        return new Result(true, result);
    }

    public static Result makeLobby (Message command) {

        User host = App.getUserByUsername(command.getFromBody("host")); //TODO: system save? az nafiseh bepors
        String name = (String) command.getFromBody("name");
        String password = (String) command.getFromBody("password");
        boolean isVisible = "true".equalsIgnoreCase(command.getFromBody("isVisible"));
        boolean isPrivate = "true".equalsIgnoreCase(command.getFromBody("isPrivate"));

        for (Lobby lobby : App.getLobbies()) {
            if (lobby.getName().equals(name) && !lobby.isExpired()) {
                return new Result(false, "this lobby name already exists");
            }
        }

        Random random = new Random();
        int ID = App.getLobbies().size() * 100 + random.nextInt(10);

        Lobby lobby = new Lobby(host, name, ID, isVisible, isPrivate, password);
        App.getLobbies().add(lobby);

        return new Result(true, "lobby made successfully");
    }

    public static Result joinLobby (Message command) {
        User user = App.getUserByUsername(command.getFromBody("user"));
        String name = command.getFromBody("name");
        String password = command.getFromBody("password");

        Lobby lobby = null;

        for (Lobby l : App.getLobbies()) {
            if (l.getName().equalsIgnoreCase(name)) {
                lobby = l;
            }
        }
        if (lobby == null) {
            return new Result(false, "there's no lobby with the name");
        }
        if (!lobby.isPrivate()) {
            lobby.getUsers().add(user);
            return new Result(true, "you're gonna join the selected lobby");
        }
        if (!password.equalsIgnoreCase(lobby.getPassword())) {
            return new Result(false, "wrong password!");
        }
        lobby.getUsers().add(user);
        return new Result(true, "you're gonna join the selected lobby");
    }

    public static Result setHostPlayer (Message command) {
        String lobbyName = command.getFromBody("name");
        String hairColor = command.getFromBody("hairColor");
        String pantColor = command.getFromBody("pantColor");
        int pantIndex = Integer.parseInt(command.getFromBody("pantIndex"));
        int shirtIndex = Integer.parseInt(command.getFromBody("shirtIndex"));
        int hairIndex = Integer.parseInt(command.getFromBody("hairIndex"));
        int id = Integer.parseInt(command.getFromBody("id"));
        int farmIdSelect = Integer.parseInt(command.getFromBody("farmIdSelect"));
        Lobby lobby = findLobby(lobbyName);

        if (lobby== null) {
            return new Result(false, "invalid lobby!");
        }

        int size = lobby.getPlayers().size();
        if (size >= 3) return new Result(false, "no more capacity!");

        Player player = new Player(hairColor, pantColor, pantIndex, shirtIndex, hairIndex, id, size + 1);
        lobby.setFarmSelections(size, farmIdSelect);

        if (!lobby.getPlayers().contains(player)) {
            lobby.getPlayers().add(player);
            lobby.setHostPlayer(player);
        } else {
            return new Result(false, "you already are here!");
        }
        return new Result(true, "host player successfully added to lobby.");
    }

    public static Result addPlayer (Message command) {
        String lobbyName = command.getFromBody("name");
        String hairColor = command.getFromBody("hairColor");
        String pantColor = command.getFromBody("pantColor");
        int pantIndex = Integer.parseInt(command.getFromBody("pantIndex"));
        int shirtIndex = Integer.parseInt(command.getFromBody("shirtIndex"));
        int hairIndex = Integer.parseInt(command.getFromBody("hairIndex"));
        int id = Integer.parseInt(command.getFromBody("id"));
        int farmIdSelect = Integer.parseInt(command.getFromBody("farmIdSelect"));
        Lobby lobby = findLobby(lobbyName);

        if (lobby== null) {
            return new Result(false, "invalid lobby!");
        }

        int size = lobby.getPlayers().size();
        if (size >= 4) return new Result(false, "no more capacity!");

        Player player = new Player(hairColor, pantColor, pantIndex, shirtIndex, hairIndex, id, size);
        lobby.setFarmSelections(size, farmIdSelect);

        if (!lobby.getPlayers().contains(player)) {
            lobby.getPlayers().add(player);
        } else {
            return new Result(false, "you already are here!");
        }
        return new Result(true, "player successfully added to lobby.");
    }

    public static Result hostLeaveLobby (Message command) {
        String lobbyName = command.getFromBody("name");
        Lobby lobby = findLobby(lobbyName);
        if (lobby == null) return new Result(false, "something went wrong! invalid lobby");
        User hostUser = lobby.getHost();
        Player hostPlayer = lobby.getHostPlayer();
        lobby.getPlayers().remove(hostPlayer);
        lobby.getUsers().remove(hostUser);
        if (lobby.getUsers().isEmpty()) {
            App.getLobbies().remove(lobby);
            return new Result(true, "lobby got deleted.");
        }
        lobby.setHostPlayer(lobby.getPlayers().get(0));
        lobby.setHost(lobby.getUsers().get(0));

        return new Result(true, "done.");
    }


    public static Result leaveLobby (Message command) {
        String lobbyName = command.getFromBody("name");
        String username = command.getFromBody("username");
        Lobby lobby = findLobby(lobbyName);

        if (lobby == null) return new Result(false, "invalid lobby name!");

        User user = null;
        for (User u : lobby.getUsers()) {
            if (u.getUsername().equals(username)) {
                user = u;
            }
        }
        if (user == null) return new Result(false, "invalid username!");

        int id = user.getId();
        lobby.getUsers().remove(user);

        Player player = null;
        for (Player p : lobby.getPlayers()) {
            if (p.getId() == id) player = p;
        }
        if (player == null) return new Result(false, "invalid player!");

        lobby.getPlayers().remove(player);

        return new Result(true, "user left successfully");
    }

    public static Result getPlayersList (Message command) {
        String lobbyName = command.getFromBody("name");
        Lobby lobby = findLobby(lobbyName);
        String result = "Players List:\n\n";
        for (User user : lobby.getUsers()) {
            if (user.getId() == lobby.getHost().getId()) {
                result = result + "host: ";
            }
            result = result + user.getNickname() + "\n";
        }

        return new Result(true, result);
    }

    public static Result startGame (Message command) {
        Lobby lobby = findLobby(command.getFromBody("name"));
        if (lobby == null) return new Result(false, "invalid lobby!");
        if (lobby.getPlayers().size() < 2) return new Result(false, "wait for someone to join!");
        String lobbyJason = JSONUtils.toJson(lobby.getLobbyData());
        ServerApp.getUpdateBody().put("startGame", lobbyJason);
        ServerApp.setUpdateThread(new UpdateThread());
        ServerApp.startUpdateThread();
        return new Result(true, "game created successfully");
    }

    private static Lobby findLobby (String name) {
        for (Lobby l : App.getLobbies()) {
            if (l.getName().equalsIgnoreCase(name)) {
                return l;
            }
        }
        return null;
    }
}
