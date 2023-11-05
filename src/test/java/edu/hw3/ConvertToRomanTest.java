package edu.hw3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class ConvertToRomanTest {
    static Arguments[] numbers() {
        return new Arguments[] {
            Arguments.of(1,    "I"),
            Arguments.of(2,    "II"),
            Arguments.of(3,    "III"),
            Arguments.of(4,    "IV"),
            Arguments.of(5,    "V"),
            Arguments.of(6,    "VI"),
            Arguments.of(7,    "VII"),
            Arguments.of(8,    "VIII"),
            Arguments.of(9,    "IX"),
            Arguments.of(10,   "X"),
            Arguments.of(11,   "XI"),
            Arguments.of(22,   "XXII"),
            Arguments.of(33,   "XXXIII"),
            Arguments.of(44,   "XLIV"),
            Arguments.of(55,   "LV"),
            Arguments.of(66,   "LXVI"),
            Arguments.of(77,   "LXXVII"),
            Arguments.of(88,   "LXXXVIII"),
            Arguments.of(99,   "XCIX"),
            Arguments.of(111,  "CXI"),
            Arguments.of(222,  "CCXXII"),
            Arguments.of(333,  "CCCXXXIII"),
            Arguments.of(444,  "CDXLIV"),
            Arguments.of(555,  "DLV"),
            Arguments.of(666,  "DCLXVI"),
            Arguments.of(777,  "DCCLXXVII"),
            Arguments.of(888,  "DCCCLXXXVIII"),
            Arguments.of(999,  "CMXCIX"),
            Arguments.of(1000, "M"),
            Arguments.of(1111, "MCXI"),
            Arguments.of(2222, "MMCCXXII"),
            Arguments.of(3333, "MMMCCCXXXIII"),
            Arguments.of(4444, "MMMMCDXLIV"),
            Arguments.of(5000, "MMMMM")
        };
    }

    @ParameterizedTest
    @MethodSource("numbers")
    void test1(int num, String expectedRomanNum) {
        RomanNumeralConverter converter = new RomanNumeralConverter();

        String actualRomanNum = converter.convertToRoman(num);

        assertThat(actualRomanNum).isEqualTo(expectedRomanNum);
    }
}
