package lesson_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generic<T> {
    T[] array;

    public Generic(T[] array) {
        this.array = array;
    }

    public void printElements() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    // № 1
    public void changeElements(T[] arr, int out, int in) {
        T temp = arr[out];
        arr[out] = arr[in];
        arr[in] = temp;
    }

    // № 2
    public ArrayList<T> refactorArray(T[] a) {
        ArrayList<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(a));
        return list;
    }
}
