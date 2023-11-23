package edu.hw7;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PiCalculatorTest {
    @ParameterizedTest
    @ValueSource(ints = {
        10_000,
        100_000,
        1_000_000,
        10_000_000,
        100_000_000,
        1_000_000_000,
    })
    void singleThreadAccuracyDoesNotExceedOneAndHalfPercent(int numOfIterations) {
        double actualValue = PiCalculator.calculateBySingleThread(numOfIterations);

        assertThat(actualValue)
            .isCloseTo(Math.PI, Percentage.withPercentage(1.5));
    }

    @ParameterizedTest
    @ValueSource(ints = {
        10_000,
        100_000,
        1_000_000,
        10_000_000,
        100_000_000,
        1_000_000_000,
    })
    void multiThreadAccuracyDoesNotExceedOneAndHalfPercent(int numOfIterations) {
        double actualValue = PiCalculator.calculateByMultipleThreads(numOfIterations, 8);

        assertThat(actualValue)
            .isCloseTo(Math.PI, Percentage.withPercentage(1.5));
    }
}
