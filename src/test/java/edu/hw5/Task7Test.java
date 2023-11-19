package edu.hw5;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task7Test {
    static Arguments[] threeCharsWithThirdZeroTestCases() {
        return new Arguments[] {
            Arguments.of("000",     true),
            Arguments.of("110",     true),
            Arguments.of("100110",  true),
            Arguments.of("000000",  true),
            Arguments.of("110111",  true),

            Arguments.of("",        false),
            Arguments.of("001",     false),
            Arguments.of("0",       false),
            Arguments.of("10",      false),
            Arguments.of("01",      false),
            Arguments.of("0010000", false),
            Arguments.of("90034",   false),
            Arguments.of("aa0",     false),
        };
    }
    @ParameterizedTest
    @MethodSource("threeCharsWithThirdZeroTestCases")
    void threeCharsWithZeroThirdReturnsExpectedResult(String string, boolean expectedResult) {
        Task7 task7 = new Task7();

        Boolean actualResult = task7.containsAtLeastThreeCharsAndThirdIsZero(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Arguments[] startsAndEndsWithSameCharTestCases() {
        return new Arguments[] {
            Arguments.of("0",       true),
            Arguments.of("1",       true),
            Arguments.of("10001",   true),
            Arguments.of("000000",  true),
            Arguments.of("1010101", true),
            Arguments.of("11",      true),
            Arguments.of("00",      true),

            Arguments.of("",        false),
            Arguments.of("2",       false),
            Arguments.of("3",       false),
            Arguments.of("4",       false),
            Arguments.of("5",       false),
            Arguments.of("6",       false),
            Arguments.of("7",       false),
            Arguments.of("8",       false),
            Arguments.of("9",       false),
            Arguments.of("01",      false),
            Arguments.of("10",      false),
            Arguments.of("100110",  false),
            Arguments.of("0011",    false),
            Arguments.of("bb",      false),
            Arguments.of("99",      false),
            Arguments.of("301013",  false),
            Arguments.of("a00a",    false),
        };
    }
    @ParameterizedTest
    @MethodSource("startsAndEndsWithSameCharTestCases")
    void startsAndEndsWithSameCharReturnsExpectedResult(String string, boolean expectedResult) {
        Task7 task7 = new Task7();

        Boolean actualResult = task7.startsAndEndsWithSameChar(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Arguments[] lengthAtLeastOneAtMostThreeTestCases() {
        return new Arguments[] {
            Arguments.of("0",     true),
            Arguments.of("1",     true),
            Arguments.of("00",    true),
            Arguments.of("01",    true),
            Arguments.of("10",    true),
            Arguments.of("11",    true),
            Arguments.of("000",   true),
            Arguments.of("001",   true),
            Arguments.of("010",   true),
            Arguments.of("011",   true),
            Arguments.of("100",   true),
            Arguments.of("101",   true),
            Arguments.of("110",   true),
            Arguments.of("111",   true),

            Arguments.of("",      false),
            Arguments.of("2",     false),
            Arguments.of("aa",    false),
            Arguments.of("1c3",   false),
            Arguments.of("1111",  false),
            Arguments.of("00010", false),
            Arguments.of("0000",  false),
            Arguments.of("00a",   false),
        };
    }

    @ParameterizedTest
    @MethodSource("lengthAtLeastOneAtMostThreeTestCases")
    void hasLengthAtLeastOneAndAtMostThreeReturnsExpectedResult(String string, boolean expectedResult) {
        Task7 task7 = new Task7();

        Boolean actualResult = task7.hasLengthAtLeastOneAtMostThree(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
