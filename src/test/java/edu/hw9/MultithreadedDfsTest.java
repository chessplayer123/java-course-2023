package edu.hw9;

import edu.project2.Coordinate;
import edu.project2.Generators.DfsGenerator;
import edu.project2.Generators.Generator;
import edu.project2.Maze;
import edu.project2.Solvers.DfsSolver;
import edu.project2.Solvers.Solver;
import org.junit.jupiter.api.RepeatedTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class MultithreadedDfsTest {
    @RepeatedTest(16)
    void multiThreadedDfs_shouldProduceSamePathAsSingleThreadedAlgorithm() {
        Generator generator = new DfsGenerator();
        Solver singleThreadSolver = new DfsSolver();
        Solver multiThreadSolver = new MultithreadedDfs();

        int width = 397;
        int height = 212;
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(height - 1, width - 1);
        Maze maze = generator.generate(width, height);

        List<Coordinate> expectedPath = singleThreadSolver.solve(maze, start, end);
        List<Coordinate> actualPath = multiThreadSolver.solve(maze, start, end);

        assertThat(actualPath).isEqualTo(expectedPath);
    }

    @RepeatedTest(16)
    void multiThreadedDfs_shouldBeAbleToSolveMazeWithLoop() {
        Maze emptyMaze = new Maze(2, 2);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(1, 1);

        Solver solver = new MultithreadedDfs();

        List<Coordinate> actualPath = solver.solve(emptyMaze, start, end);
        List<List<Coordinate>> expectedPaths = List.of(
            List.of(new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 1)),
            List.of(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1))
        );

        assertThat(actualPath).isIn(expectedPaths);
    }
}
