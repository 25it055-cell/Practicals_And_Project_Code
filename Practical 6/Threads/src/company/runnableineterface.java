package company;





class MyTheradRunnable1 implements Runnable{
    @Override
    public void run() {
        System.out.println("I am a Thread");
    }
}

class MyTheradRunnable2 implements Runnable{
    @Override
    public void run() {
        System.out.println("I am not a Thread");
    }
}
public class runnableineterface {
    public static void main(String[]args){
        MyTheradRunnable1 bullet1=new MyTheradRunnable1();
        Thread gun1=new Thread(bullet1);
        MyTheradRunnable2 bullet2=new MyTheradRunnable2();
        Thread gun2=new Thread(bullet2);
        gun1.start();
        gun2.start();
    }
}
