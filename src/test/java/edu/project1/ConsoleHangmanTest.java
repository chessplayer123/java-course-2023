package edu.project1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import edu.project1.ConsoleHangman.GameStatus;

public class ConsoleHangmanTest {
    private static final OutputStream dummyStream = new OutputStream() {
        private final StringBuilder message = new StringBuilder();

        @Override
        public void write(int i) {
            message.append((char)i);
        }

        @Override
        public String toString() {
            return message.toString();
        }
    };

    @Test
    void testCorrectWordGuess() {
        ArrayDict dict = new ArrayDict("test");
        ByteArrayInputStream input = new ByteArrayInputStream("t\ne\ns\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);
        game.run();

        GameStatus actualResult = game.getGameStatus();
        GameStatus expectedResult = GameStatus.Win;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testExceedingNumOfAttempts() {
        ArrayDict dict = new ArrayDict("test");
        ByteArrayInputStream input = new ByteArrayInputStream("a\nb\nc\nd\nf\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);
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
        ArrayDict dict = new ArrayDict("test");
        ByteArrayInputStream input = new ByteArrayInputStream("t\nb\nc\nd\ne\ns\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);
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
        ArrayDict dict = new ArrayDict("test");
        ByteArrayInputStream input = new ByteArrayInputStream("t\nt\nt\nt\ne\ns\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);
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
        ArrayDict dict = new ArrayDict("test");
        ByteArrayInputStream input = new ByteArrayInputStream("t\ntoo long\n\n\ne\ns".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);
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
        ArrayDict dict = new ArrayDict("word");
        ByteArrayInputStream input = new ByteArrayInputStream("".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);
        game.run();

        GameStatus actualResult = game.getGameStatus();
        GameStatus expectedResult = GameStatus.Defeat;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testInitWithoutRunCall() {
        ArrayDict dict = new ArrayDict("word");
        ByteArrayInputStream input = new ByteArrayInputStream("w\no\nr\nd\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);

        GameStatus actualStatus = game.getGameStatus();
        GameStatus expectedStatus = GameStatus.NotStarted;

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void testIncorrectWordLength() {
        ArrayDict dict = new ArrayDict("");
        ByteArrayInputStream input = new ByteArrayInputStream("w\no\nr\nd\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);
        game.run();

        GameStatus actualStatus = game.getGameStatus();
        GameStatus expectedStatus = GameStatus.NotStarted;

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void testExceptionOnEmptyDictionary() {
        Exception exception = assertThrows(IllegalArgumentException.class, ArrayDict::new);

        String expectedMessage = "Expected at least one word in dict";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testProgramOutput() {
        ArrayDict dict = new ArrayDict("word");
        ByteArrayInputStream input = new ByteArrayInputStream("w\no\nr\nd\n".getBytes());

        ConsoleHangman game = new ConsoleHangman(dict, input, dummyStream);
        game.run();

        String expectedOutput = """
Hangman game:
    You are given a word of length 4
    You can make 5 mistakes
    Press <CTRL-D> to give up
< Attempts: [0/5]
< ****
> < Correct guess.
< Attempts: [0/5]
< w***
> < Correct guess.
< Attempts: [0/5]
< wo**
> < Correct guess.
< Attempts: [0/5]
< wor*
> < Correct guess. You have guessed the word.
Congratulations. You have won. Word: 'word'.""";
        String actualOutput = dummyStream.toString();

        assertEquals(expectedOutput, actualOutput);
    }
}
