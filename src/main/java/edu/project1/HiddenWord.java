package edu.project1;


public class HiddenWord {
    enum GuessResult {
        GuessedChar,
        GuessedWord,
        WrongGuess,
        RepeatedGuess,
    }

    private final String word;
    private final char[] guess;
    private int guessedCount;

    HiddenWord(String word) {
        this.word = word;
        guess = new char[word.length()];
        guessedCount = 0;
        for (int i = 0; i < word.length(); ++i) {
            guess[i] = '*';
        }
    }

    public GuessResult makeGuess(char ch) {
        GuessResult status = GuessResult.WrongGuess;
        for (int i = 0; i < word.length(); ++i) {
            if (word.charAt(i) == ch && guess[i] == '*') {
                guess[i] = word.charAt(i);
                ++guessedCount;
                status = GuessResult.GuessedChar;
            } else if (guess[i] == ch) {
                // Already guessed char
                status = GuessResult.RepeatedGuess;
                break;
            }
        }
        if (guessedCount == word.length()) {
            return GuessResult.GuessedWord;
        }
        return status;
    }

    public String getGuess() {
        return String.valueOf(guess);
    }

    public String getWord() {
        return word;
    }
}
