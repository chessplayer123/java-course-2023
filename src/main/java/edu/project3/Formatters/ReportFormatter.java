package edu.project3.Formatters;

import edu.project3.LogReport;
import edu.project3.Table;

public interface ReportFormatter {
    String formatTable(Table report);

    default String format(LogReport report) {
        StringBuilder builder = new StringBuilder();
        while (report.hasNext()) {
            builder.append(formatTable(report.next())).append("\n");
        }
        return builder.toString();
    }
}
