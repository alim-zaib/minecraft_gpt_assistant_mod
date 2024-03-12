package net.alimzaib.GPTAssistantMod.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.alimzaib.GPTAssistantMod.util.OpenAIUtil;
import net.alimzaib.GPTAssistantMod.util.ScreenshotTaker;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.alimzaib.GPTAssistantMod.GPTAssistantMod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class AskVisionCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("askvision")
                .then(Commands.argument("query", StringArgumentType.greedyString())
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            String query = StringArgumentType.getString(context, "query");

                            CompletableFuture.supplyAsync(() -> {
                                try {
                                    Path screenshotPath = ScreenshotTaker.takeAndSaveScreenshot();
                                    byte[] screenshotData = Files.readAllBytes(screenshotPath);
                                    String modifiedQuery = "In the context of Minecraft version 1.20.1, " + query;
                                    GPTAssistantMod.LOGGER.info("Sending image and query to OpenAI: " + modifiedQuery);

                                    return OpenAIUtil.askWithImage(modifiedQuery, screenshotData); // Assume this method returns a Future<String>
                                } catch (IOException e) {
                                    GPTAssistantMod.LOGGER.error("Failed to process the screenshot", e);
                                    return null;
                                } catch (Exception e) {
                                    GPTAssistantMod.LOGGER.error("An unexpected error occurred", e);
                                    return null;
                                }
                            }).thenAcceptAsync(response -> {
                                if (response != null && !response.isEmpty()) {
                                    GPTAssistantMod.LOGGER.info("Received response from OpenAI: " + response);
                                    JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                                    String textResponse = jsonResponse.get("choices").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsJsonObject().get("content").getAsString();

                                    source.getServer().execute(() -> {
                                        context.getSource().sendSuccess(() -> Component.literal(textResponse), false);

                                    });
                                } else {
                                    source.getServer().execute(() -> {
                                        source.sendFailure(Component.literal("Failed to get a valid response from OpenAI."));
                                    });
                                }
                            });

                            return 1; // Immediately return from the command execution
                        }))
        );
    }
}
