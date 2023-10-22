package edu.project1;


public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        final ArrayDict dict = new ArrayDict();

        ConsoleHangman game = new ConsoleHangman(dict, System.in, System.out);

        game.run();
    }
}
