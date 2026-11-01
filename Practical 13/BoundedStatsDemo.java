import java.util.Arrays;

public class BoundedStatsDemo {

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }
        double sum = 0.0;
        for (N val : values) {
            if (val != null) {
                sum += val.doubleValue();
            }
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        System.out.println("=== BOUNDED GENERICS: <N extends Number> double average(N[] values) ===\n");

        Integer[] intScores = { 85, 92, 78, 90, 88 };
        double intAvg = average(intScores);
        System.out.println("Integer[] array   : " + Arrays.toString(intScores));
        System.out.println("Calculated Average: " + String.format("%.2f", intAvg));
        System.out.println();

        Double[] doubleTemps = { 98.6, 99.1, 98.4, 100.2, 97.9 };
        double doubleAvg = average(doubleTemps);
        System.out.println("Double[] array    : " + Arrays.toString(doubleTemps));
        System.out.println("Calculated Average: " + String.format("%.2f", doubleAvg));
        System.out.println();

        Long[] longDistances = { 1500000L, 2300000L, 1800000L, 3100000L };
        double longAvg = average(longDistances);
        System.out.println("Long[] array      : " + Arrays.toString(longDistances));
        System.out.println("Calculated Average: " + String.format("%.2f", longAvg));
        System.out.println();

        Float[] floatWeights = { 12.5f, 15.8f, 11.2f };
        double floatAvg = average(floatWeights);
        System.out.println("Float[] array     : " + Arrays.toString(floatWeights));
        System.out.println("Calculated Average: " + String.format("%.2f", floatAvg));
        System.out.println();

        System.out.println("=== WHY IT REFUSES NON-NUMBER TYPES ===");
        System.out.println("1. Type Constraint: <N extends Number> specifies that N must be Number or a subclass of Number.");
        System.out.println("2. Compile-Time Safety: Types like String or Boolean do not extend java.lang.Number.");
        System.out.println("   Calling average(new String[]{\"10\", \"20\"}) causes a compile error:");
        System.out.println("   'type argument java.lang.String is not within bounds of type-variable N'");
        System.out.println("3. Member Guarantee: The compiler guarantees that all instances of N provide the .doubleValue()");
        System.out.println("   method from java.lang.Number, making polymorphic numeric calculations safe without casts.");
    }
}
