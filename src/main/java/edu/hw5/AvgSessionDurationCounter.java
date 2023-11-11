package edu.hw5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class AvgSessionDurationCounter {
    public AvgSessionDurationCounter() {
    }

    public Duration getAverageSessionDuration(List<String> sessions) throws IllegalArgumentException {
        if (sessions.isEmpty()) {
            return Duration.ofMillis(0);
        }

        SimpleDateFormat sessionFormat = new SimpleDateFormat("yyyy-MM-dd, hh:mm");

        long sessionsSum = 0;
        for (String session: sessions) {
            String[] range = session.split(" - ");
            if (range.length != 2) {
                throw new IllegalArgumentException("wrong session format");
            }

            try {
                Date start = sessionFormat.parse(range[0]);
                Date end = sessionFormat.parse(range[1]);
                sessionsSum += end.getTime() - start.getTime();
            } catch (ParseException e) {
                throw new IllegalArgumentException("wrong date format");
            }
        }
        return Duration.ofMillis(sessionsSum / sessions.size());
    }
}
