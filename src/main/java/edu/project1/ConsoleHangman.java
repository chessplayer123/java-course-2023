package edu.project1;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class ConsoleHangman {
    enum GameStatus {
        NotStarted,
        InProgress,
        Win,
        Defeat,
    }

    private final int maxMistakes = 5;
    private HiddenWord hiddenWord;
    private final Dictionary dict;
    private final Scanner scanner;
    private final PrintStream printStream;
    private GameStatus gameStatus;
    private int mistakes;

    public ConsoleHangman(
        Dictionary dict,
        InputStream inputStream,
        OutputStream outputStream
    ) {
        scanner = new Scanner(inputStream);
        printStream = new PrintStream(outputStream);
        this.dict = dict;

        mistakes = 0;
        gameStatus = GameStatus.NotStarted;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getMistakes() {
        return mistakes;
    }

    private char readGuessedChar() throws InvalidInputException, EndOfStreamException {
        printStream.print("> ");
        try {
            String userInput;
            userInput = scanner.nextLine();
            if (userInput.length() != 1) {
                throw new InvalidInputException();
            }
            return userInput.charAt(0);
        } catch (NoSuchElementException e) {
            throw new EndOfStreamException();
        }
    }

    private void processChar(char guess) {
        switch (hiddenWord.makeGuess(guess)) {
            case WrongGuess -> {
                printStream.printf("< Wrong guess. There's no char '%c' in word.\n", guess);
                ++mistakes;
                if (mistakes >= maxMistakes) {
                    gameStatus = GameStatus.Defeat;
                }
            }
            case GuessedChar -> printStream.println("< Correct guess.");
            case RepeatedGuess -> printStream.printf("< Letter '%c' was already guessed.\n", guess);
            case GuessedWord -> {
                printStream.println("< Correct guess. You have guessed the word.");
                gameStatus = GameStatus.Win;
            }
            default -> {
                // Unreachable
            }
        }
    }

    private void printHeader() {
        printStream.printf(
            """
            Hangman game:
                You are given a word of length %d
                You can make %d mistakes
                Press <CTRL-D> to give up
            """,
            hiddenWord.getGuess().length(), maxMistakes
        );
    }

    public void run() {
        gameStatus = GameStatus.NotStarted;

        String word = dict.getRandomWord();
        if (word.length() == 0) {
            return;
        }

        hiddenWord = new HiddenWord(word);
        printHeader();
        mistakes = 0;
        gameStatus = GameStatus.InProgress;

        while (gameStatus == GameStatus.InProgress) {
            printStream.printf("< Attempts: [%d/%d]\n< %s\n", mistakes, maxMistakes, hiddenWord.getGuess());

            try {
                char guess = readGuessedChar();
                processChar(guess);
            } catch (InvalidInputException e) {
                printStream.println("< Wrong input.");
            } catch (EndOfStreamException e) {
                printStream.println("< Resign.");
                gameStatus = GameStatus.Defeat;
                break;
            }
        }

        if (gameStatus == GameStatus.Win) {
            printStream.printf("Congratulations. You have won. Word: '%s'.", hiddenWord.getWord());
        } else {
            printStream.printf("You have lost. Word: '%s'.", hiddenWord.getWord());
        }
    }

    static class InvalidInputException extends RuntimeException {
    }

    static class EndOfStreamException extends RuntimeException {
    }
}
