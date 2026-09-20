package kz.aitu.daa.util;

import java.util.Random;

public final class ArrayGenerator {

    public enum InputType {
        RANDOM("random"),
        SORTED("sorted"),
        DUPLICATES("duplicates");

        private final String csvName;

        InputType(String csvName) {
            this.csvName = csvName;
        }

        public String csvName() {
            return csvName;
        }
    }

    private ArrayGenerator() {
    }

    public static int[] generate(InputType type, int n, Random rnd) {
        return switch (type) {
            case RANDOM -> random(n, rnd);
            case SORTED -> sorted(n);
            case DUPLICATES -> duplicates(n, rnd);
        };
    }

    public static int[] random(int n, Random rnd) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt();
        }
        return a;
    }

    public static int[] sorted(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
    }

    public static int[] duplicates(int n, Random rnd) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rnd.nextInt(10);
        }
        return a;
    }
}