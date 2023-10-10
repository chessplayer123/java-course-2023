package edu.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Task2Test {
    @Test
    void testMaxInt() {
        Task2 task2 = new Task2();

        int number = Integer.MAX_VALUE; // 2147483647

        int count = task2.countDigits(number);

        assertEquals(10, count);
    }

    @Test
    void testZero() {
        Task2 task2 = new Task2();

        int number = 0;

        int count = task2.countDigits(number);

        assertEquals(1, count);
    }

    @Test
    void testOneNonZeroDigit() {
        Task2 task2 = new Task2();

        int number = 7;

        int count = task2.countDigits(number);

        assertEquals(1, count);
    }

    @Test
    void testNumber() {
        Task2 task2 = new Task2();

        int number = 544;

        int count = task2.countDigits(number);

        assertEquals(3, count);
    }

    @Test
    void testNumberWithZeroes() {
        Task2 task2 = new Task2();

        int number = 50702;

        int count = task2.countDigits(number);

        assertEquals(5, count);
    }

    @Test
    void testNegativeNumber() {
        Task2 task2 = new Task2();

        int number = -19;

        int count = task2.countDigits(number);

        assertEquals(2, count);
    }
}
