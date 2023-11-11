package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {
        "~!@#$%^&*|",
        "password!p455w0rd*",
        "V4L!D",
        "P@55w0RD",
        "!",
        "~",
        "!",
        "@",
        "#",
        "$",
        "%",
        "^",
        "&",
        "*",
        "|"
    })
    void validPassword(String password) {
        PasswordValidator validator = new PasswordValidator();

        Boolean actualResult = validator.validatePassword(password);

        assertThat(actualResult).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "1Nv4L1Dp455W0rD",
        "0123456789",
        "abcdefghjklmnopqrstuvwxyz",
        "ABCDEFGHJKLMNOPQRSTUVWXYZ",
        "-_/?+(){}",
    })
    void invalidPassword(String password) {
        PasswordValidator validator = new PasswordValidator();

        Boolean actualResult = validator.validatePassword(password);

        assertThat(actualResult).isFalse();
    }
}
