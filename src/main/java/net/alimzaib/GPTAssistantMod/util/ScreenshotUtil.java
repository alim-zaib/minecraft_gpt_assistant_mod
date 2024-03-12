package net.alimzaib.GPTAssistantMod.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class ScreenshotUtil {

    public static Path findMostRecentScreenshot(Path screenshotDir) throws IOException {
        try (Stream<Path> paths = Files.walk(screenshotDir)) { // Use Files.walk to search directories recursively
            Optional<Path> mostRecentFilePath = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".png"))
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()));

            if (mostRecentFilePath.isPresent()) {
                return mostRecentFilePath.get();
            } else {
                throw new IOException("No screenshots found in " + screenshotDir);
            }
        }
    }
}
