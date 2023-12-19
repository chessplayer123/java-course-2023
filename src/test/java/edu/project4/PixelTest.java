package edu.project4;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class PixelTest {
    @Test
    void mixWithUntouchedPixel_shouldReplaceColors() {
        Pixel actualPixel = Pixel.create(255, 19, 0);
        Pixel expectedPixel = Pixel.create(0, 15, 191);

        actualPixel.mix(expectedPixel.red(), expectedPixel.green(), expectedPixel.blue());

        assertThat(actualPixel)
            .extracting(Pixel::red, Pixel::green, Pixel::blue)
            .containsExactly(expectedPixel.red(), expectedPixel.green(), expectedPixel.blue());
    }

    @Test
    void mixWithTouchedPixel_shouldSetAverage() {
        Pixel srcPixel = Pixel.create(255, 19, 0);
        Pixel actualPixel = Pixel.create(0, 0, 0);
        Pixel mixPixel = Pixel.create(0, 15, 191);

        actualPixel.mix(srcPixel.red(), srcPixel.green(), srcPixel.blue());
        actualPixel.mix(mixPixel.red(), mixPixel.green(), mixPixel.blue());

        assertThat(actualPixel)
            .extracting(Pixel::red, Pixel::green, Pixel::blue)
            .containsExactly(
                (srcPixel.red() + mixPixel.red()) / 2,
                (srcPixel.green() + mixPixel.green()) / 2,
                (srcPixel.blue() + mixPixel.blue()) / 2
            );
    }

    @Test
    void pixelRGBRepresentationIsCorrect() {
        Pixel pixel = Pixel.create(0xff, 0x01, 0xab);

        int actualRGB = pixel.toRGB();
        int expectedRGB = 0xff01ab;

        assertThat(actualRGB).isEqualTo(expectedRGB);
    }

    @Test
    void hitCountIncrementedAfterMix() {
        Pixel actualPixel = Pixel.create(0, 0, 0);
        Pixel mixPixel = Pixel.create(255, 255, 255);

        actualPixel.mix(mixPixel.red(), mixPixel.green(), mixPixel.blue());

        int actualHitCount = actualPixel.getHitCount();
        int expectedHitCount = 1;

        assertThat(actualHitCount).isEqualTo(expectedHitCount);
    }
}
