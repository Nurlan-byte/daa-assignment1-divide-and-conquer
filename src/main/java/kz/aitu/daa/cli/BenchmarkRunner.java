package kz.aitu.daa.cli;

import kz.aitu.daa.algorithms.MergeSort;
import kz.aitu.daa.algorithms.QuickSelect;
import kz.aitu.daa.algorithms.QuickSort;
import kz.aitu.daa.metrics.Metrics;
import kz.aitu.daa.util.ArrayGenerator;
import kz.aitu.daa.util.ArrayGenerator.InputType;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public final class BenchmarkRunner {

    private static final int[] DEFAULT_SIZES = { 1_000, 10_000, 100_000, 1_000_000 };
    private static final int DEFAULT_RUNS = 5;
    private static final long SEED = 42L;

    private BenchmarkRunner() {
    }

    public static void main(String[] args) throws IOException {
        Path out = Path.of(args.length > 0 ? args[0] : "results.csv");
        int[] sizes = args.length > 1 ? parseSizes(args[1]) : DEFAULT_SIZES;
        int runs = args.length > 2 ? Integer.parseInt(args[2]) : DEFAULT_RUNS;

        System.out.println("sizes = " + Arrays.toString(sizes) + ", runs per case = " + runs);
        warmUp();

        List<String> rows = new ArrayList<>();
        rows.add("algorithm,input,n,time_ms,comparisons,max_depth");

        for (InputType type : InputType.values()) {
            for (int n : sizes) {
                int[] base = ArrayGenerator.generate(type, n, new Random(SEED + n));

                rows.add(measure("mergesort", type, n, runs, base,
                        (a, m, rnd) -> MergeSort.sort(a, m)));
                rows.add(measure("quicksort", type, n, runs, base,
                        QuickSort::sort));
                rows.add(measure("quickselect", type, n, runs, base,
                        (a, m, rnd) -> QuickSelect.selectInPlace(a, a.length / 2, m, rnd)));
            }
        }

        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(out, StandardCharsets.UTF_8))) {
            rows.forEach(w::println);
        }
        System.out.println("written: " + out.toAbsolutePath() + " (" + (rows.size() - 1) + " rows)");
    }

    @FunctionalInterface
    private interface Algorithm {
        void run(int[] a, Metrics m, Random rnd);
    }

    private static String measure(String name, InputType type, int n, int runs,
            int[] base, Algorithm algorithm) {
        double[] times = new double[runs];
        long[] comparisons = new long[runs];
        int[] depths = new int[runs];

        Metrics m = new Metrics();

        for (int r = 0; r < runs; r++) {
            int[] a = base.clone();
            Random rnd = new Random(SEED + r);
            m.reset();

            m.startTimer();
            algorithm.run(a, m, rnd);
            m.stopTimer();

            times[r] = m.timeMs();
            comparisons[r] = m.comparisons();
            depths[r] = m.maxDepth();
        }

        int medianRun = medianIndex(times);
        String row = String.format(Locale.ROOT, "%s,%s,%d,%.3f,%d,%d",
                name, type.csvName(), n, times[medianRun], comparisons[medianRun], depths[medianRun]);
        System.out.println(row);
        return row;
    }

    private static int medianIndex(double[] values) {
        Integer[] order = new Integer[values.length];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (i, j) -> Double.compare(values[i], values[j]));
        return order[values.length / 2];
    }

    private static void warmUp() {
        System.out.println("warm-up...");
        Random rnd = new Random(SEED);
        for (int i = 0; i < 20; i++) {
            int[] a = ArrayGenerator.random(20_000, rnd);
            MergeSort.sort(a.clone(), new Metrics());
            QuickSort.sort(a.clone(), new Metrics(), rnd);
            QuickSelect.selectInPlace(a.clone(), 10_000, new Metrics(), rnd);
        }
    }

    private static int[] parseSizes(String csv) {
        String[] parts = csv.split(",");
        int[] sizes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            sizes[i] = Integer.parseInt(parts[i].trim());
        }
        return sizes;
    }
}