package net.alimzaib.GPTAssistantMod.commands;


import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.alimzaib.GPTAssistantMod.config.ModConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.alimzaib.GPTAssistantMod.util.OpenAIValidator;
import net.minecraft.server.level.ServerPlayer;

import net.alimzaib.GPTAssistantMod.config.ModConfig;
import net.alimzaib.GPTAssistantMod.util.OpenAIValidator;
import java.util.concurrent.CompletableFuture;

public class SetApiKeyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setapikey")
                .then(Commands.argument("apikey", StringArgumentType.string())
                        .executes(context -> {
                            String apiKey = StringArgumentType.getString(context, "apikey");
                            ModConfig.setApiKey(apiKey); // Store the key first

                            // Immediately acknowledge the command before validation
                            context.getSource().sendSuccess(() -> Component.literal("API key stored. Validating..."), true);

                            // Now validate the API key asynchronously
                            OpenAIValidator.validateApiKey(apiKey).thenAcceptAsync(isValid -> {
                                // This runs in another thread, so we must ensure any interaction with the game is thread-safe
                                if (isValid) {
                                    // If the key is valid, inform the player. Must be run in the game's main thread.
                                    context.getSource().sendSuccess(() -> Component.literal("API key is valid!"), true);
                                } else {
                                    // If the key is invalid, inform the player. Must also be run in the game's main thread.
                                    context.getSource().sendFailure(Component.literal("API key is invalid or could not be validated."));
                                }
                            }, command -> context.getSource().getServer().execute(command)); // Switch back to the main server thread to interact with the game

                            return 1; // Return a result code
                        })
                )
        );
    }
}
