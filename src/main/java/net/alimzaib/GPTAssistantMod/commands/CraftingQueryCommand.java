package net.alimzaib.GPTAssistantMod.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.alimzaib.GPTAssistantMod.GPTAssistantMod;
import net.alimzaib.GPTAssistantMod.util.InventoryUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.alimzaib.GPTAssistantMod.util.OpenAIUtil;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.CompletableFuture;

public class CraftingQueryCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("askinventory")
                .then(Commands.argument("query", StringArgumentType.greedyString())
                        .executes(CraftingQueryCommand::handleCraftingQuery))
        );
    }

    private static int handleCraftingQuery(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        try {
            Player player = source.getPlayerOrException();
            String inventoryState = InventoryUtil.getInventoryStateAsText(player);
            String query = StringArgumentType.getString(context, "query");
            String prompt = "Given my inventory: \n" + inventoryState + "\n" + "Question: " + query + " in Minecraft version 1.20.1?";

            GPTAssistantMod.LOGGER.info("Sending prompt to GPT: " + prompt);

            CompletableFuture.supplyAsync(() -> {
                try {
                    return OpenAIUtil.askGPT(prompt);
                } catch (Exception e) {
                    GPTAssistantMod.LOGGER.error("Exception while querying GPT", e);
                    return "{\"error\":\"Failed to get response from GPT\"}";
                }
            }).thenAcceptAsync(response -> {
                if (response != null && !response.contains("\"error\":")) {
                    JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                    String assistantResponse = jsonResponse.getAsJsonArray("choices")
                            .get(0).getAsJsonObject()
                            .get("message").getAsJsonObject()
                            .get("content").getAsString();

                    context.getSource().sendSuccess(() -> Component.literal(assistantResponse), false);
                } else {
                    source.getServer().execute(() -> source.sendFailure(Component.literal("Failed to get a valid response from GPT.")));
                }
            });

        } catch (CommandSyntaxException e) {
            source.sendFailure(Component.literal("This command can only be run by a player."));
            GPTAssistantMod.LOGGER.error("Error executing command: ", e);
        }
        return 1;
    }
}
