package edu.hw7;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FactorialCalculatorTest {
    static Arguments[] factorials() {
        return new Arguments[] {
            Arguments.of(0,  0L),
            Arguments.of(1,  1L),
            Arguments.of(2,  2L),
            Arguments.of(3,  6L),
            Arguments.of(4,  24L),
            Arguments.of(5,  120L),
            Arguments.of(6,  720L),
            Arguments.of(8,  40320L),
            Arguments.of(10, 3628800L),
            Arguments.of(15, 1307674368000L),
            Arguments.of(20, 2432902008176640000L),
        };
    }

    @ParameterizedTest
    @MethodSource("factorials")
    void test(int number, long expectedResult) {
        long actualResult = FactorialCalculator.factorial(number);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void factorialOfNegativeNumberThrowsException() {
        assertThatThrownBy(() -> FactorialCalculator.factorial(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("number can't be negative");
    }
}
