package edu.project4;

import edu.project4.Renderers.RendererParameters;
import edu.project4.Transformations.Affine;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import edu.project4.Transformations.Transformation;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RendererParametersTest {
    @RepeatedTest(16)
    void randomPickedAffineIsPresent() {
        Random random = new Random();
        List<Affine> affineList = List.of(
            Affine.random(random),
            Affine.random(random),
            Affine.random(random),
            Affine.random(random)
        );
        RendererParameters parameters = new RendererParameters(
            10_000,
            1,
            Rect.from(-1, 1, -1, 1),
            affineList,
            List.of(Transformation.curl())
        );

        Affine randomAffine = parameters.peekAffine(random);

        assertThat(affineList).contains(randomAffine);
    }

    @RepeatedTest(16)
    void randomPickedTransformationIsPresent() {
        Random random = new Random();
        List<Transformation> transformationList = List.of(
            Transformation.curl(),
            Transformation.spherical(),
            Transformation.eyefish(),
            Transformation.heart()
        );
        RendererParameters parameters = new RendererParameters(
            10_000,
            1,
            Rect.from(-1, 1, -1, 1),
            List.of(Affine.random(random)),
            transformationList
        );

        Transformation randomTransformation = parameters.peekTransformation(random);

        assertThat(transformationList).contains(randomTransformation);
    }

    @Test
    void parametersWithEmptyListOfAffine_shouldThrowException() {
        assertThatThrownBy(() -> new RendererParameters(
            10_000, 1, Rect.from(-1, 1, -1, 1), Collections.emptyList(), List.of(Transformation.curl())
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("list of affine and nonlinear functions cannot be empty");
    }

    @Test
    void parametersWithEmptyListOfNonLinearFunctions_shouldThrowException() {
        Random random = new Random();
        assertThatThrownBy(() -> new RendererParameters(
            10_000, 1, Rect.from(-1, 1, -1, 1), List.of(Affine.random(random)), List.of()
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("list of affine and nonlinear functions cannot be empty");
    }
}
