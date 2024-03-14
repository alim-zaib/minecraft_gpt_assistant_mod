package net.alimzaib.GPTAssistantMod.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.alimzaib.GPTAssistantMod.GPTAssistantMod;
import net.alimzaib.GPTAssistantMod.util.OpenAIUtil;

import java.util.concurrent.CompletableFuture;

public class GeneralQueryCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("askgpt")
                .then(Commands.argument("query", StringArgumentType.greedyString())
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            String query = StringArgumentType.getString(context, "query");
                            String prompt = "Question: " + query + " in Minecraft version 1.20.1?";

                            GPTAssistantMod.LOGGER.info("Sending query to GPT: " + prompt);

                            // Perform the query asynchronously to avoid blocking the main thread
                            CompletableFuture.supplyAsync(() -> {
                                try {
                                    return OpenAIUtil.askGPT(prompt);
                                } catch (Exception e) {
                                    GPTAssistantMod.LOGGER.error("Exception while querying GPT", e);
                                    return "{\"error\":\"Failed to get response from GPT\"}";
                                }
                            }).thenAcceptAsync(response -> {
                                // Check if the response is an error message before proceeding
                                if (response != null && !response.contains("\"error\":")) {
                                    try {
                                        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                                        String assistantResponse = jsonResponse.getAsJsonArray("choices")
                                                .get(0).getAsJsonObject()
                                                .get("message").getAsJsonObject()
                                                .get("content").getAsString();

                                        // Schedule the successful response to be sent back to the player on the main thread
                                        context.getSource().sendSuccess(() -> Component.literal(assistantResponse), false);
                                    } catch (Exception e) {
                                        source.getServer().execute(() -> source.sendFailure(Component.literal("Failed to process the response.")));
                                        GPTAssistantMod.LOGGER.error("Error processing the GPT response", e);
                                    }
                                } else {
                                    source.getServer().execute(() -> source.sendFailure(Component.literal("GPT response error.")));
                                }
                            });


                            return 1;
                        }))
        );
    }
}
