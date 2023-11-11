package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task7 {
    public Task7() {
    }

    public Boolean containsAtLeastThreeCharsAndThirdIsZero(String string) {
        Matcher matcher = Pattern.compile("[01]{2}0[01]*").matcher(string);
        return matcher.matches();
    }

    public Boolean startsAndEndsWithSameChar(String string) {
        Matcher matcher = Pattern.compile("([01])([01]*\\1)?").matcher(string);
        return matcher.matches();
    }

    public Boolean hasLengthAtLeastOneAtMostThree(String string) {
        Matcher matcher = Pattern.compile("[01]{1,3}").matcher(string);
        return matcher.matches();
    }
}
