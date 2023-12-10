package edu.project4.Renderers;

import edu.project4.Rect;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import java.util.List;
import java.util.random.RandomGenerator;

public record RendererParameters(
    int iterationsPerSample,
    int symmetry,
    Rect rect,
    List<Affine> affineTransforms,
    List<Transformation> nonLinearTransforms
) {
    public Transformation peekTransformation(RandomGenerator random) {
        return nonLinearTransforms.get(random.nextInt(nonLinearTransforms.size()));
    }

    public Affine peekAffine(RandomGenerator random) {
        return affineTransforms.get(random.nextInt(affineTransforms.size()));
    }
}
