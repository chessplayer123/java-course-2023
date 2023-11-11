package edu.hw5.DateParserChain;

import java.time.LocalDate;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public final class RelativeDateFormatParser extends DateParserChain {
    private final String pattern;
    private final int daysDelta;

    public RelativeDateFormatParser(@NotNull String pattern, int daysDelta) {
        this.pattern = pattern;
        this.daysDelta = daysDelta;
    }

    @Override
    public Optional<LocalDate> parse(@NotNull String date) {
        if (date.equals(pattern)) {
            return Optional.of(LocalDate.now().plusDays(daysDelta));
        }
        return parseByNextChain(date);
    }
}
