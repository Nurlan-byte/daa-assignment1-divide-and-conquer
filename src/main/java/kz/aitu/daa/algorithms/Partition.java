package kz.aitu.daa.algorithms;

import kz.aitu.daa.metrics.Metrics;

import java.util.Random;

public final class Partition {

    private Partition() {
    }

    public static long randomPartition3(int[] a, int lo, int hi, Random rnd, Metrics m) {
        int pivotIndex = lo + rnd.nextInt(hi - lo + 1);
        return partition3(a, lo, hi, a[pivotIndex], m);
    }

    public static long partition3(int[] a, int lo, int hi, int pivot, Metrics m) {
        int lt = lo;
        int i = lo;
        int gt = hi;

        while (i <= gt) {
            m.incComparisons();
            int cmp = Integer.compare(a[i], pivot);

            if (cmp < 0) {
                swap(a, lt, i);
                lt++;
                i++;
            } else if (cmp > 0) {
                swap(a, i, gt);
                gt--;
            } else {
                i++;
            }
        }

        return pack(lt, gt);
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static long pack(int lt, int gt) {
        return ((long) lt << 32) | (gt & 0xFFFFFFFFL);
    }

    public static int lt(long packed) {
        return (int) (packed >> 32);
    }

    public static int gt(long packed) {
        return (int) packed;
    }
}