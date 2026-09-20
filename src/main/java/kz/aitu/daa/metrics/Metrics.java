package kz.aitu.daa.metrics;

public class Metrics {

    private long comparisons;
    private long startNanos;
    private long elapsedNanos;
    private int depth;
    private int maxDepth;

    public void incComparisons() {
        comparisons++;
    }

    public long comparisons() {
        return comparisons;
    }

    public void startTimer() {
        startNanos = System.nanoTime();
    }

    public void stopTimer() {
        elapsedNanos = System.nanoTime() - startNanos;
    }

    public double timeMs() {
        return elapsedNanos / 1_000_000.0;
    }

    public void enterRecursion() {
        depth++;
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void exitRecursion() {
        depth--;
    }

    public int maxDepth() {
        return maxDepth;
    }

    public int currentDepth() {
        return depth;
    }

    public void reset() {
        comparisons = 0;
        startNanos = 0;
        elapsedNanos = 0;
        depth = 0;        // NEW
        maxDepth = 0;     // NEW
    }

    @Override
    public String toString() {
        return "comparisons=" + comparisons
                + ", maxDepth=" + maxDepth        // NEW
                + ", time=" + timeMs() + " ms";
    }
}