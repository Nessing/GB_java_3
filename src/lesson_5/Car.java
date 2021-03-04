package lesson_5;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static volatile int CARS_FINISHED;
    private static boolean IS_WIN = false;
    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public static int getCarsFinished() {
        return CARS_FINISHED;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    public void startRace() {
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        // проверка победителя
        if (!IS_WIN) {
            // блок, для захвата монитора, позволяющий определять только одного победителя
            synchronized (Car.class) {
                IS_WIN = true;
                System.out.println(this.name + " - WIN");
            }
            /* Пример вывода в консоль:
            ...
            Участник #3 закончил этап: Дорога 40 метров
            Участник #4 закончил этап: Дорога 40 метров
            Участник #3 - WIN
            Участник #2 закончил этап: Тоннель 80 метров
            Участник #2 начал этап: Дорога 40 метров
            ...
             */
        }
        // счетчик количества участников, прошедших гонку
        CARS_FINISHED++;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

