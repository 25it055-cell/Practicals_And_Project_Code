package company;



class MyThread1 extends Thread {
    @Override
    public void run() {
        int i = 0;
        while (i < 100) {
            System.out.println("Thread 1");
            System.out.println("She is sad");
            i++;

        }
    }
}
class MyThread2 extends Thread {
    @Override
    public void run() {
        int i = 0;
        while (i < 100) {
            System.out.println("Thread 2");
            System.out.println("He is sad");
            i++;

        }
    }
}
public class extend {
    static void main(String[] args) {
        MyThread1 t1=new MyThread1();
        MyThread2 t2=new MyThread2();
        t1.start();
        t2.start();
    }

}
