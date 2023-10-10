package edu.hw1;

import edu.hw1.Task1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Task1Test {
    @Test
    void testEmptyString() {
        Task1 task1 = new Task1();

        String videoLen = "";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(-1, result);
    }

    @Test
    void testWithZeroMinutes() {
        Task1 task1 = new Task1();

        String videoLen = "00:17";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(17, result);
    }

    @Test
    void testWithZeroSeconds() {
        Task1 task1 = new Task1();

        String videoLen = "15:00";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(900, result);
    }

    @Test
    void testWrongFormat() {
        Task1 task1 = new Task1();

        String videoLen = "12:31:12";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(-1, result);
    }

    @Test
    void testWithoutColon() {
        Task1 task1 = new Task1();

        String videoLen = "12";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(-1, result);
    }

    @Test
    void testWithoutMinutes() {
        Task1 task1 = new Task1();

        String videoLen = ":15";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(-1, result);
    }

    @Test
    void testWithoutSeconds() {
        Task1 task1 = new Task1();

        String videoLen = "99:";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(-1, result);
    }

    @Test
    void testUnexpectedChars() {
        Task1 task1 = new Task1();

        String videoLen = "11:10testString";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(-1, result);
    }

    @Test
    void testWithSecondsAndMinutes() {
        Task1 task1 = new Task1();

        String videoLen = "100:59";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(6059, result);
    }

    @Test
    void testSecondsOutOfBoundaries() {
        Task1 task1 = new Task1();

        String videoLen = "05:71";

        int result = task1.minutesToSeconds(videoLen);

        assertEquals(-1, result);
    }
}
