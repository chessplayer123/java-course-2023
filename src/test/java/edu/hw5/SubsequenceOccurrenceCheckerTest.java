package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class SubsequenceOccurrenceCheckerTest {
    static Arguments[] containsAsSubsequence() {
        return new Arguments[] {
            Arguments.of("abc", "a1b2c"),
            Arguments.of("sequence", "sequence"),
            Arguments.of("subsequence", "sSuEbQsUeEqNuCeEn!c!e"),
            Arguments.of("test", "1t22e333s1t55555"),
            Arguments.of("", "text"),
            Arguments.of("\t\n\r", "tabular\tdata\nnewline\r"),
            Arguments.of("[01]", "[01][01][01]"),
            Arguments.of("{text}", "{some}{text}{in}{curly}{brackets}"),
            Arguments.of(" ", "test with spaces"),
        };
    }

    static Arguments[] notContainsAsSubsequence() {
        return new Arguments[] {
            Arguments.of("abc", "123"),
            Arguments.of("text", "txt"),
            Arguments.of("not empty text", ""),
            Arguments.of("sequence", "seq"),
            Arguments.of("reversed", "desrever"),
            Arguments.of("a{5}", "aaaaa"),
            Arguments.of("[01]*", "000111"),
            Arguments.of("^.*$", "random text"),
        };
    }

    @ParameterizedTest
    @MethodSource("containsAsSubsequence")
    void subsequenceContains(String subSeq, String seq) {
        SubsequenceOccurrenceChecker checker = new SubsequenceOccurrenceChecker();

        Boolean actualResult = checker.check(subSeq, seq);

        assertThat(actualResult).isTrue();
    }

    @ParameterizedTest
    @MethodSource("notContainsAsSubsequence")
    void subsequenceNotContains(String subSeq, String seq) {
        SubsequenceOccurrenceChecker checker = new SubsequenceOccurrenceChecker();

        Boolean actualResult = checker.check(subSeq, seq);

        assertThat(actualResult).isFalse();
    }
}
