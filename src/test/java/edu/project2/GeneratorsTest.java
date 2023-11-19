package edu.project2;

import edu.project2.Generators.DfsGenerator;
import edu.project2.Generators.EllerGenerator;
import edu.project2.Generators.Generator;
import edu.project2.Generators.KruskalGenerator;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.RepeatedTest;
import java.util.ArrayDeque;
import java.util.HashSet;

public class GeneratorsTest {
    static boolean isFullyConnected(Maze maze) {
        HashSet<Coordinate> visited = new HashSet<>();
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();

        queue.addFirst(new Coordinate(0, 0));

        while (!queue.isEmpty()) {
            Coordinate cur = queue.pollFirst();
            if (visited.contains(cur)) {
                continue;
            }
            visited.add(cur);

            if (!maze.get(cur).hasWallBottom) {
                queue.addLast(new Coordinate(cur.row() + 1, cur.col()));
            }
            if (!maze.get(cur).hasWallRight) {
                queue.addLast(new Coordinate(cur.row(), cur.col() + 1));
            }
            if (cur.row() > 0 && !maze.get(cur.row() - 1, cur.col()).hasWallBottom) {
                queue.addLast(new Coordinate(cur.row() - 1, cur.col()));
            }
            if (cur.col() > 0 && !maze.get(cur.row(), cur.col() - 1).hasWallRight) {
                queue.addLast(new Coordinate(cur.row(), cur.col() - 1));
            }
        }

        return visited.size() == maze.getWidth() * maze.getHeight();
    }

    @RepeatedTest(10)
    void connectivityOfMazeByDfsGenerator() {
        int width = 12;
        int height = 17;

        Generator generator = new DfsGenerator();

        Maze maze = generator.generate(width, height);
        boolean isConnected = isFullyConnected(maze);

        assertThat(isConnected).isTrue();
    }

    @RepeatedTest(10)
    void connectivityOfMazeByKruskalGenerator() {
        int width = 11;
        int height = 7;
        Generator generator = new KruskalGenerator();

        Maze maze = generator.generate(width, height);
        boolean isConnected = isFullyConnected(maze);

        assertThat(isConnected).isTrue();
    }

    @RepeatedTest(10)
    void connectivityOfMazeByEllerGenerator() {
        int width = 7;
        int height = 7;
        Generator generator = new EllerGenerator();

        Maze maze = generator.generate(width, height);
        boolean isConnected = isFullyConnected(maze);

        assertThat(isConnected).isTrue();
    }
}
