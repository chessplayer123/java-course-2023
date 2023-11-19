package edu.project2;

public class Cell {
    public boolean hasWallRight;
    public boolean hasWallBottom;

    private Cell() {
    }

    public Cell(boolean hasWallRight, boolean hasWallBottom) {
        this.hasWallRight = hasWallRight;
        this.hasWallBottom = hasWallBottom;
    }
}
