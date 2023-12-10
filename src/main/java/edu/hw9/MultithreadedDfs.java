package edu.hw9;

import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.Solvers.Solver;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MultithreadedDfs implements Solver {
    private Maze maze;
    private AtomicReferenceArray<Coordinate> prevPathNode;

    int getArrayIndex(int row, int col) {
        return row * maze.getWidth() + col;
    }

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        prevPathNode = new AtomicReferenceArray<>(maze.getHeight() * maze.getWidth());
        prevPathNode.set(getArrayIndex(end.row(), end.col()), end);

        try (ForkJoinPool pool = new ForkJoinPool()) {
            pool.invoke(new Subtask(end));
        }

        List<Coordinate> path = new ArrayList<>();

        Coordinate last = start;
        while (true) {
            path.add(last);
            if (prevPathNode.get(getArrayIndex(last.row(), last.col())) == last) {
                break;
            }
            last = prevPathNode.get(getArrayIndex(last.row(), last.col()));
        }

        return path;
    }

    private class Subtask extends RecursiveTask<Void> {
        private final Coordinate coordinate;

        private Subtask(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        private List<Coordinate> markAndGetUnvisitedNeighbours(Coordinate pos) {
            List<Coordinate> neighbours = new ArrayList<>();

            // Left
            if (pos.col() > 0 && !maze.get(pos.row(), pos.col() - 1).hasWallRight
                && prevPathNode.compareAndSet(getArrayIndex(pos.row(), pos.col() - 1), null, pos)
            ) {
                neighbours.add(new Coordinate(pos.row(), pos.col() - 1));
            }

            // Top
            if (pos.row() > 0 && !maze.get(pos.row() - 1, pos.col()).hasWallBottom
                && prevPathNode.compareAndSet(getArrayIndex(pos.row() - 1, pos.col()), null, pos)
            ) {
                neighbours.add(new Coordinate(pos.row() - 1, pos.col()));
            }

            // Right
            if (!maze.get(pos).hasWallRight
                && prevPathNode.compareAndSet(getArrayIndex(pos.row(), pos.col() + 1), null, pos)
            ) {
                neighbours.add(new Coordinate(pos.row(), pos.col() + 1));
            }

            // Bottom
            if (!maze.get(pos).hasWallBottom
                && prevPathNode.compareAndSet(getArrayIndex(pos.row() + 1, pos.col()), null, pos)
            ) {
                neighbours.add(new Coordinate(pos.row() + 1, pos.col()));
            }

            return neighbours;
        }

        @Override
        protected Void compute() {
            Deque<Coordinate> stack = new ArrayDeque<>();
            stack.push(coordinate);

            while (!stack.isEmpty()) {
                Coordinate current = stack.pop();
                List<Coordinate> neighbours = markAndGetUnvisitedNeighbours(current);
                if (neighbours.isEmpty()) {
                    continue;
                }

                stack.push(neighbours.getFirst());
                for (int i = 1; i < neighbours.size(); ++i) {
                    new Subtask(neighbours.get(i)).fork();
                }
            }

            return null;
        }
    }
}
