package net.alimzaib.GPTAssistantMod.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.alimzaib.GPTAssistantMod.GPTAssistantMod;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.alimzaib.GPTAssistantMod.util.OpenAIUtil;
import net.alimzaib.GPTAssistantMod.util.ScreenshotUtil;

import java.io.IOException;
import java.nio.file.*;

public class AskVisionCommand {
    private static final String SCREENSHOTS_PATH = "C:/Users/Alim/Desktop/Minecraft GPT Mod/run/screenshots/";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("askvision")
                .then(Commands.argument("query", StringArgumentType.greedyString())
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            String query = StringArgumentType.getString(context, "query");
                            try {
                                Path screenshotDir = Paths.get(SCREENSHOTS_PATH); // Adjust this path as needed
                                Path screenshotPath = ScreenshotUtil.findMostRecentScreenshot(screenshotDir);
                                byte[] screenshotData = Files.readAllBytes(screenshotPath);
                                String modifiedQuery = "In the context of Minecraft, " + query;
                                String response = OpenAIUtil.askWithImage(modifiedQuery, screenshotData);
                                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                                String content = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject().getAsJsonObject("message").get("content").getAsString();
                                source.sendSuccess(() -> Component.literal(content), false);
                            } catch (IOException e) {
                                source.sendFailure(Component.literal("Failed to process the screenshot."));
                                GPTAssistantMod.LOGGER.error("Error occurred while processing screenshot", e);
                            } catch (Exception e) {
                                source.sendFailure(Component.literal("An internal error occurred."));
                                GPTAssistantMod.LOGGER.error("An exception occurred while trying to process the AskVision command", e);
                            }
                            return 1;
                        })));
    }
}
