package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class FreqDictTest {
    static Arguments[] lists() {
        return new Arguments[] {
            Arguments.of(
                List.of(1, 1, 2, 2),
                Map.of(
                    1, 2,
                    2, 2
                )
            ),
            Arguments.of(
                List.of("a", "str", "pow", "str", "pow"),
                Map.of(
                    "a", 1,
                    "str", 2,
                    "pow", 2
                )
            ),
            Arguments.of(
                List.of("a"),
                Map.of(
                    "a", 1
                )
            ),
            Arguments.of(
                List.of(),
                Map.of()
            ),
            Arguments.of(
                List.of(1, 2, -128, -256),
                Map.of(
                    1, 1,
                    2, 1,
                    -128, 1,
                    -256, 1
                )
            ),
        };
    }

    @ParameterizedTest
    @MethodSource("lists")
    <T> void funcReturnCorrectMapForDifferentTypes(List<T> elements, Map<T, Integer> expectedMap) {
        WordFrequencyCounter counter = new WordFrequencyCounter();

        Map<T, Integer> actualMap = counter.freqDict(elements);

        assertThat(actualMap).isEqualTo(expectedMap);
    }
}
