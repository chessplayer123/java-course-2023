package edu.hw8;

import edu.hw8.PasswordHacker.PasswordHacker;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PasswordHackerTest {
    @Test
    void hackerShouldReturnMapWithCorrectPasswords() throws IOException, ExecutionException, InterruptedException {
        int maxPasswordLen = 4;
        int numOfThreads = 16;
        String hashes = """
            MY_MAIL@mail.ru 0b907bd06803cf206b0d661ce3bfb144
            Oth3R_M41l@gmail.com a250048142da0f6e9044abbe4943a1c0
            """;
        BufferedReader reader = new BufferedReader(new StringReader(hashes));

        PasswordHacker hacker = new PasswordHacker();

        Map<String, String> expectedPasswords = Map.of(
            "MY_MAIL@mail.ru", "P455",
            "Oth3R_M41l@gmail.com", "w0Rd"
        );
        Map<String, String> actualPasswords = hacker.hackPasswords(reader, maxPasswordLen, numOfThreads);

        assertThat(actualPasswords).isEqualTo(expectedPasswords);
    }

    @Test
    void hackDataWithWrongFormatThrowsException() {
        int maxPasswordLen = 4;
        int numOfThreads = 16;
        String hashes = """
            MY_MAIL@mail.ru 0b907bd06803cf206b0d661ce3bfb144 P455
            """;
        BufferedReader reader = new BufferedReader(new StringReader(hashes));

        PasswordHacker hacker = new PasswordHacker();

        assertThatThrownBy(() -> hacker.hackPasswords(reader, maxPasswordLen, numOfThreads))
            .isInstanceOf(IOException.class)
            .hasMessage("wrong data format");
    }

    @Test
    void multipleMailsWithSamePasswordShouldBeStoredSeparately(
    ) throws IOException, ExecutionException, InterruptedException {
        int maxPasswordLen = 4;
        int numOfThreads = 16;
        String hashes = """
            MY_MAIL@mail.ru acf45d9058a3ac06986012fed9c74204
            strong_password@gmail.com acf45d9058a3ac06986012fed9c74204
            """;
        BufferedReader reader = new BufferedReader(new StringReader(hashes));

        PasswordHacker hacker = new PasswordHacker();

        Map<String, String> expectedPasswords = Map.of(
            "MY_MAIL@mail.ru", "r4Nd",
            "strong_password@gmail.com", "r4Nd"
        );
        Map<String, String> actualPasswords = hacker.hackPasswords(reader, maxPasswordLen, numOfThreads);

        assertThat(actualPasswords).isEqualTo(expectedPasswords);
    }
}
