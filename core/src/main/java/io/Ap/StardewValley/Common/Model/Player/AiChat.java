package io.Ap.StardewValley.Common.Model.Player;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AiChat {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = "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";

    public static String getAIResponse(String userMessage , String context) throws Exception {
        Gson gson = new Gson();
        String req = "{\n" +
                "\"model\" : \"deepseek/deepseek-chat-v3-0324:free\",\n" +
                "\"messages\" : [\n" +
                "{\n" +
                "\"role\" : \"system\",\n" +
                "\"content\" : \" " +context + "\"\n" +
                "},\n" +
                "{\n" +
                "\"role\" : \"user\",\n" +
                "\"content\" : \"" + userMessage+"\"\n" +
                "}\n" +
                "]\n" +
                "}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(req))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), JsonObject.class)
                .getAsJsonArray("choices").get(0)
                .getAsJsonObject().getAsJsonObject("message").get("content").getAsString();

    }

    public static String getNpcDialogue(String message  , String context) {
        try {
            String response = getAIResponse(message, context);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Fuck You";
        }
    }
}

