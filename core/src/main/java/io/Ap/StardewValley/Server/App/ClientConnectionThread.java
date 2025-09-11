package io.Ap.StardewValley.Server.App;

import io.Ap.StardewValley.Common.ConnectionThread;
import io.Ap.StardewValley.Common.Message;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionThread extends ConnectionThread {

    public ClientConnectionThread(Socket socket) throws IOException {
        super(socket);
    }
    @Override
    protected boolean handleMessage(Message message) {

        if (message.getType() == Message.Type.command) {
            sendMessage(ServerConnectionController.handleCommand(message));
            return true;
        }
        if (message.getType() == Message.Type.update) {
            //TODO
            ServerConnectionController.handleUpdate(message);
//            return true;
        }
        return false;
    }

    @Override
    public void run() {
        ServerApp.addClientConnection(this);
        super.run();
        ServerApp.removeClientConnection(this);
    }
}
