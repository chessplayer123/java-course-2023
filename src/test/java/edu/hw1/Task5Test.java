package edu.hw1;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class Task5Test {
    @Test
    void testPalindrome() {
        Task5 task5 = new Task5();

        int number = 1234321;

        boolean result = task5.isPalindromeDescendant(number);

        assertTrue(result);
    }

    @Test
    void testDescendant() {
        Task5 task5 = new Task5();

        int number = 123312; // -> 363

        boolean result = task5.isPalindromeDescendant(number);

        assertTrue(result);
    }

    @Test
    void testNotPalindromeAndEvenLength() {
        Task5 task5 = new Task5();

        int number = 1234; // -> 363

        boolean result = task5.isPalindromeDescendant(number);

        assertTrue(result);
    }

    @Test
    void testNotPalindromeWithOddLength() {
        Task5 task5 = new Task5();

        int number = 12345;

        boolean result = task5.isPalindromeDescendant(number);

        assertFalse(result);
    }
}
