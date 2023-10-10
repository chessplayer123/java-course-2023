package edu.hw1;

public class Task8 {
    private static final int FIELD_SIZE = 8;

    public Task8() {
    }

    private boolean isKnightOnCell(int rw, int cl, int[][] board) {
        return 0 <= rw && rw < FIELD_SIZE && 0 <= cl && cl < FIELD_SIZE && board[rw][cl] == 1;
    }

    private boolean isKnightAttacksKnight(int rw, int cl, int[][] board) {
        return isKnightOnCell(rw + 2, cl + 1, board)
            || isKnightOnCell(rw + 2, cl - 1, board)

            || isKnightOnCell(rw - 2, cl + 1, board)
            || isKnightOnCell(rw - 2, cl - 1, board)

            || isKnightOnCell(rw + 1, cl + 2, board)
            || isKnightOnCell(rw - 1, cl + 2, board)

            || isKnightOnCell(rw + 1, cl - 2, board)
            || isKnightOnCell(rw - 1, cl - 2, board);
    }

    public boolean knightBoardCapture(int[][] board) {
        if (board.length != FIELD_SIZE) {
            return false;
        }
        for (int rw = 0; rw < FIELD_SIZE; ++rw) {
            if (board[rw].length != FIELD_SIZE) {
                return false;
            }
            for (int cl = 0; cl < FIELD_SIZE; ++cl) {
                if ((board[rw][cl] != 0 && board[rw][cl] != 1)
                    || (board[rw][cl] == 1 && isKnightAttacksKnight(rw, cl, board))
                ) {
                    return false;
                }
            }
        }
        return true;
    }
}
