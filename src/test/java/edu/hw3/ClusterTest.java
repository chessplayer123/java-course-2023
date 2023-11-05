package edu.hw3;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.Vector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ClusterTest {
    static Arguments[] clusters() {
        return new Arguments[] {
            Arguments.of(
                "((()))",
                List.of("((()))")
            ),
            Arguments.of(
                "((()))(())()()(()())",
                List.of("((()))", "(())", "()", "()", "(()())")
            ),
            Arguments.of(
                "((())())(()(()()))",
                List.of("((())())", "(()(()()))")
            ),
            Arguments.of(
                "()()(()())",
                List.of("()", "()", "(()())")
            ),
            Arguments.of(
                "(()()()())",
                List.of("(()()()())")
            ),
        };
    }

    @ParameterizedTest
    @MethodSource("clusters")
    void validBracketSequenceReturnCorrectAnswer(String seq, List<String> expectedClusters) {
        BracketClusterer clusterer = new BracketClusterer();

        List<String> actualClusters = clusterer.clusterize(seq);

        assertThat(actualClusters).isEqualTo(expectedClusters);
    }

    @Test
    void invalidStringFormatThrowsException() {
        BracketClusterer clusterer = new BracketClusterer();

        String seq = "(()(Wrong format))";

        assertThatThrownBy(() -> {
            clusterer.clusterize(seq);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("invalid string format");
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "(())(()",
        "(",
        "(((((",
        ")",
        "())"
    })
    void unbalancedSeqThrowsException(String seq) {
        BracketClusterer clusterer = new BracketClusterer();

        assertThatThrownBy(() -> {
            clusterer.clusterize(seq);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("sequence isn't balanced");
    }
}
