package edu.hw7;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.RepeatedTest;

public class ThreadsIncrementerTest {
    @RepeatedTest(32)
    void threadsOperationsAreAtomic() throws InterruptedException {
        int numOfIterations = 100_000;

        ThreadsIncrementer incrementer = new ThreadsIncrementer(numOfIterations);

        incrementer.startThreads();

        int actualValue = incrementer.getCounter();

        assertThat(actualValue).isEqualTo(numOfIterations);
    }
}
