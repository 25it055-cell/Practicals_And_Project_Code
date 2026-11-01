package company;



class MyNewThr1 extends Thread{
    public void run(){
        int i=1;
        while(i<100){
            System.out.println("I am thread ");
            i++;
        }
    }
}

class MyNewThr2 extends Thread{


    public void run(){
        int i=34;
        System.out.println("Thank you");
        while(true){
            System.out.println("thread " );
        }
    }
}
public class Thread_methods {
    static void main(String[] args) {
        MyNewThr1 t1=new MyNewThr1();
        MyNewThr2 t2=new MyNewThr2();
        t1.start();
        try{
            t1.join();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        t2.start();

    }
}
