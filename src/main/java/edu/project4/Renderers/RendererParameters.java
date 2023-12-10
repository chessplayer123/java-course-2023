package edu.project4.Renderers;

import edu.project4.Rect;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import java.util.List;
import java.util.random.RandomGenerator;

public class RendererParameters {
    private final int iterationsPerSample;
    private final int symmetry;
    private final Rect rect;
    private final List<Affine> affineTransforms;
    private final List<Transformation> nonLinearTransforms;

    public RendererParameters(
        int iterationsPerSample,
        int symmetry,
        Rect rect,
        List<Affine> affineTransforms,
        List<Transformation> nonLinearTransform
    ) throws IllegalArgumentException {
        if (affineTransforms.isEmpty() || nonLinearTransform.isEmpty()) {
            throw new IllegalArgumentException("list of affine and nonlinear functions cannot be empty");
        }

        this.iterationsPerSample = iterationsPerSample;
        this.symmetry = symmetry;
        this.rect = rect;
        this.affineTransforms = affineTransforms;
        this.nonLinearTransforms = nonLinearTransform;
    }

    public Rect rect() {
        return rect;
    }

    public int iterationsPerSample() {
        return iterationsPerSample;
    }

    public int symmetry() {
        return symmetry;
    }

    public Transformation peekTransformation(RandomGenerator random) {
        return nonLinearTransforms.get(random.nextInt(nonLinearTransforms.size()));
    }

    public Affine peekAffine(RandomGenerator random) {
        return affineTransforms.get(random.nextInt(affineTransforms.size()));
    }

    @Override
    public int hashCode() {
        return symmetry
            + iterationsPerSample
            + affineTransforms.hashCode()
            + nonLinearTransforms.hashCode()
            + rect.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o.getClass() != RendererParameters.class) {
            return false;
        }
        RendererParameters other = (RendererParameters) o;

        return symmetry == other.symmetry
            && iterationsPerSample == other.iterationsPerSample
            && affineTransforms.equals(other.affineTransforms)
            && nonLinearTransforms.equals(other.nonLinearTransforms)
            && rect.equals(other.rect);
    }
}
