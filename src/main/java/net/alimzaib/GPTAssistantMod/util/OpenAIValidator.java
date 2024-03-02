package net.alimzaib.GPTAssistantMod.util;

import net.alimzaib.GPTAssistantMod.GPTAssistantMod;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
public class OpenAIValidator {
    public static CompletableFuture<Boolean> validateApiKey(String apiKey) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                  {
                    "model": "gpt-3.5-turbo",
                    "messages": [{"role": "system", "content": "This is a test."}]
                  }
                  """))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() == 200) {
                        return true;
                    } else {
                        // Log the response body for debugging purposes
                        GPTAssistantMod.LOGGER.error("API Key validation failed with status code {}: {}", response.statusCode(), response.body());
                        return false;
                    }
                })
                .exceptionally(e -> {
                    GPTAssistantMod.LOGGER.error("API Key validation failed with exception: ", e);
                    return false;
                });
    }
}