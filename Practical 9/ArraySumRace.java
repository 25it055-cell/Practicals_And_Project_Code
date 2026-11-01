class SharedTotal {
    long total = 0;

    synchronized void add(long value) {
        total += value;
    }
}

public class ArraySumRace {

    public static void main(String[] args) throws Exception {

        int[] numbers = new int[1000000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 1;
        }

        SharedTotal total = new SharedTotal();

        Thread[] threads = new Thread[4];

        int part = numbers.length / 4;

        for (int i = 0; i < 4; i++) {

            int start = i * part;
            int end = (i == 3) ? numbers.length : start + part;

            threads[i] = new Thread(() -> {

                long sum = 0;

                for (int j = start; j < end; j++) {
                    sum += numbers[j];
                }

                total.add(sum);
            });

            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: 1000000");
        System.out.println("Actual: " + total.total);
    }
}