package edu.project3.StatisticalFunctions;

import edu.project3.LogRecord;
import edu.project3.Table;
import java.util.HashMap;
import java.util.Map;

public class UsersAddresses implements StatisticalFunction {
    private final Map<String, Long> addresses;
    private static final int LIMIT = 16;

    public UsersAddresses() {
        addresses = new HashMap<>();
    }

    @Override
    public void process(LogRecord logRecord) {
        addresses.merge(logRecord.remoteAddress(), 1L, Long::sum);
    }

    @Override
    public Table getStatistic() {
        Table table = new Table("Наибольшая нагрузка по адресам", new String[] {"Адрес", "Кол-во запросов"});

        addresses.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(LIMIT)
            .forEach(entry -> table.addLine(entry.getKey(), String.valueOf(entry.getValue())));

        return table;
    }
}
