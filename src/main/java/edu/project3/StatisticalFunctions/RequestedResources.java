package edu.project3.StatisticalFunctions;

import edu.project3.LogRecord;
import edu.project3.Table;
import java.util.HashMap;
import java.util.Map;

public class RequestedResources implements StatisticalFunction {
    private final Map<String, Long> resources;

    public RequestedResources() {
        resources = new HashMap<>();
    }

    @Override
    public void process(LogRecord logRecord) {
        resources.merge(logRecord.endpoint(), 1L, Long::sum);
    }

    @Override
    public Table getStatistic() {
        Table table = new Table("Запрашиваемые ресурсы", new String[] {"Ресурс", "Количество"});

        resources.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(entry -> table.addLine(
                "`%s`".formatted(entry.getKey()),
                String.valueOf(entry.getValue())
            ));

        return table;
    }
}
