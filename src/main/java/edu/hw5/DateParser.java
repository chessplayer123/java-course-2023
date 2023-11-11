package edu.hw5;

import edu.hw5.DateParserChain.DateFormatParser;
import edu.hw5.DateParserChain.DateParserChain;
import edu.hw5.DateParserChain.DaysAgoPatternParser;
import edu.hw5.DateParserChain.RelativeDateFormatParser;
import java.time.LocalDate;
import java.util.Optional;

public class DateParser {
    private final DateParserChain parserChain;

    public DateParser() {
        parserChain = new RelativeDateFormatParser("today", +0);
        parserChain
            .setNextChain(new RelativeDateFormatParser("tomorrow", +1))
            .setNextChain(new RelativeDateFormatParser("yesterday", -1))
            .setNextChain(new DateFormatParser("yyyy-MM-dd"))
            .setNextChain(new DateFormatParser("yyyy-M-d"))
            .setNextChain(new DateFormatParser("d/M/yyyy"))
            .setNextChain(new DateFormatParser("d/M/yy"))
            .setNextChain(new DaysAgoPatternParser());
    }

    public Optional<LocalDate> parseDate(String date) {
        return parserChain.parse(date);
    }
}
