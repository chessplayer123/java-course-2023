package edu.project1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.project1.HiddenWord.GuessResult;

public class HiddenWordTest {
    @Test
    void testDefaultRepresentation() {
        String word = "word";
        HiddenWord hiddenWord = new HiddenWord(word);

        String actualWord = hiddenWord.getWord();
        String actualGuess = hiddenWord.getGuess();
        String expectedGuess = "****";

        assertEquals(word, actualWord);
        assertEquals(expectedGuess, actualGuess);
    }

    @Test
    void testOneCharCorrectGuess() {
        HiddenWord hiddenWord = new HiddenWord("word");

        char guess = 'o';

        GuessResult actualResult = hiddenWord.makeGuess(guess);
        GuessResult expectedResult = GuessResult.GuessedChar;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testOneCharIncorrectGuess() {
        HiddenWord hiddenWord = new HiddenWord("word");

        char guess = 't';

        GuessResult actualResult = hiddenWord.makeGuess(guess);
        GuessResult expectedResult = GuessResult.WrongGuess;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testHiddenWordChangeOnCorrectGuess() {
        HiddenWord hiddenWord = new HiddenWord("word");

        char charGuess = 'd';

        hiddenWord.makeGuess(charGuess);
        String actualWordGuess = hiddenWord.getGuess();
        String expectedWordGuess = "***d";

        assertEquals(expectedWordGuess, actualWordGuess);
    }

    @Test
    void testHiddenWordChangeOnIncorrectGuess() {
        HiddenWord hiddenWord = new HiddenWord("word");

        char charGuess = 'l';

        hiddenWord.makeGuess(charGuess);
        String actualWordGuess = hiddenWord.getGuess();
        String expectedWordGuess = "****";

        assertEquals(expectedWordGuess, actualWordGuess);
    }

    @Test
    void testRepeatedGuess() {
        HiddenWord hiddenWord = new HiddenWord("word");

        char charGuess = 'd';

        hiddenWord.makeGuess(charGuess);
        GuessResult actualResult = hiddenWord.makeGuess(charGuess);
        GuessResult expectedResult = GuessResult.RepeatedGuess;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testEntireWordGuess() {
        HiddenWord word = new HiddenWord("we");

        char charW = 'w', charE = 'e';

        word.makeGuess(charW);
        GuessResult actualResult = word.makeGuess(charE);
        GuessResult expectedResult = word.makeGuess(charE);

        assertEquals(expectedResult, actualResult);
    }
}
