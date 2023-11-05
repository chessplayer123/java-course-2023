package edu.hw3;

import org.jetbrains.annotations.NotNull;

public class AtbashCipher {
    public AtbashCipher() {
    }

    public String atbash(@NotNull String src) {
        int strLen = src.length();
        char[] translation = new char[strLen];
        for (int i = 0; i < strLen; ++i) {
            char ch = src.charAt(i);
            if ('a' <= ch && ch <= 'z') {
                ch = (char) ('a' + ('z' - ch));
            } else if ('A' <= ch && ch <= 'Z') {
                ch = (char) ('A' + ('Z' - ch));
            }
            translation[i] = ch;
        }
        return String.valueOf(translation);
    }
}
