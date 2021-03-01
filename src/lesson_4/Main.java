package lesson_4;

public class Main {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread t1 = new Thread(() -> {
            myThread.printA();
        });
        Thread t2 = new Thread(() -> {
            myThread.printB();
        });
        Thread t3 = new Thread(() -> {
            myThread.printC();
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
