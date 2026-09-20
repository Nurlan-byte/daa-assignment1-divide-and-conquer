package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuickSortTest {

    @Test
    void matchesLibrarySortOnRandomArrays() {
        Random rnd = new Random(777);

        for (int t = 0; t < 100; t++) {
            int n = 1 + rnd.nextInt(500);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(2001) - 1000;
            }
            int[] expected = a.clone();
            Arrays.sort(expected);

            QuickSort.sort(a, new Metrics(), new Random(t));

            assertArrayEquals(expected, a, "failed on array of size " + n);
        }
    }

    @Test
    void handlesEdgeCases() {
        int[] empty = {};
        QuickSort.sort(empty, new Metrics());
        assertArrayEquals(new int[] {}, empty);

        int[] single = { -5 };
        QuickSort.sort(single, new Metrics());
        assertArrayEquals(new int[] { -5 }, single);

        int[] equal = new int[1000];
        Arrays.fill(equal, 3);
        int[] expectedEqual = equal.clone();
        QuickSort.sort(equal, new Metrics());
        assertArrayEquals(expectedEqual, equal);

        int[] sorted = new int[1000];
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = i;
        }
        int[] expectedSorted = sorted.clone();
        QuickSort.sort(sorted, new Metrics());
        assertArrayEquals(expectedSorted, sorted);
    }

    @Test
    void rejectsNullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> QuickSort.sort(null, new Metrics()));
    }

    @Test
    void depthOnSortedInputIsBounded() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        Metrics m = new Metrics();

        QuickSort.sort(a, m, new Random(42));

        int bound = (int) (2 * (Math.log(n) / Math.log(2)));
        assertTrue(m.maxDepth() <= bound,
                "maxDepth=" + m.maxDepth() + " must be <= 2*log2(n)=" + bound);
    }

    @Test
    void duplicatesStayLinear() {
        int n = 100_000;
        int[] a = new int[n];
        Arrays.fill(a, 5);
        Metrics m = new Metrics();

        QuickSort.sort(a, m, new Random(7));

        assertTrue(m.comparisons() <= 4L * n,
                "3-way partition must finish an all-equal array in O(n), got " + m.comparisons());
    }
}