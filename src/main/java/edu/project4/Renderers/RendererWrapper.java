package edu.project4.Renderers;

import edu.project4.FractalImage;
import edu.project4.ImageProcessors.ImageProcessor;
import edu.project4.Pixel;
import edu.project4.Rect;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RendererWrapper {
    private static final int DEFAULT_ITERATIONS_PER_SAMPLE = 10;
    private static final int AFFINE_TRANSFORMATIONS_COUNT = 25;

    private int iterationsPerSample = DEFAULT_ITERATIONS_PER_SAMPLE;
    private int symmetry = 1;

    private final List<ImageProcessor> imageProcessors = new ArrayList<>();
    private final List<Transformation> nonLinearTransforms = new ArrayList<>();
    private final List<Pixel> targetColors = new ArrayList<>();
    private final Renderer renderer;
    private Rect biunitRect = Rect.from(-1, 1, -1, 1);

    private RendererWrapper(Renderer renderer) {
        this.renderer = renderer;
    }

    public static RendererWrapper wrap(Renderer renderer) {
        return new RendererWrapper(renderer);
    }

    public RendererWrapper setTransformations(Transformation... transformations) {
        this.nonLinearTransforms.addAll(List.of(transformations));
        return this;
    }

    public RendererWrapper setRect(Rect biunitRect) {
        this.biunitRect = biunitRect;
        return this;
    }

    public RendererWrapper setTargetColors(Pixel... pixels) {
        targetColors.addAll(List.of(pixels));
        return this;
    }

    public RendererWrapper setIterationsPerSample(int iterationsPerSample) {
        this.iterationsPerSample = iterationsPerSample;
        return this;
    }

    public RendererWrapper setSymmetry(int symmetry) {
        this.symmetry = symmetry;
        return this;
    }

    public RendererWrapper addProcessor(ImageProcessor processor) {
        imageProcessors.add(processor);
        return this;
    }

    public RendererParameters buildParameters(long seed) {
        Random random = new Random(seed);

        List<Affine> affineTransforms = new ArrayList<>(AFFINE_TRANSFORMATIONS_COUNT);
        if (targetColors.isEmpty()) {
            for (int i = 0; i < AFFINE_TRANSFORMATIONS_COUNT; ++i) {
                affineTransforms.add(Affine.random(random));
            }
        } else {
            for (int i = 0; i < AFFINE_TRANSFORMATIONS_COUNT; ++i) {
                affineTransforms.add(Affine.random(random, targetColors.get(random.nextInt(targetColors.size()))));
            }
        }

        return new RendererParameters(iterationsPerSample, symmetry, biunitRect, affineTransforms, nonLinearTransforms);
    }

    public void render(FractalImage image, int samples, long seed) {
        RendererParameters params = buildParameters(seed);
        renderer.render(image, samples, params);
        imageProcessors.forEach(processor -> processor.process(image));
    }
}
