package edu.hw1;

public class Task7 {
    public Task7() {
    }

    private int getBitmaskLength(int n) {
        int numLen = Integer.SIZE - 1;
        while ((n & (1 << (numLen - 1))) == 0 && numLen > 0) {
            --numLen;
        }
        return numLen;
    }

    public int rotateLeft(int n, int shiftWithLoops) {
        if (n == 0) {
            return 0;
        } else if (shiftWithLoops < 0) {
            return rotateRight(n, -shiftWithLoops);
        }

        int numLen = getBitmaskLength(n);
        int shift = shiftWithLoops % numLen;
        int res = ((n << shift) | (n >> (numLen - shift))) & ((1 << numLen) - 1);
        return res;
    }

    public int rotateRight(int n, int shiftWithLoops) {
        if (n == 0) {
            return 0;
        } else if (shiftWithLoops < 0) {
            return rotateLeft(n, -shiftWithLoops);
        }

        int numLen = getBitmaskLength(n);
        int shift = shiftWithLoops % numLen;
        int res = ((n >> shift) | (n << (numLen - shift))) & ((1 << numLen) - 1);
        return res;
    }
}
