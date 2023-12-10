package edu.project4.Renderers;

import edu.project4.FractalImage;
import edu.project4.ImageProcessors.ImageProcessor;
import edu.project4.Pixel;
import edu.project4.Rect;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RendererWrapper {
    private static final int DEFAULT_ITERATIONS_PER_SAMPLE = 10;

    private int iterationsPerSample = DEFAULT_ITERATIONS_PER_SAMPLE;
    private int symmetry = 1;
    private Rect biunitRect = Rect.from(-1, 1, -1, 1);
    private List<Transformation> nonLinearTransforms = Collections.emptyList();
    private List<Affine> affineList = Collections.emptyList();
    private final List<ImageProcessor> imageProcessors = new ArrayList<>();
    private RandomAffineInfo randomAffine = new RandomAffineInfo(0, 0, new Pixel[] {});
    private final Renderer renderer;

    private RendererWrapper(Renderer renderer) {
        this.renderer = renderer;
    }

    public static RendererWrapper wrap(Renderer renderer) {
        return new RendererWrapper(renderer);
    }

    public RendererWrapper setTransformations(Transformation... transformations) {
        nonLinearTransforms = List.of(transformations);
        return this;
    }

    public RendererWrapper setAffine(Affine... affines) {
        affineList = List.of(affines);
        return this;
    }

    public RendererWrapper setRandomAffine(int count) {
        randomAffine = new RandomAffineInfo(count, 0, new Pixel[] {});
        return this;
    }

    public RendererWrapper setRandomAffine(int count, int density, Pixel... targetColors) {
        randomAffine = new RandomAffineInfo(count, density, targetColors);
        return this;
    }

    public RendererWrapper setRect(Rect biunitRect) {
        this.biunitRect = biunitRect;
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

        List<Affine> affineWithRandom = new ArrayList<>(List.copyOf(affineList));
        if (randomAffine.targetColors().length == 0) {
            for (int i = 0; i < randomAffine.count; ++i) {
                affineWithRandom.add(Affine.random(random));
            }
        } else {
            for (int i = 0; i < randomAffine.count; ++i) {
                affineWithRandom.add(Affine.random(
                    random,
                    randomAffine.targetColors[random.nextInt(randomAffine.targetColors.length)],
                    randomAffine.density
                ));
            }
        }

        return new RendererParameters(iterationsPerSample, symmetry, biunitRect, affineWithRandom, nonLinearTransforms);
    }

    public void render(FractalImage image, int samples, long seed) {
        RendererParameters params = buildParameters(seed);
        renderer.render(image, samples, params);
        imageProcessors.forEach(processor -> processor.process(image));
    }

    private record RandomAffineInfo(int count, int density, Pixel[] targetColors) {
    }
}
