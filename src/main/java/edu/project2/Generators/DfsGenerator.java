package edu.project2.Generators;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/*
1. Randomly select a node (or cell) N.
2. Push the node N onto a queue Q.
3. Mark the cell N as visited.
4. Randomly select an adjacent cell A of node N that has not been visited. If all the neighbors of N have been visited:
    - Continue to pop items off the queue Q until a node is encountered with at least one non-visited neighbor -
    assign this node to N and go to step 4.
    - If no nodes exist: stop.
5. Break the wall between N and A.
6. Assign the value A to N.
7. Go to step 2.
*/
public class DfsGenerator implements Generator {
    private static final Random RANDOM = new Random();

    private Character getRandomUnvisitedDirection(Set<Coordinate> visited, Coordinate pos, int width, int height) {
        ArrayList<Character> neighbours = new ArrayList<>();
        if (pos.col() > 0 && !visited.contains(new Coordinate(pos.row(), pos.col() - 1))) {
            neighbours.add('l');
        }
        if (pos.row() > 0 && !visited.contains(new Coordinate(pos.row() - 1, pos.col()))) {
            neighbours.add('u');
        }
        if (pos.row() < height - 1 && !visited.contains(new Coordinate(pos.row() + 1, pos.col()))) {
            neighbours.add('d');
        }
        if (pos.col() < width - 1 && !visited.contains(new Coordinate(pos.row(), pos.col() + 1))) {
            neighbours.add('r');
        }

        if (neighbours.size() == 0) {
            return ' ';
        }
        return neighbours.get(RANDOM.nextInt(neighbours.size()));
    }

    @Override
    public Maze generate(int width, int height) {
        HashSet<Coordinate> visited = new HashSet<>();
        LinkedList<Coordinate> queue = new LinkedList<>();
        Maze maze = new Maze(width, height);

        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                maze.setWallRight(rw, cl);
                maze.setWallBot(rw, cl);
            }
        }

        queue.addFirst(new Coordinate(0, 0));
        while (!queue.isEmpty()) {
            Coordinate pos = queue.peekFirst();
            visited.add(pos);

            switch (getRandomUnvisitedDirection(visited, pos, width, height)) {
                case 'l' -> {
                    maze.removeWallRight(pos.row(), pos.col() - 1);
                    queue.addFirst(new Coordinate(pos.row(), pos.col() - 1));
                }
                case 'r' -> {
                    maze.removeWallRight(pos.row(), pos.col());
                    queue.addFirst(new Coordinate(pos.row(), pos.col() + 1));
                }
                case 'd' -> {
                    maze.removeWallBot(pos.row(), pos.col());
                    queue.addFirst(new Coordinate(pos.row() + 1, pos.col()));
                }
                case 'u' -> {
                    maze.removeWallBot(pos.row() - 1, pos.col());
                    queue.addFirst(new Coordinate(pos.row() - 1, pos.col()));
                }
                default -> queue.pollFirst();
            }
        }
        return maze;
    }
}
