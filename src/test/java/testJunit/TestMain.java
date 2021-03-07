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

    // тест на возвращение true если в массиве есть хоть одна четверка или единица
    @ParameterizedTest
    @MethodSource("dataForTestContainsOneOrFourTrue")
    public void massTestContainsOneOrFourTrue(int[] arr) {
        Assertions.assertTrue(main.containsOneOrFour(arr));
    }

    public static Stream<Arguments> dataForTestContainsOneOrFourTrue() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] {1, 2, 3, 5, 6, 7}));
        out.add(Arguments.arguments(new int[] {2, 2, 4, 8, 6, 7}));
        out.add(Arguments.arguments(new int[] {2, 2, 1, 8, 4, 3}));
        out.add(Arguments.arguments(new int[] {4, 2, 1, 8, 4, 3}));
        return out.stream();
    }

    // тест на возвращение false если в массиве есть хоть одна четверка или единица
    @ParameterizedTest
    @MethodSource("dataForTestContainsOneOrFourFalse")
    public void massTestContainsOneOrFourFalse(int[] arr) {
        Assertions.assertFalse(main.containsOneOrFour(arr));
    }

    public static Stream<Arguments> dataForTestContainsOneOrFourFalse() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[] {7, 2, 3, 5, 6, 7}));
        out.add(Arguments.arguments(new int[] {2, 2, 9, 8, 6, 7}));
        out.add(Arguments.arguments(new int[] {2, 2, 5, 8, 9, 3}));
        out.add(Arguments.arguments(new int[] {2, 2, 0, 8, 6, 3}));
        return out.stream();
    }
}
