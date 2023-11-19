package edu.hw5.DateParserChain;

import java.time.LocalDate;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public abstract class DateParserChain {
    private DateParserChain nextParser;

    public DateParserChain setNextChain(DateParserChain nextParser) {
        this.nextParser = nextParser;
        return nextParser;
    }

    protected Optional<LocalDate> parseByNextChain(@NotNull String date) {
        if (nextParser != null) {
            return nextParser.parse(date);
        }
        return Optional.empty();
    }

    public abstract Optional<LocalDate> parse(@NotNull String date);
}
