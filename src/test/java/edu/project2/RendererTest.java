package edu.project2;

import edu.project2.Renderers.Renderer;
import edu.project2.Renderers.StringRenderer;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class RendererTest {
    @Test
    void emptyMazeRendering() {
        Renderer renderer = new StringRenderer(1, 1);
        Maze emptyMaze = new Maze(10, 10);

        String actualRender = renderer.render(emptyMaze);
        String expectedRender = """
        ┌───────────────────┐
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        │                   │
        └───────────────────┘
        """;

        assertThat(actualRender).isEqualTo(expectedRender);
    }

    @Test
    void mazeWithAllWallsRendering() {
        int width = 10;
        int height = 10;

        Renderer renderer = new StringRenderer(1, 1);
        Maze maze = new Maze(width, height);

        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                maze.get(rw, cl).hasWallRight = true;
                maze.get(rw, cl).hasWallBottom = true;
            }
        }

        String actualRender = renderer.render(maze);
        String expectedRender = """
        ┌─┬─┬─┬─┬─┬─┬─┬─┬─┬─┐
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
        │ │ │ │ │ │ │ │ │ │ │
        └─┴─┴─┴─┴─┴─┴─┴─┴─┴─┘
        """;

        assertThat(actualRender).isEqualTo(expectedRender);
    }

    @Test
    void connectedMazeRendering() {
        int width = 3;
        int height = 3;

        Renderer renderer = new StringRenderer(3, 3);
        Maze maze = new Maze(width, height);

        boolean[][] bottomWalls = new boolean[][] {
            {true, true, false},
            {false, true, true},
            {true, true, true}
        };

        boolean[][] rightWalls = new boolean[][] {
            {false, false, true},
            {false, false, true},
            {false, false, true}
        };

        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                maze.get(rw, cl).hasWallBottom = bottomWalls[rw][cl];
                maze.get(rw, cl).hasWallRight = rightWalls[rw][cl];
            }
        }

        String actualRender = renderer.render(maze);
        String expectedRender = """
        ┌───────────┐
        │           │
        │           │
        │           │
        ├───────╴   │
        │           │
        │           │
        │           │
        │   ╶───────┤
        │           │
        │           │
        │           │
        └───────────┘
        """;

        assertThat(actualRender).isEqualTo(expectedRender);
    }

    @Test
    void mazePathRendering() {
        int width = 3;
        int height = 3;

        Renderer renderer = new StringRenderer(3, 3);

        Maze maze = new Maze(width, height);
        boolean[][] bottomWalls = new boolean[][] {
            {true, true, false},
            {false, true, true},
            {true, true, true}
        };
        boolean[][] rightWalls = new boolean[][] {
            {false, false, true},
            {false, false, true},
            {false, false, true}
        };
        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                maze.get(rw, cl).hasWallBottom = bottomWalls[rw][cl];
                maze.get(rw, cl).hasWallRight = rightWalls[rw][cl];
            }
        }
        List<Coordinate> path = List.of(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(0, 2),
            new Coordinate(1, 2),
            new Coordinate(1, 1),
            new Coordinate(1, 0),
            new Coordinate(2, 0),
            new Coordinate(2, 1),
            new Coordinate(2, 2)
        );

        String actualRender = renderer.render(maze, path);
        String expectedRender = """
        ┌───────────┐
        │███ ▷▷▷ ▽▽▽│
        │███ ▷▷▷ ▽▽▽│
        │███ ▷▷▷ ▽▽▽│
        ├───────╴   │
        │▽▽▽ ◁◁◁ ◁◁◁│
        │▽▽▽ ◁◁◁ ◁◁◁│
        │▽▽▽ ◁◁◁ ◁◁◁│
        │   ╶───────┤
        │▷▷▷ ▷▷▷ ███│
        │▷▷▷ ▷▷▷ ███│
        │▷▷▷ ▷▷▷ ███│
        └───────────┘
        """;

        assertThat(actualRender).isEqualTo(expectedRender);
    }

    @Test
    void emptyPathRendering() {
        int width = 3;
        int height = 3;

        Renderer renderer = new StringRenderer(3, 3);

        Maze maze = new Maze(width, height);
        boolean[][] bottomWalls = new boolean[][] {
            {true, true, false},
            {false, true, true},
            {true, true, true}
        };
        boolean[][] rightWalls = new boolean[][] {
            {false, false, true},
            {false, false, true},
            {false, false, true}
        };
        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                maze.get(rw, cl).hasWallBottom = bottomWalls[rw][cl];
                maze.get(rw, cl).hasWallRight = rightWalls[rw][cl];
            }
        }
        List<Coordinate> path = List.of();

        String actualRender = renderer.render(maze, path);
        String expectedRender = renderer.render(maze);

        assertThat(actualRender).isEqualTo(expectedRender);
    }

}
