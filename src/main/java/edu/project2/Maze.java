package edu.project2;

public class Maze {
    private final int width;
    private final int height;
    private final Cell[][] data;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;

        data = new Cell[height][width];
        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                data[rw][cl] = new Cell(cl == width - 1, rw == height - 1);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell get(int row, int col) throws ArrayIndexOutOfBoundsException {
        return data[row][col];
    }

    public Cell get(Coordinate pos) throws ArrayIndexOutOfBoundsException {
        return data[pos.row()][pos.col()];
    }

    public void setWallRight(int row, int col) throws ArrayIndexOutOfBoundsException {
        data[row][col].hasWallRight = true;
    }

    public void removeWallRight(int row, int col) throws ArrayIndexOutOfBoundsException {
        data[row][col].hasWallRight = false;
    }

    public void setWallBot(int row, int col) throws ArrayIndexOutOfBoundsException {
        data[row][col].hasWallBottom = true;
    }

    public void removeWallBot(int row, int col) throws ArrayIndexOutOfBoundsException {
        data[row][col].hasWallBottom = false;
    }
}
