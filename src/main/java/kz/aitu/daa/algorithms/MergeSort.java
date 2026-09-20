package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;

public final class MergeSort {

    private MergeSort() {
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

    private static void sort(int[] a, int lo, int hi, Metrics m) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;

        sort(a, lo, mid, m);
        sort(a, mid + 1, hi, m);
        merge(a, lo, mid, hi, m);
    }

    private static void merge(int[] a, int lo, int mid, int hi, Metrics m) {
        int[] buffer = new int[hi - lo + 1]; // <-- new array on every merge call
        System.arraycopy(a, lo, buffer, 0, buffer.length);

        int i = 0; // index into the left half of the buffer
        int j = mid - lo + 1; // index into the right half of the buffer
        int leftEnd = mid - lo;
        int rightEnd = hi - lo;

        for (int k = lo; k <= hi; k++) {
            if (i > leftEnd) {
                a[k] = buffer[j++];
            } else if (j > rightEnd) {
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