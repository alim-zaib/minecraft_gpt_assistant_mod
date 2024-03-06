package net.alimzaib.GPTAssistantMod.util;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;

public class ScreenshotUtil {

    public static Path findMostRecentScreenshot(Path screenshotDir) throws IOException {
        try (Stream<Path> paths = Files.list(screenshotDir)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".png")) // Assuming screenshots are in PNG format.
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()))
                    .orElseThrow(() -> new IOException("No screenshots found in " + screenshotDir));
        }
    }
}
