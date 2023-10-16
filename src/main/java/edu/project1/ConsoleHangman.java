package edu.project1;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;


public class ConsoleHangman {
    enum GameStatus {
        NotStarted,
        InProgress,
        Win,
        Defeat,
    }

    private final int maxMistakes = 5;
    private final HiddenWord hiddenWord;
    private final Scanner scanner;
    private final PrintStream printStream;
    private GameStatus gameStatus;
    private int mistakes;

    public ConsoleHangman(
        @NotNull String word,
        InputStream inputStream,
        OutputStream outputStream
    ) throws IllegalArgumentException {
        if (word.length() == 0) {
            throw new IllegalArgumentException("Incorrect word length (expected > 0)");
        }
        gameStatus = GameStatus.NotStarted;
        scanner = new Scanner(inputStream);
        printStream = new PrintStream(outputStream);
        hiddenWord = new HiddenWord(word);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getMistakes() {
        return mistakes;
    }

    private char readGuessedChar() {
        printStream.print("> ");
        try {
            String userInput;
            userInput = scanner.nextLine();
            if (userInput.length() != 1) {
                printStream.println("Wrong input.");
                return '\r';
            }
            return userInput.charAt(0);
        } catch (NoSuchElementException e) {
            printStream.println("Resign.");
            gameStatus = GameStatus.Defeat;
            return '\r';
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
                You are given with the %d character string
                You can make %d mistakes
                Press <CTRL-D> to surrender
            """,
            hiddenWord.getGuess().length(), maxMistakes
        );
    }

    public void run() {
        printHeader();

        mistakes = 0;
        gameStatus = GameStatus.InProgress;
        while (gameStatus == GameStatus.InProgress) {
            printStream.printf("< Attempts: [%d/%d]\n< %s\n", mistakes, maxMistakes, hiddenWord.getGuess());

            char guess = readGuessedChar();
            if (guess == '\r') {
                continue;
            }
            processChar(guess);
        }

        if (gameStatus == GameStatus.Win) {
            printStream.printf("Congratulations. You have won. The word was: '%s'.", hiddenWord.getWord());
        } else {
            printStream.printf("You have lost. The word was: '%s'.", hiddenWord.getWord());
        }
    }
}
