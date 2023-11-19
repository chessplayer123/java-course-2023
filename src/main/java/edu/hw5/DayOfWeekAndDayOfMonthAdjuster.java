package edu.hw5;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public record DayOfWeekAndDayOfMonthAdjuster(DayOfWeek dayOfWeek, int dayOfMonth) implements TemporalAdjuster {
    private boolean isExpectedDate(Temporal temporal) {
        return temporal.get(ChronoField.DAY_OF_MONTH) == dayOfMonth
            && temporal.get(ChronoField.DAY_OF_WEEK) == dayOfWeek.getValue();
    }

    @Override
    public Temporal adjustInto(Temporal start) {
        Temporal current = start;

        while (!isExpectedDate(current)) {
            current = current.with(TemporalAdjusters.next(dayOfWeek));
        }

        return current;
    }
}
