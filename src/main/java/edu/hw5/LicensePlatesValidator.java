package edu.hw5;

public class LicensePlatesValidator {
    public LicensePlatesValidator() {
    }

    public Boolean validatePlate(String plate) {
        return plate.matches("[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}");
    }
}
