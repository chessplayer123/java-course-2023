package edu.project3.StatisticalFunctions;

import edu.project3.LogRecord;
import edu.project3.Table;
import java.time.OffsetDateTime;
import java.util.List;

public class GeneralInfo implements StatisticalFunction {
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private long numOfRequests;
    private long sumSizeOfRequests;
    private final List<String> filenames;

    public GeneralInfo(List<String> filenames) {
        startDate = OffsetDateTime.MAX;
        endDate = OffsetDateTime.MIN;
        numOfRequests = 0;
        sumSizeOfRequests = 0;
        this.filenames = filenames;
    }

    @Override
    public void process(LogRecord logRecord) {
        if (logRecord.timeLocal().isBefore(startDate)) {
            startDate = logRecord.timeLocal();
        }
        if (logRecord.timeLocal().isAfter(endDate)) {
            endDate = logRecord.timeLocal();
        }
        sumSizeOfRequests += logRecord.size();
        ++numOfRequests;
    }

    @Override
    public Table getStatistic() {
        Table table = new Table("Общая информация", new String[] {"Метрика", "Значение"});

        table.addLine("Файлы",                  "`%s`".formatted(String.join("`,`", filenames)));
        table.addLine("Начальная дата",         startDate.toLocalDate().toString());
        table.addLine("Конечная дата",          endDate.toLocalDate().toString());
        table.addLine("Количество запросов",    String.valueOf(numOfRequests));
        table.addLine("Средний размер ответа",  String.valueOf(sumSizeOfRequests / numOfRequests));
        return table;
    }
}
