package lesson_4;

import java.util.concurrent.*;

// пример написания ДЗ при помощи ExecutorService с фиксированным количеством потоков
public class MainExecute {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                myThread.printA();
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                myThread.printB();
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                myThread.printC();
            }
        });
        executor.shutdown();
    }
}
