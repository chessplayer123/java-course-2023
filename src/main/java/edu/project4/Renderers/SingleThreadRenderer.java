package edu.project4.Renderers;

import edu.project4.FractalImage;
import edu.project4.Point;
import edu.project4.Rect;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import java.util.concurrent.ThreadLocalRandom;

public class SingleThreadRenderer implements Renderer {
    private static final int NORMALIZATION_STEPS = 20;

    public SingleThreadRenderer() {
    }

    @Override
    public void render(FractalImage image, int samples, RendererParameters params) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final Rect biunitRect = params.rect();
        final Rect imageRect = image.rect();

        for (int i = 0; i < samples; ++i) {
            Point newPoint = biunitRect.getRandomPoint(random);
            for (int step = -NORMALIZATION_STEPS; step < params.iterationsPerSample(); ++step) {
                Affine affine = params.peekAffine(random);
                Transformation nonLinear = params.peekTransformation(random);
                newPoint = affine.andThen(nonLinear).apply(newPoint);

                if (step < 0 || !biunitRect.containsPoint(newPoint)) {
                    continue;
                }

                double angle = 0.0;
                for (int s = 0; s < params.symmetry(); angle += 2 * Math.PI / params.symmetry(), ++s) {
                    Point rotated = newPoint.rotate(angle).mapFromTo(biunitRect, imageRect);
                    if (imageRect.containsPoint(rotated)) {
                        image.pixel(rotated).mix(affine.red(), affine.green(), affine.blue());
                    }
                }
            }
        }
    }
}
