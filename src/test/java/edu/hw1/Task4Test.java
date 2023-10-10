package edu.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Task4Test {
    @Test
    void testEmptyString() {
        Task4 task4 = new Task4();

        String input = "";

        String result = task4.fixString(input);

        assertEquals("", result);
    }

    @Test
    void testStringWithOddLength() {
        Task4 task4 = new Task4();

        String input = "abcde";

        String result = task4.fixString(input);

        assertEquals("badce", result);
    }

    @Test
    void testStringWithEvenLength() {
        Task4 task4 = new Task4();

        String input = "abcdef";

        String result = task4.fixString(input);

        assertEquals("badcfe", result);
    }
}
