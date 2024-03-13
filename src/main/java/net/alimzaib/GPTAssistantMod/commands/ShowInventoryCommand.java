package net.alimzaib.GPTAssistantMod.commands;


import com.mojang.brigadier.CommandDispatcher;
import net.alimzaib.GPTAssistantMod.util.InventoryUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ShowInventoryCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("showInventory")
                .executes(context -> {
                    CommandSourceStack source = context.getSource();
                    String inventoryState = InventoryUtil.getInventoryStateAsText(source.getPlayerOrException());
                    context.getSource().sendSuccess(() -> Component.literal(inventoryState), false);
                    return 1;
                }));
    }
}
