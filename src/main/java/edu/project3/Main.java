package edu.project3;

import edu.project3.Formatters.FormatterType;
import edu.project3.Formatters.ReportFormatter;
import edu.project3.StatisticalFunctions.DailyLoad;
import edu.project3.StatisticalFunctions.GeneralInfo;
import edu.project3.StatisticalFunctions.RequestedResources;
import edu.project3.StatisticalFunctions.ResponseCodes;
import edu.project3.StatisticalFunctions.UsersAddresses;
import java.io.IOException;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"MultipleStringLiterals", "ReturnCount"})
public final class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd:HH:mm:ss Z")
        .toFormatter(Locale.ENGLISH);
    private static final ArgParser ARG_PARSER = new ArgParser()
        .requiredArgument("path")
        .optionalArgument("from",   "0001-01-01:00:00:00 +0000")
        .optionalArgument("to",     "9999-01-01:00:00:00 +0000")
        .optionalArgument("format", "markdown");

    private Main() {
    }

    public static void main(String[] args) {
        Map<String, String> parsedArgs;
        try {
            parsedArgs = ARG_PARSER.parse(args);
        } catch (ValidationException exception) {
            LOGGER.error(exception.getMessage());
            return;
        }

        OffsetDateTime from;
        OffsetDateTime to;
        ReportFormatter formatter;
        LogLoader.Logs logs;
        try {
            formatter = FormatterType.fromString(parsedArgs.get("format"));
            from = OffsetDateTime.from(DATE_FORMAT.parse(parsedArgs.get("from")));
            to = OffsetDateTime.from(DATE_FORMAT.parse(parsedArgs.get("to")));
            logs = LogLoader.load(parsedArgs.get("path"));
        } catch (Exception exception) {
            LOGGER.error("Wrong argument format: {}", exception.getMessage());
            return;
        }

        Stream<LogRecord> records;
        try {
            records = LogParser.parse(logs.stream(), from, to);
        } catch (IOException | ParseException exception) {
            LOGGER.error("Wrong file format: {}", exception.getMessage());
            return;
        }

        LogReport report = LogReport.builder()
            .statistic(new GeneralInfo(logs.filenames()))      // Required
            .statistic(new RequestedResources()) // Required
            .statistic(new ResponseCodes())      // Required
            .statistic(new UsersAddresses())     // Additional
            .statistic(new DailyLoad())          // Additional
            .source(records)
            .build();

        LOGGER.info("\n{}", formatter.format(report));
    }
}
