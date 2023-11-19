package edu.project2.Generators;

import edu.project2.Maze;
import java.util.Random;

/*
1. Initialize the cells of the first row to each exist in their own set.
2. Now, randomly join adjacent cells, but only if they are not in the same set.
   When joining adjacent cells, merge the cells of both sets into a single set,
   indicating that all cells in both sets are now connected (there is a path that connects any two cells in the set).
3. For each set, randomly create vertical connections downward to the next row.
   Each remaining set must have at least one vertical connection.
   The cells in the next row thus connected must share the set of the cell above them.
4. Flesh out the next row by putting any remaining cells into their own sets.
5. Repeat until the last row is reached.
6. For the last row, join all adjacent cells that do not share a set,
    and omit the vertical connections, and you’re done!
 */
public class EllerGenerator implements Generator {
    @Override
    public Maze generate(int width, int height) {
        Random random = new Random();

        Maze maze = new Maze(width, height);
        int[] row = new int[width];
        int counter = 1;
        // step 1
        for (int i = 0; i < width; ++i) {
            row[i] = counter++;
        }

        for (int rw = 0; rw < height; ++rw) {
            int len = 1;
            for (int cl = 0; cl < width; ++cl) {
                maze.setWallBot(rw, cl); // step 3
                boolean placeWallRight = (cl == width - 1 || row[cl + 1] == row[cl] || random.nextBoolean());
                // step 2
                if (placeWallRight) {
                    maze.setWallRight(rw, cl);
                    int idx = random.nextInt(len);
                    // open path to next row and classify to another set
                    if (rw < maze.getHeight() - 1) {
                        maze.removeWallBot(rw, cl - idx);
                    }
                    row[cl - idx] = counter++;
                    len = 1;
                } else {
                    row[cl + 1] = row[cl];
                    len++;
                }
            }
        }

        // step 6
        for (int cl = 0; cl < width - 1; ++cl) {
            if (row[cl + 1] != row[cl]) {
                maze.removeWallRight(height - 1, cl);
            }
        }
        return maze;
    }
}
