package edu.project2;

import edu.project2.Generators.DfsGenerator;
import edu.project2.Generators.EllerGenerator;
import edu.project2.Generators.KruskalGenerator;
import edu.project2.Renderers.Renderer;
import edu.project2.Renderers.StringRenderer;
import edu.project2.Solvers.DfsSolver;
import edu.project2.Solvers.Solver;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.IntStream;

@SuppressWarnings({"RegexpSingleLineJava", "MagicNumber"})
public class Main {
    private Main() {

    }

    public static void main(String[] args) {
        int width = 15;
        int height = 7;

        Scanner input = new Scanner(System.in);

        System.out.print("""
        Choose generator:
        1. Kruskal's algorithm.
        2. Eller's algorithm.
        3. Dfs.
        """);

        Maze maze = null;
        while (maze == null) {
            System.out.print("> ");
            try {
                int ans = input.nextInt();
                maze = switch (ans) {
                    case 1 -> new KruskalGenerator().generate(width, height);
                    case 2 -> new EllerGenerator().generate(width, height);
                    case 3 -> new DfsGenerator().generate(width, height);
                    default -> {
                        System.out.println("[ERROR] Wrong option.");
                        yield null;
                    }
                };
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.println("[ERROR] Wrong input format.");
            } catch (NoSuchElementException e) {
                System.out.println("Abort.");
                return;
            }
        }

        Renderer renderer = new StringRenderer(2, 1);
        Solver solver = new DfsSolver();

        List<Coordinate> path = solver.solve(maze, new Coordinate(0, 0), new Coordinate(height - 1, width - 1));

        String[] mazeRender = renderer.render(maze).split("\n");
        String[] pathRender = renderer.render(maze, path).split("\n");

        String layout = String.join(
            "\n",
            IntStream.range(0, mazeRender.length)
                .mapToObj(i -> mazeRender[i] + " ".repeat(5) + pathRender[i])
                .toList()
        );
        System.out.printf("\n%s", layout);
    }
}
