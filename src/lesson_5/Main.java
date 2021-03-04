package lesson_5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static final int CARS_COUNT = 4;
    public static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(CARS_COUNT);
    public static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(CARS_COUNT);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            final int w = i;
            new Thread(() -> {
                try {
                    cars[w].run();
                    COUNT_DOWN_LATCH.countDown();
                    CYCLIC_BARRIER.await();
                    cars[w].startRace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            COUNT_DOWN_LATCH.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ожидание всех участников для завершения гонки
        while (true) {
            if (Car.getCarsFinished() == CARS_COUNT) {
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
                break;
            }
        }
    }
}
