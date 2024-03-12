package net.alimzaib.GPTAssistantMod.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.alimzaib.GPTAssistantMod.config.ModConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

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

    // Method to send image and prompt to OpenAI API
    public static String askWithImage(String prompt, byte[] imageData) throws Exception {
        String encodedImage = Base64.getEncoder().encodeToString(imageData);
        String apiKey = ModConfig.loadApiKey(); // Make sure this correctly fetches your API key.
        HttpClient client = HttpClient.newHttpClient();

        String imageJson = String.format("{\"type\": \"image_url\", \"image_url\": {\"url\": \"data:image/jpeg;base64,%s\"}}", encodedImage);
        String textJson = String.format("{\"type\": \"text\", \"text\": \"%s\"}", prompt.replace("\"", "\\\""));

        String payload = String.format("{ \"model\": \"gpt-4-vision-preview\", \"messages\": [{ \"role\": \"user\", \"content\": [%s, %s] }], \"max_tokens\": 300 }", textJson, imageJson);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }



}


