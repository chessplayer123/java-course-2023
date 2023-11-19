package edu.project3;

import static edu.project3.ParserTest.DATE_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import edu.project3.Formatters.AsciiDocFormatter;
import edu.project3.Formatters.FormatterType;
import edu.project3.Formatters.MarkdownFormatter;
import edu.project3.Formatters.ReportFormatter;
import edu.project3.StatisticalFunctions.DailyLoad;
import edu.project3.StatisticalFunctions.UsersAddresses;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

public class FormattersTest {
    @Test
    void addingLineWithDifferentNumOfColsThrowsException() {
        Table table = new Table("Title", new String[] {"one column"});

        assertThatThrownBy(() -> table.addLine("one", "two", "three"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Lines must have the same size");
    }

    @Test
    void markdownFormatterReturnExpectedString() {
        MarkdownFormatter formatter = new MarkdownFormatter();

        Table table = new Table("Markdown-title", new String[] {"Keys", "Col1", "Col2"});
        table.addLine("Key1", "Value11", "Value12");
        table.addLine("Key2", "Value21", "Value22");
        table.addLine("Key3", "Value31", "Value32");

        String actualOutput = formatter.formatTable(table);
        String expectedOutput = """
            ### Markdown-title
            |Keys|Col1|Col2|
            |:-:|:-:|:-:|
            |Key1|Value11|Value12|
            |Key2|Value21|Value22|
            |Key3|Value31|Value32|
            """;

        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    void asciiDocFormatterReturnExpectedString() {
         AsciiDocFormatter formatter = new AsciiDocFormatter();

        Table table = new Table("Markdown-title", new String[] {"Keys", "Col1", "Col2"});
        table.addLine("Key1", "Value11", "Value12");
        table.addLine("Key2", "Value21", "Value22");
        table.addLine("Key3", "Value31", "Value32");

        String actualOutput = formatter.formatTable(table);
        String expectedOutput = """
            .Markdown-title
            [options="header"]
            |===
            |Keys|Col1|Col2
            |Key1|Value11|Value12
            |Key2|Value21|Value22
            |Key3|Value31|Value32
            |===
            """;

        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    static Arguments[] formatters() {
        return new Arguments[] {
            Arguments.of("adoc", new AsciiDocFormatter()),
            Arguments.of("markdown", new MarkdownFormatter())
        };
    }

    @ParameterizedTest
    @MethodSource("formatters")
    void formatterFromStringReturnsExpectedInstance(String name, ReportFormatter expectedFormatter) {
        ReportFormatter actualFormatter = FormatterType.fromString(name);

        assertThat(actualFormatter).isInstanceOf(expectedFormatter.getClass());
    }

    @Test
    void formatterFromStringThrowsExceptionOnUnknownFormatter() {
        assertThatThrownBy(() -> FormatterType.fromString("text"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Unknown formatter type");
    }

    @Test
    void logReportFormatterReturnExpectedOutput() {
        ReportFormatter formatter = new AsciiDocFormatter();

        LogReport report = LogReport.builder()
            .statistic(new DailyLoad())
            .statistic(new UsersAddresses())
            .source(Stream.of(new LogRecord(
                "address", "-",
                OffsetDateTime.parse("19/May/2015:19:05:38 +0000", DATE_FORMAT),
                "GET", "/1", "HTTP", 0, 0, "referer", "agent"
            )))
            .build();

        String actualOutput = formatter.format(report);
        String expectedOutput = """
          .Ежедневная нагрузка
          [options="header"]
          |===
          |Дата|Количество запросов|Самый запрашиваемый ресурс
          |2015-05-19|1|`/1`
          |===

          .Наибольшая нагрузка по адресам
          [options="header"]
          |===
          |Адрес|Кол-во запросов
          |address|1
          |===

          """;

        assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}
