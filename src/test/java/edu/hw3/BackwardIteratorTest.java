package edu.hw3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.LinkedList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class BackwardIteratorTest {
    static Arguments[] lists() {
        return new Arguments[] {
            Arguments.of(List.of(-10, 5, 0, 8, 4)),
            Arguments.of(List.of(0, 1, 3)),
            Arguments.of(List.of("first", "second", "third")),
            Arguments.of(List.of(0)),
            Arguments.of(List.of()),
        };
    }

    @ParameterizedTest
    @MethodSource("lists")
    <T> void testCorrectTraversing(List<T> list) {
        BackwardIterator<T> iterator = new BackwardIterator<>(list);

        List<T> actualTraverse = new LinkedList<T>();
        while (iterator.hasNext()) {
            actualTraverse.add(iterator.next());
        }
        List<T> expectedTraverse = list.reversed();

        assertThat(actualTraverse).isEqualTo(expectedTraverse);
    }
}
