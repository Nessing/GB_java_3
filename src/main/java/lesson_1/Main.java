package lesson_1;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // № 1
        Integer[] arrayInt = new Integer[]{1, 2, 3, 4, 5};
        Generic<Integer> generic = new Generic<>(arrayInt);
        generic.printElements(); // 1 2 3 4 5
        generic.changeElements(arrayInt, 2, 4);
        generic.printElements(); // 1 2 5 4 3
        ArrayList<Integer> a = generic.refactorArray(arrayInt);
    }
}
