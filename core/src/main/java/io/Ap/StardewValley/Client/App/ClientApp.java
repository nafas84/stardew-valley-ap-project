package io.Ap.StardewValley.Client.App;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientApp {
    public static final int TIMEOUT_MILLIS = 10000;

    private static String clientIp;
    private static int clientPort;
    private static int clientNumber;
    private static ServerConnectionThread serverConnectionThread;
    private static AtomicBoolean startGame = new AtomicBoolean(false);
    private static String serverIp;
    private static int serverPort;

    private static boolean exitFlag = false;

    public static boolean isEnded() {
        return exitFlag;
    }

    public static void initFromArgs(String[] args) throws Exception {
        String[] clientInfo = args[0].split(":");
        clientIp = clientInfo[0];
        clientPort = Integer.parseInt(clientInfo[1]);

        String[] serverInfo = args[1].split(":");
        serverIp = serverInfo[0];
        serverPort = Integer.parseInt(serverInfo[1]);

        serverConnectionThread = new ServerConnectionThread(new Socket(serverIp, serverPort));
    }

    public static void endAll() {
        exitFlag = true;

        if (serverConnectionThread != null) {
            serverConnectionThread.end();
        }
    }

    public static void connectServer() {
        if (serverConnectionThread != null && !serverConnectionThread.isAlive()) {
            serverConnectionThread.start();
        } else {
            throw new IllegalStateException("can't connect to the server!");
        }
    }

    public static String getClientIP() {
        return clientIp;
    }

    public static int getClientPort() {
        return clientPort;
    }

    public static ServerConnectionThread getServerConnectionThread() {
        return serverConnectionThread;
    }

    public static int getClientNumber() {
        return clientNumber;
    }

    public static AtomicBoolean getStartGame() {
        return startGame;
    }

    public static void setClientNumber(int clientNumber) {
        ClientApp.clientNumber = clientNumber;
    }

    public static void setStartGame(boolean startGame) {
        ClientApp.startGame.set(startGame);
    }
}