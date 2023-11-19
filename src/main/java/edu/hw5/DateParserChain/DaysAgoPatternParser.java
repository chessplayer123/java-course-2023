package edu.hw5.DateParserChain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class DaysAgoPatternParser extends DateParserChain {
    private static final Pattern DAYS_AGO_PATTERN = Pattern.compile("(\\d+) days ago");

    @Override
    public Optional<LocalDate> parse(@NotNull String date) {
        Matcher m = DAYS_AGO_PATTERN.matcher(date);
        if (m.matches()) {
            int daysAgo = Integer.parseInt(m.group(1));
            return Optional.of(LocalDate.now().minusDays(daysAgo));
        }
        return parseByNextChain(date);
    }
}
