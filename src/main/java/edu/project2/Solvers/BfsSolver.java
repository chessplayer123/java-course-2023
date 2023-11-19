package edu.project2.Solvers;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BfsSolver implements Solver {
    private Coordinate[][] getBfsTraverse(Maze maze, Coordinate start) {
        record Pair(Coordinate to, Coordinate prev) {}

        Coordinate[][] traversal = new Coordinate[maze.getHeight()][maze.getWidth()];
        HashSet<Coordinate> visited = new HashSet<>();
        ArrayDeque<Pair> queue = new ArrayDeque<>();

        queue.addLast(new Pair(start, null));

        while (!queue.isEmpty()) {
            Pair pos = queue.pollFirst();
            if (visited.contains(pos.to)) {
                continue;
            }
            visited.add(pos.to);
            traversal[pos.to.row()][pos.to.col()] = pos.prev;

            if (pos.to.col() > 0 && !maze.get(pos.to.row(), pos.to.col() - 1).hasWallRight) {
                queue.addLast(new Pair(new Coordinate(pos.to.row(), pos.to.col() - 1), pos.to));
            }
            if (pos.to.row() > 0 && !maze.get(pos.to.row() - 1, pos.to.col()).hasWallBottom) {
                queue.addLast(new Pair(new Coordinate(pos.to.row() - 1, pos.to.col()), pos.to));
            }
            if (!maze.get(pos.to.row(), pos.to.col()).hasWallRight) {
                queue.addLast(new Pair(new Coordinate(pos.to.row(), pos.to.col() + 1), pos.to));
            }
            if (!maze.get(pos.to.row(), pos.to.col()).hasWallBottom) {
                queue.addLast(new Pair(new Coordinate(pos.to.row() + 1, pos.to.col()), pos.to));
            }
        }

        return traversal;
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        Coordinate[][] traversal = getBfsTraverse(maze, end);
        List<Coordinate> path = new LinkedList<>();
        Coordinate current = start;

        while (current != null) {
            path.add(current);
            current = traversal[current.row()][current.col()];
        }

        return (path.getLast() == end ? path : null);
    }
}
