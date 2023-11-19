package edu.project3;

import static edu.project3.ParserTest.DATE_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import edu.project3.Formatters.MarkdownFormatter;
import edu.project3.StatisticalFunctions.DailyLoad;
import edu.project3.StatisticalFunctions.GeneralInfo;
import edu.project3.StatisticalFunctions.RequestedResources;
import edu.project3.StatisticalFunctions.ResponseCodes;
import edu.project3.StatisticalFunctions.StatisticalFunction;
import edu.project3.StatisticalFunctions.UsersAddresses;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

public class StatisticalFunctionsTest {
    static Stream<LogRecord> records() {
        return Stream.of(
            new LogRecord(
                "192.168.0.1", "-",
                OffsetDateTime.parse("19/May/2000:19:05:38 +0000", DATE_FORMAT),
                "GET", "/api/v2/test", "HTTP", 200, 1, "referer", "agent"
            ),
            new LogRecord(
                "192.168.0.2", "-",
                OffsetDateTime.parse("20/May/2000:19:05:38 +0000", DATE_FORMAT),
                "GET", "/endpoint1", "HTTP", 300, 5, "referer", "agent"
            ),
            new LogRecord(
                "192.168.0.2", "-",
                OffsetDateTime.parse("01/Jun/2000:19:05:38 +0000", DATE_FORMAT),
                "GET", "/api/v2/test", "HTTP", 400, 6, "referer", "agent"
            )
        );
    }

    @Test
    void usersAddressesReturnExpectedStatistic() {
        StatisticalFunction function = new UsersAddresses();
        records().forEach(function::process);

        Table actualStatistic = function.getStatistic();

        Table expectedStatistic = new Table(
            "Наибольшая нагрузка по адресам",
            new String[] {"Адрес", "Кол-во запросов"}
        );
        expectedStatistic.addLine("192.168.0.2", "2");
        expectedStatistic.addLine("192.168.0.1", "1");

        assertThat(actualStatistic).isEqualTo(expectedStatistic);
    }

    @Test
    void responseCodesReturnExpectedStatistic() {
        StatisticalFunction function = new ResponseCodes();
        records().forEach(function::process);

        Table actualStatistic = function.getStatistic();

        Table expectedStatistic = new Table("Коды ответа", new String[] {"Код", "Имя", "Количество"});
        expectedStatistic.addLine("200", "OK", "1");
        expectedStatistic.addLine("300", "Multiple Choices", "1");
        expectedStatistic.addLine("400", "Bad Request", "1");

        assertThat(actualStatistic).isEqualTo(expectedStatistic);
    }

    @Test
    void requestedResourcesReturnExpectedStatistic() {
        StatisticalFunction function = new RequestedResources();
        records().forEach(function::process);

        Table actualStatistic = function.getStatistic();

        Table expectedStatistic = new Table("Запрашиваемые ресурсы", new String[] {"Ресурс", "Количество"});
        expectedStatistic.addLine("`/api/v2/test`", "2");
        expectedStatistic.addLine("`/endpoint1`", "1");

        assertThat(actualStatistic).isEqualTo(expectedStatistic);
    }

    @Test
    void dailyLoadReturnExpectedStatistic() {
        StatisticalFunction function = new DailyLoad();
        records().forEach(function::process);

        Table actualStatistic = function.getStatistic();

        Table expectedStatistic = new Table("Ежедневная нагрузка", new String[] {
            "Дата",
            "Количество запросов",
            "Самый запрашиваемый ресурс"
        });
        expectedStatistic.addLine("2000-05-19", "1", "`/api/v2/test`");
        expectedStatistic.addLine("2000-05-20", "1", "`/endpoint1`");
        expectedStatistic.addLine("2000-06-01", "1", "`/api/v2/test`");

        assertThat(actualStatistic).isEqualTo(expectedStatistic);
    }

    @Test
    void generalInfoReturnExpectedStatistic() {
        StatisticalFunction function = new GeneralInfo();
        records().forEach(function::process);

        Table actualStatistic = function.getStatistic();

        Table expectedStatistic = new Table("Общая информация", new String[] {"Метрика", "Значение"});
        expectedStatistic.addLine("Файлы",                 "-");
        expectedStatistic.addLine("Начальная дата",        "2000-05-19");
        expectedStatistic.addLine("Конечная дата",         "2000-06-01");
        expectedStatistic.addLine("Количество запросов",   "3");
        expectedStatistic.addLine("Средний размер ответа", "4");

        assertThat(actualStatistic).isEqualTo(expectedStatistic);
    }
}
