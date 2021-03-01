package lesson_4;

public class MyThread {
    private final Object monitor = new Object();
    private volatile char flag = 'A';

    public void printA() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (flag != 'A') {
                        monitor.wait();
                    }
                    System.out.print('A');
                    flag = 'B';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (flag != 'B') {
                        monitor.wait();
                    }
                    System.out.print('B');
                    flag = 'C';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (flag != 'C') {
                        monitor.wait();
                    }
                    System.out.print('C');
                    flag = 'A';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
