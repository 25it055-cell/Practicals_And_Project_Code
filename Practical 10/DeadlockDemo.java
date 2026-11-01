public class DeadlockDemo {
    public static void main(String[] args) {
        final Object resourceA = new Object();
        final Object resourceB = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("Thread 1 locked resource A");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread 1 waiting for resource B...");
                synchronized (resourceB) {
                    System.out.println("Thread 1 locked resource B");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println("Thread 2 locked resource B");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Thread 2 waiting for resource A...");
                synchronized (resourceA) {
                    System.out.println("Thread 2 locked resource A");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
