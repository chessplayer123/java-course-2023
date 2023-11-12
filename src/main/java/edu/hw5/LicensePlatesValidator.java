package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LicensePlatesValidator {
    private static final Pattern RU_LICENSE_PLATE_PATTERN =
        Pattern.compile("[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}");

    public LicensePlatesValidator() {
    }

    public Boolean validatePlate(String plate) {
        Matcher matcher = RU_LICENSE_PLATE_PATTERN.matcher(plate);
        return matcher.matches();
    }
}
