package lesson_1.bigTask;

import lesson_1.bigTask.fruits.Fruit;

import java.util.ArrayList;

public class Box<T extends Fruit> {

    private ArrayList<T> fruits = new ArrayList<>();

    /*
    метод добавления фрукта в коробку
     */
    public void add(T a) {
        fruits.add(a);
    }

    /*
    метод getWeight() который высчитывает вес коробки,
    зная количество фруктов и вес одного фрукта(вес яблока - 1.0f,
    апельсина - 1.5f, не важно в каких это единицах)
     */
    public float getWeight() {
        float weight = 0.0f;
        for (int i = 0; i < fruits.size(); i++) {
            weight += fruits.get(i).getWeight();
        }
        return weight;
    }

    /*
    метод compare, который позволяет сравнить текущую
    коробку с той, которую подадут в compare в качестве
    параметра, true - если их веса равны, false в противном
    случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
     */
    public boolean compare(Box box) {
        return this.getWeight() == box.getWeight();
    }

    /*
    метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
     */
    public void replace(Box<T> boxPut, int count) {
        if (boxPut.fruits == null || boxPut.fruits.size() == 0) {
            throw new ArrayIndexOutOfBoundsException("Коробка пуста");
        }
        for (int i = 0; i < count; i++) {
            try {
                fruits.add(boxPut.fruits.get(0));
                boxPut.fruits.remove(0);
            } catch (IndexOutOfBoundsException e) {
                System.err.println("В коробке больше нет фруктов");
                break;
            }
        }
    }
}
