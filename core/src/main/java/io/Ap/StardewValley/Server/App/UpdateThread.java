package io.Ap.StardewValley.Server.App;


import io.Ap.StardewValley.Common.Message;

import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateThread extends Thread {
    private AtomicBoolean end;

    public UpdateThread() {
        this.end = new AtomicBoolean(false);
    }

    private void sendUpdateToAll() {
        for(ClientConnectionThread connection : ServerApp.getConnections()) {
            if (true) {
                connection.sendMessage(new Message(ServerApp.getUpdateBody(), Message.Type.update));
                System.out.println(ServerApp.getUpdateBody());
            }

        }
    }

    @Override
    public void run() {
        int minute = 0;
        while(!end.get()) {
            if(minute == 1 * 60) {
                ServerApp.getUpdateBody().put("advanceTime", 1);
                minute = 0;
            }

            if(!ServerApp.getUpdateBody().isEmpty()) {
                sendUpdateToAll();
                ServerApp.getUpdateBody().clear();
            }
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                System.out.println("error");
                throw new RuntimeException(e);
            }
            minute++;
        }
    }

    public void end() {
        end.set(true);
    }
}
