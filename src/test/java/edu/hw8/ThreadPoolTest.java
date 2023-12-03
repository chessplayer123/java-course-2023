package edu.hw8;

import static org.assertj.core.api.Assertions.assertThat;
import edu.hw8.ThreadPool.FixedThreadPool;
import edu.hw8.ThreadPool.ThreadPool;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ThreadPoolTest {
    static int fibonacci(int n) {
        int a = 1;
        int b = 1;
        int buf;

        for (int i = 1; i < n; ++i) {
            buf = b;
            b += a;
            a = buf;
        }

        return a;
    }

    @Test
    void parallelFibonacciCalculation() throws Exception {
        int count = 32;
        int threadsNum = 8;
        int[] expectedNumbers = new int[count];
        int[] actualNumbers = new int[count];

        try (ThreadPool pool = FixedThreadPool.create(threadsNum)) {
            for (int i = 1; i <= count; ++i) {
                int index = i - 1;
                pool.execute(() -> actualNumbers[index] = fibonacci(index + 1));
                expectedNumbers[index] = fibonacci(index+1);
            }

            pool.start();
            pool.awaitTermination();
        }

        assertThat(actualNumbers).containsExactly(expectedNumbers);
    }

    @Test
    void taskAddedInStartedPoolShouldNotBeExecuted() throws Exception {
        int count = 16;
        int threadsNum = 8;
        AtomicIntegerArray actualArray = new AtomicIntegerArray(16);
        int[] expectedArray = new int[16];

        try (ThreadPool pool = FixedThreadPool.create(threadsNum)) {
            for (int i = 0; i < count; ++i) {
                int finalI = i;
                pool.execute(() -> actualArray.set(finalI, finalI));
                expectedArray[i] = i;
            }

            pool.start();
            pool.execute(() -> actualArray.set(15, -1));
            pool.awaitTermination();
        }

        assertThat(actualArray).containsExactly(expectedArray);
    }

    @Test
    void startAlreadyStartedPoolDoesNotRepeatTasks() throws Exception {
        int count = 16;
        int threadsNum = 8;
        AtomicIntegerArray actualArray = new AtomicIntegerArray(16);
        int[] expectedArray = new int[16];

        try (ThreadPool pool = FixedThreadPool.create(threadsNum)) {
            for (int i = 0; i < count; ++i) {
                int finalI = i;
                pool.execute(() -> actualArray.set(finalI, finalI));
                expectedArray[i] = i;
            }

            pool.start();
            pool.start();
            pool.awaitTermination();
        }

        assertThat(actualArray).containsExactly(expectedArray);
    }
}
