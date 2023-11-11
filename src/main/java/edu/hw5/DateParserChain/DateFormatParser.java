package edu.hw5.DateParserChain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public final class DateFormatParser extends DateParserChain {
    private final String dateFormat;

    public DateFormatParser(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Optional<LocalDate> parse(@NotNull String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
            return Optional.of(parsedDate);
        } catch (DateTimeParseException e) {
            return parseByNextChain(date);
        }
    }
}
