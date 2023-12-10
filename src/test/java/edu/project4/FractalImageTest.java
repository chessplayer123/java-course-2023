package edu.project4;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class FractalImageTest {
    @Test
    void fractalImageReturnExpectedRect() {
        int width = 1280;
        int height = 960;
        FractalImage image = FractalImage.create(width, height, Pixel.create(0, 0, 0));

        Rect actualRect = image.rect();
        Rect expectedRect = new Rect(0, 0, width, height);

        assertThat(actualRect).isEqualTo(expectedRect);
    }

    @Test
    void gettingPixelThroughPointReturnExpectedValue() {
        FractalImage image = FractalImage.create(540, 480, Pixel.create(0, 0, 0));
        int x = 103;
        int y = 421;

        image.pixel(x, y).setRGB(255, 255, 255);

        Pixel expectedPixel = Pixel.create(255, 255, 255);
        Pixel actualPixel = image.pixel(new Point(x + 0.5, y + 0.1));

        assertThat(actualPixel)
            .extracting(Pixel::red, Pixel::green, Pixel::blue)
            .containsExactly(expectedPixel.red(), expectedPixel.green(), expectedPixel.blue());
    }
}
