package io.Ap.StardewValley.Client.App;

import io.Ap.StardewValley.Common.ConnectionThread;
import io.Ap.StardewValley.Common.Message;

import java.io.IOException;
import java.net.Socket;

public class ServerConnectionThread extends ConnectionThread {

    public ServerConnectionThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected boolean handleMessage(Message message) {
        if (message.getType() == Message.Type.command) {
            sendMessage(ClientConnectionController.handleCommand(message));
            return true;
        }
        if (message.getType() == Message.Type.update) {
            ClientConnectionController.handleUpdate(message);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        super.run();
    }
}
