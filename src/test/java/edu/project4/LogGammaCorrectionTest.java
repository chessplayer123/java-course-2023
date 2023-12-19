package edu.project4;

import edu.project4.ImageProcessors.ImageProcessor;
import edu.project4.ImageProcessors.LogGammaCorrection;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class LogGammaCorrectionTest {
    @Test
    void gammaCorrectionReturnExpectedValues() {
        FractalImage actualImage = new FractalImage(3, 3, new Pixel[][] {
            {new Pixel(0, 0, 0, 0), new Pixel(255, 255, 255, 10), new Pixel(0, 0, 0, 0)},
            {new Pixel(255, 255, 255, 10), new Pixel(0, 0, 0, 0), new Pixel(255, 255, 255, 10)},
            {new Pixel(0, 0, 0, 0), new Pixel(255, 255, 255, 10), new Pixel(0, 0, 0, 0)}
        });

        FractalImage expectedImage = new FractalImage(3, 3, new Pixel[][] {
            {new Pixel(0, 0, 0, 0), new Pixel(255, 255, 255, 10), new Pixel(0, 0, 0, 0)},
            {new Pixel(255, 255, 255, 10), new Pixel(0, 0, 0, 0), new Pixel(255, 255, 255, 10)},
            {new Pixel(0, 0, 0, 0), new Pixel(255, 255, 255, 10), new Pixel(0, 0, 0, 0)}
        });

        ImageProcessor processor = LogGammaCorrection.withGamma(2.2);
        processor.process(actualImage);

        assertThat(actualImage.data()).isEqualTo(expectedImage.data());
    }
}
