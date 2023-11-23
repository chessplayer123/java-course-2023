package edu.hw7;

import java.util.stream.LongStream;

public class FactorialCalculator {
    private FactorialCalculator() {
    }

    public static long factorial(int num) throws IllegalArgumentException {
        if (num < 0) {
            throw new IllegalArgumentException("number can't be negative");
        } else if (num == 0) {
            return 0L;
        }
        return LongStream.range(1, num + 1)
            .parallel()
            .reduce(1L, (num1, num2) -> num1 * num2);
    }
}
