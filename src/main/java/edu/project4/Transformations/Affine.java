package edu.project4.Transformations;

import edu.project4.Pixel;
import edu.project4.Point;
import java.util.Random;

public record Affine(
    double a, double b, double c,
    double d, double e, double f,
    int red, int green, int blue
) implements Transformation {
    private static final int COLOR_BOUND = 256;

    public static Affine random(Random random) {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            c = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);
            f = random.nextDouble(-1, 1);
        } while (!isAffineCoefficients(a, b, c, d, e, f));

        int red = random.nextInt(COLOR_BOUND);
        int green = random.nextInt(COLOR_BOUND);
        int blue = random.nextInt(COLOR_BOUND);

        return new Affine(a, b, c, d, e, f, red, green, blue);
    }

    public static Affine random(Random random, Pixel targetColor, int density) {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            c = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);
            f = random.nextDouble(-1, 1);
        } while (!isAffineCoefficients(a, b, c, d, e, f));

        int red = random.nextInt(
            Math.max(targetColor.red() - density, 0),
            Math.min(targetColor.red() + density, COLOR_BOUND)
        );
        int green = random.nextInt(
            Math.max(targetColor.green() - density, 0),
            Math.min(targetColor.green() + density, COLOR_BOUND)
        );
        int blue = random.nextInt(
            Math.max(targetColor.blue() - density, 0),
            Math.min(targetColor.blue() + density, COLOR_BOUND)
        );

        return new Affine(a, b, c, d, e, f, red, green, blue);
    }

    @Override
    public Point apply(Point point) {
        return new Point(
            a * point.x() + b * point.y() + c,
            d * point.x() + e * point.y() + f
        );
    }

    private static boolean isAffineCoefficients(double a, double b, double c, double d, double e, double f) {
        return Math.pow(a, 2) + Math.pow(d, 2) < 1
            && Math.pow(b, 2) + Math.pow(e, 2) < 1
            && Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(b, 2) + Math.pow(e, 2) < 1 + Math.pow(a * e + b * d, 2);
    }
}
