package edu.project3.StatisticalFunctions;

import edu.project3.LogRecord;
import edu.project3.Table;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DailyLoad implements StatisticalFunction {
    Map<String, Load> dailyLoad;

    public DailyLoad() {
        dailyLoad = new HashMap<>();
    }

    @Override
    public void process(LogRecord logRecord) {
        String localDate = logRecord.timeLocal().toLocalDate().toString();
        if (!dailyLoad.containsKey(localDate)) {
            dailyLoad.put(localDate, new Load());
        }
        Load load = dailyLoad.get(localDate);
        load.numOfRequests += 1;
        load.distribution.merge(logRecord.endpoint(), 1L, Long::sum);
    }

    @Override
    public Table getStatistic() {
        Table table = new Table("Ежедневная нагрузка", new String[] {
            "Дата",
            "Количество запросов",
            "Самый запрашиваемый ресурс"
        });

        dailyLoad.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> table.addLine(
                entry.getKey(),
                String.valueOf(entry.getValue().numOfRequests),
                "`%s`".formatted(entry.getValue().getMostLoadEndpoint())
            ));

        return table;
    }

    private static class Load {
        long numOfRequests = 0;
        Map<String, Long> distribution = new HashMap<>();

        public String getMostLoadEndpoint() {
            Map.Entry<String, Long> maxLoad = distribution
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>("-", 0L));

            return maxLoad.getKey();
        }
    }
}
