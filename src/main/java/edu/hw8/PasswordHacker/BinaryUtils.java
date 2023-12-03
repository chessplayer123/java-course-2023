package edu.hw8.PasswordHacker;

public final class BinaryUtils {
    private static final int LOWER_BYTE_BITMASK = 0xFF;

    private BinaryUtils() {
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();

        for (byte hexPair : bytes) {
            String digit = Integer.toHexString(LOWER_BYTE_BITMASK & hexPair);
            if (digit.length() < 2) {
                hex.append("0");
            }
            hex.append(digit);
        }

        return hex.toString();
    }

    public static String convertToRadix(long source, String alphabet) {
        StringBuilder password = new StringBuilder();

        long number = source;
        do {
            password.append(alphabet.charAt((int) number % alphabet.length()));
            number /= alphabet.length();
        } while (number > 0);

        return password.toString();
    }
}
