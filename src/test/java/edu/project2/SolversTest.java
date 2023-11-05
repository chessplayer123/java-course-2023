package edu.project2;

import edu.project2.Solvers.BfsSolver;
import edu.project2.Solvers.DfsSolver;
import edu.project2.Solvers.Solver;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;

public class SolversTest {
    static Arguments[] solvers() {
        return new Arguments[] {
            Arguments.of(new DfsSolver()),
            Arguments.of(new BfsSolver())
        };
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void returnCorrectPath(Solver solver) {
        int width = 3;
        int height = 3;

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

        List<Coordinate> expectedPath = List.of(
            new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2),
            new Coordinate(1, 2), new Coordinate(1, 1), new Coordinate(1, 0),
            new Coordinate(2, 0), new Coordinate(2, 1), new Coordinate(2, 2)
        );
        List<Coordinate> actualPath = solver.solve(
            maze, new Coordinate(0, 0), new Coordinate(2, 2)
        );

        assertThat(actualPath).isEqualTo(expectedPath);
    }

    @ParameterizedTest
    @MethodSource("solvers")
    void unreachableEndPoint(Solver solver) {
        int width = 16;
        int height = 10;

        Maze mazeWithAllWalls = new Maze(width, height);
        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                mazeWithAllWalls.setWallRight(rw, cl);
                mazeWithAllWalls.setWallBot(rw, cl);
            }
        }

        List<Coordinate> actualPath = solver.solve(
            mazeWithAllWalls, new Coordinate(0, 0), new Coordinate(height - 1, width - 1)
        );

        assertThat(actualPath).isNull();
    }
}
