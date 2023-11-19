package edu.project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LogParser {
    private static final Pattern LOG_PATTERN = Pattern.compile(
        "(?<remoteAddress>.+) - (?<remoteUser>.+) \\[(?<timeLocal>.+)] "
      + "\"(?<requestType>.+) (?<endpoint>.+) (?<protocol>.+)\" (?<status>\\d+) (?<bodyBytesSent>\\d+) "
      + "\"(?<httpReferer>.+)\" \"(?<httpUserAgent>.+)\""
    );
    private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
        .appendPattern("dd/MMM/yyyy:HH:mm:ss Z")
        .toFormatter(Locale.ENGLISH);

    private LogParser() {
    }

    public static Stream<LogRecord> parse(
        InputStream stream,
        OffsetDateTime from,
        OffsetDateTime to
    ) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Stream.Builder<LogRecord> builder = Stream.builder();

        String line;
        int lineNum = 0;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = LOG_PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new ParseException("Log record have incorrect format", lineNum);
            }
            OffsetDateTime date = OffsetDateTime.from(DATE_FORMAT.parse(matcher.group("timeLocal")));
            if (date.isAfter(from) && date.isBefore(to)) {
                builder.add(new LogRecord(
                    matcher.group("remoteAddress"),
                    matcher.group("remoteUser"),
                    date,
                    matcher.group("requestType"),
                    matcher.group("endpoint"),
                    matcher.group("protocol"),
                    Integer.parseInt(matcher.group("status")),
                    Integer.parseInt(matcher.group("bodyBytesSent")),
                    matcher.group("httpReferer"),
                    matcher.group("httpUserAgent")
                ));
            }
            ++lineNum;
        }

        return builder.build();
    }
}
