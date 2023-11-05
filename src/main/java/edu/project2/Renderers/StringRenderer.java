package edu.project2.Renderers;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.List;

public class StringRenderer implements Renderer {
    private final int cellWidth;
    private final int cellHeight;

    public StringRenderer(int cellWidth, int cellHeight) {
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    private String renderRow(Maze maze, int row, char[] placeholders) {
        StringBuilder output = new StringBuilder();

        output.append(Border.vert());
        for (int cl = 0; cl < maze.getWidth(); cl++) {
            output.append(String.valueOf(placeholders[cl]).repeat(cellWidth));
            if (maze.get(row, cl).hasWallRight) {
                output.append(Border.vert());
            } else {
                output.append(' ');
            }
        }
        output.append('\n');

        return output.toString();
    }

    private String renderBottomBorder(Maze maze, int row) throws IllegalArgumentException {
        if (row < -1 || row >= maze.getHeight()) {
            throw new IllegalArgumentException("invalid row index");
        }
        StringBuilder output = new StringBuilder();

        Border leftBorder = Border.empty()
            .toRightOn(row < 0 || maze.get(row, 0).hasWallBottom)
            .toBotOn(row < maze.getHeight() - 1)
            .toUpOn(row >= 0);
        output.append(leftBorder);

        for (int cl = 0; cl < maze.getWidth(); ++cl) {
            Border cellCorner = Border.empty()
                // cell above has right wall
                .toUpOn(row >= 0 && maze.get(row, cl).hasWallRight)
                // cell below has right wall
                .toBotOn(row < maze.getHeight() - 1 && maze.get(row + 1, cl).hasWallRight)
                // cell on the right side has bottom wall (or rendering top border)
                .toRightOn(cl < maze.getWidth() - 1 && (row < 0 || maze.get(row, cl + 1).hasWallBottom));

            // render bottom wall
            if (row < 0 || maze.get(row, cl).hasWallBottom) {
                output.append(Border.hor().toString().repeat(cellWidth));
                cellCorner.toLeft();
            } else {
                output.append(" ".repeat(cellWidth));
            }
            output.append(cellCorner);
        }
        output.append('\n');
        return output.toString();
    }

    @Override
    public String render(Maze maze) {
        StringBuilder output = new StringBuilder();

        output.append(renderBottomBorder(maze, -1));

        char[] placeholders = new char[maze.getWidth()];
        for (int cl = 0; cl < maze.getWidth(); cl++) {
            placeholders[cl] = ' ';
        }

        for (int rw = 0; rw < maze.getHeight(); rw++) {
            output.append(renderRow(maze, rw, placeholders).repeat(cellHeight));
            output.append(renderBottomBorder(maze, rw));
        }

        return output.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        if (path.size() == 0) {
            return render(maze);
        }

        StringBuilder output = new StringBuilder();

        output.append(renderBottomBorder(maze, -1));

        char[][] placeholders = new char[maze.getHeight()][maze.getWidth()];
        for (int rw = 0; rw < maze.getHeight(); rw++) {
            for (int cl = 0; cl < maze.getWidth(); cl++) {
                placeholders[rw][cl] = ' ';
            }
        }
        for (int i = 1; i < path.size() - 1; ++i) {
            Coordinate cur = path.get(i);
            Coordinate next = path.get(i + 1);
            if (next.row() == cur.row() + 1) {
                placeholders[cur.row()][cur.col()] = '▽';
            } else if (next.row() == cur.row() - 1) {
                placeholders[cur.row()][cur.col()] = '△';
            } else if (next.col() == cur.col() - 1) {
                placeholders[cur.row()][cur.col()] = '◁';
            } else {
                placeholders[cur.row()][cur.col()] = '▷';
            }
        }
        placeholders[path.getFirst().row()][path.getFirst().col()] = '█';
        placeholders[path.getLast().row()][path.getLast().col()] = '█';

        for (int rw = 0; rw < maze.getHeight(); rw++) {
            output.append(renderRow(maze, rw, placeholders[rw]).repeat(cellHeight));
            output.append(renderBottomBorder(maze, rw));
        }

        return output.toString();
    }
}
