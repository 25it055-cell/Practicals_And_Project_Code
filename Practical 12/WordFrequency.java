import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "Java is powerful, Java is versatile. Developers love Java because Java makes coding structured, and clean coding makes developers happy. Happy developers write great Java code!";

        System.out.println("Input Paragraph:\n" + paragraph + "\n");

        String cleaned = paragraph.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = cleaned.split("\\s+");

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequencyMap.entrySet());

        Comparator<Map.Entry<String, Integer>> frequencyComparator = new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                int countCompare = e2.getValue().compareTo(e1.getValue());
                if (countCompare != 0) {
                    return countCompare;
                }
                return e1.getKey().compareTo(e2.getKey());
            }
        };

        Collections.sort(entries, frequencyComparator);

        System.out.println(String.format("%-15s | %-10s", "WORD", "FREQUENCY"));
        System.out.println("----------------------------");
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(String.format("%-15s | %-10d", entry.getKey(), entry.getValue()));
        }
    }
}
