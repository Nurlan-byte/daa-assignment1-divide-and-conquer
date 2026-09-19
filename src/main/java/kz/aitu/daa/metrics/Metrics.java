package kz.aitu.daa.metrics;

public class Metrics {

    private long comparisons;
    private long startNanos;
    private long elapsedNanos;

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

    public void reset() {
        comparisons = 0;
        startNanos = 0;
        elapsedNanos = 0;
    }

    @Override
    public String toString() {
        return "comparisons=" + comparisons + ", time=" + timeMs() + " ms";
    }
}