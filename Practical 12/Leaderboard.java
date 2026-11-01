import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {
    public static void main(String[] args) {
        Map<String, Integer> playerScores = new HashMap<>();
        playerScores.put("Sophia", 980);
        playerScores.put("James", 980);
        playerScores.put("Liam", 920);
        playerScores.put("Mia", 920);
        playerScores.put("Alexander", 850);
        playerScores.put("Charlotte", 850);
        playerScores.put("Benjamin", 850);
        playerScores.put("Lucas", 790);
        playerScores.put("Emma", 710);
        playerScores.put("Noah", 640);

        System.out.println("Total Registered Players: " + playerScores.size());
        System.out.println("Raw Player Scores Map:\n" + playerScores + "\n");

        int topN = 5;
        printTopN(playerScores, topN);

        System.out.println();
        printTopN(playerScores, 10);
    }

    public static void printTopN(Map<String, Integer> scoreMap, int n) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(scoreMap.entrySet());

        Comparator<Map.Entry<String, Integer>> leaderboardComparator = new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> p1, Map.Entry<String, Integer> p2) {
                int scoreComparison = p2.getValue().compareTo(p1.getValue());
                if (scoreComparison != 0) {
                    return scoreComparison;
                }
                return p1.getKey().compareToIgnoreCase(p2.getKey());
            }
        };

        Collections.sort(entries, leaderboardComparator);

        int limit = Math.min(n, entries.size());

        System.out.println("==================================================");
        System.out.println("               TOP " + limit + " LEADERBOARD                 ");
        System.out.println("==================================================");
        System.out.println(String.format("%-6s | %-18s | %-8s", "RANK", "PLAYER NAME", "SCORE"));
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = entries.get(i);
            System.out.println(String.format("#%-5d | %-18s | %-8d", (i + 1), entry.getKey(), entry.getValue()));
        }
        System.out.println("==================================================");
    }
}
