package io.Ap.StardewValley.Common;


import io.Ap.StardewValley.Common.Model.Result;

import java.util.HashMap;

public class Message {
    private Type type;
    private HashMap<String, Object> body;

    /*
     * Empty constructor needed for JSON Serialization/Deserialization
     */

    public Message() {}

    public Message(HashMap<String, Object> body, Type type) {
        this.body = body;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public <T> T getFromBody(String fieldName) {
        return (T) body.get(fieldName);
    }

    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName)));
    }

    public Result getResult () {
        boolean success = body.get("success").equals(true);
        String message = (String) body.get("message");
        return new Result(success, message);
    }

    public HashMap<String, Object> getBody() {
        return body;
    }

    public enum Type {
        command,
        response,
        update
    }
}
