package edu.project4;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Random;

public class RectTest {
    @Test
    void rectFromMethod_shouldProduceExpectedRect() {
        double x = -10.5;
        double y = 15.0;
        double width = 15.5;
        double height = 103.5;

        Rect actualRect = Rect.from(x, x + width, y, y + height);
        Rect expectedRect = new Rect(x, y, width, height);

        assertThat(actualRect).isEqualTo(expectedRect);
    }

    static Arguments[] rectPoints() {
        return new Arguments[] {
            Arguments.of(Rect.from(-10, 10, -10, 10), new Point(0, 0),         true),
            Arguments.of(Rect.from(-10, 10, -10, 10), new Point(-10.5, -10.5), false),
            Arguments.of(Rect.from(0, 1920, 0, 1080), new Point(540, 480),     true),
            Arguments.of(Rect.from(0, 1920, 0, 1080), new Point(1920, 1080),   false),
            Arguments.of(Rect.from(-0.5, 0, -0.4, 0), new Point(0.0, -0.3),    false)
        };
    }

    @ParameterizedTest
    @MethodSource("rectPoints")
    void rectCorrectlyDeterminesWhetherItContainsPoint(Rect rect, Point point, boolean expectedResult) {
        boolean actualResult = rect.containsPoint(point);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @RepeatedTest(16)
    void rectRandomPointLocatedInsideRect() {
        Random random = new Random();

        Rect rect = new Rect(0.5, 10.3, 15.3, 10.0);

        Point randomPoint = rect.getRandomPoint(random);

        assertThat(rect.containsPoint(randomPoint)).isTrue();
    }
}
