package testJunit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

    }

    // метод возвращает массив элементов идущих после последней четверки входного массива
    public int[] searchFour(int[] arr) {
        // проверка: массив не должен быть пустым
        if (arr == null) throw new RuntimeException();
        int indexNumber = -1;
        // поиск цифры "4" и получение его индекса, со смещением на следующую позицию
        for (int i = 0; i < arr.length; i++) {
            // поиск последней цифры "4"
            if (arr[i] == 4) indexNumber = i + 1;
        }
        // проверка: индекс следующего элемента от элемента "4" существует и не выходит за пределы основного массива
        if (indexNumber == -1 || indexNumber > arr.length) throw new RuntimeException();
        // создание нового массива
        int[] array = {};
        // заполнение нового массива array элементами после последнего элемента "4"
        for (int i = indexNumber; i < arr.length; i++) {
            array = Arrays.copyOf(array, array.length + 1);
            array[array.length - 1] = arr[i];
        }
        return array;
    }

    //!!! этот метод подсмотрел у Вас, простой и понятный, понравился, решил попробовать
    public int[] searchFourTeacher(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) return Arrays.copyOfRange(arr, i + 1, arr.length);
        }
        throw new RuntimeException();
    }

    // метод возвращает true если в переданном массиве есть хоть одна четверка или единица
    public boolean containsOneOrFour(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int a : arr) {
            list.add(a);
        }
        if (list.contains(1) && list.contains(4)) return true;
        return false;
    }
}
