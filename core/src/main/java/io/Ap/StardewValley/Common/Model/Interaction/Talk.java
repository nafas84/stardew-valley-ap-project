package io.Ap.StardewValley.Common.Model.Interaction;

public class Talk {
    private int senderId;
    private int receiverId;
    private String message;

    public Talk() {
    } //needed for json

    public Talk(int senderId, int receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public int getSenderId() {
        return this.senderId;
    }

    public int getReceiverId() {
        return this.receiverId;
    }

    public String getMessage() {
        return this.message;
    }
}
