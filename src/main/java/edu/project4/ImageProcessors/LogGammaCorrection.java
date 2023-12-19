package edu.project4.ImageProcessors;

import edu.project4.FractalImage;

public class LogGammaCorrection implements ImageProcessor {
    private final double gamma;

    private LogGammaCorrection(double gamma) {
        this.gamma = gamma;
    }

    public static LogGammaCorrection withGamma(double gamma) {
        return new LogGammaCorrection(gamma);
    }

    @Override
    public void process(FractalImage image) {
        double maxNormal = 0.0;
        for (int y = 0; y < image.height(); ++y) {
            for (int x = 0; x < image.width(); ++x) {
                if (image.pixel(x, y).getHitCount() != 0) {
                    maxNormal = Math.max(maxNormal, Math.log10(image.pixel(x, y).getHitCount()));
                }
            }
        }

        for (int y = 0; y < image.height(); ++y) {
            for (int x = 0; x < image.width(); ++x) {
                double mul = Math.pow(Math.log10(image.pixel(x, y).getHitCount()) / maxNormal, 1 / gamma);
                image.pixel(x, y).setRGB(
                    (int) (image.pixel(x, y).red() * mul),
                    (int) (image.pixel(x, y).green() * mul),
                    (int) (image.pixel(x, y).blue() * mul)
                );
            }
        }
    }
}
