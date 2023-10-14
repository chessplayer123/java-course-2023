package edu.hw1;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Task3 {
    public Task3() {
    }

    public boolean isNestable(int[] inner, int[] outer) {
        if (inner.length * outer.length == 0) {
            return false;
        }

        int minInner = inner[0];
        int maxInner = inner[0];

        int minOuter = outer[0];
        int maxOuter = outer[0];

        for (int value: inner) {
            minInner = min(value, minInner);
            maxInner = max(value, maxInner);
        }
        for (int value: outer) {
            minOuter = min(value, minOuter);
            maxOuter = max(value, maxOuter);
        }

        return minOuter < minInner && maxInner < maxOuter;
    }
}
