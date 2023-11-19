package edu.project3;

import edu.project3.StatisticalFunctions.StatisticalFunction;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class LogReport implements Iterator<Table> {
    private final Iterator<StatisticalFunction> statistics;

    private LogReport(Iterator<StatisticalFunction> statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean hasNext() {
        return statistics.hasNext();
    }

    @Override
    public Table next() {
        StatisticalFunction statistic = statistics.next();
        return statistic.getStatistic();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Stream<LogRecord> records;
        private final List<StatisticalFunction> statistics;

        private Builder() {
            records = Stream.empty();
            statistics = new LinkedList<>();
        }

        public Builder source(Stream<LogRecord> source) {
            records = source;
            return this;
        }

        public Builder statistic(StatisticalFunction function) {
            statistics.add(function);
            return this;
        }

        public LogReport build() {
            records.forEach(logRecord -> statistics.forEach(function -> function.process(logRecord)));

            return new LogReport(statistics.iterator());
        }
    }
}
