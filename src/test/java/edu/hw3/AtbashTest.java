package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class AtbashTest {
    @Test
    void capitalLetterEncoding() {
        AtbashCipher encoder = new AtbashCipher();

        String src = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String actualEncoding = encoder.atbash(src);
        String expectedEncoding = "ZYXWVUTSRQPONMLKJIHGFEDCBA";

        assertThat(actualEncoding).isEqualTo(expectedEncoding);
    }

    @Test
    void lowercaseLetterEncoding() {
        AtbashCipher encoder = new AtbashCipher();

        String src = "abcdefghijklmnopqrstuvwxyz";

        String actualEncoding = encoder.atbash(src);
        String expectedEncoding = "zyxwvutsrqponmlkjihgfedcba";

        assertThat(actualEncoding).isEqualTo(expectedEncoding);
    }

    @Test
    void immutabilityOfNonLatinChars() {
        AtbashCipher encoder = new AtbashCipher();

        String src = "1234567890!-=/.,_\\|~`";

        String actualEncoding = encoder.atbash(src);

        assertThat(actualEncoding).isEqualTo(src);
    }

    @ParameterizedTest
    @CsvSource({
        "Hello world!, Svool dliow!",
        "Th1s is H0w AtB4Sh C1ph3r W0rk5, Gs1h rh S0d ZgY4Hs X1ks3i D0ip5"
    })
    void stringWithLatinAndNonLatinChars(String src, String expectedEncoding) {
        AtbashCipher encoder = new AtbashCipher();

        String actualEncoding = encoder.atbash(src);

        assertThat(actualEncoding).isEqualTo(expectedEncoding);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Hello world!",
        "Th1s is H0w AtB4Sh C1ph3r W0rk5",
        "?.,AaPpLlEe35"
    })
    void reEncodedStringEqualsSourceString(String src) {
        AtbashCipher encoder = new AtbashCipher();

        String encoded = encoder.atbash(src);
        String reEncodedString = encoder.atbash(encoded);

        assertThat(reEncodedString).isEqualTo(src);
    }
}
