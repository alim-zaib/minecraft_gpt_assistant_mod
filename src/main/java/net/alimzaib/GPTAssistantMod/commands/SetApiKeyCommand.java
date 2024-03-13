package net.alimzaib.GPTAssistantMod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.alimzaib.GPTAssistantMod.config.ModConfig;
import net.alimzaib.GPTAssistantMod.util.OpenAIValidator;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class SetApiKeyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setapikey")
                .then(Commands.argument("apikey", StringArgumentType.string())
                        .executes(context -> {
                            String apiKey = StringArgumentType.getString(context, "apikey");
                            context.getSource().sendSuccess(() -> Component.literal("Validating API key..."), true);


                            // Validate the API key asynchronously
                            OpenAIValidator.validateApiKey(apiKey).thenAcceptAsync(isValid -> {
                                if (isValid) {
                                    // If the key is valid, store it and inform the player.
                                    ModConfig.setApiKey(apiKey); // Store the key only if valid
                                    context.getSource().getServer().execute(() -> { // Ensure we're on the main thread
                                        context.getSource().sendSuccess(() -> Component.literal("API key is valid and stored!"), true);

                                    });
                                } else {
                                    context.getSource().getServer().execute(() -> { //
                                        context.getSource().sendFailure(Component.literal("API key is invalid or could not be validated."));
                                    });
                                }
                            });

                            return 1;
                        })
                )
        );
    }
}
