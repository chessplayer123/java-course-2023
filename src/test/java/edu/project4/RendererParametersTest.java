package edu.project4;

import edu.project4.Renderers.RendererParameters;
import edu.project4.Transformations.Affine;
import static org.assertj.core.api.Assertions.assertThat;
import edu.project4.Transformations.Transformation;
import org.junit.jupiter.api.RepeatedTest;
import java.util.List;
import java.util.Random;

public class RendererParametersTest {
    @RepeatedTest(16)
    void randomPickedAffineIsPresent() {
        Random random = new Random();
        RendererParameters parameters = new RendererParameters(
            10_000,
            1,
            Rect.from(-1, 1, -1, 1),
            List.of(
                Affine.random(random),
                Affine.random(random),
                Affine.random(random),
                Affine.random(random)
            ),
            List.of()
        );

        Affine randomAffine = parameters.peekAffine(random);

        assertThat(parameters.affineTransforms()).contains(randomAffine);
    }

    @RepeatedTest(16)
    void randomPickedTransformationIsPresent() {
        Random random = new Random();
        RendererParameters parameters = new RendererParameters(
            10_000,
            1,
            Rect.from(-1, 1, -1, 1),
            List.of(Affine.random(random)),
            List.of(
                Transformation.curl(),
                Transformation.spherical(),
                Transformation.eyefish(),
                Transformation.heart()
            )
        );

        Transformation randomTransformation = parameters.peekTransformation(random);

        assertThat(parameters.nonLinearTransforms()).contains(randomTransformation);
    }
}
