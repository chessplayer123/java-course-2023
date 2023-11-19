package edu.hw5;

import java.util.regex.Pattern;

public class SubsequenceOccurrenceChecker {
    public SubsequenceOccurrenceChecker() {
    }

    public Boolean check(String subSeq, String seq) {
        // text -> .*\\Qt\\E.*\\Qe\\E.*\\Qx\\E.*\\Qt\\E.*
        // quote each character (\\Q \\E) and add .* between chars
        String pattern = ".*\\Q%s\\E.*".formatted(
            String.join("\\E.*\\Q", subSeq.split(""))
        );

        return Pattern.matches(pattern, seq);
    }
}
