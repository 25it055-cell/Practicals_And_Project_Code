import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        // Chat log lines
        String[] logs = {
                "10:05 alice Hello there",
                "10:10 bob How are you?",
                "InvalidLine",
                "10:20 charlie Good morning"
        };

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        int matches = 0;
        StringBuilder report = new StringBuilder();

        for (String line : logs) {

            // Split into time, user, message
            String[] parts = line.split(" ", 3);

            // Skip malformed lines
            if (parts.length < 3) {
                continue;
            }

            String time = parts[0];
            String user = parts[1];
            String message = parts[2];

            // Check keyword (case-insensitive)
            if (ChatFilter.containsKeyword(message, keyword)) {
                matches++;
                report.append(time)
                        .append(" ")
                        .append(user)
                        .append(": ")
                        .append(message)
                        .append("\n");
            }
        }

        System.out.println("\nMatches: " + matches);

        if (matches > 0) {
            System.out.println("Matching Messages:");
            System.out.println(report);
        } else {
            System.out.println("No matching messages found.");
        }

        sc.close();
    }
}