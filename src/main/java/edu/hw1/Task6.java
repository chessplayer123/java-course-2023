package edu.hw1;

import java.util.Arrays;

public class Task6 {
    private static final int LOWER_BOUND = 1000;
    private static final int UPPER_BOUND = 10000;
    private static final int TARGET_NUMBER = 6174;

    public Task6() {
    }

    private void reverseChars(char[] chars) {
        int len = chars.length;
        int half = len / 2;
        char buffer;
        for (int i = 0; i < half; ++i) {
            buffer = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = buffer;
        }
    }

    public int countK(int num) {
        if (num <= LOWER_BOUND || UPPER_BOUND <= num) {
            return -1;
        } else if (num == TARGET_NUMBER) {
            return 0;
        }

        char[] strNum = String.valueOf(num).toCharArray();

        Arrays.sort(strNum);
        int incOrder = Integer.parseInt(String.valueOf(strNum));
        reverseChars(strNum);
        int decOrder = Integer.parseInt(String.valueOf(strNum));

        // all digits are same
        if (incOrder == decOrder) {
            return -1;
        }

        return 1 + countK(decOrder - incOrder);
    }
}
