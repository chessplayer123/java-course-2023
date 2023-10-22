package edu.hw2;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task2Test {
    static Arguments[] rectangles() {
        return new Arguments[]{
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    void rectangleArea(Rectangle rect) {
        rect = rect.setWidth(20);
        rect = rect.setHeight(10);

        assertThat(rect.area()).isEqualTo(200.0);
    }

    @Test
    void squareHeightChangeReturnRectangle() {
        Rectangle square = new Square();
        Rectangle rect = square.setHeight(10);

        assertThat(rect).isInstanceOf(Rectangle.class);
    }

    @Test
    void squareWidthChangeReturnRectangle() {
        Rectangle square = new Square();
        Rectangle rect = square.setWidth(10);

        assertThat(rect).isInstanceOf(Rectangle.class);
    }

    @Test
    void squareSizeChangeReturnSquare() {
        Square square = new Square();
        Rectangle rect = square.setSide(10);

        assertThat(rect).isInstanceOf(Square.class);
    }

    @Test
    void squareSizeChange_returnRectangleWithValidSize() {
        int width = 17;
        int height = 6;
        int squareSide = 10;

        Square square = new Square(squareSide);

        Rectangle rect = square.setHeight(height);
        rect = rect.setWidth(width);

        assertThat(rect).extracting("width", "height")
                        .containsExactly(width, height);
    }

    @Test
    void squareConstructorFromSideFillWidthAndHeight() {
        int side = 11;
        Square square = new Square(side);

        assertThat(square).extracting("width", "height")
                          .containsExactly(side, side);
    }

    @Test
    void setSideReturnSquareWithValidSize() {
        int side = 11;
        Square square = new Square();

        square = square.setSide(side);

        assertThat(square).extracting("width", "height")
                          .containsExactly(side, side);
    }

    @Test
    void testSquareAreaCorrectness() {
        int squareSide = 7;
        Square square = new Square(squareSide);

        double actualArea = square.area();
        double expectedArea = squareSide * squareSide;

        assertThat(actualArea).isEqualTo(expectedArea);
    }

    @Test
    void settingNegativeWidthThrowsException() {
        Rectangle rect = new Rectangle();

        assertThatThrownBy(() -> {
            rect.setWidth(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Sides expected to be >= 0");
    }

    @Test
    void settingNegativeHeightThrowsException() {
        Rectangle rect = new Rectangle();

        assertThatThrownBy(() -> {
            rect.setHeight(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Sides expected to be >= 0");
    }

    @Test
    void settingNegativeSideThrowsException() {
        Square square = new Square();

        assertThatThrownBy(() -> {
            square.setSide(-1);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Sides expected to be >= 0");
    }

    @Test
    void constructorWithInvalidArgumentsThrowException() {
        int width = -1;
        int height = 10;

        assertThatThrownBy(() -> {
            new Rectangle(width, height);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Sides expected to be >= 0");
    }
}
