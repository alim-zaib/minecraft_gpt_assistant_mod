package net.alimzaib.GPTAssistantMod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Screenshot;

import java.io.IOException;
import java.nio.file.*;
import java.util.function.Consumer;
import net.alimzaib.GPTAssistantMod.GPTAssistantMod;

public class ScreenshotTaker {

    public static Path takeAndSaveScreenshot() throws IOException, InterruptedException {
        Minecraft mc = Minecraft.getInstance();
        // Use Minecraft's default screenshots directory.
        Path screenshotsDirPath = mc.gameDirectory.toPath().resolve("screenshots");

        Consumer<Component> messageConsumer = message -> GPTAssistantMod.LOGGER.info(message.getString());
        GPTAssistantMod.LOGGER.info("Attempting to save screenshot to: " + screenshotsDirPath);
        // Let Minecraft handle screenshot saving in its default location.
        Screenshot.grab(screenshotsDirPath.toFile(), mc.getMainRenderTarget(), messageConsumer);

        // Wait a bit to ensure the file system has updated.
        Thread.sleep(1000);

        GPTAssistantMod.LOGGER.info("Looking for screenshots in: " + screenshotsDirPath);

        try {
            // Now find the most recent screenshot in the default directory.
            Path mostRecent = ScreenshotUtil.findMostRecentScreenshot(screenshotsDirPath);
            GPTAssistantMod.LOGGER.info("Most recent screenshot: " + mostRecent);
            return mostRecent;
        } catch (IOException e) {
            GPTAssistantMod.LOGGER.error("Failed to find the most recent screenshot in " + screenshotsDirPath, e);
            throw e;
        }
    }
}
