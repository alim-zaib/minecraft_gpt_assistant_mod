package net.alimzaib.GPTAssistantMod.util;
import com.google.gson.JsonArray;
import net.alimzaib.GPTAssistantMod.config.ModConfig;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.google.gson.JsonObject;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.google.gson.Gson;

public class OpenAIUtil {
    public static String askGPT(String prompt) throws Exception {
        String apiKey = ModConfig.loadApiKey(); // Fetches the API key
        HttpClient client = HttpClient.newHttpClient();

        // Adjusted URL for chat completions
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        // Constructing the JSON payload with Gson for better accuracy
        JsonObject jsonPayload = new JsonObject();
        jsonPayload.addProperty("model", "gpt-4");
        JsonArray messages = new JsonArray();
        JsonObject messageContent = new JsonObject();
        messageContent.addProperty("role", "user");
        messageContent.addProperty("content", prompt);
        messages.add(messageContent);
        jsonPayload.add("messages", messages);

        String requestBody = new Gson().toJson(jsonPayload);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}

