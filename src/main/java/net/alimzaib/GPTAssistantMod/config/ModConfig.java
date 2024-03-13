package net.alimzaib.GPTAssistantMod.config;

import java.util.prefs.Preferences;

public class ModConfig {
    private static final String API_KEY_PREF = "APIKey";
    private static final Preferences prefs = Preferences.userNodeForPackage(ModConfig.class);

    // Declaring apiKey as a static variable in the class
    private static String apiKey = "";

    public static String getApiKey() {
        if (apiKey.isEmpty()) {
            loadApiKey();
        }
        return apiKey;
    }

    public static void setApiKey(String newApiKey) {
        apiKey = newApiKey;
        prefs.put(API_KEY_PREF, apiKey);
    }

    public static String loadApiKey() {
        return prefs.get(API_KEY_PREF, "");
    }

}
