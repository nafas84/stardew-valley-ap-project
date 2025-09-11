package io.Ap.StardewValley.Server.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerApp {
    public static final int TIMEOUT_MILLIS = 10000;
    private static final ArrayList<ClientConnectionThread> connections = new ArrayList<>();
    private static boolean exitFlag = false;
    private static ListenerThread listenerThread;

    //TODO update stuff
    private static HashMap<String, Object> updateBody = new HashMap<>();
    private static UpdateThread updateThread;

//    private static ArrayList<String> onlineUsernames = new ArrayList<>();


    public static boolean isEnded() {
        return exitFlag;
    }

    public static void setListenerThread(ListenerThread listenerThread) {
        ServerApp.listenerThread = listenerThread;
    }

    public static List<ClientConnectionThread> getConnections() {
        return connections;
    }

    public static void startListening() {
        if (listenerThread != null && !listenerThread.isAlive()) {
            listenerThread.start();
        } else {
            throw new IllegalStateException("Listener thread is already running or not set.");
        }
    }

    public static void endAll() {
        exitFlag = true;
        for (ClientConnectionThread connection : connections)
            connection.end();
        connections.clear();
        listenerThread.end();
    }

    public static void removeClientConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread != null) {
            connections.remove(clientConnectionThread);

//            onlineUsernames.remove(clientConnectionThread);
            clientConnectionThread.end();
//            updateBody.put("onlineUsers", getOnlineUsersString());
        }
    }

    public static void addClientConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread != null && !connections.contains(clientConnectionThread)) {
            connections.add(clientConnectionThread);

//            HashMap<String, Object> body = new HashMap<>();
//            body.put("controller", "LobbyController");
//            body.put("request", "getUsername");
//            Message message = clientConnectionThread.sendAndWaitForResponse(new Message(body, Message.Type.command), TIMEOUT_MILLIS);
//            String username = message.getResult().message();
//            onlineUsernames.put(clientConnectionThread, username);
//
//            updateBody.put("onlineUsers", getOnlineUsersString());
        }
    }

    public static HashMap<String, Object> getUpdateBody() {
        return updateBody;
    }

    public static UpdateThread getUpdateThread() {
        return updateThread;
    }

    public static void setUpdateThread(UpdateThread updateThread) {
        ServerApp.updateThread = updateThread;
    }

    public static void startUpdateThread() {
        if(updateThread != null && !updateThread.isAlive()) {
            updateThread.start();
        } else {
            throw new IllegalStateException("Update thread is already running or not set.");
        }
    }

    public static void endGame() {
        updateThread.end();
    }

//    private static String getOnlineUsersString() {
//        String result = "Online Users:\n\n";
//        for (String u : onlineUsernames.values()) {
//            result = result + u +"\n";
//        }
//        return result;
//    }
}