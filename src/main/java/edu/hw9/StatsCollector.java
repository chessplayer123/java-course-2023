package edu.hw9;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class StatsCollector {
    private static final int THREADS_NUM = 8;
    private static final String EMPTY_METRIC_MESSAGE = "metric can't be empty";

    private final List<Metric> metrics;
    private final Semaphore semaphore;
    private final ExecutorService pool;

    public StatsCollector() {
        metrics = new CopyOnWriteArrayList<>();
        semaphore = new Semaphore(THREADS_NUM);
        pool = Executors.newFixedThreadPool(THREADS_NUM);
    }

    public void push(String metricName, double[] values) throws IllegalArgumentException, InterruptedException {
        if (values.length == 0) {
            throw new IllegalArgumentException(EMPTY_METRIC_MESSAGE);
        }

        semaphore.acquire();
        pool.execute(() -> {
            Metric metric = Metric.from(metricName, values);
            metrics.add(metric);
            semaphore.release();
        });
    }

    public List<Metric> stats() throws InterruptedException {
        semaphore.acquire(THREADS_NUM);
        return metrics;
    }

    public record Metric(String name, double sum, double min, double max, double avg) {
        public static Metric from(String name, double[] values) throws IllegalArgumentException {
            return new Metric(
                name,
                Arrays.stream(values).sum(),
                Arrays.stream(values).min().orElseThrow(() -> new IllegalArgumentException(EMPTY_METRIC_MESSAGE)),
                Arrays.stream(values).max().orElseThrow(),
                Arrays.stream(values).average().orElseThrow()
            );
        }
    }
}
