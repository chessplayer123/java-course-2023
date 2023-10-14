package edu.hw1;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class Task8Test {
    @Test
    void testEmptyBoard() {
        Task8 task8 = new Task8();

        int[][] board = new int[][] {};

        boolean cantCapture = task8.knightBoardCapture(board);

        assertFalse(cantCapture);
    }

    @Test
    void testUnexpectedDigits() {
        Task8 task8 = new Task8();

        int[][] board = new int[][] {
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 0, 0},
            {0, 0, 0, 0, 9, 0, 0, 0},
            {0, 2, 6, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {3, 0, 0, 5, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 0, 0, 0, 0, 0, 0}
        };

        boolean cantCapture = task8.knightBoardCapture(board);

        assertFalse(cantCapture);
    }

    @Test
    void testZeroesBoard() {
        Task8 task8 = new Task8();

        int[][] board = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };

        boolean cantCapture = task8.knightBoardCapture(board);

        assertTrue(cantCapture);
    }

    @Test
    void testCanNotCapture() {
        Task8 task8 = new Task8();

        int[][] board = new int[][] {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        };

        boolean cantCapture = task8.knightBoardCapture(board);

        assertTrue(cantCapture);
    }

    @Test
    void testCanCapture() {
        Task8 task8 = new Task8();

        int[][] board = new int[][] {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0}
        };

        boolean cantCapture = task8.knightBoardCapture(board);

        assertFalse(cantCapture);
    }

    @Test
    void testPiecesOnEdge() {
        Task8 task8 = new Task8();

        int[][] board = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
        };

        boolean cantCapture = task8.knightBoardCapture(board);

        assertFalse(cantCapture);
    }

    @Test
    void testPieceInCenter() {
        Task8 task8 = new Task8();

        int[][] board = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
        };

        boolean cantCapture = task8.knightBoardCapture(board);

        assertTrue(cantCapture);
    }
}
