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

public class CraftingQueryCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("askinventory")
                .then(Commands.argument("query", StringArgumentType.greedyString())
                        .executes(CraftingQueryCommand::handleCraftingQuery))
        );
    }

    private static int handleCraftingQuery(CommandContext<CommandSourceStack> context) {
        try {
            Player player = context.getSource().getPlayerOrException();
            String inventoryState = InventoryUtil.getInventoryStateAsText(player);
            String query = StringArgumentType.getString(context, "query");
            String prompt = "Given my inventory: \n" + inventoryState + "\n" + "Question: " + query + " in Minecraft?";


            // Log the prompt for debugging
            GPTAssistantMod.LOGGER.info("Sending prompt to GPT: " + prompt);

            String response = OpenAIUtil.askGPT(prompt); // Ensure askGPT method properly extracts the response
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            String assistantResponse = jsonResponse.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();

            // Send the assistant's response back to the player
            context.getSource().sendSuccess(() -> Component.literal(assistantResponse), false);

        } catch (CommandSyntaxException e) {
            context.getSource().sendFailure(Component.literal("This command can only be run by a player."));
            GPTAssistantMod.LOGGER.error("Error executing command: ", e);
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("An unexpected error occurred."));
            GPTAssistantMod.LOGGER.error("Unexpected error processing command", e);
        }
        return 1;
    }
}
