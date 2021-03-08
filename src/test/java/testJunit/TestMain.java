package testJunit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestMain {
    private Main main;

    @BeforeEach
    public void init() {
        main = new Main();
    }

    @Test
    public void testSearchFour_1() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        int[] result = {5, 6};
        int[] actualArr = main.searchFour(arr);
        Assertions.assertArrayEquals(result, actualArr);
    }

    @Test
    public void testSearchFour_2() {
        int[] arr = {1, 2, 4, 4, 2, 3, 4, 1, 7};
        int[] result = {1, 7};
        int[] actualArr = main.searchFour(arr);
        Assertions.assertArrayEquals(result, actualArr);
    }

    @Test
    public void testSearchFour_3() {
        int[] arr = {1, 2, 4, 4, 2, 3, 4, 1, 4};
        int[] result = {};
        int[] actualArr = main.searchFour(arr);
        Assertions.assertArrayEquals(result, actualArr);
    }

    @Test
    public void testSearchFour_4() {
        int[] arr = {5, 7, 4, 2, 7, 9, 6};
        int[] result = {2, 7, 9, 6};
        int[] actualArr = main.searchFour(arr);
        Assertions.assertArrayEquals(result, actualArr);
    }

    // тест на возвращение true если массив состоит из четверок и единиц
    @ParameterizedTest
    @MethodSource("dataForTestContainsOneOrFourTrue")
    public void massTestContainsOneOrFourTrue(int[] arr) {
        Assertions.assertTrue(main.containsOneOrFour(arr));
    }

    public static Stream<Arguments> dataForTestContainsOneOrFourTrue() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] {1, 1, 1, 4, 4, 4}));
        out.add(Arguments.arguments(new int[] {4, 4, 1, 1, 4, 4}));
        out.add(Arguments.arguments(new int[] {4, 1, 1, 1, 1, 1}));
        out.add(Arguments.arguments(new int[] {4, 4, 4, 1, 4, 4}));
        return out.stream();
    }

    // тест на возвращение false если в массиве нет хоть одной четверки и единицы
    @ParameterizedTest
    @MethodSource("dataForTestContainsOneOrFourFalse")
    public void massTestContainsOneOrFourFalse(int[] arr) {
        Assertions.assertFalse(main.containsOneOrFour(arr));
    }

    public static Stream<Arguments> dataForTestContainsOneOrFourFalse() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] {1, 1, 1, 1, 1, 1}));
        out.add(Arguments.arguments(new int[] {4, 4, 4, 4, 4, 4}));
        out.add(Arguments.arguments(new int[] {4, 4, 4, 2, 9, 3}));
        out.add(Arguments.arguments(new int[] {1, 1, 1, 8, 6, 3}));
        return out.stream();
    }

    @Test
    public void testSearchFourTeacher_1() {
        int[] arr = {4, 2, 3, 2, 5, 6};
        int[] result = {2, 3, 2, 5, 6};
        int[] actualArr = main.searchFour(arr);
        Assertions.assertArrayEquals(result, actualArr);
    }

    @Test
    public void testSearchFourTeacher_2() {
        int[] arr = {1, 2, 4, 4, 2, 3, 4, 1, 7};
        int[] result = {1, 7};
        int[] actualArr = main.searchFour(arr);
        Assertions.assertArrayEquals(result, actualArr);
    }

    @Test
    public void testSearchFourTeacher_3() {
        int[] arr = {1, 2, 4, 4, 2, 3, 4, 1, 4};
        int[] result = {};
        int[] actualArr = main.searchFour(arr);
        Assertions.assertArrayEquals(result, actualArr);
    }

    @Test
    public void testSearchFourTeacher_4() {
        int[] arr = {5, 7, 4, 2, 7, 9, 6};
        int[] result = {2, 7, 9, 6};
        int[] actualArr = main.searchFour(arr);
        Assertions.assertArrayEquals(result, actualArr);
    }

    /*

    метод expected нельзя использовать и при добавлении библиотеки в ручную (org.junit.Assert) не дает результатов.
    Из-за чего это может быть?

    @Test(expected = RuntimeException.class)
    public void testSearchFourTeacher_5_exception() {
        int[] arr = {5, 7, 1, 2, 7, 9, 6};
        int[] result = {};
        int[] actualArr = main.searchFour(arr);
    }

     */
}
