package edu.project4;

public record FractalImage(int width, int height, Pixel[][] data) {
    public static FractalImage create(int width, int height, Pixel background) {
        Pixel[][] emptyImage = new Pixel[height][width];
        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                emptyImage[rw][cl] = Pixel.create(background.red(), background.green(), background.blue());
            }
        }
        return new FractalImage(width, height, emptyImage);
    }

    public Pixel pixel(int x, int y) {
        return data[y][x];
    }

    public Pixel pixel(Point point) {
        return data[(int) point.y()][(int) point.x()];
    }

    public Rect rect() {
        return new Rect(0, 0, width, height);
    }
}
