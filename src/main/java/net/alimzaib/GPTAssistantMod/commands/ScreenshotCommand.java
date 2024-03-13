package net.alimzaib.GPTAssistantMod.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.alimzaib.GPTAssistantMod.util.ScreenshotTaker;
import net.alimzaib.GPTAssistantMod.GPTAssistantMod;

import java.io.IOException;
import java.nio.file.Path;

public class ScreenshotCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("screenshot")
                .executes(context -> {
                    try {
                        Path screenshotPath = ScreenshotTaker.takeAndSaveScreenshot();
                        context.getSource().sendSuccess(() -> Component.literal("Screenshot saved: " + screenshotPath), false);
                    } catch (IOException e) {
                        context.getSource().sendFailure(Component.literal("Failed to take screenshot: " + e.getMessage()));
                        GPTAssistantMod.LOGGER.error("Unexpected IOException processing command", e);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        context.getSource().sendFailure(Component.literal("Screenshot taking was interrupted."));
                        GPTAssistantMod.LOGGER.error("Unexpected InterruptedException processing command", e);
                    }
                    return 1;
                }));
    }
}
