package company;



class MyThr extends Thread{
    public MyThr(String name){
        super(name);

    }

    public void run(){
        int i=34;
        System.out.println("Thank you");
//        while(true){
//            System.out.println("I am thread");
//        }
    }
}
public class thread_constructor {
    static void main(String[] args) {
        MyThr t=new MyThr("Smeet");
        t.start();
        System.out.println("ID of t is "+ t.getName());
    }
}
