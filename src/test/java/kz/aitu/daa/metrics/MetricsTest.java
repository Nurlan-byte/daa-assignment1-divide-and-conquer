package kz.aitu.daa.metrics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MetricsTest {

    @Test
    void countsComparisons() {
        Metrics m = new Metrics();

        m.incComparisons();
        m.incComparisons();
        m.incComparisons();

        assertEquals(3, m.comparisons());
    }

    @Test
    void resetClearsCounters() {
        Metrics m = new Metrics();
        m.incComparisons();
        m.incComparisons();

        m.reset();

        assertEquals(0, m.comparisons());
    }

    @Test
    void measuresTime() throws InterruptedException {
        Metrics m = new Metrics();

        m.startTimer();
        Thread.sleep(5);
        m.stopTimer();

        assertTrue(m.timeMs() > 0, "elapsed time must be positive, got " + m.timeMs());
    }
}