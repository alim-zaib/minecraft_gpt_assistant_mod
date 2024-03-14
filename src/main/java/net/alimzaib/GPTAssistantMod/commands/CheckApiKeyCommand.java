package net.alimzaib.GPTAssistantMod.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.alimzaib.GPTAssistantMod.config.ModConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.alimzaib.GPTAssistantMod.util.OpenAIValidator;
import net.minecraft.network.chat.Component;

public class CheckApiKeyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("checkOpenAIKey")
                .requires(source -> source.hasPermission(2))
                .executes(context -> checkApiKey(context.getSource())));
    }

    private static int checkApiKey(CommandSourceStack source) {
        String apiKey = ModConfig.loadApiKey();
        // Use the API key asynchronously to validate it, ensuring not to block the game's main thread
        OpenAIValidator.validateApiKey(apiKey).thenAcceptAsync(valid -> {
            if (valid) {
                source.getServer().execute(() -> source.sendSuccess(() -> Component.literal("OpenAI API key is valid. Current key: " + apiKey), false));
            } else {
                source.getServer().execute(() -> source.sendFailure(Component.literal("OpenAI API key is invalid or could not be validated.")));
            }
        });

        return 1;
    }
}
