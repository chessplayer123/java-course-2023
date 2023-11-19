package edu.project3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

public class ParserTest {
    public static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
        .appendPattern("dd/MMM/yyyy:HH:mm:ss Z")
        .toFormatter(Locale.ENGLISH);

    @Test
    void incorrectLogFormatThrowsException() {
        InputStream logs = new ByteArrayInputStream(
            "127.0.0.1 - - [19/May/2015:19:05:38 +0000] \"GET /1 HTTP\" 0 0 \"\" \"\"".getBytes()
        );

        OffsetDateTime from = OffsetDateTime.MIN;
        OffsetDateTime to = OffsetDateTime.MAX;

        assertThatThrownBy(() -> LogParser.parse(logs, from, to))
            .isInstanceOf(ParseException.class)
            .hasMessage("Log record have incorrect format");
    }

    @Test
    void validLogIsParsedIntoLogRecord() throws IOException, ParseException {
        InputStream logs = new ByteArrayInputStream(
            "address - - [19/May/2015:19:05:38 +0000] \"GET /1 HTTP\" 0 0 \"referer\" \"agent\"".getBytes()
        );

        OffsetDateTime from = OffsetDateTime.MIN;
        OffsetDateTime to = OffsetDateTime.MAX;

        List<LogRecord> actualRecords = LogParser.parse(logs, from, to).toList();
        List<LogRecord> expectedRecords = List.of(
            new LogRecord(
                "address", "-",
                OffsetDateTime.parse("19/May/2015:19:05:38 +0000", DATE_FORMAT),
                "GET", "/1", "HTTP", 0, 0, "referer", "agent"
            )
        );

        assertThat(actualRecords).isEqualTo(expectedRecords);
    }
}
