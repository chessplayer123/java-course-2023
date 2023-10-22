package edu.hw2.Task2;


public class Rectangle {
    private int width;
    private int height;
    private static final String ERROR_MESSAGE = "Sides expected to be >= 0";

    public Rectangle() {
        width = 0;
        height = 0;
    }

    public Rectangle(int width, int height) throws IllegalArgumentException {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }

        this.width = width;
        this.height = height;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public Rectangle setWidth(int width) throws IllegalArgumentException {
        if (width < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        this.width = width;
        return this;
    }

    public Rectangle setHeight(int height) throws IllegalArgumentException {
        if (height < 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        this.height = height;
        return this;
    }

    public final double area() {
        return width * height;
    }
}
