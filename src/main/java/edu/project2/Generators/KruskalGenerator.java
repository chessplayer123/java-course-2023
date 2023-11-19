package edu.project2.Generators;

import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.ArrayList;
import java.util.Collections;

/*
1. Throw all the edges in the graph into a set.
2. Pull out the random edge. If the edge connects two disjoint trees, join the trees. Otherwise, throw that edge away.
3. Repeat until there are no more edges left.
 */
public class KruskalGenerator implements Generator {
    private Coordinate getDsuParent(Coordinate[][] dsu, Coordinate pos) {
        if (dsu[pos.row()][pos.col()] == pos) {
            return pos;
        } else {
            Coordinate parent = getDsuParent(dsu, dsu[pos.row()][pos.col()]);
            dsu[pos.row()][pos.col()] = parent;
            return parent;
        }
    }

    @Override
    public Maze generate(int width, int height) {
        record Wall(Coordinate from, Coordinate to) {}

        Maze maze = new Maze(width, height);
        ArrayList<Wall> walls = new ArrayList<>(2 * width * height);
        Coordinate[][] dsu = new Coordinate[height][width];

        for (int rw = 0; rw < height; ++rw) {
            for (int cl = 0; cl < width; ++cl) {
                maze.setWallRight(rw, cl);
                maze.setWallBot(rw, cl);

                dsu[rw][cl] = new Coordinate(rw, cl);
                if (rw < height - 1) {
                    walls.add(new Wall(new Coordinate(rw, cl), new Coordinate(rw + 1, cl)));
                }
                if (cl < width - 1) {
                    walls.add(new Wall(new Coordinate(rw, cl), new Coordinate(rw, cl + 1)));
                }
            }
        }

        Collections.shuffle(walls);
        for (Wall wall: walls) {
            Coordinate fromSet = getDsuParent(dsu, wall.from);
            Coordinate toSet = getDsuParent(dsu, wall.to);
            if (fromSet != toSet) {
                if (wall.from.row() == wall.to.row() - 1) { // go down
                    maze.removeWallBot(wall.from.row(), wall.from.col());
                } else { // go right
                    maze.removeWallRight(wall.from.row(), wall.from.col());
                }
                dsu[fromSet.row()][fromSet.col()] = toSet;
            }
        }
        return maze;
    }
}
