package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;

public final class InsertionSort {

    private InsertionSort() {
    }

    public static void sort(int[] a, Metrics m) {
        if (a == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (a.length < 2) {
            return;
        }
        sort(a, 0, a.length - 1, m);
    }

    public static void sort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= lo) {
                m.incComparisons();
                if (a[j] <= key) {
                    break;
                }
                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = key;
        }
    }
}