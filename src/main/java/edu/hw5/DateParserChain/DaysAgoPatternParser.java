package edu.hw5.DateParserChain;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public final class DaysAgoPatternParser extends DateParserChain {
    @Override
    public Optional<LocalDate> parse(@NotNull String date) {
        Pattern pattern = Pattern.compile("(\\d+) day(s)? ago");
        Matcher m = pattern.matcher(date);
        if (m.matches()) {
            return Optional.of(LocalDate.now().plusDays(-Integer.parseInt(m.group(1))));
        }
        return parseByNextChain(date);
    }
}
