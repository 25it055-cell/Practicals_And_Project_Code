import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = Arrays.asList(
            "alice",
            "bob",
            "alice",
            "charlie",
            "bob",
            "david",
            "emma",
            "david",
            "frank",
            "charlie",
            "grace"
        );

        System.out.println("All Logins (with repeats):");
        System.out.println(logins + "\n");

        Set<String> uniqueSet = new HashSet<>(logins);
        System.out.println("Unique Visitors Count (via HashSet): " + uniqueSet.size());
        System.out.println("HashSet Elements (unordered): " + uniqueSet + "\n");

        Set<String> orderedUniqueSet = new LinkedHashSet<>(logins);
        System.out.println("Unique Visitors (in first-seen order via LinkedHashSet):");
        System.out.println(orderedUniqueSet + "\n");

        System.out.println("First Three Distinct Visitors:");
        Iterator<String> iterator = orderedUniqueSet.iterator();
        int count = 0;
        while (iterator.hasNext() && count < 3) {
            count++;
            System.out.println(count + ". " + iterator.next());
        }
    }
}
