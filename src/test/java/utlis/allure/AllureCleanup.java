package utlis.allure;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class AllureCleanup {

    private static final Path RESULTS_DIR = Paths.get("Reports", "allure-results");
    private static final Path REPORT_DIR = Paths.get("Reports", "allure-report");

    private AllureCleanup() {
    }

    public static void cleanPreviousResults() {
        deleteDirectory(RESULTS_DIR);
        deleteDirectory(REPORT_DIR);
        createDirectory(RESULTS_DIR);
    }

    private static void deleteDirectory(Path directory) {
        if (!Files.exists(directory)) {
            return;
        }
        try {
            FileUtils.deleteDirectory(directory.toFile());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to delete directory: " + directory, e);
        }
    }

    private static void createDirectory(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create directory: " + directory, e);
        }
    }
}
