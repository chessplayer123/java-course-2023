package edu.hw1;

import org.jetbrains.annotations.NotNull;

public class Task4 {
    public Task4() {
    }

    public String fixString(@NotNull String src) {
        int n = src.length();
        char[] result = new char[n];
        if (n % 2 != 0) {
            result[n - 1] = src.charAt(n - 1);
        }
        for (int i = 0; i < n - 1; i += 2) {
            result[i] = src.charAt(i + 1);
            result[i + 1] = src.charAt(i);
        }
        return String.valueOf(result);
    }
}
