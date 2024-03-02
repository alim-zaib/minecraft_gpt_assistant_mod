package net.alimzaib.GPTAssistantMod.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.alimzaib.GPTAssistantMod.util.InventoryUtil; // Make sure to import InventoryUtil

public class ShowInventoryCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("showInventory")
                .executes(context -> {
                    CommandSourceStack source = context.getSource();
                    String inventoryState = InventoryUtil.getInventoryStateAsText(source.getPlayerOrException());
                    context.getSource().sendSuccess(() -> Component.literal(inventoryState), false);
                    return 1; // Return a successful result
                }));
    }
}
