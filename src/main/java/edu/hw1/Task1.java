package edu.hw1;



public class Task1 {
    private static final int SECONDS_IN_MINUTE = 60;

    public Task1() {
    }

    public int minutesToSeconds(String timeString) {
        if (!timeString.matches("\\d+:\\d+")) {
            return -1;
        }

        String[] parts = timeString.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);

        if (seconds >= SECONDS_IN_MINUTE) {
            return -1;
        }
        return minutes * SECONDS_IN_MINUTE + seconds;
    }
}
