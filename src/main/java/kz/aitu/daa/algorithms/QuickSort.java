package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;

import java.util.Random;

public final class QuickSort {

    public static final int CUTOFF = 15;

    private QuickSort() {
    }

    public static void sort(int[] a, Metrics m) {
        sort(a, m, new Random());
    }

    public static void sort(int[] a, Metrics m, Random rnd) {
        if (a == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (a.length < 2) {
            return;
        }
        sort(a, 0, a.length - 1, m, rnd);
    }

    private static void sort(int[] a, int lo, int hi, Metrics m, Random rnd) {
        m.enterRecursion();
        try {
            while (lo < hi) {
                if (hi - lo + 1 <= CUTOFF) {
                    InsertionSort.sort(a, lo, hi, m);
                    return;
                }

                long parts = Partition.randomPartition3(a, lo, hi, rnd, m);
                int lt = Partition.lt(parts);
                int gt = Partition.gt(parts);

                int leftSize = lt - lo;
                int rightSize = hi - gt;

                if (leftSize < rightSize) {
                    sort(a, lo, lt - 1, m, rnd);
                    lo = gt + 1;
                } else {
                    sort(a, gt + 1, hi, m, rnd);
                    hi = lt - 1;
                }
            }
        } finally {
            m.exitRecursion();
        }
    }
}