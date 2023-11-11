package edu.hw5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.List;

public class Friday13thFinderTest {
    static Arguments[] fridays13thInYear() {
        return new Arguments[] {
            Arguments.of(2000, List.of("2000-10-13")),
            Arguments.of(1950, List.of("1950-01-13", "1950-10-13")),
            Arguments.of(2023, List.of("2023-01-13", "2023-10-13")),
            Arguments.of(2024, List.of("2024-09-13", "2024-12-13")),
            Arguments.of(1925, List.of("1925-02-13", "1925-03-13", "1925-11-13")),
        };
    }

    @ParameterizedTest
    @MethodSource("fridays13thInYear")
    void correctYear(int year, List<String> expectedDates) {
        Friday13thFinder finder = new Friday13thFinder();

        List<LocalDate> actualDates = finder.findAllFriday13th(year);

        assertThat(actualDates).isEqualTo(expectedDates.stream().map(LocalDate::parse).toList());
    }

    @Test
    void negativeYearThrowsException() {
        Friday13thFinder finder = new Friday13thFinder();
        int year = -10;

        assertThatThrownBy(() -> finder.findAllFriday13th(year))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("year must be positive");
    }

    static Arguments[] closestNextFriday13th() {
        return new Arguments[] {
            Arguments.of(LocalDate.of(2024,  1,  1), LocalDate.of(2024,  9, 13)),
            Arguments.of(LocalDate.of(2023,  1,  1), LocalDate.of(2023,  1, 13)),
            Arguments.of(LocalDate.of(1925,  1,  1), LocalDate.of(1925,  2, 13)),
            Arguments.of(LocalDate.of(1925,  3, 13), LocalDate.of(1925,  3, 13)),
            Arguments.of(LocalDate.of(1925, 10, 13), LocalDate.of(1925, 11, 13)),
            Arguments.of(LocalDate.of(1949, 12, 14), LocalDate.of(1950,  1, 13)),
            Arguments.of(LocalDate.of(1950,  1, 14), LocalDate.of(1950, 10, 13)),
            Arguments.of(LocalDate.of(2000,  1, 13), LocalDate.of(2000, 10, 13)),
            Arguments.of(LocalDate.of(2000,  1, 13), LocalDate.of(2000, 10, 13)),
        };
    }

    @ParameterizedTest
    @MethodSource("closestNextFriday13th")
    void correctNextFriday13th(LocalDate beginDate, LocalDate expectedDate) {
        Friday13thFinder finder = new Friday13thFinder();

        LocalDate actualDate = finder.findNextFriday13th(beginDate);

        assertThat(actualDate).isEqualTo(expectedDate);
    }
}
