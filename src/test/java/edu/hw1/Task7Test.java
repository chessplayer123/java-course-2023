package edu.hw1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Task7Test {
    @Test
    void testNegativeShift() {
        Task7 task7 = new Task7();

        int number = 0b1010;

        int shiftedLeft = task7.rotateLeft(number, -1);
        int shiftedRight = task7.rotateRight(number, -1);

        assertEquals(0b0101, shiftedLeft);
        assertEquals(0b0101, shiftedRight);
    }

    @Test
    void testRotateZero() {
        Task7 task7 = new Task7();

        int number = 0;

        int shiftedLeft = task7.rotateLeft(number, 10);
        int shiftedRight = task7.rotateRight(number, 10);

        assertEquals(0, shiftedLeft);
        assertEquals(0, shiftedRight);
    }

    @Test
    void testMaxIntSize() {
        Task7 task7 = new Task7();

        int number = 0x40_00_00_00; // == 0b1{0..0}0 (32)

        int shiftedLeft = task7.rotateLeft(number, 1);
        int shiftedRight = task7.rotateRight(number, 1);

        assertEquals(1, shiftedLeft);
        assertEquals(0x20_00_00_00, shiftedRight);
    }

    @Test
    void testRotateLessThanNumberLength() {
        Task7 task7 = new Task7();

        int number = 0b10011;

        int shiftedLeft = task7.rotateLeft(number, 3);
        int shiftedRight = task7.rotateRight(number, 3);

        assertEquals(0b11100, shiftedLeft);
        assertEquals(0b01110, shiftedRight);
    }

    @Test
    void testRotateMoreThanNumberLength() {
        Task7 task7 = new Task7();

        int number = 0b10011;

        int shiftedLeft = task7.rotateLeft(number, 8); // 8 % 5 == 3
        int shiftedRight = task7.rotateRight(number, 8); // 8 % 5 == 3

        assertEquals(0b11100, shiftedLeft);
        assertEquals(0b01110, shiftedRight);
    }
}
