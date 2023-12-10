package edu.project4;

import edu.project4.Renderers.RendererParameters;
import edu.project4.Renderers.RendererWrapper;
import edu.project4.Renderers.SingleThreadRenderer;
import static org.assertj.core.api.Assertions.assertThat;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;

public class RendererWrapperTest {
    @Test
    void wrapperProduceExpectedParameters() {
        int seed = 6;
        Random random = new Random(seed);
        int symmetry = 3;
        int iterationsPerSample = 10_000;
        Rect rect = Rect.from(-1.777, 1.777, -1, 1);
        List<Affine> affineList = List.of(Affine.random(random), Affine.random(random));
        Transformation[] transformations = new Transformation[] {Transformation.curl(), Transformation.swirl()};

        RendererParameters actualParameters = RendererWrapper.wrap(new SingleThreadRenderer())
            .setSymmetry(symmetry)
            .setIterationsPerSample(iterationsPerSample)
            .setRect(rect)
            .setRandomAffine(affineList.size())
            .setTransformations(transformations)
            .buildParameters(seed);

        RendererParameters expectedParameters = new RendererParameters(
            iterationsPerSample,
            symmetry,
            rect,
            affineList,
            List.of(transformations)
        );

        assertThat(actualParameters).isEqualTo(expectedParameters);
    }
}
