package edu.hw5;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Task8Test {
    static Arguments[] oddLengthPatternInput() {
        return new Arguments[] {
            Arguments.of("0",         true),
            Arguments.of("1",         true),
            Arguments.of("000",       true),
            Arguments.of("001",       true),
            Arguments.of("010",       true),
            Arguments.of("011",       true),
            Arguments.of("100",       true),
            Arguments.of("101",       true),
            Arguments.of("110",       true),
            Arguments.of("111",       true),
            Arguments.of("110110110", true),

            Arguments.of("",          false),
            Arguments.of("a",         false),
            Arguments.of("7",         false),
            Arguments.of("123",       false),
            Arguments.of("abc",       false),
            Arguments.of("00",        false),
            Arguments.of("01",        false),
            Arguments.of("10",        false),
            Arguments.of("11",        false),
            Arguments.of("aa",        false),
            Arguments.of("11001100",  false),
        };
    }
    @ParameterizedTest
    @MethodSource("oddLengthPatternInput")
    void oddLengthPatternCheck(String string, Boolean expectedResult) {
        Boolean actualResult = Task8.hasOddLength(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Arguments[] zeroOddOneEvenPatternInput() {
        return new Arguments[] {
            Arguments.of("0",        true),
            Arguments.of("001",      true),
            Arguments.of("010",      true),
            Arguments.of("01100",    true),
            Arguments.of("01101",    true),
            Arguments.of("10",       true),
            Arguments.of("11",       true),
            Arguments.of("1100",     true),
            Arguments.of("1101",     true),
            Arguments.of("11001100", true),

            Arguments.of("",         false),
            Arguments.of("0ab",      false),
            Arguments.of("00",       false),
            Arguments.of("0110",     false),
            Arguments.of("12",       false),
            Arguments.of("1",        false),
            Arguments.of("101",      false)
        };
    }

    @ParameterizedTest
    @MethodSource("zeroOddOneEvenPatternInput")
    void startsWithZeroOddOrStartsWithOneEvenPatternCheck(String string, Boolean expectedResult) {
        Boolean actualResult = Task8.isStartsWithZeroHasOddLenOrStartsWithOneHasEvenLen(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Arguments[] numOfZeroesDivByThreePatternInput() {
        return new Arguments[] {
            Arguments.of("",             true),
            Arguments.of("01010",        true),
            Arguments.of("000",          true),
            Arguments.of("00011",        true),
            Arguments.of("1000",         true),
            Arguments.of("0010",         true),
            Arguments.of("100010001",    true),
            Arguments.of("101010101010", true),
            Arguments.of("111",          true),

            Arguments.of("0",            false),
            Arguments.of("10",           false),
            Arguments.of("0101",         false),
            Arguments.of("10101",        false),
            Arguments.of("00111",        false),
            Arguments.of("000abc",       false),
            Arguments.of("222",          false),
            Arguments.of("00",           false),
        };
    }

    @ParameterizedTest
    @MethodSource("numOfZeroesDivByThreePatternInput")
    void numOfZeroesDivisibleByThreePatternCheck(String string, Boolean expectedResult) {
        Boolean actualResult = Task8.hasNumberOfZeroesDivisibleByThree(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Arguments[] anyStringExcept11Or111PatternInput() {
        return new Arguments[] {
            Arguments.of("",      true),
            Arguments.of("1",     true),
            Arguments.of("0",     true),
            Arguments.of("0000",  true),
            Arguments.of("0101",  true),
            Arguments.of("00",    true),
            Arguments.of("000",   true),
            Arguments.of("01",    true),
            Arguments.of("011",   true),

            Arguments.of("11",    false),
            Arguments.of("111",   false),
        };
    }

    @ParameterizedTest
    @MethodSource("anyStringExcept11Or111PatternInput")
    void isAnyStringExcept11Or111PatternCheck(String string, Boolean expectedResult) {
        Boolean actualResult = Task8.isAnyStringExcept11Or111(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Arguments[] charsOnOddPositionsAreZeroesPatternInput() {
        return new Arguments[] {
            Arguments.of("0",     true),
            Arguments.of("01",    true),
            Arguments.of("00",    true),
            Arguments.of("000",   true),
            Arguments.of("010",   true),
            Arguments.of("0101",  true),
            Arguments.of("01010", true),
            Arguments.of("01000", true),

            Arguments.of("",      false),
            Arguments.of("1",     false),
            Arguments.of("10",    false),
            Arguments.of("100",   false),
            Arguments.of("001",   false),
            Arguments.of("0010",  false),
            Arguments.of("00100", false),
            Arguments.of("01020", false),
        };
    }

    @ParameterizedTest
    @MethodSource("charsOnOddPositionsAreZeroesPatternInput")
    void charsOnOddPositionsAreZeroesPatternCheck(String string, Boolean expectedResult) {
        Boolean actualResult = Task8.hasEveryCharOnOddPositionEqualsZero(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Arguments[] atLeast2ZeroesAndAtMost1OnePatternInput() {
        return new Arguments[] {
            Arguments.of("00",     true),
            Arguments.of("00000",  true),
            Arguments.of("100",    true),
            Arguments.of("100000", true),
            Arguments.of("010",    true),
            Arguments.of("001000", true),
            Arguments.of("001",    true),
            Arguments.of("000001", true),

            Arguments.of("",       false),
            Arguments.of("1",      false),
            Arguments.of("0",      false),
            Arguments.of("100100", false),
            Arguments.of("101",    false),
            Arguments.of("002",    false),
            Arguments.of("0012",   false),
            Arguments.of("020",    false),
        };
    }

    @ParameterizedTest
    @MethodSource("atLeast2ZeroesAndAtMost1OnePatternInput")
    void hasAtLeast2ZeroesAndAtMost1OnePatternCheck(String string, Boolean expectedResult) {
        Boolean actualResult = Task8.hasAtLeast2ZeroesAndAtMost1One(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    static Arguments[] consecutiveOnesPatternInput() {
        return new Arguments[] {
            Arguments.of("",           true),
            Arguments.of("0",          true),
            Arguments.of("1",          true),
            Arguments.of("010",        true),
            Arguments.of("01",         true),
            Arguments.of("10",         true),
            Arguments.of("010010",     true),
            Arguments.of("101",        true),
            Arguments.of("0101000010", true),
            Arguments.of("0000000",    true),

            Arguments.of("11",         false),
            Arguments.of("1011",       false),
            Arguments.of("11011",      false),
            Arguments.of("12",         false),
            Arguments.of("11111",      false),
            Arguments.of("1101011",    false),
        };
    }

    @ParameterizedTest
    @MethodSource("consecutiveOnesPatternInput")
    void hasNoConsecutiveOnesPatternCheck(String string, Boolean expectedResult) {
        Boolean actualResult = Task8.hasNoConsecutiveOnes(string);

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}
