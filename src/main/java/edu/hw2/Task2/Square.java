package edu.hw2.Task2;

public class Square extends Rectangle {
    public Square() {
    }

    public Square(int side) throws IllegalArgumentException {
        super(side, side);
    }

    public Square setSide(int side) throws IllegalArgumentException {
        super.setWidth(side);
        super.setHeight(side);
        return this;
    }

    @Override
    public Rectangle setWidth(int width) throws IllegalArgumentException {
        return new Rectangle(width, getHeight());
    }

    @Override
    public Rectangle setHeight(int height) throws IllegalArgumentException {
        return new Rectangle(getWidth(), height);
    }
}
