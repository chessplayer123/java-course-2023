package edu.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Task6Test {
    @Test
    void testIdenticalDigits() {
        Task6 task6 = new Task6();

        int number = 1111;

        int len = task6.countK(number);

        assertEquals(-1, len);
    }

    @Test
    void testNegativeNumber() {
        Task6 task6 = new Task6();

        int number = -1024;

        int len = task6.countK(number);

        assertEquals(-1, len);
    }

    @Test
    void testLessThan4Digits() {
        Task6 task6 = new Task6();

        int number = 123;

        int len = task6.countK(number);

        assertEquals(-1, len);
    }

    @Test
    void testMoreThan4Digits() {
        Task6 task6 = new Task6();

        int number = 123456;

        int len = task6.countK(number);

        assertEquals(-1, len);
    }

    @Test
    void testNumber6174() {
        Task6 task6 = new Task6();

        int number = 6174;

        int len = task6.countK(number);

        assertEquals(0, len);
    }

    @Test
    void test4DigitNumber() {
        Task6 task6 = new Task6();

        int number = 6621;

        int len = task6.countK(number);

        assertEquals(5, len);
    }
}
