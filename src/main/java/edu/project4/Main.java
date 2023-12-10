package edu.project4;

import edu.project4.ImageProcessors.LogGammaCorrection;
import edu.project4.Renderers.MultiThreadRenderer;
import edu.project4.Renderers.RendererWrapper;
import edu.project4.Transformations.Transformation;
import java.io.IOException;
import java.nio.file.Path;

public final class Main {
    private Main() {
    }

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) throws IOException {
        FractalImage image = FractalImage.create(1920, 1080, Pixel.create(0, 0, 0));

        RendererWrapper.wrap(new MultiThreadRenderer(16))
            .setSymmetry(1)
            .setRect(Rect.from(-1.777, 1.777, -1, 1))
            .setIterationsPerSample(1000)
            .addProcessor(LogGammaCorrection.withGamma(2.2))
            .setTransformations(Transformation.curl(), Transformation.eyefish(), Transformation.spherical())
            .setRandomAffine(
                20, 5,
                Pixel.create(255, 221, 226), Pixel.create(250, 160, 148),
                Pixel.create(158, 217, 204), Pixel.create(0, 140, 118)
            )
            .render(image, 50_000, System.currentTimeMillis());

        ImageUtils.save(Path.of("src", "main", "resources", "fractals/fractal.jpg"), image);
    }
}
