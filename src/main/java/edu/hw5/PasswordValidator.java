package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    public PasswordValidator() {
    }

    public Boolean validatePassword(String password) {
        Matcher matcher = Pattern.compile("[~!@#$%^&*|]").matcher(password);
        return matcher.find();
    }
}
