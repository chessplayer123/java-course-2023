package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final Pattern REQUIRED_CHARS = Pattern.compile("[~!@#$%^&*|]");

    public PasswordValidator() {
    }

    public Boolean validatePassword(String password) {
        Matcher matcher = REQUIRED_CHARS.matcher(password);
        return matcher.find();
    }
}
