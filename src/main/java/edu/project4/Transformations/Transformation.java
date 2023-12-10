package edu.project4.Transformations;

import edu.project4.Point;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    static Transformation swirl() {
        return point -> {
            double rSqr = Math.pow(point.x(), 2) + Math.pow(point.y(), 2);
            return new Point(
                point.x() * Math.sin(rSqr) - point.y() * Math.cos(rSqr),
                point.x() * Math.cos(rSqr) + point.y() * Math.sin(rSqr)
            );
        };
    }

    static Transformation curl() {
        return point -> {
            double xSqr = Math.pow(point.x(), 2);
            double ySqr = Math.pow(point.y(), 2);
            double t1 = 1 + point.x() + xSqr - ySqr;
            double t2 = point.y() + 2 * point.x() * point.y();
            double mul = 1 / (Math.pow(t1, 2) + Math.pow(t2, 2));

            return new Point(mul * (point.x() * t1 + point.y() * t2), mul * (point.y() * t1 - point.x() * t2));
        };
    }

    static Transformation eyefish() {
        return point -> {
            double mul = 2.0 / (Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2)) + 1);
            return new Point(mul * point.x(), mul * point.y());
        };
    }

    static Transformation linear() {
        return point -> point;
    }

    static Transformation heart() {
        return point -> {
            double theta = Math.atan2(point.y(), point.x());
            double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));

            return new Point(r * Math.sin(theta * r), -r * Math.cos(theta * r));
        };
    }

    static Transformation spherical() {
        return point -> {
            double rSqr = Math.pow(point.x(), 2) + Math.pow(point.y(), 2);
            return new Point(point.x() / rSqr, point.y() / rSqr);
        };
    }
}
