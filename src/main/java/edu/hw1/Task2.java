package edu.hw1;

public final class Task2 {
    private static final int RADIX = 10;

    public Task2() {
    }

    public int countDigits(int number) {
        int count = 1;
        int n = number;
        while (n / RADIX != 0) {
            n /= RADIX;
            ++count;
        }
        return count;
    }
}
