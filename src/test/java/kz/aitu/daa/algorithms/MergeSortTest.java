package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MergeSortTest {

    @Test
    void matchesLibrarySortOnRandomArrays() {
        Random rnd = new Random(12345);

        for (int t = 0; t < 100; t++) {
            int n = 1 + rnd.nextInt(500);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(2001) - 1000;
            }
            int[] expected = a.clone();
            Arrays.sort(expected);

            MergeSort.sort(a, new Metrics());

            assertArrayEquals(expected, a, "failed on array of size " + n);
        }
    }

    @Test
    void handlesEdgeCases() {
        int[] empty = {};
        MergeSort.sort(empty, new Metrics());
        assertArrayEquals(new int[] {}, empty);

        int[] single = { 42 };
        MergeSort.sort(single, new Metrics());
        assertArrayEquals(new int[] { 42 }, single);

        int[] equal = { 7, 7, 7, 7, 7, 7, 7 };
        MergeSort.sort(equal, new Metrics());
        assertArrayEquals(new int[] { 7, 7, 7, 7, 7, 7, 7 }, equal);

        int[] sorted = { 1, 2, 3, 4, 5, 6, 7, 8 };
        MergeSort.sort(sorted, new Metrics());
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5, 6, 7, 8 }, sorted);

        int[] reversed = new int[500];
        for (int i = 0; i < reversed.length; i++) {
            reversed[i] = reversed.length - i;
        }
        int[] expectedReversed = reversed.clone();
        Arrays.sort(expectedReversed);
        MergeSort.sort(reversed, new Metrics());
        assertArrayEquals(expectedReversed, reversed);
    }

    @Test
    void rejectsNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> MergeSort.sort(null, new Metrics()));
    }

    @Test
    void sortsLargeArray() {
        int n = 100_000;
        Random rnd = new Random(7);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt();
        }
        int[] expected = a.clone();
        Arrays.sort(expected);

        MergeSort.sort(a, new Metrics());

        assertArrayEquals(expected, a);
    }
}