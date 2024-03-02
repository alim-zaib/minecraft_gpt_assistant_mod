package net.alimzaib.GPTAssistantMod.config;

import java.util.prefs.Preferences;

public class ModConfig {
    private static final String API_KEY_PREF = "APIKey";
    private static final Preferences prefs = Preferences.userNodeForPackage(ModConfig.class);

    // Declaring apiKey as a static variable in the class
    private static String apiKey = "";

    public static String getApiKey() {
        // Here you can add the logic to load the API key if it's not already loaded
        if (apiKey.isEmpty()) {
            loadApiKey();
        }
        return apiKey;
    }

    public static void setApiKey(String newApiKey) {
        apiKey = newApiKey;
        // Save the API key using Preferences API
        prefs.put(API_KEY_PREF, apiKey);
    }

    public static String loadApiKey() {
        // Load and return the API key using Preferences API
        return prefs.get(API_KEY_PREF, ""); // Default to empty string if not found
    }

}
