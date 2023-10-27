package edu.hw3;

public class RomanNumeralConverter {
    enum RomanDigit {
        M(1000),
        CM(900),
        D(500),
        CD(400),
        C(100),
        XC(90),
        L(50),
        XL(40),
        X(10),
        IX(9),
        V(5),
        IV(4),
        I(1);

        private int value;

        RomanDigit(int value) {
            this.value = value;
        }
    }

    public RomanNumeralConverter() {
    }

    public String convertToRoman(int num) {
        StringBuilder romanNum = new StringBuilder();
        int numCopy = num;
        for (RomanDigit digit: RomanDigit.values()) {
            while (numCopy >= digit.value) {
                romanNum.append(digit);
                numCopy -= digit.value;
            }
        }
        return romanNum.toString();
    }
}
