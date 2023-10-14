package edu.hw1;

public class Task5 {
    private final static int RADIX = 10;
    private final static int RADIX_SQR = 100;

    public Task5() {
    }

    private boolean isPalindrome(String str) {
        int len = str.length();
        int half = len / 2;
        for (int i = 0; i < half; ++i) {
            if (str.charAt(i) != str.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindromeDescendant(int n) {
        String strN = String.valueOf(n);
        if (isPalindrome(strN)) {
            return true;
        }
        int descendant = 0;
        int len = strN.length();
        if (len % 2 != 0) {
            return false;
        }
        for (int i = 0; i < len; i += 2) {
            int sum = (strN.charAt(i) - '0') + (strN.charAt(i + 1) - '0');
            if (sum >= RADIX) {
                descendant = descendant * RADIX_SQR + sum;
            } else {
                descendant = descendant * RADIX + sum;
            }

        }
        return isPalindromeDescendant(descendant);
    }
}
