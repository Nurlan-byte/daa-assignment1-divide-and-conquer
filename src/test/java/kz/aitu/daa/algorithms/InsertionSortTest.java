package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InsertionSortTest {

    @Test
    void matchesLibrarySortOnRandomArrays() {
        Random rnd = new Random(42);

        for (int t = 0; t < 50; t++) {
            int n = 1 + rnd.nextInt(100);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(201) - 100;
            }
            int[] expected = a.clone();
            Arrays.sort(expected);

            InsertionSort.sort(a, new Metrics());

            assertArrayEquals(expected, a, "failed on array of size " + n);
        }
    }

    @Test
    void handlesEdgeCases() {
        int[] empty = {};
        InsertionSort.sort(empty, new Metrics());
        assertArrayEquals(new int[] {}, empty);

        int[] single = { 42 };
        InsertionSort.sort(single, new Metrics());
        assertArrayEquals(new int[] { 42 }, single);

        int[] equal = { 7, 7, 7, 7, 7 };
        InsertionSort.sort(equal, new Metrics());
        assertArrayEquals(new int[] { 7, 7, 7, 7, 7 }, equal);

        int[] sorted = { 1, 2, 3, 4, 5 };
        InsertionSort.sort(sorted, new Metrics());
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, sorted);
    }

    @Test
    void sortsOnlyTheGivenRange() {
        int[] a = { 9, 5, 1, 7, 3 };

        InsertionSort.sort(a, 1, 3, new Metrics());

        assertArrayEquals(new int[] { 9, 1, 5, 7, 3 }, a);
    }

    @Test
    void sortedInputCostsLinearComparisons() {
        int n = 100;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        Metrics m = new Metrics();

        InsertionSort.sort(a, m);

        assertEquals(n - 1, m.comparisons(),
                "on sorted input each element needs exactly one comparison");
    }
}