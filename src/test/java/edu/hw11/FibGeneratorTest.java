package edu.hw11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.Assertions.assertThat;

public class FibGeneratorTest {
    static Arguments[] fibValues() {
        return new Arguments[] {
            Arguments.of(0,  1L),
            Arguments.of(1,  1L),
            Arguments.of(2,  1L),
            Arguments.of(3,  2L),
            Arguments.of(4,  3L),
            Arguments.of(5,  5L),
            Arguments.of(6,  8L),
            Arguments.of(7,  13L),
            Arguments.of(8,  21L),
            Arguments.of(9,  34L),
            Arguments.of(10, 55L),
            Arguments.of(11, 89L),
            Arguments.of(12, 144L),
        };
    }
    @ParameterizedTest
    @MethodSource("fibValues")
    void fibFunctionReturnExpectedValue(int n, long expectedValue)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object fibClassInstance = FibGenerator.generate();
        long actualValue = (long) fibClassInstance
            .getClass()
            .getMethod("fib", int.class)
            .invoke(fibClassInstance, n);

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
