package edu.project4;

import java.util.random.RandomGenerator;

public record Rect(double x, double y, double width, double height) {
    public static Rect from(double left, double right, double top, double bottom) {
        return new Rect(left, top, right - left, bottom - top);
    }

    public Point getRandomPoint(RandomGenerator random) {
        return new Point(random.nextDouble(x, right()), random.nextDouble(y, bottom()));
    }

    public boolean containsPoint(Point point) {
        return x <= point.x() && point.x() < right()
            && y <= point.y() && point.y() < bottom();
    }

    public double right() {
        return x + width;
    }

    public double bottom() {
        return y + height;
    }
}
