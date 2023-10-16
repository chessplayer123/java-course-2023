package edu.project1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import edu.project1.ConsoleHangman.GameStatus;

public class ConsoleHangmanTest {
    private static final OutputStream dummyStream = new OutputStream() {
        @Override
        public void write(int i) {
        }
    };

    @Test
    void testCorrectWordGuess() {
        String word = "test";
        ByteArrayInputStream input = new ByteArrayInputStream("t\ne\ns\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(word, input, dummyStream);
        game.run();

        GameStatus actualResult = game.getGameStatus();
        GameStatus expectedResult = GameStatus.Win;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testExceedingNumOfAttempts() {
        String word = "test";
        ByteArrayInputStream input = new ByteArrayInputStream("a\nb\nc\nd\nf\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(word, input, dummyStream);
        game.run();

        GameStatus actualResult = game.getGameStatus();
        GameStatus expectedResult = GameStatus.Defeat;
        int actualMistakesNum = game.getMistakes();
        int expectedMistakesNum = 5;

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedMistakesNum, actualMistakesNum);
    }

    @Test
    void testCorrectWordGuessWithMistakes() {
        String word = "test";
        ByteArrayInputStream input = new ByteArrayInputStream("t\nb\nc\nd\ne\ns\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(word, input, dummyStream);
        game.run();

        GameStatus actualResult = game.getGameStatus();
        GameStatus expectedResult = GameStatus.Win;
        int actualMistakesNum = game.getMistakes();
        int expectedMistakesNum = 3;

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedMistakesNum, actualMistakesNum);
    }

    @Test
    void testCorrectWordGuessWithRepeats() {
        String word = "test";
        ByteArrayInputStream input = new ByteArrayInputStream("t\nt\nt\nt\ne\ns\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(word, input, dummyStream);
        game.run();

        GameStatus actualResult = game.getGameStatus();
        GameStatus expectedResult = GameStatus.Win;
        int actualMistakesNum = game.getMistakes();
        int expectedMistakesNum = 0;

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedMistakesNum, actualMistakesNum);
    }

    @Test
    void testCorrectWordGuessWithWrongFormat() {
        String word = "test";
        ByteArrayInputStream input = new ByteArrayInputStream("t\ntoo long\n\n\ne\ns".getBytes());

        ConsoleHangman game = new ConsoleHangman(word, input, dummyStream);
        game.run();

        GameStatus actualResult = game.getGameStatus();
        GameStatus expectedResult = GameStatus.Win;
        int actualMistakesNum = game.getMistakes();
        int expectedMistakesNum = 0;

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedMistakesNum, actualMistakesNum);
    }

    @Test
    void testEndOfStream() {
        String word = "word";
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());

        ConsoleHangman game = new ConsoleHangman(word, input, dummyStream);
        game.run();

        GameStatus actualResult = game.getGameStatus();
        GameStatus expectedResult = GameStatus.Defeat;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testInitWithoutRunCall() {
        String word = "word";
        ByteArrayInputStream input = new ByteArrayInputStream("w\no\nr\nd\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(word, input, dummyStream);

        GameStatus actualStatus = game.getGameStatus();
        GameStatus expectedStatus = GameStatus.NotStarted;

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void testIncorrectWordLength() {
        String emptyString = "";
        ByteArrayInputStream input = new ByteArrayInputStream("w\no\nr\nd\n".getBytes());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new ConsoleHangman(emptyString, input, dummyStream);
        });

        String expectedMessage = "Incorrect word length (expected > 0)";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
