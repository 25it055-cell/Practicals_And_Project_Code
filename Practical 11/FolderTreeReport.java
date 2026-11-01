import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class FolderTreeReport {
    public static void main(String[] args) {
        Path startPath = Paths.get(".");
        Path reportPath = Paths.get("folder_report.txt");

        List<String> reportLines = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        reportLines.add("================================================================================");
        reportLines.add("                          NIO FOLDER TREE REPORT                                ");
        reportLines.add("================================================================================");
        reportLines.add(String.format("%-45s | %-10s | %-12s | %-19s", "PATH / NAME", "TYPE", "SIZE (BYTES)", "MODIFIED TIME"));
        reportLines.add("--------------------------------------------------------------------------------");

        long[] totalCount = new long[3];

        try (Stream<Path> stream = Files.walk(startPath)) {
            stream.forEach(path -> {
                try {
                    BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
                    Path relativePath = startPath.relativize(path);
                    String displayName = relativePath.toString().isEmpty() ? "." : relativePath.toString();

                    if (displayName.length() > 45) {
                        displayName = "..." + displayName.substring(displayName.length() - 42);
                    }

                    String type;
                    long size;
                    if (attrs.isDirectory()) {
                        type = "DIR";
                        size = 0;
                        totalCount[1]++;
                    } else {
                        type = "FILE";
                        size = attrs.size();
                        totalCount[0]++;
                        totalCount[2] += size;
                    }

                    String modifiedTime = dateFormat.format(new Date(attrs.lastModifiedTime().toMillis()));
                    String line = String.format("%-45s | %-10s | %-12d | %-19s", displayName, type, size, modifiedTime);
                    reportLines.add(line);

                } catch (IOException e) {
                    reportLines.add("Error reading attributes for " + path + ": " + e.getMessage());
                }
            });

            reportLines.add("--------------------------------------------------------------------------------");
            reportLines.add("SUMMARY:");
            reportLines.add("  Total Files Scanned       : " + totalCount[0]);
            reportLines.add("  Total Directories Scanned : " + totalCount[1]);
            reportLines.add("  Total Combined File Size  : " + totalCount[2] + " bytes (" + String.format("%.2f", totalCount[2] / 1024.0) + " KB)");
            reportLines.add("================================================================================");

            Files.write(reportPath, reportLines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            for (String line : reportLines) {
                System.out.println(line);
            }

            System.out.println("\nReport successfully saved to: " + reportPath.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Failed to walk folder tree: " + e.getMessage());
        }
    }
}
