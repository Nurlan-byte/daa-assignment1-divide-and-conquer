package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;

public final class MergeSort {

    public static final int CUTOFF = 15;

    private MergeSort() {
    }

    public static void sort(int[] a, Metrics m) {
        if (a == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (a.length < 2) {
            return;
        }
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int[] buffer, int lo, int hi, Metrics m) {
        if (hi - lo + 1 <= CUTOFF) {
            InsertionSort.sort(a, lo, hi, m);
            return;
        }
        int mid = lo + (hi - lo) / 2;

        sort(a, buffer, lo, mid, m);
        sort(a, buffer, mid + 1, hi, m);
        merge(a, buffer, lo, mid, hi, m);
    }

    private static void merge(int[] a, int[] buffer, int lo, int mid, int hi, Metrics m) {
        System.arraycopy(a, lo, buffer, lo, hi - lo + 1);

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > hi) {
                a[k] = buffer[i++];
            } else {
                m.incComparisons();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }
}