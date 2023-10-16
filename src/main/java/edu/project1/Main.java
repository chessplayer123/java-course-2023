package edu.project1;


import java.util.Random;


public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        final String[] dict = new String[] {"exception", "string", "software"};
        final Random rand = new Random();
        String word = dict[rand.nextInt(dict.length)];

        ConsoleHangman game = new ConsoleHangman(word, System.in, System.out);
        game.run();
    }
}
