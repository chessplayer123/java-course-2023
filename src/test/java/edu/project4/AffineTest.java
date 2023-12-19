package edu.project4;

import edu.project4.Transformations.Affine;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import org.junit.jupiter.api.RepeatedTest;
import java.util.Random;

public class AffineTest {
    @RepeatedTest(16)
    void affineRandomWithTargetColor_shouldNotExceedDensity() {
        Random random = new Random();
        Pixel targetColor = Pixel.create(128, 0, 255);
        int density = 10;

        Affine randomAffine = Affine.random(random, targetColor, density);

        assertThat(randomAffine.red())
            .isBetween(0, 255)
            .isCloseTo(targetColor.red(), within(density));
        assertThat(randomAffine.green())
            .isBetween(0, 255)
            .isCloseTo(targetColor.green(), within(density));
        assertThat(randomAffine.blue())
            .isBetween(0, 255)
            .isCloseTo(targetColor.blue(), within(density));
    }
}
