package edu.hw5;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;

public class DateParserTest {
    static Arguments[] dates() {
        return new Arguments[] {
            Arguments.of("2020-10-10",    Optional.of(LocalDate.of(2020, 10, 10))),
            Arguments.of("2020-12-2",     Optional.of(LocalDate.of(2020, 12, 2))),
            Arguments.of("1234-1-1",      Optional.of(LocalDate.of(1234, 1, 1))),
            Arguments.of("1/3/1976",      Optional.of(LocalDate.of(1976, 3, 1))),
            Arguments.of("01/03/2000",    Optional.of(LocalDate.of(2000, 3, 1))),
            Arguments.of("1/12/01",       Optional.of(LocalDate.of(2001, 12, 1))),
            Arguments.of("1/3/20",        Optional.of(LocalDate.of(2020, 3, 1))),
            Arguments.of("tomorrow",      Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("today",         Optional.of(LocalDate.now())),
            Arguments.of("yesterday",     Optional.of(LocalDate.now().plusDays(-1))),
            Arguments.of("1 day ago",     Optional.of(LocalDate.now().plusDays(-1))),
            Arguments.of("2 days ago",    Optional.of(LocalDate.now().plusDays(-2))),
            Arguments.of("2234 days ago", Optional.of(LocalDate.now().plusDays(-2234))),

            Arguments.of("",              Optional.empty()),
            Arguments.of("34/03/2000",    Optional.empty()),
            Arguments.of("12/15/2000",    Optional.empty()),
            Arguments.of("12/15/0",       Optional.empty()),
            Arguments.of("2020-20-20",    Optional.empty()),
            Arguments.of("0-0-0",         Optional.empty()),
            Arguments.of("1/2/3/4",       Optional.empty()),
            Arguments.of("1/3/100",       Optional.empty()),
            Arguments.of("1-2-3-4",       Optional.empty()),
            Arguments.of("-1 days ago",   Optional.empty()),
            Arguments.of("today today",   Optional.empty()),
        };
    }

    @ParameterizedTest
    @MethodSource("dates")
    void testParseDate(String date, Optional<LocalDate> expectedOutput) {
        DateParser parser = new DateParser();

        Optional<LocalDate> actualOutput = parser.parseDate(date);

        assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}
