package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MagicNumber")
public class Friday13thFinder {
    public Friday13thFinder() {
    }

    public List<LocalDate> findAllFriday13th(int year) throws IllegalArgumentException {
        if (year <= 0) {
            throw new IllegalArgumentException("year must be positive");
        }

        List<LocalDate> fridays = new ArrayList<>();

        LocalDate curDate = LocalDate.of(year, Month.JANUARY, 13);
        while (curDate.getYear() == year) {
            if (curDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridays.add(curDate);
            }
            curDate = curDate.plusMonths(1);
        }

        return fridays;
    }

    public LocalDate findNextFriday13th(LocalDate date) {
        return date.with(new DayOfWeekAndDayOfMonthAdjuster(DayOfWeek.FRIDAY, 13));
    }
}
