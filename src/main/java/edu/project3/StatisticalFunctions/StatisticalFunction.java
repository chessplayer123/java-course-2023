package edu.project3.StatisticalFunctions;

import edu.project3.LogRecord;
import edu.project3.Table;

public interface StatisticalFunction {
    void process(LogRecord logRecord);

    Table getStatistic();
}
