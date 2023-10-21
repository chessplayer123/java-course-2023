package edu.project1;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class ArrayDict implements Dictionary {
    String[] words;

    public ArrayDict(@NotNull String... words) throws IllegalArgumentException {
        if (words.length == 0) {
            throw new IllegalArgumentException("Expected at least one word in dict");
        }
        this.words = words;
    }

    public @NotNull String getRandomWord() {
        final Random rand = new Random();
        return words[rand.nextInt(words.length)];
    }
}
