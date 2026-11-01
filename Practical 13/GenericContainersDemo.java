class Box<T> {
    private T value;

    public Box() {
    }

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Box containing: " + value;
    }
}

class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

public class GenericContainersDemo {
    public static <E> E getFirstElement(E[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=== 1. GENERIC BOX<T> TYPE SAFETY ===");
        Box<String> stringBox = new Box<>("Hello Generics");
        String stringVal = stringBox.get();
        System.out.println("String Box Value (no cast needed): " + stringVal);

        Box<Integer> intBox = new Box<>(42);
        Integer intVal = intBox.get();
        System.out.println("Integer Box Value (no cast needed): " + intVal);

        Box<Double> doubleBox = new Box<>(99.95);
        Double doubleVal = doubleBox.get();
        System.out.println("Double Box Value (no cast needed): " + doubleVal);

        System.out.println("\n=== 2. GENERIC PAIR<A, B> DEMO ===");
        Pair<String, Integer> studentRank = new Pair<>("Alice", 1);
        String studentName = studentRank.getFirst();
        Integer rank = studentRank.getSecond();
        System.out.println("Pair 1: Name = " + studentName + ", Rank = " + rank);

        Pair<Integer, String> httpStatus = new Pair<>(404, "Not Found");
        System.out.println("Pair 2: HTTP Status = " + httpStatus.getFirst() + " (" + httpStatus.getSecond() + ")");

        Pair<Double, Boolean> sensorReading = new Pair<>(36.6, true);
        System.out.println("Pair 3: Temperature = " + sensorReading.getFirst() + " C, Calibrated = " + sensorReading.getSecond());

        System.out.println("\n=== 3. GENERIC METHOD getFirstElement ===");
        String[] words = { "Java", "Python", "C++", "Rust" };
        String firstWord = getFirstElement(words);
        System.out.println("First element of String array: " + firstWord);

        Integer[] numbers = { 100, 200, 300, 400 };
        Integer firstNumber = getFirstElement(numbers);
        System.out.println("First element of Integer array: " + firstNumber);

        Double[] decimals = { 3.14, 2.718, 1.414 };
        Double firstDecimal = getFirstElement(decimals);
        System.out.println("First element of Double array: " + firstDecimal);

        String[] emptyArray = {};
        String firstFromEmpty = getFirstElement(emptyArray);
        System.out.println("First element of empty array: " + firstFromEmpty);
    }
}
