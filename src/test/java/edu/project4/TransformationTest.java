package edu.project4;

import edu.project4.Transformations.Transformation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TransformationTest {
    private static final double PRECISION = 0.00001;

    static Arguments[] swirlFunction() {
        return new Arguments[] {
            Arguments.of(new Point(0.0, 0.0),      new Point(0.0, 0.0)),
            Arguments.of(new Point(1.0, 1.0),      new Point(1.3254444, 0.49315)),
            Arguments.of(new Point(-3.14, 2.7),    new Point(3.46160745, -2.27307586)),
        };

    }

    @ParameterizedTest
    @MethodSource("swirlFunction")
    void swirlFunction_shouldReturnExpectedValue(Point argument, Point expectedValue) {
        Transformation transformation = Transformation.swirl();

        Point actualValue = transformation.apply(argument);

        assertThat(actualValue.x())
            .isCloseTo(expectedValue.x(), within(PRECISION));
        assertThat(actualValue.y())
            .isCloseTo(expectedValue.y(), within(PRECISION));
    }

    static Arguments[] curlFunction() {
        return new Arguments[] {
            Arguments.of(new Point(0.0, 0.0),      new Point(0.0, 0.0)),
            Arguments.of(new Point(1.0, 1.0),      new Point(0.38461538, -0.07692308)),
            Arguments.of(new Point(-3.14, 2.7),    new Point(-0.19585349, -0.2143561545)),
        };

    }

    @ParameterizedTest
    @MethodSource("curlFunction")
    void curlFunction_shouldReturnExpectedValue(Point argument, Point expectedValue) {
        Transformation transformation = Transformation.curl();

        Point actualValue = transformation.apply(argument);

        assertThat(actualValue.x())
            .isCloseTo(expectedValue.x(), Percentage.withPercentage(0.00001));
        assertThat(actualValue.y())
            .isCloseTo(expectedValue.y(), Percentage.withPercentage(0.00001));
    }

    static Arguments[] eyefishFunction() {
        return new Arguments[] {
            Arguments.of(new Point(0.0, 0.0),      new Point(0.0, 0.0)),
            Arguments.of(new Point(1.0, 1.0),      new Point(0.8284271, 0.82842712)),
            Arguments.of(new Point(-3.14, 2.7),    new Point(-1.2215029, 1.0503369)),
        };

    }

    @ParameterizedTest
    @MethodSource("eyefishFunction")
    void eyefishFunction_shouldReturnExpectedValue(Point argument, Point expectedValue) {
        Transformation transformation = Transformation.eyefish();

        Point actualValue = transformation.apply(argument);

        assertThat(actualValue.x())
            .isCloseTo(expectedValue.x(), Percentage.withPercentage(0.00001));
        assertThat(actualValue.y())
            .isCloseTo(expectedValue.y(), Percentage.withPercentage(0.00001));
    }

    static Arguments[] linearFunction() {
        return new Arguments[] {
            Arguments.of(new Point(0.0, 0.0),      new Point(0.0, 0.0)),
            Arguments.of(new Point(1.0, 1.0),      new Point(1.0, 1.0)),
            Arguments.of(new Point(1.5, 2.3),      new Point(1.5, 2.3)),
            Arguments.of(new Point(-3.14, 2.7),    new Point(-3.14, 2.7)),
            Arguments.of(new Point(0.0, -128.821), new Point(0, -128.821)),
            Arguments.of(new Point(-10.0, -15.0),  new Point(-10.0, -15.0))
        };

    }

    @ParameterizedTest
    @MethodSource("linearFunction")
    void linearFunction_shouldReturnExpectedValue(Point argument, Point expectedValue) {
        Transformation transformation = Transformation.linear();

        Point actualValue = transformation.apply(argument);

        assertThat(actualValue.x())
            .isCloseTo(expectedValue.x(), Percentage.withPercentage(0.00001));
        assertThat(actualValue.y())
            .isCloseTo(expectedValue.y(), Percentage.withPercentage(0.00001));
    }

    static Arguments[] heartFunction() {
        return new Arguments[] {
            Arguments.of(new Point(0.0, 0.0),      new Point(0.0, 0.0)),
            Arguments.of(new Point(1.0, 1.0),      new Point(1.2671621, -0.62793322)),
            Arguments.of(new Point(-3.14, 2.7),    new Point(-2.48681706, 3.31139561)),
        };

    }

    @ParameterizedTest
    @MethodSource("heartFunction")
    void heartFunction_shouldReturnExpectedValue(Point argument, Point expectedValue) {
        Transformation transformation = Transformation.heart();

        Point actualValue = transformation.apply(argument);

        assertThat(actualValue.x())
            .isCloseTo(expectedValue.x(), Percentage.withPercentage(0.00001));
        assertThat(actualValue.y())
            .isCloseTo(expectedValue.y(), Percentage.withPercentage(0.00001));
    }

    static Arguments[] sphericalFunction() {
        return new Arguments[] {
            Arguments.of(new Point(1.0, 1.0),      new Point(0.5, 0.5)),
            Arguments.of(new Point(1.5, 2.3),      new Point(0.198939, 0.30503978)),
            Arguments.of(new Point(-3.14, 2.7),    new Point(-0.18309464, 0.15743807)),
        };

    }

    @ParameterizedTest
    @MethodSource("sphericalFunction")
    void sphericalFunction_shouldReturnExpectedValue(Point argument, Point expectedValue) {
        Transformation transformation = Transformation.spherical();

        Point actualValue = transformation.apply(argument);

        assertThat(actualValue.x())
            .isCloseTo(expectedValue.x(), Percentage.withPercentage(0.00001));
        assertThat(actualValue.y())
            .isCloseTo(expectedValue.y(), Percentage.withPercentage(0.00001));
    }
}
