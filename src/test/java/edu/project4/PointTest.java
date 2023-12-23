package edu.project4;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PointTest {
    private static double DOUBLE_PRECISION = 0.000001;
    static Arguments[] pointsRotated() {
        return new Arguments[] {
            Arguments.of(new Point(0, 0), Math.PI, new Point(0, 0)),
            Arguments.of(new Point(10, 0), 2 * Math.PI, new Point(10, 0)),
            Arguments.of(new Point(-5.5, 0), Math.PI, new Point(5.5, 0)),
            Arguments.of(new Point(-17.3, 0.5), Math.PI, new Point(17.3, -0.5)),
            Arguments.of(new Point(-17.3, 0.5), Math.PI / 2, new Point(-0.5, -17.3)),
            Arguments.of(new Point(1.0, 0.0), Math.PI / 3, new Point(0.5, 0.866025403)),
        };
    }

    @ParameterizedTest
    @MethodSource("pointsRotated")
    void pointRotate_shouldReturnExpectedPoint(Point point, double angle, Point expectedRotated) {
        Point actualRotated = point.rotate(angle);

        assertThat(actualRotated.x())
            .isCloseTo(expectedRotated.x(), within(DOUBLE_PRECISION));
        assertThat(actualRotated.y())
            .isCloseTo(expectedRotated.y(), within(DOUBLE_PRECISION));
    }

    @Test
    void pointCorrectlyMappedFromOneRectToAnother() {
        Point point = new Point(0.5, 0.5);
        Rect src = Rect.from(0, 1, 0, 1);
        Rect dst = Rect.from(0, 1920, 0, 1080);

        Point actualPoint = point.mapFromTo(src, dst);
        Point expectedPoint = new Point(960.0, 540.0);

        assertThat(actualPoint).isEqualTo(expectedPoint);
    }
}
