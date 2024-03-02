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
        // Adjust this call as needed; for example, you might directly use validateApiKey
        // or implement a new method in OpenAIValidator that doesn't consume tokens
        boolean valid = OpenAIValidator.validateApiKey(ModConfig.loadApiKey()).join();

        if (valid) {
            // Adjust based on your Minecraft version
            source.sendSuccess(() -> Component.literal("OpenAI API key is valid."), false);
        } else {
            // Adjust based on your Minecraft version
            source.sendFailure(Component.literal("OpenAI API key is invalid or could not be validated."));
        }

        return valid ? 1 : 0;
    }
}

