package edu.hw7;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class PiCalculator {
    private static final double CIRCLE_RADIUS = 0.5;

    private PiCalculator() {
    }

    @SuppressWarnings("MagicNumber")
    private static double evaluatePiApproximation(int pointsInCircleCount, int totalPointsCount) {
        return 4  * ((double) pointsInCircleCount / totalPointsCount);
    }

    private static int getCountOfRandomPointsInCircle(int totalCount) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();

        int pointsInCircleCount = 0;
        for (int i = 0; i < totalCount; ++i) {
            double x = random.nextDouble(0, 2 * CIRCLE_RADIUS);
            double y = random.nextDouble(0, 2 * CIRCLE_RADIUS);

            if (Math.pow(x - CIRCLE_RADIUS, 2) + Math.pow(y - CIRCLE_RADIUS, 2) <= CIRCLE_RADIUS * CIRCLE_RADIUS) {
                ++pointsInCircleCount;
            }
        }
        return pointsInCircleCount;
    }

    public static double calculateBySingleThread(int totalCount) {
        int circleCount = getCountOfRandomPointsInCircle(totalCount);
        return evaluatePiApproximation(circleCount, totalCount);
    }

    public static double calculateByMultipleThreads(int iterationsNum, int threadsNum) {
        // ceil total count of points to be divisible by threadsNum
        int totalCount = iterationsNum + threadsNum - (iterationsNum % threadsNum);

        try (ExecutorService pool = Executors.newFixedThreadPool(threadsNum)) {
            List<Callable<Integer>> tasks = Stream
                .<Callable<Integer>>generate(() -> () -> getCountOfRandomPointsInCircle(totalCount / threadsNum))
                .limit(threadsNum)
                .toList();

            int circleCount = pool.invokeAll(tasks)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException cause) {
                        throw new RuntimeException(cause);
                    }
                })
                .reduce(0, Integer::sum);

            return evaluatePiApproximation(circleCount, totalCount);
        } catch (InterruptedException cause) {
            throw new RuntimeException(cause);
        }
    }
}
