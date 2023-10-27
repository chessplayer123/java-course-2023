package edu.hw3;

import java.util.List;
import java.util.Vector;
import org.jetbrains.annotations.NotNull;

public class BracketClusterer {
    public BracketClusterer() {
    }

    public List<String> clusterize(@NotNull String seq) throws IllegalArgumentException {
        Vector<String> clusters = new Vector<>();
        int openedNum = 0;
        int clusterBegin = 0;

        for (int i = 0, len = seq.length(); i < len; ++i) {
            switch (seq.charAt(i)) {
                case '(' -> ++openedNum;
                case ')' -> --openedNum;
                default -> throw new IllegalArgumentException("invalid string format");
            }

            if (openedNum == 0) {
                clusters.add(seq.substring(clusterBegin, i + 1));
                clusterBegin = i + 1;
            } else if (openedNum < 0 || i == len - 1) {
                throw new IllegalArgumentException("sequence isn't balanced");
            }
        }

        return clusters;
    }
}
