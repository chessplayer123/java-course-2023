package edu.hw5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.Duration;
import java.util.List;

public class AvgSessionDurationCounterTest {
    static Arguments[] sessions() {
        return new Arguments[] {
            Arguments.of(
                List.of(
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"
                ),
                Duration.parse("PT3H40M")
            ),
            Arguments.of(
                List.of(),
                Duration.ofMillis(0)
            ),
            Arguments.of(
                List.of("2022-04-01, 21:30 - 2022-04-02, 01:20"),
                Duration.parse("PT3H50M")
            ),
            Arguments.of(
                List.of("2005-06-01, 15:11 - 2005-07-02, 03:20"),
                Duration.parse("P30DT12H9M") // 30d 12h 9m
            ),
            Arguments.of(
                List.of("2000-07-01, 00:01 - 2000-07-02, 03:20"), // 1d 3h 19m
                Duration.parse("P1DT3H19M")
            ),
            Arguments.of(
                List.of(
                    "2022-12-31, 23:30 - 2023-01-01, 02:30",  //  3h
                    "2000-07-15, 00:01 - 2000-07-16, 00:01",  // 24h
                    "2005-06-01, 15:11 - 2005-06-15, 15:11"   // 14*24h
                ),
                Duration.ofHours(121)
            ),
        };
    }

    @ParameterizedTest
    @MethodSource("sessions")
    void correctAvgDurationCountForValidSessions(List<String> sessions, Duration expectedAvgDuration) {
        AvgSessionDurationCounter counter = new AvgSessionDurationCounter();

        Duration actualAvgDuration = counter.getAverageSessionDuration(sessions);

        assertThat(actualAvgDuration).isEqualTo(expectedAvgDuration);
    }

    @Test
    void wrongDateFormatThrowsException() {
        AvgSessionDurationCounter counter = new AvgSessionDurationCounter();

        List<String> sessions = List.of("2022-03-12 20:20 - 2022-03-12 23:50");

        assertThatThrownBy(() -> counter.getAverageSessionDuration(sessions))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("wrong date format");
    }

    @Test
    void wrongSessionFormatThrowsException() {
        AvgSessionDurationCounter counter = new AvgSessionDurationCounter();

        List<String> sessions = List.of("2022-03-12 20:20 - 2022-03-12 23:50 - 2022-03-13 00:31");

        assertThatThrownBy(() -> counter.getAverageSessionDuration(sessions))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("wrong session format");
    }
}
