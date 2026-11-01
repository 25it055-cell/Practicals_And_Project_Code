import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) {
        String keyword = "ERROR";
        List<Path> logFiles = createSampleLogs();

        System.out.println("=== LOG ANALYZER REPORT ===");
        System.out.println("Keyword to search: \"" + keyword + "\"\n");

        long grandTotalLines = 0;
        long grandTotalKeywordMatches = 0;

        for (Path filePath : logFiles) {
            try {
                BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                long fileSize = attrs.size();

                long fileLines = 0;
                long keywordMatches = 0;

                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    fileLines++;
                    if (line.contains(keyword)) {
                        keywordMatches++;
                    }
                }

                grandTotalLines += fileLines;
                grandTotalKeywordMatches += keywordMatches;

                System.out.println("File: " + filePath.getFileName());
                System.out.println("  Size (attributes) : " + fileSize + " bytes");
                System.out.println("  Total Lines       : " + fileLines);
                System.out.println("  Lines with \"" + keyword + "\" : " + keywordMatches);
                System.out.println();

            } catch (IOException e) {
                System.err.println("Error reading " + filePath + ": " + e.getMessage());
            }
        }

        System.out.println("=== OVERALL TOTALS ===");
        System.out.println("Files Scanned               : " + logFiles.size());
        System.out.println("Grand Total Lines           : " + grandTotalLines);
        System.out.println("Grand Total Keyword Matches : " + grandTotalKeywordMatches);
    }

    private static List<Path> createSampleLogs() {
        List<Path> files = new ArrayList<>();
        try {
            Path dir = Paths.get("logs");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            Path appLog = dir.resolve("app.log");
            List<String> appLines = Arrays.asList(
                "2026-10-28 08:00:01 INFO Application started successfully",
                "2026-10-28 08:05:12 DEBUG Connection established to cache server",
                "2026-10-28 08:12:44 ERROR Connection timeout to payment gateway",
                "2026-10-28 08:15:00 INFO Retrying payment processing",
                "2026-10-28 08:15:05 ERROR Payment retry failed: 504 Gateway Timeout",
                "2026-10-28 08:20:00 INFO Service running normal"
            );
            Files.write(appLog, appLines);
            files.add(appLog);

            Path dbLog = dir.resolve("database.log");
            List<String> dbLines = Arrays.asList(
                "2026-10-28 08:00:05 INFO Database pool initialized with 20 connections",
                "2026-10-28 08:10:20 WARN Query execution took longer than 500ms",
                "2026-10-28 08:12:45 ERROR Deadlock detected in transaction #419",
                "2026-10-28 08:30:10 INFO Database backup completed"
            );
            Files.write(dbLog, dbLines);
            files.add(dbLog);

            Path authLog = dir.resolve("auth.log");
            List<String> authLines = Arrays.asList(
                "2026-10-28 08:01:00 INFO User alice logged in",
                "2026-10-28 08:02:15 ERROR Authentication failed for user charlie: Invalid password",
                "2026-10-28 08:02:18 ERROR Authentication failed for user charlie: Account locked",
                "2026-10-28 08:05:30 INFO User bob logged in",
                "2026-10-28 08:10:00 INFO Token refreshed for session 882"
            );
            Files.write(authLog, authLines);
            files.add(authLog);

        } catch (IOException e) {
            System.err.println("Error initializing sample logs: " + e.getMessage());
        }
        return files;
    }
}
