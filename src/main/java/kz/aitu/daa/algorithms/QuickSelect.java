package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;

import java.util.Random;

public final class QuickSelect {

    private QuickSelect() {
    }

    public static int select(int[] a, int k, Metrics m) {
        return select(a, k, m, new Random());
    }

    public static int select(int[] a, int k, Metrics m, Random rnd) {
        validate(a, k);
        int[] work = a.clone();
        return selectInPlace(work, k, m, rnd);
    }

    public static int selectInPlace(int[] a, int k, Metrics m, Random rnd) {
        validate(a, k);
        return select(a, 0, a.length - 1, k, m, rnd);
    }

    private static void validate(int[] a, int k) {
        if (a == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (a.length == 0) {
            throw new IllegalArgumentException("array must not be empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException(
                    "k is out of range: k=" + k + ", expected 0.." + (a.length - 1));
        }
    }

    private static int select(int[] a, int lo, int hi, int k, Metrics m, Random rnd) {
        m.enterRecursion();
        try {
            if (lo == hi) {
                return a[lo];
            }

            long parts = Partition.randomPartition3(a, lo, hi, rnd, m);
            int lt = Partition.lt(parts);
            int gt = Partition.gt(parts);

            if (k < lt) {
                return select(a, lo, lt - 1, k, m, rnd);
            }
            if (k > gt) {
                return select(a, gt + 1, hi, k, m, rnd);
            }
            return a[k];
        } finally {
            m.exitRecursion();
        }
    }
}