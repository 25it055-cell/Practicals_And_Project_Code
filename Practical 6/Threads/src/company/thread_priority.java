package company;



class MyThr1 extends Thread{
    public MyThr1(String name){
        super(name);

    }

    public void run(){
        int i=34;
        System.out.println("Thank you");
            while(true){
                System.out.println("I am thread "+this.getName() );
            }
    }
}
public class thread_priority {
    static void main(String[] args) {
        MyThr1 t1=new MyThr1("SMEET1");
        MyThr1 t2=new MyThr1("SMEET2");
        MyThr1 t3=new MyThr1("SMEET3");
        MyThr1 t4=new MyThr1("SMEET4");
        MyThr1 t5=new MyThr1("SMEET5  imp");
        t5.setPriority(Thread.MAX_PRIORITY);
        t1.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }
}
