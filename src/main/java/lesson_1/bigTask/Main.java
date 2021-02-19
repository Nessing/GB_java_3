package lesson_1.bigTask;

import lesson_1.bigTask.fruits.Apple;
import lesson_1.bigTask.fruits.Orange;

public class Main {
    public static void main(String[] args) {
        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        Box<Orange> orangeBox1 = new Box<>();
        Box<Orange> orangeBox2 = new Box<>();

        appleBox1.add(new Apple());
        appleBox1.add(new Apple());
        appleBox2.add(new Apple());
        appleBox2.add(new Apple());
        orangeBox1.add(new Orange());
        orangeBox2.add(new Orange());

        System.out.println("appleBox1: " + appleBox1.getWeight());
        System.out.println("appleBox2: " + appleBox2.getWeight());
        System.out.println("orangeBox1: " + orangeBox1.getWeight());
        System.out.println("equals? " + appleBox1.compare(orangeBox1));

        appleBox1.replace(appleBox2, 4);

        System.out.println("appleBox1: " + appleBox1.getWeight());
        System.out.println("appleBox2: " + appleBox2.getWeight());
        System.out.println("orangeBox1: " + orangeBox1.getWeight());
    }
}
