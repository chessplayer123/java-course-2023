package edu.project4;

public record Point(double x, double y) {
    public Point rotate(double angle) {
        return new Point(
            Math.cos(angle) * x - Math.sin(angle) * y,
            Math.sin(angle) * x + Math.cos(angle) * y
        );
    }

    public Point mapFromTo(Rect src, Rect dst) {
        return new Point(
            dst.right() - (src.right() - x) / src.width() * dst.width(),
            dst.bottom() - (src.bottom() - y) / src.height() * dst.height()
        );
    }
}
