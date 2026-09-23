package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuickSelectTest {

    @Test
    void matchesSortedElementOnRandomArrays() {
        Random rnd = new Random(2024);

        for (int t = 0; t < 100; t++) {
            int n = 1 + rnd.nextInt(400);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(1001) - 500;
            }
            int k = rnd.nextInt(n);

            int[] sorted = a.clone();
            Arrays.sort(sorted);

            int actual = QuickSelect.select(a, k, new Metrics(), new Random(t));

            assertEquals(sorted[k], actual, "failed for n=" + n + ", k=" + k);
        }
    }

    @Test
    void everyKIsCorrect() {
        Random rnd = new Random(5);
        int n = 200;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(50);
        }
        int[] sorted = a.clone();
        Arrays.sort(sorted);

        for (int k = 0; k < n; k++) {
            assertEquals(sorted[k], QuickSelect.select(a, k, new Metrics(), new Random(k)),
                    "failed for k=" + k);
        }
    }

    @Test
    void handlesEdgeCases() {
        assertEquals(9, QuickSelect.select(new int[] { 9 }, 0, new Metrics()));

        int[] a = { 5, 1, 9, 3, 7 };
        assertEquals(1, QuickSelect.select(a, 0, new Metrics()), "minimum");
        assertEquals(9, QuickSelect.select(a, 4, new Metrics()), "maximum");

        int[] equal = new int[1000];
        Arrays.fill(equal, 4);
        assertEquals(4, QuickSelect.select(equal, 500, new Metrics()));
    }

    @Test
    void rejectsInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[] {}, 0, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(null, 0, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[] { 1, 2, 3 }, -1, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[] { 1, 2, 3 }, 3, new Metrics()));
    }

    @Test
    void doesNotModifyCallerArray() {
        int[] a = { 5, 2, 8, 1, 9, 3 };
        int[] snapshot = a.clone();

        QuickSelect.select(a, 3, new Metrics());

        assertArrayEquals(snapshot, a);
    }

    @Test
    void comparisonCountGrowsLinearly() {
        Random rnd = new Random(11);
        int n = 500_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt();
        }
        Metrics m = new Metrics();

        QuickSelect.select(a, n / 2, m, new Random(3));

        assertTrue(m.comparisons() <= 10L * n,
                "expected O(n) comparisons, got " + m.comparisons() + " for n=" + n);
    }
}